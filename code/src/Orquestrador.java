import java.io.FileNotFoundException; 

import model.Simulation;
import utils.CallGenerator;
import utils.Utils;
import experiment.ExperimentAlpha;
import experiment.ExperimentIterations;
import experiment.IExperiment;
import experiment.Parameters;

public class Orquestrador {
	public static void main( String[] args ) throws FileNotFoundException{
		String path = "." ;
		String jsonFile = "parameters.json" ;
		Integer numFloors = 50 ;
		Integer maxNumCalls = 20 ;
		Integer totalTime = 50 ;
		String callsFile = "calls.in" ;
		Parameters params = null ;
		Simulation sim = null ;
		switch( args.length ){
			case 0 :
				params = Utils.parseJsonParameters( jsonFile ) ;
				sim = new Simulation( params ) ;
				sim.simulate() ;
				break ;
			case 1 :
				if( args[ 0 ].equalsIgnoreCase( "-h" ) ){
					Utils.debug( "Usage: java -jar Orquestrador.jar" ) ;
					Utils.debug( "\tWithout parameters to run with parameters.json in current path" ) ;
					Utils.debug( "\t-h for help" ) ;
					Utils.debug( "\t-c [callsfile] -nf [numfloors] -nc [maxnumcalls] -t [totaltime] to generate calls file" ) ;
					Utils.debug( "\t-j [jsonfilepath] to specify an input json file" ) ;
					Utils.debug( "\t-p [path] to change current path for search input files for experiments" ) ;
				}else
					Utils.debug( "Invalid parameter" ) ;
				break ;
			case 2 :
				if( args[ 0 ].equalsIgnoreCase( "-j" ) ){
					jsonFile = args[ 1 ] ;
					params = Utils.parseJsonParameters( jsonFile ) ;
					sim = new Simulation( params ) ;
					sim.simulate() ;
				}else if( args[ 0 ].equalsIgnoreCase( "-p" ) ){
					path = args[ 1 ] ;
					String dirExpAlpha = "expalpha" ;
					IExperiment expAlpha = new ExperimentAlpha( path + "/" + dirExpAlpha ) ;
					expAlpha.generateInput() ;
					expAlpha.execute() ;
					
					String dirExpIterations = "expiterations" ;
					IExperiment expIterations = new ExperimentIterations( path + "/" + dirExpIterations ) ;
					expIterations.generateInput() ;
					expIterations.execute() ;
				}
				break ;
			case 8 :
				for( Integer i = 0 ; i < args.length ; i++){
					if( args[ i ].equalsIgnoreCase( "-c" ) ){
						callsFile = args[ i + 1 ] ;
						i++ ;
					}else if( args[ i ].equalsIgnoreCase( "-nf" ) ){
						numFloors = Integer.parseInt( args[ i + 1 ] ) ;
						i++ ;
					}else if( args[ i ].equalsIgnoreCase( "-nc" ) ){
						maxNumCalls = Integer.parseInt( args[ i + 1 ] ) ;
						i++ ;
					}else if( args[ i ].equalsIgnoreCase( "-t" ) ){
						totalTime = Integer.parseInt( args[ i + 1 ] ) ;
						i++ ;
					}
				}
				CallGenerator generator = new CallGenerator( numFloors , maxNumCalls ) ;
				generator.generateAndSaveCalls( totalTime ,  callsFile ) ;
				break ;
			default :
				Utils.debug( "Invalid parameters" ) ;
		}
	}
}