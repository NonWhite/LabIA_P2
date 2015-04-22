package model ;

import java.util.ArrayList;
import java.util.List;

public class Building implements Comparable<Building> {
	private Integer elevatorsCapacity ;
	private Integer numElevators ;
	private List<Elevator> elevators ;
	private Integer numFloors ;
//	private List<Floor> floors ;
	private List<String> instructions ;
	private Integer heuristicValue ;
	
	public Integer getElevatorsCapacity() {
		return elevatorsCapacity;
	}

	public void setElevatorsCapacity(Integer elevatorsCapacity) {
		this.elevatorsCapacity = elevatorsCapacity;
	}

	public Integer getNumElevators() {
		return numElevators;
	}

	public void setNumElevators(Integer numElevators) {
		this.numElevators = numElevators;
	}

	public List<Elevator> getElevators() {
		return elevators;
	}

	public void setElevators(List<Elevator> elevators) {
		this.elevators = elevators;
	}

	public Integer getNumFloors() {
		return numFloors;
	}

	public void setNumFloors(Integer numFloors) {
		this.numFloors = numFloors;
	}

	public List<String> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}

	public Integer getHeuristicValue() {
		return heuristicValue;
	}

	public void setHeuristicValue(Integer heuristicValue) {
		this.heuristicValue = heuristicValue;
	}

	public Building(){
		this.elevatorsCapacity = 5 ;
		this.numElevators = 10 ;
		this.elevators = new ArrayList<Elevator>() ;
		for( Integer i = 0 ; i < this.numElevators ; i++) this.elevators.add( new Elevator( i+1 , this.elevatorsCapacity ) ) ;
		this.numFloors = 20 ;
		this.instructions = new ArrayList<String>() ;
		this.heuristicValue = 0 ;
	}
	
	public Building( Integer numElevators , Integer numFloors , Integer elevatorsCapacity ){
		this.elevatorsCapacity = elevatorsCapacity ;
		this.numElevators = numElevators ;
		this.elevators = new ArrayList<Elevator>() ;
		for( Integer i = 0 ; i < this.numElevators ; i++) this.elevators.add( new Elevator( i+1 , this.elevatorsCapacity ) ) ;
		this.numFloors = numFloors ;
		this.instructions = new ArrayList<String>() ;
		this.heuristicValue = 0 ;
	}
	
	public Building( Building other ){
		this.elevatorsCapacity = other.elevatorsCapacity ;
		this.numElevators = other.numElevators ;
		this.elevators = new ArrayList<Elevator>() ;
		for( Integer i = 0 ; i < this.numElevators ; i++) this.elevators.add( new Elevator( other.elevators.get( i ) ) ) ;
		this.numFloors = other.numFloors ;
		this.instructions = other.instructions ;
		this.heuristicValue = other.heuristicValue ;
	}
	
	public Boolean hasBetterDistance( Building other ){
		return this.heuristicValue < other.heuristicValue ;
	}
	
	public void move(){
		for( Integer i = 0 ; i < this.numElevators ; i++) elevators.get( i ).move() ;
	}
	
	public void planElevatorMovement( Integer elevatorPosition , ElevatorCall call ){
		Integer cost = this.elevators.get( elevatorPosition ).addCall( call ) ;
		this.heuristicValue += cost ;
		this.instructions.add( "Recogerá la llamada " + call + " con el elevator " + elevatorPosition ) ;
	}
	
	@Override
	public String toString() {
		String s = "" ;
		s += "Num elevators = " + this.numElevators + "\n" ;
		s += "Num floors = " + this.numFloors + "\n" ;
		s += "Cost = " + this.heuristicValue + "\n" ;
		for( Elevator e : this.elevators ) s += e ;
		return s ;
	}
	
	@Override
	public int compareTo( Building other ){
		return ( heuristicValue < other.heuristicValue ? -1 : heuristicValue > other.heuristicValue ? 1 : 0 ) ;
	}
}