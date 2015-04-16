from random import randint
from collections import namedtuple
import sys
import json

class CallGenerator :
	def __init__( self , floors = 20 ) :
		self.num_floors = floors

	def generateSingleElevatorCall( self ) :
		ElevatorCall = namedtuple( 'ElevatorCall' , 'current_floor destiny_floor direction' )
		current_floor = randint( 1 , self.num_floors )
		destiny_floor = randint( 1 , self.num_floors )
		while destiny_floor == current_floor :
			destiny_floor = randint( 1 , self.num_floors )
		direction = ( 1 if current_floor < destiny_floor else -1 )
		return ElevatorCall( current_floor , destiny_floor , direction )
	
	def generateElevatorCalls( self , max_calls ) :
		num_calls = randint( 1 , max_calls )
		lst_calls = []
		for i in range( num_calls ) : lst_calls.append( self.generateSingleElevatorCall() )
		return lst_calls
	
	def extractCallsFromFile( self , filepath ) :
		ElevatorCall = namedtuple( 'ElevatorCall' , 'current_floor destiny_floor direction' )
		raw_data = json.loads( open( filepath , 'r' ).read() )
		calls = []
		for i in range( len( raw_data ) ) :
			raw_call = raw_data[ i ]
			for j in range( len( raw_call ) ) :
				direction = ( 1 if raw_call[ j ][ 0 ] < raw_call[ j ][ 1 ] else -1 )
				raw_call[ j ] = ElevatorCall( raw_call[ j ][ 0 ] , raw_call[ j ][ 1 ] , direction )
			calls.append( raw_call )
		return calls

if __name__ == "__main__" :
	generator = CallGenerator()
	print "Number of Floors = %s" % ( generator.num_floors )
	ec = generator.generateSingleElevatorCall()
	print "Current = %s , Destiny = %d" % ( ec.current_floor , ec.destiny_floor )
	lst = generator.extractCallsFromFile( 'test.in' )
	print lst
	sys.exit( 0 )
