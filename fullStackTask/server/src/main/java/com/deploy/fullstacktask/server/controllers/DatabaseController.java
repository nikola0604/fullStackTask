package com.deploy.fullstacktask.server.controllers;

import com.deploy.fullstacktask.server.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class DatabaseController
{
	@Autowired
	RecordService service;
	
	@RequestMapping(value = "/ingested", method = RequestMethod.GET)
	public String displayIngested(@Param("filterBy") String filterBy, Model md)
	{
		md.addAttribute("records", service.findAll());
		md.addAttribute("clients", service.generateFilterByClients());
		return "ingested";
	}
	
	@RequestMapping(value = "/ingested/sorted", method = RequestMethod.GET)
	public String displayIngestedSortedBy(@RequestParam("sortBy") String sortBy, Model md)
	{
		md.addAttribute("records", service.findAllSortedBy(sortBy));
		md.addAttribute("clients", service.generateFilterByClients());
		return "ingested";
	}
	
	@RequestMapping(value = "/ingested/filtered", method = RequestMethod.GET)
	public String displayIngestedFilteredBy(@RequestParam("filterBy") String filterBy, Model md)
	{
		md.addAttribute("records", service.findAllFilteredBy(filterBy));
		
		return "ingestedFiltered";
	}
}
