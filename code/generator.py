from random import randint
from collections import namedtuple
import sys

class CallGenerator :
	def __init__( self , floors = 20 ) :
		self.num_floors = floors

	def mod( self , a ) : return ( a % self.maxcode + self.maxcode ) % self.maxcode
	def add( self , a , b ) : return self.mod( self.mod( a ) + self.mod( b ) )
	def mult( self , a , b ) : return self.mod( self.mod( a ) * self.mod( b ) )

	def generateElevatorCall( self ) :
		ElevatorCall = namedtuple( 'ElevatorCall' , 'current_floor destiny_floor' )
		current_floor = randint( 1 , self.num_floors )
		destiny_floor = randint( 1 , self.num_floors )
		while destiny_floor == current_floor :
			destiny_floor = randint( 1 , self.num_floors )
		print "Current = %s , Destiny = %d" % ( current_floor , destiny_floor )
		return ElevatorCall( current_floor , destiny_floor )
	

if __name__ == "__main__" :
	generator = CallGenerator()
	print "Number of Floors = %s" % ( generator.num_floors )
	ec = generator.generateElevatorCall()
	print "Current = %s , Destiny = %d" % ( ec.current_floor , ec.destiny_floor )
	sys.exit( 0 )
