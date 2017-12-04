package com.deploy.fullstacktask.client.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FileUploadController
{
	@GetMapping("/ingestor")
	public String ingestor()
	{
		return "ingestor";
	}
}
