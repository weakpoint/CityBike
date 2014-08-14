package edu.citybike.controller;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.citybike.charts.ChartData;
import edu.citybike.charts.ChartDataConfigurator;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.view.CurrentUser;

@Controller
public class StatisticController {

	private ChartDataConfigurator chartDataConfigurator;
	
	public void setChartDataConfigurator(ChartDataConfigurator chartDataConfigurator) {
		this.chartDataConfigurator = chartDataConfigurator;
	}


	@RequestMapping(value = "/statistics")
	public String showStatisticPage(HttpSession session) throws PersistenceException {
		Map<Integer, ChartData> generateChartData = chartDataConfigurator.generateChartData();

		Set<Integer> keySet = generateChartData.keySet();
		CurrentUser currentUser = ((CurrentUser) session.getAttribute("currentUser"));
		
		for(int i : keySet){
			((ChartData)generateChartData.get(i)).setStartDate(new Date(System.currentTimeMillis()-3600000));
			((ChartData)generateChartData.get(i)).setEndDate(new Date(System.currentTimeMillis()));
			System.out.println(((ChartData)generateChartData.get(i)).getDescription());
			System.out.println(((ChartData)generateChartData.get(i)).generateOutputData(currentUser.getUserKey()));
		}
		return "statisticPage";
	}
}
