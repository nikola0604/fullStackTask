package com.deploy.fullstacktask.server.psql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class PostgresqlUtil
{
	JdbcTemplate template;
	DataSource driver;

	@Autowired
	public PostgresqlUtil(JdbcTemplate template, DataSource driver)
	{
		this.template = template;
		this.driver = driver;
	}
	
	static private Connection con = null;
	
	static
	{
		try
		{
			Class.forName("org.postgresql.Driver");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void connect()
	{
		try
		{
			con = driver.getConnection();
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void importData(String valuesForClientInsertion,String valuesForFileMetaDataInsertion, String valuesForRequirementInsertion)
	{
		try
		{
			int insertCount;

			insertCount = template.update("INSERT INTO Client (clientName, clientId, createdOn) " +
					"VALUES " + valuesForClientInsertion + " ON CONFLICT (clientId) DO NOTHING;");
			System.out.println("No. of rows inserted into Client: " + insertCount);
			
			insertCount = template.update("INSERT INTO FileMetaData (fileMetaDataId, fileName, sourceId, provider, createdOn) " +
					"VALUES " + valuesForFileMetaDataInsertion + " ON CONFLICT (fileMetaDataId) DO NOTHING;");
			System.out.println("No. of rows inserted into FileMetaData: " + insertCount);
			
			insertCount = template.update("INSERT INTO Requirement (clientId, amount, inputDate, fileMetaDataId, createdOn) " +
					"VALUES " + valuesForRequirementInsertion + "ON CONFLICT (clientId, fileMetaDataId) DO NOTHING;");
			System.out.println("No. of rows inserted into Requirement: " + insertCount);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public void disconnect()
	{
		try
		{
			con.close();
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
}

