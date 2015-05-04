package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import experiment.Parameters;

public class Utils {
	public static Integer INF = Integer.MAX_VALUE ;
	
	public static Integer randomBetween( Integer start , Integer end ){
		Random r = new Random() ;
		return r.nextInt( end - start ) + start ;
	}
	
	public static void debug( Object obj ){
		System.out.println( obj ) ;
	}
	
	public static Parameters parseJsonParameters( String jsonFilename ){
		ObjectMapper mapper = new ObjectMapper() ;
		Parameters params = null ;
		try{
			params = mapper.readValue( new File( jsonFilename ) , Parameters.class );
		}catch( IOException e ){
			e.printStackTrace() ;
		} 
		return params ;
	}
	
	public static void saveParams( String filename , Parameters params ){
		ObjectMapper mapper = new ObjectMapper() ;
		mapper.configure( SerializationFeature.INDENT_OUTPUT , true ) ;
		try {
			mapper.writeValue( new File( filename ) , params ) ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String fillZeros( Integer num , Integer length ){
		String s = num + "" ;
		while( s.length() < length ) s = "0" + s ;
		return s ;
	}
	
	public static void copyFileTo( String inFilePath , String outFilePath ){
		try{
			File inFile = new File( inFilePath ) ;
			File outFile = new File( outFilePath ) ;
			Files.copy( inFile.toPath() ,  outFile.toPath() , StandardCopyOption.REPLACE_EXISTING ) ;
		}catch( Exception e ){
			e.printStackTrace();
		}
	}
}