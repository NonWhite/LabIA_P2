class Floor :
	def __init__( self , ID = 1 ) :
		self.ID = ID
		self.up_alert_state = 0
		self.up_alert_time = None
		self.down_alert_state = 0
		self.down_alert_time = None
		self.people = []
	
	def __str__( self ) :
		s =  "Floor ID = %s\n" % self.ID
		s += "Up alert state = %s\n" % self.up_alert_state
		s += "Up alert time = %s\n" % self.up_alert_time
		s += "Down alert state = %s\n" % self.down_alert_state
		s += "Down alert time = %s\n" % self.down_alert_time
		s += "Number of people = %s\n" % len( self.people )
		return s

if __name__ == "__main__" :
	f = Floor()
	print f
