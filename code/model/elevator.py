class Elevator :
	def __init__( self , ID = 1 , current_floor = 1 , total_capacity = 5 , movement = 0 ) :
		self.ID = ID
		self.current_floor = current_floor
		self.destiny_floor = None
		self.total_capacity = total_capacity
		self.current_capacity = total_capacity
		self.movement = movement
		self.num_calls = 0
		self.calls = []

	def addCall( self , call ) :
		if self.destiny_floor == None : self.destiny_floor = 0
		cost = 0
		if self.movement > 0 :
			#print "%s Moviendose hacia arriba" % self.ID
			if self.destiny_floor > call.current_floor : cost = 0
		elif self.movement < 0 :
			#print "%s Moviendose hacia abajo" % self.ID
			if self.destiny_floor < call.current_floor : cost = 0
		else :
			#print "%s Sin Moverse" % self.ID
			if self.destiny_floor < call.current_floor : self.movement = 1
			else : self.movement = -1
			cost = abs( self.destiny_floor - call.current_floor )
		self.calls.append( call )
		self.num_calls += 1
		return cost

	def move( self ) :
		# Movement
		self.current_floor += self.movement
		# People have to leave
		for c in self.calls :
			if c.destiny_floor == self.current_floor :
				self.current_capacity += 1
				self.calls.remove( c )
				self.num_calls -= 1

	def __str__( self ) :
		s  = "Elevator ID = %s\n" % self.ID
		s += "Current Floor = %s\n" % self.current_floor
		s += "Destiny Floor = %s\n" % self.destiny_floor
		s += "Total Capacity = %s\n" % self.total_capacity
		s += "Current Capacity = %s\n" % self.current_capacity
		return s
	
if __name__ == "__main__" :
	e = Elevator()
	print e
