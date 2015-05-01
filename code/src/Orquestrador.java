import java.io.FileNotFoundException;

import experiment.ExperimentAlpha;
import experiment.ExperimentIterations;
import experiment.IExperiment;

public class Orquestrador {
	public static void main( String[] args ) throws FileNotFoundException{
		IExperiment expAlpha = new ExperimentAlpha() ;
		expAlpha.generateInput( "expalpha" ) ;
		expAlpha.execute() ;
		
		IExperiment expIterations = new ExperimentIterations() ;
		expIterations.generateInput( "expiterations" ) ;
		expIterations.execute() ;
	}
}
