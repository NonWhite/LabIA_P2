class Elevator :
	def __init__( self , ID = 1 , current_floor = 1 , total_capacity = 5 , direction = 0 ) :
		self.ID = ID
		self.current_floor = current_floor
		self.total_capacity = total_capacity
		self.current_capacity = total_capacity
		self.direction = direction
		self.num_stops = 0
		self.stops = []
		self.inp = []
		self.out = []

	def addCall( self , call ) :
		# Calculate the cost for adding new call
		if self.direction == 0 :
			self.num_stops += 2
			self.stops.append( call.current_floor )
			self.inp.append( 1 )
			self.out.append( 0 )
			self.stops.append( call.destiny_floor )
			self.inp.append( 0 )
			self.out.append( 1 )
			self.direction = call.direction
		elif self.current_floor < call.current_floor :
			if self.direction > 0 :
				# Anhadir antes de bajar
				b = 0
			else :
				# TODO
				c = 0
		else :
			if self.direction < 0 :
				 # Anhadir antes de subir
				 d = 0
			else :
				# TODO
				e = 0
		# Calculate cost
		cost = 0
		start = self.current_floor
		for i in range( self.num_stops ) :
			cost += abs( start - self.stops[ i ] )
			start = self.stops[ i ]
		return cost

	def move( self ) :
		# Movement
		self.current_floor += self.direction
		# If there is not stops
		if self.num_stops == 0 : return
		# If next stop is current floor
		if self.current_floor == self.stops[ 0 ] :
			# People who come out
			self.current_capacity += self.out[ 0 ]
			self.out.pop( 0 )
			# People who come in
			self.current_capacity -= self.inp[ 0 ]
			self.inp.pop( 0 )
			# Update constraints
			self.num_stops -= 1
			self.stops.pop( 0 )
		if self.num_stops == 0 :
			self.direction = 0

	def __str__( self ) :
		s  = "--------------------------\n"
		s += "Elevator ID = %s\n" % self.ID
		s += "Current Floor = %s\n" % self.current_floor
		s += "Total Capacity = %s\n" % self.total_capacity
		s += "Current Capacity = %s\n" % self.current_capacity
		s += "Number of stops = %s\n" % self.num_stops
		s += "Stops in floors = %s\n" % self.stops
		s += "Incoming people = %s\n" % self.inp
		s += "Outcoming people = %s\n" % self.out
		s += "##########################\n"
		return s
	
if __name__ == "__main__" :
	e = Elevator()
	print e
