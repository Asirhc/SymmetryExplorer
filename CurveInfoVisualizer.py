
# coding: utf-8

# # Boundary symmetry visualizer
# Written in Python 3.4
# Requires the graphics.py module
#
# This code analyzes the text output of the java code.  The text files contain data regarding the symmetries of curves on low genus surfaces.  This python code produces a simple graphic displaying the symmetry pattern in a separate window, using the graphics.py module.
# Run the code, enter a genus for the surface (right now, only 3 4 and 5 work, anything else will produce an error).
# Then enter which curve you'd like to look at.  The code will display the symmetry pattern for that curve.  I've used this code to make various conjectures.

import math
from graphics import *

#read from the desired text file
def readfile(genus):  
    global f
    f = open("FillingPairInfogenus"+str(genus)+".txt",'r')
    global l
    l = f.readlines()

def curvereader(genus,check):

    print("Genus "+str(genus)+" Record "+str(check))
    print("Curve Combinatorics: " + l[check][l[check].index(':')+2:l[check].index(';')-1])
    print("Boundary Pattern: "+l[check][l[check].index(';')+2:])

    #Make list of polygon tuples for graphics.py
    gsize=4*genus - 2
    polyvertices=[]
    for nk in range(gsize):
        polyvertices.append(Point(math.floor(200+150*math.cos((nk/gsize)*2*math.pi)),math.floor(200+150*math.sin((nk/gsize)*2*math.pi))))


    #Read off symmetry pattern from the file
    pattern = l[check][l[check].index(';')+2:]
    edge=list()
    for k in range(gsize):
        edge.append(list())
        edge[k].append(int(pattern[14*k+0]))
        edge[k].append(int(pattern[14*k+2]))
        edge[k].append(int(pattern[14*k+4]))

    #Compute matched edges
    for i in range(gsize):
        for j in range(gsize):
            if(edge[i][1]==1 and edge[i][2]==1 and edge[j][0]==(edge[i][0]+1)%(gsize/2) and edge[j][1]==0 and edge[j][2]==0):
                edge[i].append(j)
                edge[j].append(i)
            if(edge[i][1]==0 and edge[i][2]==1 and (edge[j][0]+1)%(gsize/2)==edge[i][0] and edge[j][1]==1 and edge[j][2]==0):
                edge[i].append(j)
                edge[j].append(i)
            if(edge[i][1]==1 and edge[i][2]==0 and edge[j][0]==(edge[i][0]+1)%(gsize/2) and edge[j][1]==0 and edge[j][2]==1):
                edge[i].append(j)
                edge[j].append(i)
            if(edge[i][1]==0 and edge[i][2]==0 and (edge[j][0]+1)%(gsize/2)==edge[i][0] and edge[j][1]==0 and edge[j][2]==1):
                edge[i].append(j)
                edge[j].append(i)

    #Compute coordinates of lines from midpoints of matched edges of the polygon
    matchcoords=list()
    for jk in range(gsize):
        scoord=Point((polyvertices[jk].getX()+polyvertices[(jk+1) % gsize].getX())/2,(polyvertices[jk].getY()+polyvertices[(jk+1) % gsize].getY())/2) 
        ecoord=Point((polyvertices[edge[jk][3]].getX()+polyvertices[(edge[jk][3]+1)%gsize].getX())/2,(polyvertices[edge[jk][3]].getY()+polyvertices[(edge[jk][3]+1)%gsize].getY())/2)
        matchcoords.append([scoord,ecoord])
    
    #Draw image
    win = GraphWin('Polygon', 400, 400) # give title and dimensions
    poly = Polygon(polyvertices)
    poly.draw(win)

    for line in matchcoords:
        match=Line(line[0],line[1])
        match.draw(win)

    message = Text(Point(win.getWidth()/2, 20), 'Click anywhere to go back to prompt.')
    message.draw(win)    
    info = Text(Point(win.getWidth()/2, 380), "Curve #"+str(check)+" Combinatorics: " + l[check][l[check].index(':')+2:l[check].index(';')-1])
    info.draw(win)
    win.getMouse()
    win.close()

def main():    
    realtime=0
    rgenus=int(input("Please enter a genus curve to examine (3, 4, or 5 only): "))
    readfile(rgenus)

    while realtime!=-1:

        realtime=int(input("There are "+str(len(l))+" records.  Please enter the record # for the curve you'd like to check, or type -1 to quit."))
    ## add trys and throws if it can't read the input
        if realtime==-1: 
            break

        if realtime<=len(l):
            curvereader(3,realtime)
    
    return

main()
