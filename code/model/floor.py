

class Floor :
	def __init__( self , ID = 1 , up_alert_state = 0 , down_alert_state = 0 , up_alert_time = None , down_alert_time = None , people = [] ) :
		self.ID = ID
		self.up_alert_state = up_alert_state
		self.up_alert_time = up_alert_time
		self.down_alert_state = down_alert_state
		self.down_alert_time = down_alert_time
		self.people = people
	
	def __str__( self ) :
		s =  "ID = %s\n" % self.ID
		s += "Up alert state = %s\n" % self.up_alert_state
		s += "Up alert time = %s\n" % self.up_alert_time
		s += "Down alert state = %s\n" % self.down_alert_state
		s += "Down alert time = %s\n" % self.down_alert_time
		s += "Number of people = %s\n" % len( self.people )
		return s

if __name__ == "__main__" :
	f = Floor()
	print f
