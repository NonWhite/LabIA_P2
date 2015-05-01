package model ;

import java.util.* ;

import static utils.Utils.debug ;
import static utils.Utils.INF ;

public class Elevator {
	private Integer ID ;
	private Integer currentFloor ;
	private Integer totalCapacity ;
	private Integer currentCapacity ;
	private Integer direction ;
	private Integer numStops ;
	private List<ElevatorCall> stops ;
	private Integer cost ;
	private Integer distanceMoved ;
	private Integer waitingTime ;
	
	public Integer getDistanceMoved() {
		return distanceMoved;
	}

	public Integer getWaitingTime() {
		return waitingTime;
	}

	public Integer getCost(){
//		updateCost() ; // TODO: Revisar si esto afecta
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
	
	public Elevator(){
		this.totalCapacity = 5 ;
		this.currentCapacity = this.totalCapacity ;
		this.currentFloor = 1 ;
		this.direction = 0 ;
		this.ID = 1 ;
		this.numStops = 0 ;
		this.stops = new ArrayList<ElevatorCall>() ;
		this.cost = INF ;
		this.distanceMoved = 0 ;
		this.waitingTime = 0 ;
	}
	
	public Elevator( Integer id , Integer totalCapacity ){
		this.totalCapacity = totalCapacity ;
		this.currentCapacity = totalCapacity ;
		this.currentFloor = 1 ;
		this.direction = 0 ;
		this.ID = id ;
		this.numStops = 0 ;
		this.stops = new ArrayList<ElevatorCall>() ;
		this.cost = INF ;
		this.distanceMoved = 0 ;
		this.waitingTime = 0 ;
	}
	
	public Elevator( Elevator other ){
		this.currentCapacity = other.currentCapacity ;
		this.currentFloor = other.currentFloor ;
		this.direction = other.direction ;
		this.ID = other.ID ;
		this.numStops = other.numStops ;
		this.stops = new ArrayList<ElevatorCall>( other.stops ) ;
		this.totalCapacity = other.totalCapacity ;
		this.cost = other.cost ;
		this.distanceMoved = other.distanceMoved ;
		this.waitingTime = other.waitingTime ;
	}
	
	public void move(){
		// Movement
		this.currentFloor += this.direction ;
		this.distanceMoved += Math.abs( this.direction ) ;
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
		for( Integer i = 0 ; i < this.numStops ; i++) this.stops.get( i ).waitForAMoment() ;
		if( this.numStops == 0 ) this.direction = 0 ;
		else this.direction = ( this.currentFloor > this.stops.get( 0 ).getIncomingFloor() ? -1 : ( this.currentFloor < this.stops.get( 0 ).getIncomingFloor() ? 1 : 0 ) ) ;
		if( this.currentCapacity < 0 ) debug( "Capacity Overflow for elevator " + ID ) ;
	}
	
	public void addCall( ElevatorCall call ){
		if( call.getDirection() == 0 ){ // Calls for going out of elevator
//			debug( "RECOGIO PERSONA EN PISO " + this.currentFloor ) ;
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
	
	private void removeCall( ElevatorCall call ){
		this.stops.remove( call ) ;
		this.waitingTime += call.getWaitingTime() ;
		this.numStops-- ;
	}
	
	private void updateCost(){
		this.cost = INF ;
		if( this.currentCapacity < 0 ) return ; // In case of capacity overflow
		if( Simulation.COST_BY_DISTANCE && Simulation.COST_BY_WAITING_TIME ){
			Integer previous = this.currentFloor ;
			this.cost = this.distanceMoved + this.waitingTime ;
			for( ElevatorCall call : this.stops ){
				this.cost += Math.abs( call.getIncomingFloor() - previous ) ;
				this.cost += call.getWaitingTime() ;
				previous = call.getIncomingFloor() ;
			}
			return ;
		}
		if( Simulation.COST_BY_DISTANCE ){
			Integer previous = this.currentFloor ;
			this.cost = this.distanceMoved ;
			for( ElevatorCall call : this.stops ){
				this.cost += Math.abs( call.getIncomingFloor() - previous ) ;
				previous = call.getIncomingFloor() ;
			}
		}else if( Simulation.COST_BY_WAITING_TIME ){
			this.cost = this.waitingTime ;
			for( ElevatorCall call : this.stops ){
				this.cost += call.getWaitingTime() ;
			}
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
		s += "##########################\n" ;
		return s ;
	}
}