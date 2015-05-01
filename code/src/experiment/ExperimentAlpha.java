package experiment;

import java.io.FileNotFoundException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
				String jsonFilename = directory + "/in/data_" + typesOfCost.get( typeOfCost - 1 ) + "_" + Utils.fillZeros( i , 3 ) + ".json" ;
				String outputFilename = directory + "/out/data_" + typesOfCost.get( typeOfCost - 1 ) + ".out" ;
				Parameters params = new Parameters( alpha , typeOfCost , inputFilename , outputFilename ) ;
				ObjectMapper obj = new ObjectMapper() ;
				obj.configure( SerializationFeature.INDENT_OUTPUT , true ) ;
				Utils.saveToFile( jsonFilename , obj.valueToTree( params ).toString() ) ;
			}
		}
	}

	@Override
	public void execute() throws FileNotFoundException {
		// TODO Auto-generated method stub
		String jsonFilename = "" ;
		HashMap<String,String> params = Utils.parseJSON( jsonFilename ) ;
		Simulation sim = new Simulation( params );
		for( Integer i = 0 ; i < 10 ; i++){
			sim.simulate() ;
			sim.reset() ;
		}
	}
}
