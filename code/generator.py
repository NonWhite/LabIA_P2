from random import randint
from collections import namedtuple
import sys

class CallGenerator :
	def __init__( self , floors = 20 ) :
		self.num_floors = floors

	def generateSingleElevatorCall( self ) :
		ElevatorCall = namedtuple( 'ElevatorCall' , 'current_floor destiny_floor' )
		current_floor = randint( 1 , self.num_floors )
		destiny_floor = randint( 1 , self.num_floors )
		while destiny_floor == current_floor :
			destiny_floor = randint( 1 , self.num_floors )
		print "Current = %s , Destiny = %d" % ( current_floor , destiny_floor )
		return ElevatorCall( current_floor , destiny_floor )
	
	def generateElevatorCalls( self , max_calls ) :
		num_calls = randint( 1 , max_calls )
		lst_calls = []
		for i in range( num_calls ) : lst_calls.append( self.generateSingleElevatorCall() )
		return lst_calls

if __name__ == "__main__" :
	generator = CallGenerator()
	print "Number of Floors = %s" % ( generator.num_floors )
	ec = generator.generateElevatorCall()
	print "Current = %s , Destiny = %d" % ( ec.current_floor , ec.destiny_floor )
	sys.exit( 0 )
