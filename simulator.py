from generator import CallGenerator

if __name__ == "__main__" :
	total_time = 10
	num_elevators = 1
	total_calls = 3
	g = CallGenerator( 5 )
	for i in range( total_time ) :
		print "Time = %s" % i
		for r in range( total_calls ) :
			g.generateElevatorCall()
