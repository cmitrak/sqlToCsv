package com.jciapp.sqltocsv;

import java.io.FileWriter;
import java.sql.DriverManager;

import oracle.jdbc.driver.*;
import oracle.jdbc.OracleConnection;

import java.sql.*;
import java.util.Date;

import au.com.bytecode.opencsv.CSVWriter;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        dumpToCsv("C:\\JCI_APPS\\LAWSON_SIEBEL\\CSV_DUMPS\\PARENT_CHILD_RELATIONSHIP_FOR_SRVREQ_ASSET_CUSTOMER_SRVAGGRMNT_PARTS_ETC.txt");
    	
    }
    
    public static void dumpToCsv(String FileName)
     {
    	
    	try {
    		
    			Class.forName("oracle.jdbc.driver.OracleDriver");
    			//jdbc:oracle:thin:@c201u069.cg.na.jci.com:1522:Lawson
    			//Oracle Database 11g Enterprise Edition Release 11.2.0.3.0 - 64bit Production
    			String JDBC_URL = "jdbc:oracle:thin:@c201u069.cg.na.jci.com:1522:Lawson";
    			
    			Connection jdbcOraConn = DriverManager.getConnection(JDBC_URL, "Lawson", "Lawson");
    			System.out.println("Connection Successful !");
    			Statement selectStatement = jdbcOraConn.createStatement();
    			selectStatement.setFetchSize(5000);
    			long startTime= System.currentTimeMillis();
    	    	
    	    	
    			ResultSet dbResultsSet = selectStatement.executeQuery("SELECT  T3.NAME AS PARENT_TABLE, 'ROW_ID' AS PARENT_COLUMN, T4.NAME AS CHILD_TABLE, T1.NAME AS CHILD_COLUMN FROM SIEBEL.S_COLUMN T1 INNER JOIN SIEBEL.S_TABLE T3 ON T1.FKEY_TBL_ID = T3.ROW_ID INNER JOIN SIEBEL.S_TABLE T4 ON T1.TBL_ID = T4.ROW_ID AND T4.NAME IN ('S_DOC_AGREE','S_ENTLMNT','S_ASSET','S_SRV_REQ','S_EVT_ACT','S_ORG_EXT','S_ADDR_PER','S_CONTACT','S_ORDPART_MVMT','S_ACTPART_MVMT')");
    																	
    			
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
