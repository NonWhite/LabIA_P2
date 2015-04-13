from generator import CallGenerator
from model.elevator import Elevator
from model.floor import Floor

if __name__ == "__main__" :
	print Elevator()
	print Floor()
	total_time = 5
	num_elevators = 1
	calls_per_unit = 2
	g = CallGenerator( 5 )
	for i in range( total_time ) :
		print "Time = %s" % i
		for r in range( calls_per_unit ) :
			g.generateElevatorCall()
