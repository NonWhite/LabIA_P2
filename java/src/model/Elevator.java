package model ;

import java.util.* ;
import static utils.Utils.debug ;

public class Elevator {
	private Integer ID ;
	private Integer currentFloor ;
	private Integer totalCapacity ;
	private Integer currentCapacity ;
	private Integer direction ;
	private Integer numStops ;
	private List<ElevatorCall> stops ;
//	private List<Integer> stops ;
//	private List<Integer> incoming ;
//	private List<Integer> outcoming ;
	private Integer cost ;
	private Boolean willBeFull ;
	
	private static Integer INF = Integer.MAX_VALUE ;
	
	public Integer getCost(){
		return cost ;
	}
	
	public Integer getCurrentFloor(){
		return currentFloor ;
	}

	public Integer getTotalCapacity(){
		return totalCapacity ;
	}

	public Integer getCurrentCapacity(){
		return currentCapacity ;
	}

	public Integer getNumStops(){
		return numStops ;
	}
	
	public Integer getID(){
		return ID ;
	}

	public Integer getDirection(){
		return direction ;
	}

	public List<ElevatorCall> getStops(){
		return stops ;
	}
//
//	public List<Integer> getIncoming(){
//		return incoming ;
//	}
//
//	public List<Integer> getOutcoming(){
//		return outcoming ;
//	}
	
	public Elevator(){
		this.totalCapacity = 5 ;
		this.currentCapacity = this.totalCapacity ;
		this.currentFloor = 1 ;
		this.direction = 0 ;
		this.ID = 1 ;
//		this.incoming = new ArrayList<Integer>() ;
		this.numStops = 0 ;
//		this.outcoming = new ArrayList<Integer>() ;
//		this.stops = new ArrayList<Integer>() ;
		this.stops = new ArrayList<ElevatorCall>() ;
		this.cost = INF ;
		this.willBeFull = false ;
	}
	
	public Elevator( Integer id , Integer totalCapacity ){
		this.totalCapacity = totalCapacity ;
		this.currentCapacity = totalCapacity ;
		this.currentFloor = 1 ;
		this.direction = 0 ;
		this.ID = id ;
//		this.incoming = new ArrayList<Integer>() ;
		this.numStops = 0 ;
//		this.outcoming = new ArrayList<Integer>() ;
//		this.stops = new ArrayList<Integer>() ;
		this.stops = new ArrayList<ElevatorCall>() ;
		this.cost = INF ;
		this.willBeFull = false ;
	}
	
	public Elevator( Elevator other ){
		this.currentCapacity = other.currentCapacity ;
		this.currentFloor = other.currentFloor ;
		this.direction = other.direction ;
		this.ID = other.ID ;
//		this.incoming = new ArrayList<Integer>( other.incoming ) ;
		this.numStops = other.numStops ;
//		this.outcoming = new ArrayList<Integer>( other.outcoming ) ;
//		this.stops = new ArrayList<Integer>( other.stops ) ;
		this.stops = new ArrayList<ElevatorCall>( other.stops ) ;
		this.totalCapacity = other.totalCapacity ;
		this.cost = other.cost ;
		this.willBeFull = other.willBeFull ;
	}
	
	public void move(){
		// Movement
		this.currentFloor += this.direction ;
		// If there is not stops
		if( this.numStops == 0 ) return ;
		// If next stop is current floor
		for( Integer i = 0 ; i < this.numStops ; i++){
			ElevatorCall ec = this.stops.get( i ) ;
			if( ec.getIncomingFloor() != this.currentFloor ) break ;
			removeCall( ec ) ;
			i-- ;
			if( ec.getDirection() == 0 ){ // People who come out
				this.currentCapacity++ ;
			}else{ // People who come in
				addCall( new ElevatorCall( ec.getOutcomingFloor() , ec.getOutcomingFloor() , ec.getCallingTime() ) ) ;
				this.currentCapacity-- ;
			}
		}
		if( this.numStops == 0 ) this.direction = 0 ;
		else this.direction = ( this.currentFloor > this.stops.get( 0 ).getIncomingFloor() ? -1 : ( this.currentFloor < this.stops.get( 0 ).getIncomingFloor() ? 1 : 0 ) ) ;
	}
	
	private void removeCall( ElevatorCall call ){
		this.stops.remove( call ) ;
		this.numStops-- ;
	}
	
	public void addCall( ElevatorCall call ){
		if( call.getDirection() == 0 ){ // Calls for going out of elevator
			debug( "-------------------------------------------------------------------- LLEGO AL PISO DESTINO " + call.getIncomingFloor() ) ;
		}
		if( this.direction == 0 ){
			this.stops.add( call ) ;
			this.direction = ( call.getIncomingFloor() > this.currentFloor ? 1 : ( call.getIncomingFloor() < this.currentFloor ? -1 : 0 ) ) ;
		}else if( this.currentFloor < call.getIncomingFloor() ){ // If floor is above elevator
			if( this.direction > 0 ){ // If go up
				Integer pos = 0 ;
				while( pos < this.numStops && call.getIncomingFloor() > this.stops.get( pos ).getIncomingFloor() ) pos++ ;
				if( pos == this.numStops ) this.stops.add( call ) ;
				else this.stops.add( pos , call ) ;
			}else{ // If go down
				Integer pos = 0 ;
				while( pos + 1 < this.numStops && this.stops.get( pos ).getIncomingFloor() > this.stops.get( pos + 1 ).getIncomingFloor() ) pos++ ;
				if( pos + 1 == this.numStops ) this.stops.add( call ) ;
				else this.stops.add( pos , call ) ;
			}
		}else{ // If floor is below elevator
			if( this.direction > 0 ){ // If go up
				Integer pos = 0 ;
				while( pos + 1 < this.numStops && this.stops.get( pos ).getIncomingFloor() < this.stops.get( pos + 1 ).getIncomingFloor() ) pos++ ;
				if( pos + 1 == this.numStops ) this.stops.add( call ) ;
				else this.stops.add( pos , call ) ;
			}else{ // If go down
				Integer pos = 0 ;
				while( pos < this.numStops && call.getIncomingFloor() < this.stops.get( pos ).getIncomingFloor() ) pos++ ;
				if( pos == this.numStops ) this.stops.add( call ) ;
				else this.stops.add( pos , call ) ;
			}
		}
		this.numStops++ ;
		updateCost() ;
	}
	
	private void updateCost(){ // TODO: Agregar coste acumulado porque por ahora cuando elimina una parada ya se le quita ese costo, pero deberia ser el total
		Integer previous = this.currentFloor ;
		this.cost = 0 ;
		for( ElevatorCall floor : this.stops ){
			this.cost += Math.abs( floor.getIncomingFloor() - previous ) ;
			previous = floor.getIncomingFloor() ;
		}
	}
	
	@Override
	public String toString(){
		String s = "" ;
		s  = "--------------------------\n" ;
		s += "Elevator ID = " + this.ID + "\n" ;
		s += "Direction = " + this.direction + "\n" ;
		s += "Current Floor = " + this.currentFloor + "\n" ;
		s += "Total Capacity = " + this.totalCapacity + "\n" ;
		s += "Current Capacity = " + this.currentCapacity + "\n" ;
		s += "Number of stops = " + this.numStops + "\n" ;
		s += "Stops in floors = " + this.stops + "\n" ;
//		s += "Incoming people = " + this.incoming + "\n" ;
//		s += "Outcoming people = " + this.outcoming + "\n" ;
		s += "##########################\n" ;
		return s ;
	}
}