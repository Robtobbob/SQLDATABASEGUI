//Robert Flesch
// ******* NOTE: the following code will compile on nike in your project1 
//               directory with the command   javac *.java
// The code that is commented out is pseudo-code only
// Thus, you will have to make any necessary corrects to compile it.
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//import java.io.PrintWriter;
/* notes
 * type filename displays file
 * compile code on nike javac *.java
 * all java files in project 1 file 
 * when re running program delete or overwrite old stuff 
 * add readme.txt to project 
 * two things inside readme
 * 1)
 * 		a0 copy *,java to project 1 
 * 		b) issue command 
 * 2)how to compile 
 */

public class Database
{
	public static PrintStream sps;
	public static PrintStream errs;
public static void initialize(){
	 // *** declare the DBUSER commands file
    try{
	  File statfile = new File("status.txt");
	  statfile.createNewFile();
	  sps = new PrintStream(statfile);
	  // declare the error log file
	  File errfile = new File("error.txt");
	  errfile.createNewFile();
	  errs = new PrintStream(errfile);
	  //  *** create an instance of the DBCommands class
	  DBCommands DBcomm = new DBCommands();
    }
    catch(IOException e)
    {
    	errs.println("error"+e.getMessage());
    }

}
   public static void main(String[] args)
   {
	   initialize();
	   String fileName = "DBUSER.txt";
	    File DBuser = new File (fileName);
	    // ** create empty error log file
	    // ** create empty status log file
	    
		  // declare the message string 
		  String message ="";
		  // open dbuser file by creating a file object
		  Scanner infile = null;
		  try{
		   infile = new Scanner(DBuser);
		  }
		  catch(IOException e)
		  {
			  errs.println("error"+e.getMessage());
		  }
		  // declare the status log file
	      System.out.println("Made it this far...");
		   //***  Loop until EOF DBUSER file
	     
		  while (infile.hasNextLine())  {
			  String nextLine = infile.nextLine();
		      proccessSQL(nextLine);
		    }
			
			
      }//  ***  catch any IO errors must include original command as well as stauts file 
public static void proccessSQL(String nextLine) {
	//***  read and parse each command string
	
	//  ***  Finish this conditionals to test each command string
	if (nextLine.substring(0,15).equals("CREATE DATABASE")){
		  //System.out.println("creating database....");
		  // finish call to create database method pass database name
		  DBCommands.CreateDatabase(nextLine.substring(16));
		  
	}
	else if (nextLine.substring(0,13).equals("DROP DATABASE")){
		 
		DBCommands.dropDB(nextLine.substring(14));
			 // finish call to drop database method pass database name
			
	   }
	 
	else if (nextLine.substring(0,12).equals("CREATE TABLE")){
		
		String dTable = nextLine.substring(13);
		DBCommands.mkTable(dTable);
	}
		
	else if (nextLine.substring(0,10).equals("DROP TABLE")){
		String dTable = nextLine.substring(11);
		DBCommands.rmTable(dTable);
		
	  }
	else if (nextLine.substring(0,6).equals("INSERT")){
		String insert = nextLine.substring(8);
		DBCommands.insertTB(insert);
		
	}
	else if(nextLine.substring(0,13).equals("SELECT * FROM")){
		
		String info = nextLine.substring(14);
		DBCommands.selectWhere(info);
		
	}
		else if(nextLine.substring(0,11).equals("DELETE FROM")){
			String info = nextLine.substring(12);
			DBCommands.deleteWhere(info);
			
			
		}
		else if(nextLine.substring(0,6).equals("UPDATE")){
			String info = nextLine.substring(7);
			int peroid =info.indexOf('.');
			String dataBase=info.substring(0,peroid);
			int set = info.indexOf("SET");
			String tableName = info.substring(peroid+1,set);
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
	    	String replace = info.substring(qoute1+1,qoute2);
	    	int qoute3 = 0;
	    	for ( int i = qoute2+1; i < info.length(); i++ ) {
	    		char c = info.charAt(i);
		    	if( c == 8220  || c == 8221 || c == 34 ) {
		    		qoute3 = i;
		    		break;
		    	}
	    	}
	    	int qoute4 = 0;
	    	for ( int i = qoute3+1; i < info.length(); i++ ) {
	    		char c = info.charAt(i);
		    	if( c == 8220  || c == 8221 || c == 34 ) {
		    		qoute4 = i;
		    		break;
		    	}
	    	}
	    	String fInfo = info.substring(qoute3+1,qoute4);
	    	List<String> records = new ArrayList<String>();
	    	 
	    	System.out.println("\n==> Advance For Loop Example..");
	    	/*
	    	try {
	    	    BufferedReader in = new BufferedReader(new FileReader( dataBase+"/"+tableName));
	    	    String str;
	    	    while ((str = in.readLine()) != null)
	    	        if(!fInfo.equals(str)){
	    	        	 records.add(str);
	    	   			    	        }
	    	        	
	    	    in.close();*/
	}
}
     
   }


