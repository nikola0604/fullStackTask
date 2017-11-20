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
//			String url = "jdbc:postgresql://localhost/postgres";
//			con = DriverManager.getConnection(url, "postgres", "postgres");
			
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

//			Statement stmt = con.createStatement();

			insertCount = template.update("INSERT INTO Client (clientName, clientId, createdOn) " +
					"VALUES " + valuesForClientInsertion + " ON CONFLICT (clientId) DO NOTHING;");
			System.out.println("No. of rows inserted into Client: " + insertCount);
			
			insertCount = template.update("INSERT INTO FileMetaData (fileMetaDataId, fileName, sourceId, provider, createdOn) " +
					"VALUES " + valuesForFileMetaDataInsertion + " ON CONFLICT (fileMetaDataId) DO NOTHING;");
			System.out.println("No. of rows inserted into FileMetaData: " + insertCount);
			
			insertCount = template.update("INSERT INTO Requirement (clientId, amount, inputDate, fileMetaDataId, createdOn) " +
					"VALUES " + valuesForRequirementInsertion + "ON CONFLICT (clientId, fileMetaDataId) DO NOTHING;");
			System.out.println("No. of rows inserted into Requirement: " + insertCount);
			
			
//			insertCount = stmt.executeUpdate("INSERT INTO Client (clientName, clientId)" +
//					" VALUES " + valuesForClientInsertion + " ON CONFLICT (clientId) DO NOTHING;");
//			System.out.println("No. of rows inserted into Client: " + insertCount);
//
//			insertCount = stmt.executeUpdate("INSERT INTO FileMetaData (fileMetaDataId, fileName, sourceId, provider)" +
//					" VALUES " + valuesForFileMetaDataInsertion + " ON CONFLICT (fileMetaDataId) DO NOTHING;");
//			System.out.println("No. of rows inserted into FileMetaData: " + insertCount);
//
//			insertCount = stmt.executeUpdate("INSERT INTO Requirement (clientId, amount, inputDate, fileMetaDataId)" +
//					" VALUES " + valuesForRequirementInsertion + "ON CONFLICT (clientId, fileMetaDataId) DO NOTHING;");
//			System.out.println("No. of rows inserted into Requirement: " + insertCount);

//			stmt.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
//	public static void dispatchData()
//	{
//		try
//		{
//			Statement stmt = con.createStatement();
//			ResultSet rs = null;
//
//			String query = "SELECT DISTINCT clientName, Client.clientId, amount, inputDate, Requirement.fileMetaDataId, fileName, sourceId, provider " +
//					"FROM Requirement JOIN Client on Requirement.clientId = Client.clientId " +
//					"JOIN FileMetaData ON Requirement.fileMetaDataId = FileMetaData.filemetadataid ";
//
//			rs = stmt.executeQuery(query);
//
//			while(rs.next())
//			{
//				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " +
//						rs.getString(3) + " " + rs.getString(4) + " " +
//						rs.getString(5) + " " + rs.getString(6) + " " +
//						rs.getString(7) + " " + rs.getString(8));
//			}
//		}
//		catch (SQLException e)
//		{
//			System.out.println(e.getMessage());
//		}
//	}
	
//	public void sortedBy(String sortBy)
//	{
//		try
//		{
////			Statement stmt = con.createStatement();
////			ResultSet rs = null;
//
//			String query = "SELECT DISTINCT clientName, Client.clientId, amount, inputDate, Requirement.fileMetaDataId, fileName, sourceId, provider " +
//					"FROM Requirement JOIN Client on Requirement.clientId = Client.clientId " +
//					"JOIN FileMetaData ON Requirement.fileMetaDataId = FileMetaData.filemetadataid " +
//					"ORDER BY " + sortBy;
//
////			rs = stmt.executeQuery(query);
//		}
//		catch (SQLException e)
//		{
//			System.out.println(e.getMessage());
//		}
//	}
	
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

