# SymmetryExplorer

About the code: Java files written in Java version 1.8.0_60, using Eclipse Mars Version 4.5.0.  
                Python script written in 3.4, and requires graphics.py to work.
#
#
To run: Run the python script in the folder containing the .txt files.  The java code produces the text files that I've uploaded.  If you'd like to reproduce the .txt tiles yourself, compile and run the java files as a project using Eclipse for example. 
#
#
About the project: This project started as an attempt to prove whether or not closed curves on surfaces satisfying a certain mathematical property could exist.  More specifically, on a surface of genus g>=2, are there pairs of simple closed curves which minimally intersect and fill the surface? A collection of curves is said to "fill" the surface if the complement of the surfaces is a disjoint union of simply connected pieces.  These collections of curves have connections with other areas of geometry.  With the help of the examples generated by this code, I proved that the answer was in general affirmative.  There are other interesting questions that remain about these curves, and this project grew as an attempt to understand some of their symmetries and hidden structure.
#
#
What the code is doing: If you were to run the java files, the code would iterate through all possible pairs of simple closed curves, and check whether or not they fill.  If the code detects a pair that does, it prints information related to those curves to a text file.  I have uploaded a few such text files to this repo, so running the java files is not required.  If you'd like to look under the hood and reproduce the text files, use the genus variable in Drawtester3.java to change which surface to create the file for.

To see the visualizations, run the python code.  At the prompt enter a genus corresponding to a text file (3 or 4 if using the text files I uploaded), and enter a number for which example you would like to look at.  The graphic you see is a gluing pattern of the complement of the curves.  In a way it represents a type of symmetry present in the example you chose.

