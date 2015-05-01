package experiment;

public class Parameters {
	public Double alpha ;
	public Integer numIterations ;
	public Integer maxNumCallsPerMinute ; // Not necessary in alpha
	public Integer elevatorsCapacity ;
	public Boolean costByDistance ;
	public Boolean costByWaitingTime ;
	public Integer numElevators ;
	public Integer numFloors ;
	public String inputFilename ;
	public String outputFilename ;
	
	public Parameters( Double alpha , Integer typeOfCost , String inputFilename , String outputFilename ){
		this.alpha = alpha ;
		this.costByDistance = ( typeOfCost != 2 ? true : false ) ;
		this.costByWaitingTime = ( typeOfCost >= 2 ? true : false ) ;
		this.inputFilename = inputFilename ;
		this.outputFilename = outputFilename ;
		
		this.numIterations = 1000 ;
		this.maxNumCallsPerMinute = 20 ;
		this.elevatorsCapacity = 20 ;
		this.numElevators = 100 ;
		this.numFloors = 100 ;
	}
}
