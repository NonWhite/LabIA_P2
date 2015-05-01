import java.io.FileNotFoundException;

import experiment.ExperimentAlpha;
import experiment.ExperimentIterations;
import experiment.IExperiment;

public class Orquestrador {
	public static void main( String[] args ) throws FileNotFoundException{
		String path = "/Users/nonwhite/Dropbox/IME/Laboratorio IA/LabIA_P2/data" ;
		// TODO: Generate the input file for the experiments
		
		String dirExpAlpha = "expalpha" ;
		IExperiment expAlpha = new ExperimentAlpha( path + "/" + dirExpAlpha ) ;
		expAlpha.generateInput() ;
//		expAlpha.execute() ;
		
		String dirExpIterations = "expiterations" ;
		IExperiment expIterations = new ExperimentIterations( path + "/" + dirExpIterations ) ;
		expIterations.generateInput() ;
//		expIterations.execute() ;
	}
}