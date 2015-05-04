from utils import *
import os
import re
import matplotlib.pyplot as plt

def makePlot( data , xlabel , ylabel , filename ) :
	plt.plot( data[ 'points' ][ 0 ] , data[ 'points' ][ 1 ] , 'ob' )
	for i in range( len( data[ 'points' ][ 0 ] ) ) :
		plt.plot( data[ 'lines' ][ 0 ][ i ] , data[ 'lines' ][ 1 ][ i ] , 'b-' , linewidth = 2.0 )
	plt.xlabel( xlabel )
	plt.ylabel( ylabel )
	plt.savefig( filename )
	plt.clf()

def makePlots( directory ) :
	files = groupFiles( directory )
	xlabel = ( 'Iterations' if directory.find( 'alpha' ) < 0 else 'Alpha' )
	for key in files :
		lstFiles = files[ key ]
		costfilename = directory + '/../results/cost_' + key + '_' + xlabel.lower()
		timefilename = directory + '/../results/time_' + key + '_' + xlabel.lower()
		costdata = { 'points': [ [] , [] ] , 'lines': [ [] , [] ] }
		timedata = { 'points': [ [] , [] ] , 'lines': [ [] , [] ] }
		for f in lstFiles :
			stats = extractInfo( f )
			costdata[ 'points' ][ 0 ].append( stats[ 2 ] )
			costdata[ 'points' ][ 1 ].append( stats[ 3 ][ 'mean' ] )
			costdata[ 'lines' ][ 0 ].append( [ stats[ 2 ] , stats[ 2 ] ] )
			costdata[ 'lines' ][ 1 ].append( [ stats[ 3 ][ 'min' ] , stats[ 3 ][ 'max' ] ] )

			timedata[ 'points' ][ 0 ].append( stats[ 2 ] )
			timedata[ 'points' ][ 1 ].append( stats[ 4 ][ 'mean' ] )
			timedata[ 'lines' ][ 0 ].append( [ stats[ 2 ] , stats[ 2 ] ] )
			timedata[ 'lines' ][ 1 ].append( [ stats[ 4 ][ 'min' ] , stats[ 4 ][ 'max' ] ] )
		makePlot( costdata , xlabel , 'Custo' , costfilename )
		makePlot( timedata , xlabel , 'Tempo (segundos)' , timefilename )

if __name__ == "__main__" :
	directory = '/Users/nonwhite/Dropbox/IME/Laboratorio IA/LabIA_P2/data/expalpha/out/'
	makePlots( directory )
	directory = '/Users/nonwhite/Dropbox/IME/Laboratorio IA/LabIA_P2/data/expiterations/out/'
	makePlots( directory )
