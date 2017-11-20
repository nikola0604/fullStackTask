package com.deploy.fullstacktask.server.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService
{
    private final Path rootLocation;
	
	@Autowired
	public FileSystemStorageService(StorageProperties properties)
	{
		this.rootLocation = Paths.get(properties.getLocation());
	}

    @Override
    public void store(MultipartFile file)
	{
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try
		{
            if (file.isEmpty())
            {
                throw new StorageException("Failed to store empty file " + filename);
            }
            
            Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e)
		{
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public void deleteAll()
	{
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init()
	{
        try
		{
            Files.createDirectories(rootLocation);
        }
        catch (IOException e)
		{
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
