package utils;

import java.util.HashMap;
import java.util.Random;

public class Utils {
	public static Integer randomBetween( Integer start , Integer end ){
		Random r = new Random() ;
		return r.nextInt( end - start ) + start ;
	}
	
	public static void debug( Object obj ){
		System.out.println( obj ) ;
	}
	
	public static HashMap<String,Object> parseJSON( String jsonFilename ){
		HashMap<String,Object> dict = new HashMap<String,Object>() ;
		// TODO: Parse json file
		return dict ;
	}
}
