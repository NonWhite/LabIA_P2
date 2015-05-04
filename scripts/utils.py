import os

def extractParameters( filename ) :
	filename = filename[ :-4 ]
	s = filename.split( '_' )
	experiment = ( 'iterations' if s[ 1 ].find( 'alpha' ) < 0 else 'alpha' )
	typeOfCost = s[ 2 ]
	value = ( float( s[ 3 ] ) / 100.0 if experiment == 'alpha' else int( s[ 3 ] )  )
	return ( experiment , typeOfCost , value )

def stats( lst ) :
	mini = pow( 2 , 30 )
	maxi = -1
	mean = 0
	for x in lst :
		mini = min( mini , x )
		maxi = max( maxi , x )
		mean += x
	mean = float( mean ) / float( len( lst ) )
	return { 'min': mini , 'max': maxi , 'mean': mean }

def extractInfo( filename ) :
	( experiment , typeOfCost , value ) = extractParameters( filename )
	lstTime = []
	lstCost = []
	with open( filename , 'r' ) as f :
		for line in f :
			if line.find( 'Execution Time' ) >= 0 :
				lstTime.append( float( line.split( ':' )[ 1 ] ) / 1000.0 )
			if line.find( 'Cost' ) >= 0 :
				lstCost.append( int( line.split( '=' )[ 1 ] ) )
	lstCost = stats( lstCost )
	lstTime = stats( lstTime )
	return ( experiment , typeOfCost , value , lstCost , lstTime )

def getMean( lst ) :
	s = 0
	for x in lst : s += x
	return float( s ) / float( len( lst ) )

def groupFiles( directory ) :
	files = os.listdir( directory )
	resp = {}
	for f in files :
		params = extractParameters( directory + '/' + f )
		if params[ 1 ] not in resp.keys() : resp[ params[ 1 ] ] = []
		resp[ params[ 1 ] ].append( directory + '/' + f )
	return resp
