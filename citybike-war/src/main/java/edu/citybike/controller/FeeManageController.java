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
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.Fee;
import edu.citybike.model.view.FeeManagerView;

@Controller
public class FeeManageController {

	private static final Logger logger = LoggerFactory.getLogger(FeeManageController.class);
	private DatabaseFacade facade;
	private List<Boolean> changedRows;

	public DatabaseFacade getFacade() {
		return facade;
	}

	public void setFacade(DatabaseFacade facade) {
		this.facade = facade;
	}
	
	@RequestMapping("/admin/feeManager")
	public ModelAndView feeManager(HttpSession session) {

		List<FeeManagerView> rows = new ArrayList<FeeManagerView>();		
		try {
			rows = getFeeList();
		} catch (PersistenceException e) {
			logger.error("Error during getting fee list: "+e.getMessage());
		}
		ModelAndView mav = new ModelAndView("feeManagePage");
		mav.addObject("feeList", rows);
		return mav;
	}
	
	@RequestMapping(value = "/admin/feeManager", method = RequestMethod.POST)
	public ModelAndView submitForm(HttpServletRequest request){
		Map map = request.getParameterMap();
		changedRows = new ArrayList<Boolean>();
		List<FeeManagerView> rows=null;
		
		try {
			rows = changeFieldsValue(map,getFeeList());
			switch(request.getParameter("submitbtn")){
			case "add":
				rows.add(0,new FeeManagerView(new Fee(), false, true));
				break;
			case "save":
				
				saveToDatabase(rows);
				rows = getFeeList();
				changedRows.clear();
				break;
			case "delete":
				rows = removeChecked(rows);
				break;
			}
		} catch (PersistenceException e) {
			logger.error(e.getMessage(), e);
		}
		ModelAndView mav = new ModelAndView("feeManagePage");
		mav.addObject("feeList", rows);
		return mav;
	}
	
	private List<FeeManagerView> changeFieldsValue(Map<String,String[]> params, List<FeeManagerView> rows){
		if(params != null){
			String[] fee = (String[]) params.get("fee");
			String[] time = (String[]) params.get("time");
			String[] selected = (String[]) params.get("selected");
			System.out.println(fee+" "+time+" "+selected);
			if(fee == null || time == null){
				return rows;
			}
			
			double cost = 0;
			int mins = 0;
			boolean isChanged = false;
			
			for(int i = rows.size(); i < fee.length; i++){
				System.out.println(rows.size()+" "+fee.length);
				rows.add(0,new FeeManagerView(new Fee(), false, true));
			}

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
				if(row.isNewRow() && row.getFee().getTime() != (mins = Integer.parseInt(time[i]))){
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
		return rows;
	}
	
	private void saveToDatabase(List<FeeManagerView> rows) throws PersistenceException{
		FeeManagerView row; 
		for(int i = 0; i < rows.size(); i++){
			row = rows.get(i);
			if(row.isNewRow()){
				row.setFee(row.getFee());
				row.setNewRow(false);
				System.out.println(row.getFee());
				facade.add(row.getFee());
			} else if(changedRows.get(i)){
				facade.update(row.getFee());
			}
		}
	}
	
	private List<FeeManagerView> removeChecked(List<FeeManagerView> rows) throws PersistenceException{
		List<FeeManagerView> notRemoved = new ArrayList<FeeManagerView>();
		for(int i = 0; i < rows.size(); i++){
			if(rows.get(i).isChecked()){
				if(!rows.get(i).isNewRow()){
					System.out.println(rows.get(i).getFee().getKey());
					facade.remove(rows.get(i).getFee());
				}
			} else {
				notRemoved.add(rows.get(i));
			}
		}
		return notRemoved;
	}
	
	private List<FeeManagerView> getFeeList() throws PersistenceException{
		List<FeeManagerView> rows =  new ArrayList<FeeManagerView>();		
		
			List<Fee> feeList = facade.getFeeList();
			
			for(Fee fee : feeList){
				rows.add(new FeeManagerView(fee));
			}
			return rows;
	}

}
