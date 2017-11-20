package com.deploy.fullstacktask.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.deploy.fullstacktask.server.storage.StorageService;

@Controller
public class FileUploadController
{

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService)
    {
        this.storageService = storageService;
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file)
	{
        storageService.store(file);

        return "redirect:/ingest?fileName="+file.getOriginalFilename();
    }
}
