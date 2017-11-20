package com.deploy.fullstacktask.server.controllers;

import com.deploy.fullstacktask.server.parser.Parser;
import com.deploy.fullstacktask.server.psql.PostgresqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IngestController
{
	private Parser parser;
	private PostgresqlUtil psqlUtil;
	
	@Autowired
	public IngestController(Parser parser, PostgresqlUtil psqlUtil)
	{
		this.parser = parser;
		this.psqlUtil = psqlUtil;
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
		
		return "redirect:/ingested";
	}
}
