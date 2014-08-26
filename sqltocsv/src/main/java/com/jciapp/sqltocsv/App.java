package com.jciapp.sqltocsv;

import java.io.FileWriter;
import java.sql.DriverManager;

import oracle.jdbc.driver.*;
import oracle.jdbc.OracleConnection;

import java.sql.*;
import java.util.Date;

import au.com.bytecode.opencsv.CSVWriter;


/**
 * Dummy File to test GitHub Commit !!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        dumpToFile("PARENT_CHILD_RELATIONSHIP_FOR_SRVREQ_ASSET_CUSTOMER_SRVAGGRMNT_PARTS_ETC.txt");
    	
    }
    
    public static void dumpToFile(String FileName)
     {
    	
    	try {
    		
    			Class.forName("oracle.jdbc.driver.OracleDriver");
    			String JDBC_URL = "dummy";
    			Connection jdbcOraConn = DriverManager.getConnection(JDBC_URL, "pass user id ", "pass password ");
    			System.out.println("Connection Successful !");
    			Statement selectStatement = jdbcOraConn.createStatement();
    			selectStatement.setFetchSize(5000);
    			long startTime= System.currentTimeMillis();
    	    	
    	    	
    			ResultSet dbResultsSet = selectStatement.executeQuery("SELECT DISTINCT  T3.NAME AS PARENT_TABLE, 'ROW_ID' AS PARENT_COLUMN, T4.NAME AS CHILD_TABLE, T1.NAME AS CHILD_COLUMN FROM SIEBEL.S_COLUMN T1 INNER JOIN SIEBEL.S_TABLE T3 ON T1.FKEY_TBL_ID = T3.ROW_ID INNER JOIN SIEBEL.S_TABLE T4 ON T1.TBL_ID = T4.ROW_ID AND T4.NAME IN ('S_DOC_AGREE','S_ENTLMNT','S_ASSET','S_SRV_REQ','S_EVT_ACT','S_ORG_EXT','S_ADDR_PER','S_CONTACT','S_ORDPART_MVMT','S_ACTPART_MVMT')");
    																	
    			
    			long endTime= System.currentTimeMillis();
    	    	
    			
    			long execTime = endTime - startTime;
    			
    			System.out.println("Total time to exec in millisec : " + execTime + "ms");
    			
    			long startCsvWrite = System.currentTimeMillis();
    			
    			CSVWriter writer = new CSVWriter(new FileWriter(FileName), '\t');
    			
    			writer.writeAll(dbResultsSet, true);
    			
    			long endCsvWrite = System.currentTimeMillis();
    			
    			
    			long writeTime = endCsvWrite - startCsvWrite;
    			
    			System.out.println("Total time to exec in millisec : " + writeTime + "ms");
    			
    			selectStatement.close();
    	}
    	catch (Exception e)
    	{
    		System.out.println("Connection Failed !");
			e.printStackTrace(System.out);
    	}
    	
      }
    
    
}
