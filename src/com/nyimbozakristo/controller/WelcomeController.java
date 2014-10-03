package com.nyimbozakristo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/**
 * @author nelson
 * Welcome controller redirects to the relevant jsp pages, either to login or to view songs
 */

@Controller
public class WelcomeController {
	
	@RequestMapping(value = "/login.do", method = RequestMethod.GET)
	public ModelAndView printWelcome() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "Spring security allows you");
		modelAndView.setViewName("welcome");

		return modelAndView;
	}

	@RequestMapping(value = "/songs.do", method = RequestMethod.GET)
	public ModelAndView Welcome() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "Sprisssng security allows you");
		modelAndView.setViewName("songs");

		return modelAndView;
	}

}