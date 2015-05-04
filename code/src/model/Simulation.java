package model;
import java.io.BufferedWriter;
import java.io.FileNotFoundException ;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList ;
import java.util.Collections ;
import java.util.List ;

import experiment.Parameters;
import utils.CallGenerator ;
import static utils.Utils.debug ;
import static utils.Utils.randomBetween ;

@SuppressWarnings( "unused" )
public class Simulation {
	/* ======== START GENERIC PARAMETERS ======== */
	 // totalTime * maxNumCallsPerMinute < elevatorsCapacity * numElevators
	private Double alpha ;
	private Integer numIterations ;
	private Integer maxNumCallsPerMinute ;
	private Integer elevatorsCapacity ;
	
	public static Boolean COST_BY_DISTANCE ;
	public static Boolean COST_BY_WAITING_TIME ;
	/* ======== END GENERIC PARAMETERS ======== */
	
	private Building building ;
	private CallGenerator generator ;
	private String inputFilename ;
	private String outputFilename ;
	private Integer totalTime ;
	private Long executionTime ;

	@SuppressWarnings( "static-access" )
	public Simulation( Parameters params ){
		this.alpha = params.alpha ;
		this.numIterations = params.numIterations ;
		this.maxNumCallsPerMinute = params.maxNumCallsPerMinute ;
		this.elevatorsCapacity = params.elevatorsCapacity ;
		this.COST_BY_DISTANCE = params.costByDistance ;
		this.COST_BY_WAITING_TIME = params.costByWaitingTime ;
		this.inputFilename = params.inputFilename ;
		this.outputFilename = params.outputFilename ;
		this.building = new Building( params.numElevators , params.numFloors , this.elevatorsCapacity ) ;
		this.generator = new CallGenerator( params.numFloors ) ;
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
			calls.add( generator.generateElevatorCalls( t ) ) ;
		}else{
			calls = generator.extractCallsFromFile( inputFilename ) ;
			totalTime = calls.size() ;
		}
		simulate( calls ) ;
	}
	
	private void simulate( List<List<ElevatorCall>> calls ){
		Long start = System.currentTimeMillis() ;
		Building currentState = new Building( this.building ) ;
		Boolean hasSolution = true ;
		for( Integer t = 0 ; t < totalTime ; t++){
			List<ElevatorCall> lstCalls = calls.get( t ) ;
			currentState = heuristicFunction( currentState , lstCalls ) ;
			if( currentState == null ){
				hasSolution = false ;
				break ;
			}
			debug( "TIME = " + t ) ;
			debug( currentState ) ;
			currentState.moveElevators() ;
		}
		Long end = System.currentTimeMillis() ;
		this.executionTime = end - start ;
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
				options = filterList( options ) ;
				Integer selectedIndex = randomBetween( 0 ,  options.size() ) ;
				Building selection = new Building( options.get( selectedIndex ) ) ;
				if( bestSol == null || selection.isBetterThan( bestSol ) ) bestSol = new Building( selection ) ;
			}
			currentState = new Building( bestSol ) ;
		}
		return currentState ;
	}
	
	private List<Building> filterList( List<Building> options ){
		Integer maxi = -1 , mini = Integer.MAX_VALUE ;
		for( Building b : options ){
			maxi = Math.max( maxi ,  b.getHeuristicValue() ) ;
			mini = Math.min( mini ,  b.getHeuristicValue() ) ;
		}
		Integer top = ( int )( mini + alpha * ( maxi - mini ) ) ;
		List<Building> lst = new ArrayList<Building>() ;
		for( Building b : options ) if( b.getHeuristicValue() <= top ) lst.add( b ) ;
		return lst ;
	}
	
	public void reset(){
		this.building = new Building( this.building.getNumElevators() , this.building.getNumFloors() , elevatorsCapacity ) ;
		this.generator = new CallGenerator( this.building.getNumFloors() ) ;
	}
	
	private void saveResult( Building state ){
		PrintWriter pw = null ; 
		try{
			debug( outputFilename ) ;
			pw = new PrintWriter( new BufferedWriter( new FileWriter( outputFilename , true ) ) ) ;
			if( state != null ){
				pw.println( "SOLUCION FINAL:" ) ;
				pw.println( "Execution Time: " + this.executionTime ) ;
				pw.print( state ) ;
			}
			else pw.println( "NO SOLUTION" ) ;
			pw.println() ;
		}catch( Exception e ){
			
		}finally{
			if( pw != null ) pw.close() ;
		}
	}
}

