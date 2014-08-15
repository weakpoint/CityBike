package edu.citybike.controller;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

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

	private ChartDataConfigurator chartDataConfigurator;
	
	public void setChartDataConfigurator(ChartDataConfigurator chartDataConfigurator) {
		this.chartDataConfigurator = chartDataConfigurator;
	}


	@RequestMapping(value = "/statistics.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getChartData(@RequestBody String selected, HttpSession session) throws PersistenceException {
		
		CurrentUser currentUser = ((CurrentUser) session.getAttribute("currentUser"));
		System.out.println(currentUser);
		Map<Integer, ChartData> generateChartData = chartDataConfigurator.getChartData(currentUser.getAuthorities().iterator().next().getAuthority());
		System.out.println(selected);
		StatisticPageView spv = new Gson().fromJson(selected, StatisticPageView.class);
		System.out.println(spv);
		
		ChartData chartData = (ChartData)generateChartData.get(spv.getOperation());
		chartData.setStartDate(new GregorianCalendar(2013,0,30).getTime());
		chartData.setEndDate(new Date(System.currentTimeMillis()));
		String res = chartData.generateOutputData(currentUser.getUserKey());
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
		// Początek przedziału: <input type="date" name="startInterval"><br />
		// Koniec przedziału: <input type="date" name="endInterval"><br />
		ModelAndView mav = new ModelAndView("statisticPage");
		mav.addObject("timeInterval", timeIntervalMap);
		mav.addObject("operation", operationMap);
		return mav;
	}
}
