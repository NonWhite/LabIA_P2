package model;

public class ElevatorCall {
	private Integer incomingFloor ;
	private Integer outcomingFloor ;
	private Integer direction ;
	private Integer callingTime ;
	private Integer waitingTime ;
	
	public Integer getIncomingFloor(){
		return incomingFloor ;
	}
	
	public Integer getOutcomingFloor(){
		return outcomingFloor ;
	}
	
	public Integer getDirection(){
		return direction ;
	}
	
	public Integer getCallingTime(){
		return callingTime ;
	}
	
	public Integer getWaitingTime(){
		return waitingTime ;
	}
	
	public ElevatorCall( Integer in , Integer out , Integer time ){
		this.incomingFloor = in ;
		this.outcomingFloor = out ;
		this.direction = ( in < out ? 1 : in > out ? -1 : 0 ) ;
		this.callingTime = time ;
		this.waitingTime = 0 ;
	}
	
	public void waitForAMoment(){
		this.waitingTime++ ;
	}
	
	@Override
	public String toString() {
		String s = "" ;
		s += "(In=" + this.incomingFloor ;
		s += "," ;
		s += "Out=" + this.outcomingFloor ;
		s += "," ;
		s += "WaitTime=" + this.waitingTime + ")" ;
		return s ;
	}
}