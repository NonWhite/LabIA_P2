package model ;

import java.util.ArrayList;
import java.util.List;

public class Building implements Comparable<Building> {
	private Integer elevatorsCapacity ;
	private Integer numElevators ;
	private List<Elevator> elevators ;
	private Integer numFloors ;
	private List<String> instructions ;
	private Integer heuristicValue ;
	
	private static Integer INF = Integer.MAX_VALUE ;
	
	public Integer getElevatorsCapacity() {
		return elevatorsCapacity;
	}

	public Integer getNumElevators() {
		return numElevators;
	}

	public List<Elevator> getElevators() {
		return elevators;
	}

	public Integer getNumFloors() {
		return numFloors;
	}

	public List<String> getInstructions() {
		return instructions;
	}

	public Integer getHeuristicValue() {
		return heuristicValue;
	}

	public Building(){
		this.elevatorsCapacity = 5 ;
		this.numElevators = 10 ;
		this.elevators = new ArrayList<Elevator>() ;
		for( Integer i = 0 ; i < this.numElevators ; i++) this.elevators.add( new Elevator( i+1 , this.elevatorsCapacity ) ) ;
		this.numFloors = 20 ;
		this.instructions = new ArrayList<String>() ;
		this.heuristicValue = INF ;
	}
	
	public Building( Integer numElevators , Integer numFloors , Integer elevatorsCapacity ){
		this.elevatorsCapacity = elevatorsCapacity ;
		this.numElevators = numElevators ;
		this.elevators = new ArrayList<Elevator>() ;
		for( Integer i = 0 ; i < this.numElevators ; i++) this.elevators.add( new Elevator( i+1 , this.elevatorsCapacity ) ) ;
		this.numFloors = numFloors ;
		this.instructions = new ArrayList<String>() ;
		this.heuristicValue = INF ;
	}
	
	public Building( Building other ){
		this.elevatorsCapacity = other.elevatorsCapacity ;
		this.numElevators = other.numElevators ;
		this.elevators = new ArrayList<Elevator>() ;
		for( Integer i = 0 ; i < this.numElevators ; i++) this.elevators.add( new Elevator( other.elevators.get( i ) ) ) ;
		this.numFloors = other.numFloors ;
		this.instructions = new ArrayList<String>( other.instructions ) ;
		this.heuristicValue = other.heuristicValue ;
	}
	
	public Boolean isBetterThan( Building other ){
		return this.heuristicValue < other.heuristicValue ;
	}
	
	public void moveElevators(){
		for( Integer i = 0 ; i < this.numElevators ; i++) elevators.get( i ).move() ;
	}
	
	private Integer getElevatorsCost(){
		Integer cost = 0 ;
		for( Elevator e : this.elevators ) cost += ( e.getCost() == Integer.MAX_VALUE ? 0 : e.getCost() ) ;
		return cost ;
	}
	
	public void takeNewCall( Integer elevatorPosition , ElevatorCall call ){
		this.elevators.get( elevatorPosition ).addCall( call ) ;
		this.heuristicValue = getElevatorsCost() ;
		this.instructions.add( "Recogerá la llamada " + call + " con el elevador " + this.elevators.get( elevatorPosition ).getID() ) ;
	}
	
	@Override
	public String toString() {
		String s = "" , t = "" ;
		s += "Num elevators = " + this.numElevators + "\n" ;
		s += "Num floors = " + this.numFloors + "\n" ;
		s += "Cost = " + this.heuristicValue + "\n" ;
		for( Integer i = 0 ; i < this.numElevators ; i += 3 ){
			s += "------------------------------------------------------------------------------\n" ;
			Elevator e1 = this.elevators.get( i + 0 ) ;
			Elevator e2 = this.elevators.get( i + 1) ;
			Elevator e3 = this.elevators.get( i + 2 ) ;
			t = "" ;
			t += String.format( "%-20s = %15s %15s %15s\n" , "Elevator ID" , e1.getID() , e2.getID() , e3.getID() ) ;
			t += String.format( "%-20s = %15s %15s %15s\n" , "Direction" , e1.getDirection() , e2.getDirection() , e3.getDirection() ) ;
			t += String.format( "%-20s = %15s %15s %15s\n" , "Current Floor" , e1.getCurrentFloor() , e2.getCurrentFloor() , e3.getCurrentFloor() ) ;
			t += String.format( "%-20s = %15s %15s %15s\n" , "Total Capacity" , e1.getTotalCapacity() , e2.getTotalCapacity() , e3.getTotalCapacity() ) ;
			t += String.format( "%-20s = %15s %15s %15s\n" , "Current Capacity" , e1.getCurrentCapacity() , e2.getCurrentCapacity() , e3.getCurrentCapacity() ) ;
			t += String.format( "%-20s = %15s %15s %15s\n" , "Number of stops" , e1.getNumStops() , e2.getNumStops() , e3.getNumStops() ) ;
			t += String.format( "%-20s = %15s %15s %15s\n" , "Stops in floors" , e1.getStops() , e2.getStops() , e3.getStops() ) ;
			s += t ;
		}
		
		s += "##############################################################################\n" ;
		return s ;
	}
	
	@Override
	public int compareTo( Building other ){
		return ( heuristicValue < other.heuristicValue ? -1 : heuristicValue > other.heuristicValue ? 1 : 0 ) ;
	}
}