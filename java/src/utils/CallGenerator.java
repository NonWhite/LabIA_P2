package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.ElevatorCall;

public class CallGenerator {
	private Integer numFloors ;
	
	public Integer getNumFloors() {
		return numFloors;
	}

	public void setNumFloors(Integer numFloors) {
		this.numFloors = numFloors;
	}

	public CallGenerator( Integer numFloors ){
		this.numFloors = numFloors ;
	}
	
	public ElevatorCall generateSingleElevatorCall( Integer currentTime ){
		Integer in = Utils.randomBetween( 1 , numFloors ) ;
		Integer out = Utils.randomBetween( 1 ,  numFloors ) ;
		while( out == in ) out = Utils.randomBetween( 1 ,  numFloors ) ;
		return new ElevatorCall( in , out , currentTime ) ;
	}
	
	public List<ElevatorCall> generateElevatorCalls( Integer maxCalls , Integer currentTime ){
		Integer numCalls = Utils.randomBetween( 1 ,  maxCalls ) ;
		List<ElevatorCall> lstCalls = new ArrayList<ElevatorCall>() ;
		for( Integer i = 0 ; i < numCalls ; i++) lstCalls.add( generateSingleElevatorCall( currentTime ) ) ;
		return lstCalls ;
	}
	
	public List<List<ElevatorCall>> extractCallsFromFile( String filePath ) throws FileNotFoundException{
		List<List<ElevatorCall>>lstCalls = new ArrayList<List<ElevatorCall>>() ;
		Scanner sc = new Scanner( new File( filePath ) ) ;
		Integer totalTime = sc.nextInt() ;
		for( Integer t = 0 ; t < totalTime ; t++){
			Integer numCalls = sc.nextInt() ;
			lstCalls.add( new ArrayList<ElevatorCall>() ) ;
			for( Integer j = 0 ; j < numCalls ; j++){
				Integer in = sc.nextInt() ;
				Integer out = sc.nextInt() ;
				lstCalls.get( t ).add( new ElevatorCall( in , out , t ) ) ;
			}
		}
		sc.close() ;
		return lstCalls ;
	}
	
	public void generateAndSaveCalls( Integer maxNumCalls , Integer totalTime , String filePath ) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter( new File( filePath ) ) ;
		pw.println( totalTime ) ;
		pw.println() ;
		for( Integer t = 0 ; t < totalTime ; t++){
			List<ElevatorCall> calls = generateElevatorCalls( maxNumCalls , t ) ;
			pw.println( calls.size() ) ;
			for( ElevatorCall ec : calls ) pw.println( ec.getIncomingFloor() + " " + ec.getOutcomingFloor() ) ;
			pw.println() ;
		}
		pw.close() ;
	}
}