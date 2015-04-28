package model;

public class ElevatorCall {
	private Integer incomingFloor ;
	private Integer outcomingFloor ;
	private Integer direction ;
	private Integer callingTime ;
	
	public Integer getIncomingFloor(){
		return incomingFloor ;
	}
	public void setIncomingFloor( Integer incomingFloor ){
		this.incomingFloor = incomingFloor ;
	}
	
	public Integer getOutcomingFloor(){
		return outcomingFloor ;
	}
	
	public void setOutcomingFloor( Integer outcomingFloor ){
		this.outcomingFloor = outcomingFloor ;
	}
	
	public Integer getDirection(){
		return direction ;
	}
	
	public void setDirection( Integer direction ){
		this.direction = direction ;
	}
	
	public Integer getCallingTime(){
		return callingTime ;
	}
	
	public void setCallingTime( Integer callingTime ){
		this.callingTime = callingTime ;
	}
	
	public ElevatorCall( Integer in , Integer out , Integer time ){
		this.incomingFloor = in ;
		this.outcomingFloor = out ;
		this.direction = ( in < out ? 1 : in > out ? -1 : 0 ) ;
		this.callingTime = time ;
	}
	
	@Override
	public String toString() {
		String s = "" ;
		s += "(In=" + this.incomingFloor ;
		s += "," ;
		s += "Out=" + this.outcomingFloor + ")" ;
		return s ;
	}
}