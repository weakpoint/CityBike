package edu.citybike.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.citybike.database.DatabaseFacade;
import edu.citybike.database.exception.PersistenceException;
import edu.citybike.model.Fee;
import edu.citybike.model.view.FeeManagerView;

@Controller
public class FeeManageController {

	private static final Logger logger = LoggerFactory.getLogger(FeeManageController.class);
	private DatabaseFacade facade;
	private List<FeeManagerView> rows;
	private List<Boolean> changedRows;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@RequestMapping("/feeManager")
	public ModelAndView feeManager(HttpSession session) {
		String rentalNetworkCode = (String)session.getAttribute("rentalNetworkCode");
		rows = new ArrayList<FeeManagerView>();		
		try {
			List<Fee> feeList = facade.getFeeList(rentalNetworkCode);
			
			for(Fee fee : feeList){
				rows.add(new FeeManagerView(fee));
			}
		} catch (PersistenceException e) {
			logger.error("Error during getting fee list: "+e.getMessage());
		}
		ModelAndView mav = new ModelAndView("feeManagePage");
		mav.addObject("feeList", rows);
		return mav;
	}
	
	
	
	@RequestMapping(value = "/feeManager", method = RequestMethod.POST)
	public ModelAndView submitForm(HttpServletRequest request){
		Map map = request.getParameterMap();
		changedRows = new ArrayList<Boolean>();
		System.out.println(map);
		changeFieldsValue(map);
		try {
		switch(request.getParameter("submitbtn")){
		case "add":
			rows.add(new FeeManagerView(new Fee(), false, true));
			break;
		case "save":
			System.out.println("Save");
			saveToDatabase();
			changedRows.clear();
			break;
		case "delete":
			System.out.println("Do usuniecia");
			removeChecked();
			break;
		}
		} catch (PersistenceException e) {
			logger.error(e.getMessage(), e);
		}
		ModelAndView mav = new ModelAndView("feeManagePage");
		mav.addObject("feeList", rows);
		return mav;
	}
	
	private void changeFieldsValue(Map<String,String[]> params){
		if(params != null){
			String[] fee = (String[]) params.get("fee");
			String[] time = (String[]) params.get("time");
			String[] selected = (String[]) params.get("selected");
			double cost = 0;
			int mins = 0;
			boolean isChanged = false;
			
			FeeManagerView row;
			for(int i = 0; i < rows.size(); i++){
				row = rows.get(i);
				isChanged = false;
				//fee
				if(row.getFee().getFee() != (cost = Double.parseDouble(fee[i]))){
					row.getFee().setFee(cost);
					isChanged = true;
				}
				//time
				if(row.getFee().getTime() != (mins = Integer.parseInt(time[i]))){
					row.getFee().setTime(mins);
					isChanged = true;
				}
				row.setChecked(false);
				changedRows.add(isChanged);
			}
			if(selected != null){
			for(int i = 0 ; i < selected.length; i++){
				rows.get(Integer.parseInt(selected[i])).setChecked(true);
			}
			}
		}
	}
	
	private void saveToDatabase() throws PersistenceException{
		FeeManagerView row; 
		
		for(int i = 0; i < rows.size(); i++){
			row = rows.get(i);
			if(row.isNewRow()){
				//System.out.println("row to be saved (new): "+i);
				row.setFee((Fee)facade.add(row.getFee()));
				row.setNewRow(false);
			} else if(changedRows.get(i)){
				//System.out.println("row to be saved: "+i);
				facade.update(row.getFee());
			}
		}
	}
	
	private void removeChecked(){
		FeeManagerView row; 
		List<Integer> toRemove = new ArrayList<Integer>();
		for(int i = 0; i < rows.size(); i++){
			row = rows.get(i);
			if(row.isChecked()){
				toRemove.add(i);
			}
		}
		int d = 0;
		System.out.println("usu");
		for(int i : toRemove){
			rows.remove(i-d);
			changedRows.remove(i-d);
			d++;
		}
	}

}
