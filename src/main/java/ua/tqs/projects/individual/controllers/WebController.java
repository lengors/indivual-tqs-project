package ua.tqs.projects.individual.controllers;

import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class WebController
{
	@Autowired
	private MeteorologyController meteorologyController;
	
	@GetMapping(value = "/")
	public String index(Model model)
	{
		model.addAttribute("places", meteorologyController.getCities().get("data"));
		return "index";
	}
}
