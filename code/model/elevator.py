class Elevator :
	def __init__( self , ID = 1 , current_floor = 1 , total_capacity = 5 , direction = 0 ) :
		self.ID = ID
		self.current_floor = current_floor
		self.destiny_floor = 0
		self.total_capacity = total_capacity
		self.current_capacity = total_capacity
		self.direction = direction
		self.num_calls = 0
		self.calls = []

	def addCall( self , call ) :
		cost = 0
		if self.direction > 0 :
			if self.destiny_floor > call.current_floor : cost = 0
		elif self.direction < 0 :
			if self.destiny_floor < call.current_floor : cost = 0
		else :
			if self.destiny_floor < call.current_floor : self.direction = 1
			else : self.direction = -1
			cost = abs( self.destiny_floor - call.current_floor )
			self.destiny_floor = call.current_floor
		self.current_capacity -= 1
		self.calls.append( call )
		self.num_calls += 1
		return cost

	def move( self ) :
		# Movement
		self.current_floor += self.direction
		# People who come in
		for c in self.calls :
			if c.current_floor == self.current_floor :
				# TODO: verify destiny_floor for all calls from this floor
				continue
		# People who go out
		for c in self.calls :
			if c.destiny_floor == self.current_floor :
				self.current_capacity += 1
				self.calls.remove( c )
				self.num_calls -= 1
		if len( self.calls ) == 0 :
			self.direction = 0

	def __str__( self ) :
		s  = "--------------------------\n"
		s += "Elevator ID = %s\n" % self.ID
		s += "Current Floor = %s\n" % self.current_floor
		s += "Destiny Floor = %s\n" % self.destiny_floor
		s += "Total Capacity = %s\n" % self.total_capacity
		s += "Current Capacity = %s\n" % self.current_capacity
		s += "##########################\n"
		return s
	
if __name__ == "__main__" :
	e = Elevator()
	print e
