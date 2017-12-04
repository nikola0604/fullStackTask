package com.deploy.fullstacktask.server.controllers;

import com.deploy.fullstacktask.server.services.RecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class DatabaseController
{
	private RecordService service;
	private RestTemplate restTemplate;
	private StringBuilder filterBy = new StringBuilder();
	private URI uri;
	
	@Autowired DatabaseController(RecordService service, RestTemplate restTemplate)
	{
		this.service = service;
		this.restTemplate = restTemplate;
	}
	
	@RequestMapping(value = "/retrieve")
	public String dataRetrieval(@Param("filterBy") String filterBy, @Param("sortBy") String sortBy, ModelMap mp)
	{
		if(filterBy!=null)
			if(!filterBy.isEmpty())
			{
				this.filterBy.delete(0, this.filterBy.length()).append(filterBy);
			}
		
		mp.addAttribute("records", service.findAll(sortBy, this.filterBy.toString()));
		mp.addAttribute("clients", service.generateFilterByClients());
		
		try
		{
			uri = new URI("http://localhost:8080/ingest");
		}
		catch (URISyntaxException ue)
		{
			ue.printStackTrace();
		}

		restTemplate.postForObject(uri, mp, HttpStatus.class);

		return "redirect:http://localhost:8080/ingested";
	}
}
