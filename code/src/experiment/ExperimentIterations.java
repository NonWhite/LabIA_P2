package experiment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import model.Simulation;
import utils.Utils;

public class ExperimentIterations implements IExperiment {
	private String directory ;
	private List<Double> lstAlpha = Arrays.asList( 0.45 , 0.25 , 0.4 ) ;
	
	public ExperimentIterations( String directory ){
		this.directory = directory ;
	}
	
	@Override
	public void generateInput(){
		String inputFilename = directory + "/calls.in" ;
		for( Integer i = 100 ; i <= 1000 ; i += 100 ){
			Integer numIterations = i ;
			for( Integer typeOfCost = 1 ; typeOfCost <= 3 ; typeOfCost++ ){
				String outputFilename = directory + "/out/data_" + typesOfCost.get( typeOfCost - 1 ) + "_" + Utils.fillZeros( i ,  4 ) + ".out" ;
				Parameters params = new Parameters( numIterations , lstAlpha.get( typeOfCost - 1 ) , typeOfCost , inputFilename , outputFilename ) ;
				String jsonFilename = directory + "/in/data_" + typesOfCost.get( typeOfCost - 1 ) + "_" + Utils.fillZeros( i , 4 ) + ".json" ;
				Utils.saveParams( jsonFilename , params ) ;
			}
		}
	}

	@Override
	public void execute() throws FileNotFoundException {
		String inDirectory = directory + "/in" ;
		File dir = new File( inDirectory ) ;
		String[] files = dir.list() ;
		for( String p : files ){
			Utils.debug( inDirectory + "/" + p ) ;
			Parameters params = Utils.parseJsonParameters( inDirectory + "/" + p ) ;
			Utils.debug( params ) ;
			for( Integer i = 0 ; i < NUM_EXECUTIONS ; i++){
				Simulation sim = new Simulation( params ) ;
				sim.simulate() ;
				sim.reset() ;
			}
		}
	}
}
