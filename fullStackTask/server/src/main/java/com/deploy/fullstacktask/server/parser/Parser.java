package com.deploy.fullstacktask.server.parser;

import com.deploy.fullstacktask.server.storage.StorageProperties;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;

@Component
public class Parser
{
	private HSSFRow row;
	private HSSFSheet sheet;
	private int rows, cols;
	private StorageProperties properties;
	
	@Autowired
	public Parser(StorageProperties properties)
	{
		this.properties = properties;
	}
	
	public HSSFSheet parseXls(String fileName)
	{
		try
		{
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(new File(String.valueOf(Paths.get(properties.getLocation())) +
					"/" + fileName)));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			sheet = wb.getSheetAt(0);
			
			rows = sheet.getPhysicalNumberOfRows();
			cols = sheet.getRow(0).getPhysicalNumberOfCells(); // No of columns
			
			return sheet;
		}
		catch(Exception ioe)
		{
			ioe.printStackTrace();
		}
		
		return null;
	}
	
	public void constructQueries(StringBuilder valuesForClientInsertionBuilder, StringBuilder valuesForFileMetaDataInsertionBuilder,
										StringBuilder valuesForRequirementInsertionBuilder)
	{
		for(int r = 1; r < rows; r++)
		{
			row = sheet.getRow(r);
			
			if(row != null && cols == row.getPhysicalNumberOfCells())
			{
				if(row.getCell(0) != null && row.getCell(1) != null)
					valuesForClientInsertionBuilder.append("('").append(row.getCell(0).toString()+"','").
							append(row.getCell(1).toString()+"',NOW() at time zone 'UTC'),\n");
				
				if(row.getCell(4) != null && row.getCell(5) != null && row.getCell(6) != null)
					valuesForFileMetaDataInsertionBuilder.append("('").append(row.getCell(4).toString()+"','").
							append(row.getCell(5).toString()+"','").
							append(row.getCell(6).toString().split(":")[0]+"','").
							append(row.getCell(6).toString().split(":")[1]+"',NOW() at time zone 'UTC'),\n");
				
				if(row.getCell(0) != null && row.getCell(1) != null)
					valuesForRequirementInsertionBuilder.append("('").append(row.getCell(1).toString()+"','").
							append(row.getCell(3).toString()+"','").
							append(row.getCell(2).toString()+"','").
							append(row.getCell(4).toString()+"',NOW() at time zone 'UTC'),\n");
			}
		}
		
		valuesForClientInsertionBuilder.deleteCharAt(valuesForClientInsertionBuilder.length()-2);
		valuesForFileMetaDataInsertionBuilder.deleteCharAt(valuesForFileMetaDataInsertionBuilder.length()-2);
		valuesForRequirementInsertionBuilder.deleteCharAt(valuesForRequirementInsertionBuilder.length()-2);
	}
}
