package experiment;

import utils.Utils;

public class ExperimentIterations implements IExperiment {
	private String directory ;
	
	public ExperimentIterations( String directory ){
		this.directory = directory ;
	}
	
	@Override
	public void generateInput(){
		Utils.debug( directory ) ;
		// TODO Auto-generated method stub
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
	}
}
