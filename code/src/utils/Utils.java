package utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;

public class Utils {
	public static Integer INF = Integer.MAX_VALUE ;
	
	public static Integer randomBetween( Integer start , Integer end ){
		Random r = new Random() ;
		return r.nextInt( end - start ) + start ;
	}
	
	public static void debug( Object obj ){
		System.out.println( obj ) ;
	}
	
	public static HashMap<String,String> parseJSON( String jsonFilename ){
		HashMap<String,String> dict = new HashMap<String,String>() ;
		// TODO: Parse json file
		return dict ;
	}
	
	public static void saveToFile( String filename , String text ){
		try{
			PrintWriter pw = new PrintWriter( new File( filename ) ) ;
			pw.println( text ) ;
			pw.close() ;
		}catch( Exception e ){
		}
	}
	
	public static String fillZeros( Integer num , Integer length ){
		String s = num + "" ;
		while( s.length() < length ) s = "0" + s ;
		return s ;
	}
}
