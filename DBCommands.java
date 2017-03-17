

// *************************
// Note: the following code will compile on nike, however you must run
//     javac *.java  from your project1 directory
// *************************
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;

public class DBCommands

{
	// DBCOmmands() constructor

	// CREATE DATABASE method
	public static boolean CreateDatabase(String dbName) {
		try {
			new File(dbName).mkdir();

			// create File object for dbName
			// call mkdir(); method to create directory

			// return true; if it works
			//Database.sps.println("Directory " + dbName + " was created succesfully ");
			ImageView pass = new ImageView("Status Icon.png");
			Log.out(pass +" Status Icon.png "+"Directory " + dbName + " was created succesfully ", false);
			return true;
		} catch (Exception e) // catch error
		{
			// return false; if it fails
			//Database.errs.println(su"Directory " + dbName + " was created unccesfully "+e.getMessage());
			Log.out("Directory " + dbName + " was created unccesfully ", true);
			return false;
		} // end catch
	} // end createDatabase

	// DROP DATABASE method
	public static boolean dropDB(String DBname) {
		try {
			File dir = new File(DBname);
			if (dir.exists()) {
				String[] entries = dir.list();
				for (String s : entries) {
					File currentFile = new File(dir.getPath(), s);
					currentFile.delete();
				}
				dir.delete();
				Log.out("Directory " + DBname + " was remvoed succesfully",false);
				return true;
			}
			else{
				Log.out("Directory " + DBname + " was not found" ,true);
				return false;
			}
			
		}

		catch (Exception e) {
			Log.out("Directory " + DBname + " was removed unsuccesfully"+e.getMessage(),true);
			return false;
		}
	}

	// CREATE TABLE method
	public static boolean mkTable(String dTable) {
		try {

			int peroid = dTable.indexOf(".");
	    	  File dir = new File(dTable.substring(0,peroid));
	    	  File table = new File (dir,dTable.substring(peroid+1));
	    	  if(table.exists()){
	    		 Log.out("Table " + dTable + " was created succesfully ",false);
	    	  }
	    	  table.createNewFile();
	    	 
			return true;
		} catch (Exception e) // catch error
		{
			// return true; if it works
			 Log.out("Table " + dTable + " was created unsuccesfully "+e.getMessage(),true);
			return false;
		}
	}

	// DROP TABLE method
	public static boolean rmTable(String dTable) {
		try {

			int peroid = dTable.indexOf(".");
	    	  File dir = new File(dTable.substring(0,peroid));
	    	  File table = new File (dir,dTable.substring(peroid+1));
	    	  if(table.exists())
	    		  table.delete();
	    	  else
	    		  Log.out("Error Icon.png"+"DROP TABLE " +dTable+ "was not deleted, file not found ",true);

			Log.out("Table " + dTable + " was removed succesfully ",false);
			return true;
		} catch (Exception e) // catch error
		{
			// return false; if it fails
			Log.out("An expection was thrown when trying to remove table "+ dTable+e.getMessage(),true);
			return false;
		}
	}

	// INSERT method
	public static boolean insertTB(String insert) {
		int qoute = 0;
    	for ( int i = 0; i < insert.length(); i++ ) {
    		char c = insert.charAt(i);
	    	if( c == 8220  || c == 8221 || c == 34 ) {
	    		qoute = i;
	    		break;
	    	}
    	}
    	String info = insert.substring(0,qoute);
    	int peroid =insert.indexOf('.');
    	String tableName = insert.substring(peroid+1);
    	String dataBase= insert.substring(qoute+7,peroid);
    	
    	try(FileWriter fw = new FileWriter(dataBase+"/"+tableName, true);
    		    BufferedWriter bw = new BufferedWriter(fw);
    		    PrintWriter out = new PrintWriter(bw))
    		{
    		    out.println(info);
    		    Log.out(tableName + " was sucessfully written to",false);
    		   return true;
    		} catch (IOException e) {
    			Log.out("INSERT " +info+ " was not correctly insterted, error" +e.getMessage(),true);
    			return false;
    		    //exception handling left as an exercise for the reader
    		}
	}

	// SELECT method (without the where)
	public static boolean selectWhere(String info) {
		int peroid =info.indexOf('.');
    	
    	String dataBase=info.substring(0,peroid);
    	int qoute1 = 0;
    	for ( int i = 0; i < info.length(); i++ ) {
    		char c = info.charAt(i);
	    	if( c == 8220  || c == 8221 || c == 34 ) {
	    		qoute1 = i;
	    		break;
	    	}
    	}
    	int qoute2 = 0;
    	for ( int i = qoute1+1; i < info.length(); i++ ) {
    		char c = info.charAt(i);
	    	if( c == 8220  || c == 8221 || c == 34 ) {
	    		qoute2 = i;
	    		break;
	    	}
    	}
    	String fInfo = info.substring(qoute1+1,qoute2);
    	
    	int where = info.indexOf("WHERE");
    	String tableName = info.substring(peroid+1,where);
    	if(where != -1){
	    	
	    	try {
	    	    BufferedReader in = new BufferedReader(new FileReader( dataBase+"/"+tableName));
	    	    String str;
	    	    while ((str = in.readLine()) != null)
	    	        if(fInfo.equals(str)){
	    	        	Log.out("SELECTED from Column1 " + "\n"+" From "+tableName+"\n" +" Where id value is "+str+ " sucessfully",false);
	    	        	 
	    	        }
	    	        	
	    	    in.close();
	    	    return true;
	    	} catch (IOException e) {
	    		Log.out("Was unable to select"+fInfo+"from "+tableName+""+e.getMessage(),true);
	    		return false;
	    	}
    	}else
    		Log.out("Was unable to select"+fInfo+"from "+tableName+" incorrect command",true);
    	return false;
	}

	

	// DELETE method (with the where)
	public static boolean deleteWhere(String info) {
		
		int peroid =info.indexOf('.');
		String dataBase=info.substring(0,peroid);
		int where = info.indexOf("WHERE");
		if (where == -1){
			String tableName = info.substring(peroid+1);
			try {
				new FileWriter(dataBase+"/"+tableName);
			} catch (IOException e) {
				
				Log.out("Error "+e.getMessage(),true);
			}
			Log.out("DELETED file sucessfully",false);
		}
		else{
    	String tableName = info.substring(peroid+1,where);
    	int qoute1 = 0;
    	for ( int i = 0; i < info.length(); i++ ) {
    		char c = info.charAt(i);
	    	if( c == 8220  || c == 8221 || c == 34 ) {
	    		qoute1 = i;
	    		break;
	    	}
    	}
    	int qoute2 = 0;
    	for ( int i = qoute1+1; i < info.length(); i++ ) {
    		char c = info.charAt(i);
	    	if( c == 8220  || c == 8221 || c == 34 ) {
	    		qoute2 = i;
	    		break;
	    	}
    	}
    	String fInfo = info.substring(qoute1+1,qoute2);
    	List<String> records = new ArrayList<String>();
    	 
    	//.out.println("\n==> Advance For Loop Example..");
    	
    	try {
    	    BufferedReader in = new BufferedReader(new FileReader( dataBase+"/"+tableName));
    	    String str;
    	    while ((str = in.readLine()) != null)
    	        if(!fInfo.equals(str)){
    	        	 records.add(str);
    	   			    	        }
    	        	
    	    in.close();
    	    BufferedWriter out = new BufferedWriter(new FileWriter( dataBase+"/"+tableName));
    	    for (String temp : records) {
    	    	if(null!=temp){
	    		 out.write(temp);
	    		 out.write(System.lineSeparator());
    	    	}
    	    	
	    		
	    	}
    	    out.close();
    	    Log.out(fInfo+" was deleted succesfully",false);
    	    return true;
    	} catch (IOException e) {
    		Log.out("Was unable to delete"+fInfo+"from "+tableName+" "+e.getMessage(),true);
    		return false;
    	}
    	
		}
		return true;
	}


	}

 // end of class DBCommands
