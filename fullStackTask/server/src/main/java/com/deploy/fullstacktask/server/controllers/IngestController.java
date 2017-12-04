package com.deploy.fullstacktask.server.controllers;

import com.deploy.fullstacktask.server.parser.Parser;
import com.deploy.fullstacktask.server.psql.PostgresqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Controller
public class IngestController
{
	private Parser parser;
	private PostgresqlUtil psqlUtil;
	private RestTemplate restTemplate;
	private URI uri;
	
	@Autowired
	public IngestController(Parser parser, PostgresqlUtil psqlUtil, RestTemplate restTemplate)
	{
		this.parser = parser;
		this.psqlUtil = psqlUtil;
		this.restTemplate = restTemplate;
	}
	
	@RequestMapping("/ingest")
	public String ingest(@RequestParam("fileName") String fileName)
	{
		StringBuilder valuesForClientInsertionBuilder = new StringBuilder();
		StringBuilder valuesForFileMetaDataInsertionBuilder = new StringBuilder();
		StringBuilder valuesForRequirementInsertionBuilder = new StringBuilder();
		
		parser.parseXls(fileName);
		parser.constructQueries(valuesForClientInsertionBuilder, valuesForFileMetaDataInsertionBuilder, valuesForRequirementInsertionBuilder);
		
		psqlUtil.connect();
		psqlUtil.importData(valuesForClientInsertionBuilder.toString(), valuesForFileMetaDataInsertionBuilder.toString(),
				valuesForRequirementInsertionBuilder.toString());
	
		return "redirect:/retrieve";
	}
}
