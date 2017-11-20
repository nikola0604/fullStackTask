package com.deploy.fullstacktask.server.services;

import com.deploy.fullstacktask.server.model.Records;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService
{
	@Autowired
	JdbcTemplate template;
	
	public List<String> generateFilterByClients()
	{
		String sql = "SELECT DISTINCT clientName " +
				"FROM Client;";
		
		RowMapper<String> rm = (resultSet, i) ->
		{
			String client = resultSet.getString(1);
			
			return client;
		};
		
		return template.query(sql, rm);
	}
	
	public List<Records> findAll()
	{
		String sql = "SELECT DISTINCT clientName, Client.clientId, inputDate, amount, FileMetaData.fileMetaDataId, fileName, sourceId, provider " +
				"FROM Requirement JOIN Client on Requirement.clientId = Client.clientId " +
				"JOIN FileMetaData ON Requirement.fileMetaDataId = FileMetaData.filemetadataid ";
		
		RowMapper<Records> rm = (resultSet, i) ->
		{
			Records record = new Records(resultSet.getString(1), resultSet.getString(2),
					resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
					resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
			
			return record;
		};
		
		return template.query(sql, rm);
	}
	
	public List<Records> findAllSortedBy(String sortBy)
	{
		String sql = "SELECT DISTINCT clientName, Client.clientId, inputDate, amount, FileMetaData.fileMetaDataId, fileName, sourceId, provider " +
				"FROM Requirement JOIN Client on Requirement.clientId = Client.clientId " +
				"JOIN FileMetaData ON Requirement.fileMetaDataId = FileMetaData.filemetadataid " +
				"ORDER BY " + sortBy;
		
		RowMapper<Records> rm = (resultSet, i) ->
		{
			Records record = new Records(resultSet.getString(1), resultSet.getString(2),
					resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
					resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
			
			return record;
		};
		
		return template.query(sql, rm);
	}
	
	public List<Records> findAllFilteredBy(String filterBy)
	{
		filterBy = filterBy.replaceAll(",", "','");
		
		String sql = "SELECT DISTINCT clientName, Client.clientId, inputDate, amount, FileMetaData.fileMetaDataId, fileName, sourceId, provider " +
				"FROM Requirement JOIN Client on Requirement.clientId = Client.clientId " +
				"JOIN FileMetaData ON Requirement.fileMetaDataId = FileMetaData.filemetadataid " +
				"WHERE clientName IN (\'" + filterBy + "\')";
		
		System.out.println(sql);
		RowMapper<Records> rm = (resultSet, i) ->
		{
			Records record = new Records(resultSet.getString(1), resultSet.getString(2),
					resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5),
					resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
			
			return record;
		};
		
		return template.query(sql, rm);
	}
}