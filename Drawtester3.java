import javax.swing.SwingUtilities;

// This file works with GearsofAlg3.java to output the combinatorics of pairs of simple closed curves which fill on surfaces of the genus given.
// There was originally more functionality in this code, but I replaced it with some python scripting
public class Drawtester3 {

    public int gsize;
    
    public Drawtester3() {
   
    	
    	//Edit the "genus" integer to look at different surfaces.
    	//Genus should be a positive integer >=3.  There are enormous amounts of computations to do once genus>=6, so the code may be sluggish at that point.
    	int genus=6;

    	System.out.println("Testing genus "+genus+" for filling pairs of simple closed curves.");
    	
    	int[][] edge =new int[4*genus-2][4];
    	GearsofAlg3 G=new GearsofAlg3(genus);
    	G.printGoodOnestoFile();
    }
    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Drawtester3();
            }
        });
    }
}