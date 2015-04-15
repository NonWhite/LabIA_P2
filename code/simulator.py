from generator import CallGenerator
from model.elevator import Elevator
from model.floor import Floor

class Simulation :
	config = {
		'num_iterations' : 100 ,
		'max_num_calls' : 50 ,
		'elevator_capacity' : 5 ,
		'alpha' : 0.3
	}

	def __init__( self , num_elevators = 10 , num_floors = 20 , total_time = 5 ) :
		self.num_elevators = num_elevators
		self.elevators = []
		for i in range( num_elevators ) : self.elevators.append( Elevator( ID = i+1 , total_capacity = Simulation.config[ 'elevator_capacity' ] ) )
		self.num_floors = num_floors
		self.floors = []
		for i in range( num_floors ) : self.floors.append( Floor( ID = i+1 ) )
		self.generator = CallGenerator( num_floors )
		self.total_time = total_time
	
	def run( self , lst_calls ) :
		return ''

	def simulate( self ) :
		for i in range( self.total_time ) :
			print "TIME = %s" % i
			lst_calls = self.generator.generateElevatorCalls( Simulation.config[ 'max_num_calls' ] )
			print "#CALLS = %s" % len( lst_calls )
			self.run( lst_calls )
			for j in range( Simulation.config[ 'num_iterations' ] ) :
				continue

	def __str__( self ) :
		# TODO
		return ''

if __name__ == "__main__" :
	a = Simulation()
	a.simulate()
	print a
