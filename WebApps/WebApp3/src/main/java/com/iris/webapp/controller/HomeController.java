package com.iris.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iris.webapp.dao.StationDAO;
import com.iris.webapp.entity.Station;

@RestController
public class HomeController {

	@Autowired
	private StationDAO stationDAO;

	@RequestMapping("/getStations")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Station> home(Model model) {
		List<Station> allStations = stationDAO.getAllStations();
		return allStations;
	}

}