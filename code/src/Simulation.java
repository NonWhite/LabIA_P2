import java.io.FileNotFoundException ;
import java.util.ArrayList ;
import java.util.Collections ;
import java.util.List ;

import model.Building ;
import model.Elevator ;
import model.ElevatorCall ;
import utils.CallGenerator ;
import static utils.Utils.debug ;
import static utils.Utils.randomBetween ;

public class Simulation {
	public static Double alpha = 0.5 ;
	public static Integer numIterations = 500 ;
	public static Integer maxNumCallsPerMinute = 4 ;
	public static Integer elevatorsCapacity = 10 ;
	
	private Building building ;
	private CallGenerator generator ;
	private String callsFile ;
	private Integer totalTime ;
	
	public Simulation( Integer numElevators , Integer numFloors , String callsFile ){
		this.building = new Building( numElevators , numFloors , Simulation.elevatorsCapacity ) ;
		this.generator = new CallGenerator( numFloors ) ;
		this.callsFile = callsFile ;
	}
	
	public Simulation( Integer numElevators , Integer numFloors , Integer totalTime ){
		this.building = new Building( numElevators , numFloors , Simulation.elevatorsCapacity ) ;
		this.generator = new CallGenerator( numFloors ) ;
		this.totalTime = totalTime ;
		this.callsFile = null ;
	}
	
	public void simulate() throws FileNotFoundException{
		List<List<ElevatorCall>> calls = new ArrayList<List<ElevatorCall>>() ;
		if( callsFile == null ) for( Integer t = 0 ; t < totalTime ; t++){
			calls.add( generator.generateElevatorCalls( maxNumCallsPerMinute ) ) ;
		}else{
			calls = generator.extractCallsFromFile( callsFile ) ;
			totalTime = calls.size() ;
		}
		simulate( calls ) ;
	}
	
	private void simulate( List<List<ElevatorCall>> calls ){
		Building currentState = new Building( this.building ) ;
		Boolean hasSolution = true ;
		for( Integer t = 0 ; t < totalTime ; t++){
			List<ElevatorCall> lstCalls = calls.get( t ) ;
//			debug( "TIME = " + t ) ;
//			debug( lstCalls ) ;
			currentState = minimizeEnergyCost( currentState , lstCalls ) ;
			if( currentState == null ){
				hasSolution = false ;
				break ;
			}
			currentState.moveElevators() ;
			debug( currentState ) ;
		}
		if( hasSolution ) debug( "SOLUCION FINAL: " + currentState.getHeuristicValue() ) ;
		else debug( "NO SOLUTION" ) ;
	}
	
	public Building minimizeEnergyCost( Building initialState , List<ElevatorCall> lstCalls ){
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
					currentSol.planElevatorMovement( k ,  call ) ;
					options.add( currentSol ) ;
				}
				if( options.isEmpty() ) continue ;
				Collections.sort( options ) ;
				Integer newLength = Integer.parseInt( Math.round( options.size() * alpha + 0.5 ) + "" ) ;
				options = options.subList( 0 ,  newLength ) ;
				Integer selectedIndex = randomBetween( 0 ,  newLength ) ;
				Building selection = new Building( options.get( selectedIndex ) ) ;
				if( bestSol == null || selection.hasBetterDistance( bestSol ) ){
					debug( "Mejor solución encontrada, anterior = " + ( bestSol == null ? "NULL" : bestSol.getHeuristicValue() ) + " nuevo = " + selection.getHeuristicValue() ) ;
					bestSol = new Building( selection ) ;
				}
			}
			currentState = new Building( bestSol ) ;
		}
//		for( String inst : currentState.getInstructions() ) debug( inst ) ;
		return currentState ;
	}
	
	public static void main( String[] args ) throws FileNotFoundException{
//		String callsFile = "/Users/nonwhite/Dropbox/IME/Laboratorio IA/LabIA_P2/code/test2.in" ;
		Integer numElevators = 12 , numFloors = 10 , totalTime = 50 ;
//		Simulation sim = new Simulation( numElevators ,  numFloors , callsFile ) ;
		Simulation sim = new Simulation( numElevators ,  numFloors , totalTime ) ;
		sim.simulate() ;
	}
}
