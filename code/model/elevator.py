class Elevator :
	def __init__( self , ID = 1 , current_floor = 1 , total_capacity = 5 , state = 0 ) :
		self.ID = ID
		self.current_floor = current_floor
		self.destiny_floor = None
		self.total_capacity = total_capacity
		self.current_capacity = total_capacity
		self.state = state
	
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
