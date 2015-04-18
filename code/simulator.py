from generator import CallGenerator
from model.building import Building
from random import randint

class Simulation :
	config = {
		'num_iterations' : 1 ,
		'max_num_calls' : 2 ,
		'elevator_capacity' : 2 ,
		'alpha' : 0.3
	}

	def __init__( self , num_elevators = 3 , num_floors = 5 , total_time = 5 ) :
		elevators_capacity = Simulation.config[ 'elevator_capacity' ]
		self.building = Building( num_elevators = num_elevators , num_floors = num_floors , elevators_capacity = elevators_capacity )
		self.generator = CallGenerator( num_floors )
		self.total_time = total_time
	
	def minimizeEnergyCost( self , building , lst_calls ) :
		num_iterations = Simulation.config[ 'num_iterations' ]
		current_state = building.clone()
		for i in range( len( lst_calls ) ) :
			best_sol = None
			call = lst_calls[ i ]
			for j in range( num_iterations ) :
				options = []
				for k in range( current_state.num_elevators ) :
					elevator = current_state.elevators[ k ]
					if elevator.current_capacity == 0 : continue
					if elevator.direction != call.direction and elevator.direction != 0 : continue
					current_sol = current_state.clone()
					current_sol.planElevatorMovement( elevator_pos = k , call = call )
					options.append( current_sol )
				if len( options ) == 0 : continue
				options.sort( key = lambda x : x.heuristic_value )
				new_len = int( round( len( options ) * Simulation.config[ 'alpha' ] + 0.5 ) )
				options = options[ 0 : new_len ]
				selected_index = randint( 0 , new_len - 1 )
				selection = options[ selected_index ]
				if best_sol == None or selection.hasBetterDistance( best_sol ) :
					best_sol = selection
			current_state = best_sol
			if current_state == None : return None
		for ins in current_state.instructions : print ins
		return current_state

	def simulate( self , filepath = None ) :
		for i in range( 40 ) : print
		current_state = self.building
		calls = ( self.generator.extractCallsFromFile( filepath ) if filepath != None else None )
		#if filepath != None : self.total_time = len( calls )
		for i in range( self.total_time ) :
			print "TIME = %s" % i
			lst_calls = ( calls[ i ] if filepath != None else self.generator.generateElevatorCalls( Simulation.config[ 'max_num_calls' ] ) )
			print "#CALLS = %s" % len( lst_calls )
			print lst_calls
			current_state = self.minimizeEnergyCost( current_state , lst_calls )
			if current_state == None :
				print "No solution"
				break
			current_state.move()
			print current_state

	def __str__( self ) :
		# TODO
		return ''

if __name__ == "__main__" :
	a = Simulation()
	a.simulate( 'test.in' )
	print a
