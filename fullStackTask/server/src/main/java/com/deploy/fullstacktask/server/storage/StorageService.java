package com.deploy.fullstacktask.server.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService
{
    void init();

    void store(MultipartFile file);
	
    void deleteAll();
}
