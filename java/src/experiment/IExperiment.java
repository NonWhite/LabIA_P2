package experiment;

import java.io.FileNotFoundException;

public interface IExperiment {
	public void generateInput( String directory ) ;
	public void execute() throws FileNotFoundException ;
}