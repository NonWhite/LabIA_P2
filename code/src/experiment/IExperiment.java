package experiment;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public interface IExperiment {
	public static List<String> typesOfCost = Arrays.asList( "distance" , "waitingTime" , "both" ) ;
	
	public void generateInput() ;
	public void execute() throws FileNotFoundException ;
}