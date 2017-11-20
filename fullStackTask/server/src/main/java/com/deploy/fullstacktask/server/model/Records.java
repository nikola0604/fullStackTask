package com.deploy.fullstacktask.server.model;

public class Records
{
	
	private String clientName;
	private String clientId;
	private String inputDate;
	private float amount;
	private String fileMetaDataId;
	private String fileName;
	private String sourceId;
	private String provider;
	
	public Records(String clientName, String clientId, String inputDate, int amount, String fileMetaDataId, String fileName,
				   String sourceId, String provider)
	{
		this.clientName = clientName;
		this.clientId = clientId;
		this.inputDate = inputDate;
		this.amount = amount;
		this.fileMetaDataId = fileMetaDataId ;
		this.fileName = fileName;
		this.sourceId = sourceId;
		this.provider = provider;
	}
	
	public String getClientName()
	{
		return clientName;
	}
	
	public String getClientId()
	{
		return clientId;
	}
	
	public String getInputDate()
	{
		return inputDate;
	}
	
	public float getAmount()
	{
		return amount;
	}
	
	public String getFileMetaDataId()
	{
		return fileMetaDataId;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public String getSourceId()
	{
		return sourceId;
	}
	
	public String getProvider()
	{
		return provider;
	}
}
