

import java.util.Vector;

public class Log {
private static Vector<String> slist = new Vector<String>();
private static Vector<String> elist = new Vector<String>();
public static void out(String message,boolean isError ){
	if(isError)
	elist.add(message);
	else
	slist.add(message);
	
}
public static Vector<String> getErrors(){
	return elist;
}
public static Vector<String> getSuccess(){
	return slist;
}
public static void clearlists(){
	slist = new Vector<String>();
	elist = new Vector<String>();
}
}
