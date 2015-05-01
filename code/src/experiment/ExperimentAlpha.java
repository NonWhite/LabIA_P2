package experiment;

import java.io.FileNotFoundException;
import java.util.HashMap;

import model.Simulation;
import utils.Utils;

public class ExperimentAlpha implements IExperiment {
	@Override
	public void generateInput( String directory ){
		// TODO Auto-generated method stub
	}

	@Override
	public void execute() throws FileNotFoundException {
		// TODO Auto-generated method stub
		String jsonFilename = "" ;
		HashMap<String,Object> params = Utils.parseJSON( jsonFilename ) ;
		Simulation sim = new Simulation( 10 ,  10 ,  10 );
		for( Integer i = 0 ; i < 10 ; i++){
			sim.simulate() ;
			sim.reset() ;
		}
	}
}
