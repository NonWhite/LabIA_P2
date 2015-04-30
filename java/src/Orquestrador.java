import java.io.FileNotFoundException;

import utils.CallGenerator;
import model.Simulation;

public class Orquestrador {
	public static void main( String[] args ) throws FileNotFoundException{
//		String callsFile = "/Users/nonwhite/Dropbox/IME/Laboratorio IA/LabIA_P2/code/test2.in" ;
//		String callsFile = "/Users/nonwhite/out.in" ;
		Integer numElevators = 3 , numFloors = 10 , totalTime = 25 ;
//		Simulation sim = new Simulation( numElevators ,  numFloors , callsFile ) ;
////		Simulation sim = new Simulation( numElevators ,  numFloors , totalTime ) ;
//		sim.simulate() ;
		CallGenerator generator = new CallGenerator( numFloors ) ;
		String testFile = "/Users/nonwhite/out.in" ;
		generator.generateAndSaveCalls( Simulation.maxNumCallsPerMinute, totalTime , testFile ) ;
		
		Simulation sim = new Simulation( numElevators , numFloors , testFile ) ;
		for( Integer i = 0 ; i < 10 ; i++){
			sim.simulate() ;
			sim.reset() ;			
		}
	}
}
