package model;
import java.io.FileNotFoundException ;
import java.util.ArrayList ;
import java.util.Collections ;
import java.util.HashMap;
import java.util.List ;

import utils.CallGenerator ;
import static utils.Utils.debug ;
import static utils.Utils.randomBetween ;

public class Simulation {
	/* ======== START GENERIC PARAMETERS ======== */
	 // totalTime * maxNumCallsPerMinute < elevatorsCapacity * numElevators
	private Double alpha ; // = 0.9 ;
	private Integer numIterations ; //= 1000 ;
	private Integer maxNumCallsPerMinute ; //= 100 ;
	private Integer elevatorsCapacity ; // = 10 ;
	
	public static Boolean COST_BY_DISTANCE ; // = true ;
	public static Boolean COST_BY_WAITING_TIME ; // = true ;
	/* ======== END GENERIC PARAMETERS ======== */
	
	private Building building ;
	private CallGenerator generator ;
	private String inputFilename ;
	private String outputFilename ;
	private Integer totalTime ;
		
	public Simulation( HashMap<String,Object> params ){
//		this.alpha = params.get( "alpha" ) ;
//		this.numIterations = params.get( "numIterations" ) ;
//		this.maxNumCallsPerMinute = params.get( "maxNumCallsPerMinute" ) ;
//		this.elevatorsCapacity = params.get( "elevatorsCapacity" ) ;
//		this.COST_BY_DISTANCE = params.get( "costByDistance" ) ;
//		this.COST_BY_WAITING_TIME = params.get( "costByWaitingTime" ) ;
//		this.inputFilename = params.get( "inputFilename" ) ;
//		this.outputFilename = params.get( "outputFilename" ) ;
//		this.building = new Building( params.get( "numElevators" ) , params.get( "numFloors" ) , this.elevatorsCapacity ) ;
//		this.generator = new CallGenerator( params.get( "numFloors" ) ) ;
	}
	
	public Simulation( Integer numElevators , Integer numFloors , String callsFile ){
		this.building = new Building( numElevators , numFloors , elevatorsCapacity ) ;
		this.generator = new CallGenerator( numFloors ) ;
		this.inputFilename = callsFile ;
	}
	
	public Simulation( Integer numElevators , Integer numFloors , Integer totalTime ){
		this.building = new Building( numElevators , numFloors , elevatorsCapacity ) ;
		this.generator = new CallGenerator( numFloors ) ;
		this.totalTime = totalTime ;
		this.inputFilename = null ;
	}
	
	public void simulate() throws FileNotFoundException{
		List<List<ElevatorCall>> calls = new ArrayList<List<ElevatorCall>>() ;
		if( inputFilename == null ) for( Integer t = 0 ; t < totalTime ; t++){
			calls.add( generator.generateElevatorCalls( maxNumCallsPerMinute , t ) ) ;
		}else{
			calls = generator.extractCallsFromFile( inputFilename ) ;
			totalTime = calls.size() ;
		}
		simulate( calls ) ;
	}
	
	private void simulate( List<List<ElevatorCall>> calls ){
		Building currentState = new Building( this.building ) ;
		Boolean hasSolution = true ;
		for( Integer t = 0 ; t < totalTime ; t++){
			List<ElevatorCall> lstCalls = calls.get( t ) ;
			currentState = heuristicFunction( currentState , lstCalls ) ;
			if( currentState == null ){
				hasSolution = false ;
				break ;
			}
			currentState.moveElevators() ;
		}
		saveResult( hasSolution ? currentState : null ) ;
	}
	
	private Building heuristicFunction( Building initialState , List<ElevatorCall> lstCalls ){
		Building currentState = new Building( initialState ) ;
		for( Integer i = 0 ; i < lstCalls.size() ; i++){
			Building bestSol = null ;
			ElevatorCall call = lstCalls.get( i ) ;
			for( Integer j = 0 ; j < numIterations ; j++){
				List<Building> options = new ArrayList<Building>() ;
				for( Integer k = 0 ; k < currentState.getNumElevators() ; k++){
					Elevator elevator = currentState.getElevators().get( k ) ;
					if( elevator.getCurrentCapacity() == 0 ) continue ;
					Building currentSol = new Building( currentState ) ;
					currentSol.takeNewCall( k ,  call ) ;
					options.add( currentSol ) ;
				}
				if( options.isEmpty() ) continue ;
				Collections.sort( options ) ;
				Integer newLength = Integer.parseInt( Math.round( options.size() * alpha + 0.5 ) + "" ) ;
				options = options.subList( 0 ,  newLength ) ;
				Integer selectedIndex = randomBetween( 0 ,  newLength ) ;
				Building selection = new Building( options.get( selectedIndex ) ) ;
				if( bestSol == null || selection.isBetterThan( bestSol ) ){
//					debug( "Mejor solución encontrada, anterior = " + ( bestSol == null ? "NULL" : bestSol.getHeuristicValue() ) + " nuevo = " + selection.getHeuristicValue() ) ;
					bestSol = new Building( selection ) ;
				}
			}
			currentState = new Building( bestSol ) ;
		}
//		for( String inst : currentState.getInstructions() ) debug( inst ) ;
		return currentState ;
	}
	
	public void reset(){
		this.building = new Building( this.building.getNumElevators() , this.building.getNumFloors() , elevatorsCapacity ) ;
		this.generator = new CallGenerator( this.building.getNumFloors() ) ;
	}
	
	private void saveResult( Building state ){
		// TODO: implementar guardar el resultado en un archivo
		debug( outputFilename ) ;
		if( state != null ) debug( "SOLUCION FINAL:\n" + state ) ;
		else debug( "NO SOLUTION" ) ;
	}
}

