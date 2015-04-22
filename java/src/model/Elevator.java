package model ;

import java.util.* ;

public class Elevator {
	private Integer ID ;
	private Integer currentFloor ;
	private Integer totalCapacity ;
	private Integer currentCapacity ;
	private Integer direction ;
	private Integer numStops ;
	private List<Integer> stops ;
	private List<Integer> incoming ;
	private List<Integer> outcoming ;
	
	public Integer getCurrentFloor(){
		return currentFloor ;
	}

	public void setCurrentFloor( Integer currentFloor ){
		this.currentFloor = currentFloor ;
	}

	public Integer getTotalCapacity(){
		return totalCapacity ;
	}

	public void setTotalCapacity( Integer totalCapacity ){
		this.totalCapacity = totalCapacity ;
	}

	public Integer getCurrentCapacity(){
		return currentCapacity ;
	}

	public void setCurrentCapacity( Integer currentCapacity ){
		this.currentCapacity = currentCapacity ;
	}

	public Integer getNumStops(){
		return numStops ;
	}

	public void setNumStops( Integer numStops ){
		this.numStops = numStops ;
	}
	
	public Integer getID(){
		return ID ;
	}

	public void setID( Integer iD ){
		ID = iD ;
	}

	public Integer getDirection(){
		return direction ;
	}

	public void setDirection( Integer direction ){
		this.direction = direction ;
	}

	public Integer getNum_stops(){
		return numStops ;
	}

	public void setNum_stops( Integer num_stops ){
		this.numStops = num_stops ;
	}

	public List<Integer> getStops(){
		return stops ;
	}

	public void setStops( List<Integer> stops ){
		this.stops = stops ;
	}

	public List<Integer> getIncoming(){
		return incoming ;
	}

	public void setIncoming( List<Integer> incoming ){
		this.incoming = incoming ;
	}

	public List<Integer> getOutcoming(){
		return outcoming ;
	}

	public void setOutcoming( List<Integer> outcoming ){
		this.outcoming = outcoming ;
	}
	
	public Elevator(){
		this.ID = 1 ;
		this.currentFloor = 1 ;
		this.totalCapacity = 5 ;
		this.currentCapacity = this.totalCapacity ;
		this.direction = 0 ;
		this.numStops = 0 ;
		this.stops = new ArrayList<Integer>() ;
		this.incoming = new ArrayList<Integer>() ;
		this.outcoming = new ArrayList<Integer>() ;
	}
	
	public Elevator( Integer id , Integer totalCapacity ){
		this.ID = id ;
		this.currentFloor = 1 ;
		this.totalCapacity = totalCapacity ;
		this.currentCapacity = this.totalCapacity ;
		this.direction = 0 ;
		this.numStops = 0 ;
		this.stops = new ArrayList<Integer>() ;
		this.incoming = new ArrayList<Integer>() ;
		this.outcoming = new ArrayList<Integer>() ;
	}
	
	public Elevator( Elevator other ){
		this.ID = other.ID ;
		this.currentFloor = other.currentFloor ;
		this.totalCapacity = other.totalCapacity ;
		this.currentCapacity = other.currentCapacity ;
		this.direction = other.direction ;
		this.numStops = other.numStops ;
		this.stops = other.stops ;
		this.incoming = other.incoming ;
		this.outcoming = other.outcoming ;
	}
	
	public void move(){
		// Movement
		this.currentFloor += this.direction ;
		// If there is not stops
		if( this.numStops == 0 ) return ;
		// If next stop is current floor
		if( this.currentFloor == this.stops.get( 0 ) ){
			// People who come out
			this.currentCapacity += this.outcoming.get( 0 ) ;
			this.outcoming.remove( 0 ) ;
			// People who come in
			this.currentCapacity -= this.incoming.get( 0 ) ;
			this.incoming.remove( 0 ) ;
			// Update constraints
			this.numStops-- ;
			this.stops.remove( 0 ) ;
		}
		if( this.numStops == 0 ) this.direction = 0 ;
	}
	
	public Integer addCall( ElevatorCall call ){
		// TODO
		if( this.direction == 0 ){
			this.numStops += 2 ;
			this.stops.add( call.getIncomingFloor() ) ;
			this.incoming.add( 1 ) ;
			this.outcoming.add( 0 ) ;
			this.stops.add( call.getOutcomingFloor() ) ;
			this.incoming.add( 1 ) ;
			this.outcoming.add( 0 ) ;
			this.direction = call.getDirection() ;
		}else if( this.currentFloor < call.getIncomingFloor() ){
			if( this.direction > 0 ){
				
			}else{
				
			}
		}else{
			if( this.direction < 0 ){
				
			}else{
				
			}
		}
		Integer cost = 0 , previous = this.currentFloor ;
		for( Integer floor : this.stops ){
			cost += Math.abs( floor - previous ) ;
			previous = floor ;
		}
		return cost ;
	}
	
	@Override
	public String toString(){
		String s = "" ;
		s  = "--------------------------\n" ;
		s += "Elevator ID = " + this.ID + "\n" ;
		s += "Current Floor = " + this.currentFloor + "\n" ;
		s += "Total Capacity = " + this.totalCapacity + "\n" ;
		s += "Current Capacity = " + this.currentCapacity + "\n" ;
		s += "Number of stops = " + this.numStops + "\n" ;
		s += "Stops in floors = " + this.stops + "\n" ;
		s += "Incoming people = " + this.incoming + "\n" ;
		s += "Outcoming people = " + this.outcoming + "\n" ;
		s += "##########################\n" ;
		return s ;
	}
}