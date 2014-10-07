package edu.citybike.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import edu.citybike.charts.ChartData;
import edu.citybike.charts.ChartDataConfigurator;
import edu.citybike.charts.TimeInterval;
import edu.citybike.exceptions.PersistenceException;
import edu.citybike.model.view.CurrentUser;
import edu.citybike.model.view.StatisticPageView;


@Controller
public class StatisticController {
	private static final Logger logger = LoggerFactory.getLogger(StatisticController.class);
	private ChartDataConfigurator chartDataConfigurator;
	
	public void setChartDataConfigurator(ChartDataConfigurator chartDataConfigurator) {
		this.chartDataConfigurator = chartDataConfigurator;
	}


	@RequestMapping(value = "/statistics.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE+"; charset=utf-8", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getChartData(@RequestBody String selected, HttpSession session) throws PersistenceException {
		
		CurrentUser currentUser = ((CurrentUser) session.getAttribute("currentUser"));

		Map<Integer, ChartData> generateChartData = chartDataConfigurator.getChartData(currentUser.getAuthorities().iterator().next().getAuthority());
		System.out.println(selected);
		StatisticPageView spv = new Gson().fromJson(selected, StatisticPageView.class);
		System.out.println(spv);
		
		ChartData chartData = (ChartData)generateChartData.get(spv.getOperation());
		
		Date startDate;
		Date endDate;
		String res = "";
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd");

			startDate = sf.parse(spv.getStartInterval());
			endDate = sf.parse(spv.getEndInterval());
			
			chartData.setStartDate(startDate);
			chartData.setEndDate(endDate);
			chartData.setTimeInterval(spv.getTimeInterval());
			res = chartData.generateOutputData(currentUser.getUserKey());
			
		} catch (ParseException e) {
			logger.error("Wrong date format "+e.getMessage());
			res = "{\"p\":{\"error\": \"Zły format daty\"}}";
		}
		
		System.out.println(res);
		return res;
	}

	@RequestMapping(value = "/statistics")
	public ModelAndView showStatisticPage(HttpSession session) {
		CurrentUser currentUser = ((CurrentUser) session.getAttribute("currentUser"));
		Map<Integer, ChartData> generateChartData = chartDataConfigurator.getChartData(currentUser.getAuthorities()
				.iterator().next().getAuthority());
		Map<Integer, String> operationMap = new LinkedHashMap<>();
		Map<TimeInterval, String> timeIntervalMap = new LinkedHashMap<>();

		timeIntervalMap.put(TimeInterval.DAY, "Dzień");
		timeIntervalMap.put(TimeInterval.MONTH, "Miesiąc");
		timeIntervalMap.put(TimeInterval.YEAR, "Rok");

		Set<Integer> keySet = generateChartData.keySet();
		for (int i : keySet) {
			operationMap.put(i, ((ChartData) generateChartData.get(i)).getDescription());
		}

		ModelAndView mav = new ModelAndView("statisticPage");
		mav.addObject("timeInterval", timeIntervalMap);
		mav.addObject("operation", operationMap);
		return mav;
	}
}
