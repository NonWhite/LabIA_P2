from elevator import Elevator
from floor import Floor
import copy

class Building :
	def __init__( self , num_elevators = 10 , num_floors = 20 , elevators_capacity = 5 ) :
		self.elevators = []
		for i in range( num_elevators ) : self.elevators.append( Elevator( ID = i+1 , total_capacity = elevators_capacity ) )
		self.num_elevators = num_elevators
		self.floors = []
		for i in range( num_floors ) : self.floors.append( Floor( ID = i+1 ) )
		self.num_floors = num_floors
		self.heuristic_value = 0
	
	def planElevatorMovement( self , elevator_pos , call ) :
		cost = self.elevators[ elevator_pos ].addCall( call )
		self.heuristic_value += cost
	
	def hasBetterDistance( self , other ) :
		return self.heuristic_value < other.heuristic_value
	
	def move( self ) :
		for elevator in self.elevators :
			elevator.move()

	def clone( self ) :
		return copy.copy( self )

	def __str__( self ) :
		s  = "Num elevators = %s\n" % self.num_elevators
		s += "Num floors = %s\n" % self.num_floors
		s += "Heuristic value = %s\n" % self.heuristic_value
		return s
	

if __name__ == "__main__" :
	b = Building()
	print b