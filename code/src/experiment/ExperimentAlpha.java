package experiment;

import java.io.File;
import java.io.FileNotFoundException;

import model.Simulation;
import utils.Utils;

public class ExperimentAlpha implements IExperiment {
	private String directory ;
	
	public ExperimentAlpha( String directory ){
		this.directory = directory ;
	}
	
	@Override
	public void generateInput(){
		String inputFilename = directory + "/calls.in" ;
		for( Integer i = 5 ; i < 100 ; i += 5 ){
			Double alpha = Double.parseDouble( i + "" ) / 100.0 ;
			for( Integer typeOfCost = 1 ; typeOfCost <= 3 ; typeOfCost++ ){
				String outputFilename = directory + "/out/data_" + typesOfCost.get( typeOfCost - 1 ) + "_" + Utils.fillZeros( i ,  3 ) + ".out" ;
				Parameters params = new Parameters( alpha , typeOfCost , inputFilename , outputFilename ) ;
				String jsonFilename = directory + "/in/data_" + typesOfCost.get( typeOfCost - 1 ) + "_" + Utils.fillZeros( i , 3 ) + ".json" ;
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
