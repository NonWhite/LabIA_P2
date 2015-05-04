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
	
	public Parameters(){
	}
	
	// For experiment alpha
	public Parameters( Double alpha , Integer typeOfCost , String inputFilename , String outputFilename ){
		setDefaultValues() ;
		this.alpha = alpha ;
		this.costByDistance = ( typeOfCost != 2 ? true : false ) ;
		this.costByWaitingTime = ( typeOfCost >= 2 ? true : false ) ;
		this.inputFilename = inputFilename ;
		this.outputFilename = outputFilename ;
	}
	
	// For experiment iterations
	public Parameters( Integer numIterations , Double alpha , Integer typeOfCost , String inputFilename , String outputFilename ){
		setDefaultValues() ;
		this.numIterations = numIterations ;
		this.alpha = alpha ;
		this.costByDistance = ( typeOfCost != 2 ? true : false ) ;
		this.costByWaitingTime = ( typeOfCost >= 2 ? true : false ) ;
		this.inputFilename = inputFilename ;
		this.outputFilename = outputFilename ;
	}
	
	private void setDefaultValues(){
		this.alpha = 0.7 ;
		this.numIterations = 100 ;
		this.maxNumCallsPerMinute = 20 ;
		this.elevatorsCapacity = 20 ;
		this.numElevators = 50 ;
		this.numFloors = 50 ;
	}
	
	@Override
	public String toString() {
		String s = "" ;
		s += "ALPHA = " + alpha ;
		s += "ITERATIONS = " + numIterations ;
		return s ;
	}
}
