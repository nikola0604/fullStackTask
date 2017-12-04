package com.deploy.fullstacktask.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController
{
	@Autowired
	ModelMap modelMap;
	
	@RequestMapping(value = "/ingest")
	@ResponseBody
	public HttpStatus ingestRetrieved(@RequestBody ModelMap mp)
	{
		modelMap.putAll(mp);
		
		return HttpStatus.OK;
	}
	
	@RequestMapping(value = "/ingested")
	public ModelAndView displayIngested()
	{
		ModelAndView mav = new ModelAndView("ingested", modelMap);
		
		return mav;
	}
}
