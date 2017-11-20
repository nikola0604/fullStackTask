package com.deploy.fullstacktask.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FileUploadController
{
	@GetMapping("/")
	public String ingestor()
	{
		return "ingestor";
	}
}
