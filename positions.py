import sys
import ephem

planet = sys.argv[1]
date = sys.argv[2]

gatech = ephem.Observer()
gatech.lon = '10.0933'
gatech.lat = '48.83777'
gatech.elevation = 430
gatech.date = date + ' 23:00:00'

if planet == 'jupiter':
    p = ephem.Jupiter(gatech)

elif planet == 'saturn':
    p = ephem.Saturn(gatech)

elif planet == 'mars':
    p = ephem.Mars(gatech)

elif planet == 'venus':
    p = ephem.Venus(gatech)

elif planet == 'mercury':
    p = ephem.Mercury(gatech)

elif planet == 'uranus':
    p = ephem.Uranus(gatech)

pos = ephem.constellation(p)

print ('You can find ' + planet.capitalize() + ' in the constellation ' + pos[1] + '.')
