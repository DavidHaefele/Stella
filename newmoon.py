import sys
import ephem

date = sys.argv[1]
nm = ephem.next_new_moon(date)

print (nm)
