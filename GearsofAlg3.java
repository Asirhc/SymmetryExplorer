import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
//This program solves the topological problem of determining whether a pair of simple closed geodesics on a topological surface are "minimal filling curves" or not
//that is, whether or not the complement of the curves is a single topological disk.  
//Pairs of simple closed curves can be encoded by a permutation of labeled symbols, along with an orientation (+ or -) attached to each symbol.
//For a given genus surface, this program iterates through all possibilities and outputs which ones are minimally filling.


public class GearsofAlg3 
{
	public ArrayList<int[]> boundseq;
	public int genus;
	public int[][] points;
	public int[] test2;
	int numthatworks;
	public ArrayList<String> goodcombs;
	public ArrayList<String> goodones;
	
	public GearsofAlg3(int g)
	{
		genus=g;
		goodcombs=new ArrayList<String>();
		goodones=new ArrayList<String>();
		test2=new int[2*genus-1];
	 	this.LoopThroughPermutations(2*genus-2);  //for convenience, pass in one less than the # of intersections
	}
	
	//This method takes in the combinatorial description of the curves and outputs whether or not it minimally fills.
	public boolean doesitfill(int[] seq, int[] orients)			
	{
		//seq is a cycle, it tells you which junctions are connected to which, starting at junction 1.  
		//For example, seq =132, tells you to connect bands from spot 1, to 3, to 2.  The orientation tells you how to connect them.
		//orient is the list of orientations, in counter-clockwise order starting with the first junction, 1 is +, 0 is -
		
		int length=seq.length;
		int flag=0;   //this determines whether you are moving on a band associated to curve 1 (flag=0), or curve 2
		points=new int[4*seq.length][3];  
									// 1st spot indicates which "junction" of first curve you're on, 
								  // 2nd spot indicates left (0) or right (1), 3rd indicates up (1) or down (0)
		
		boundseq = new ArrayList<int[]>(); // this is what tracks which points you're traveling along the boundary.
		points[0][0]=0;  //start at the 0th junction, upper right corner
		points[0][1]=1;
		points[0][2]=1;
		int count=0;

		while(doesitcontain(boundseq, points[count])==false)
		{
			int following=(count+1)%(4*seq.length);
			boundseq.add(points[count]);
			if(flag==0)  //this means you're just traveling left or right
			{
				if (points[count][1]==1)   //if you're moving along curve 1, and you're on the right, you move right
				{
					points[following][0]=(points[count][0]+1) % length;
					points[following][2]=points[count][1];
					points[following][1]=0;
				}
				if (points[count][1]==0)  //if you're moving along curve 1, and you're on the left, you must move left
				{
					points[following][0]=(points[count][0]+length - 1)% length;
					points[following][2]=points[count][1];
					points[following][1]=1;
				}	
			}
			if(flag==1)	//this means we're traveling on curve 2 and must jump around the diagram
			{
				//next is the following junction you are moving to.  because the bands jump around various junctions
				//next will jump around the array seq
				int next=seq[(((this.findLocation(seq, points[count][0]) + 1)% length)+length)%length];	
				int prev=seq[(((this.findLocation(seq, points[count][0]) - 1)% length)+length)%length];

				if(points[count][2]==1)  //if you're on the top and moving along curve 2, there are several cases
				{
					if(orients[points[count][0]]==1) //if your current point has positive orientation, you must advance forward
					{		//top left moves to lower left, top right moves to lower right
						if(orients[next]==1)
						{	points[following][0]=(next);
							points[following][2]=(points[count][2]+1)% 2;
							points[following][1]=points[count][1];
						}
						if(orients[next]==0)
						{	points[following][0]=(next);
							points[following][1]=(points[count][1]+1) % 2;
							points[following][2]=points[count][2];
						}
					}	
					if(orients[points[count][0]]==0) //if your current point has negative orientation, you must go backwards
					{		//top left moves to top right, top right moves to top left
						if(orients[prev]==0)
						{	points[following][0]=(prev);
							points[following][2]=(points[count][2]+1) % 2;
							points[following][1]=points[count][1];
						}
						if(orients[prev]==1)
						{	points[following][0]=(prev);
							points[following][1]=(points[count][1]+1)% 2;
							points[following][2]=points[count][2];
						}
					}	
				}
				if(points[count][2]==0)  //if you're on the bottom and moving along curve 2, there are several cases
				{
					if(orients[points[count][0]]==1) //if your point has positive orientation
					{		//bottom right moves to bottom left, bottom left moves to bottom right		
						if(orients[prev]==0)
						{	points[following][0]=(prev);
							points[following][1]=(points[count][1]+1)%2;
							points[following][2]=points[count][2];
						}
						if(orients[prev]==1)
						{	points[following][0]=(prev);
							points[following][2]=(points[count][2]+1)%2;
							points[following][1]=points[count][1];
						}
					}
					if(orients[points[count][0]]==0) //if your point has negative orientation
					{		//bottom right moves to top right, bottom left moves to top left
						if(orients[next]==0)	
						{	points[following][0]=(next);
							points[following][2]=(points[count][2]+1)%2;
							points[following][1]=points[count][1];
						}
						if(orients[next]==1)
						{	points[following][0]=(next);
							points[following][1]=(points[count][1]+1)%2;
							points[following][2]=points[count][2];
						}
					}
				}	
			}
			flag=(flag+1)%2;
			count=(count+1)%((4*seq.length));
		}
		if(boundseq.size()<4*seq.length)
			{
				return false;
			}
		else {
			goodones.add(this.outputboundseq());
			return true;
		}	
	}
	//Helper methods
	public int findLocation(int[] a, int key)
	{
		for(int i=0;i<a.length;i++)
		{
			if(a[i]==key) {return i;}
		}
		return -1;
	}
	public void printboundseq()
	{
		for(int i=0;i<boundseq.size();i++)
		{
			System.out.println(boundseq.get(i)[0]+ ", "+boundseq.get(i)[1]+", "+boundseq.get(i)[2]);;
		}
	}
	public String outputboundseq()
	{				
		String temp=new String();
		
		for(int i=0;i<boundseq.size();i++)
		{
			temp=temp+boundseq.get(i)[0]+ ","+boundseq.get(i)[1]+","+boundseq.get(i)[2] + "..";
		}
		return temp;
	}
	public void printGoodOnestoFile()
	{
		//print to a file only if the file hasn't been created already
		File f = new File("FillingPairInfoGenus"+genus+".txt");
		if(!f.exists()) { 
			PrintWriter out = null;
			try {
			    out = new PrintWriter(new BufferedWriter(new FileWriter("FillingPairInfoGenus"+genus+".txt", true)));
			    for(int i=0;i<goodones.size();i++)
				{
					out.println(i + " : "+ goodcombs.get(i)+" ; "+goodones.get(i));
				}
			}catch (IOException e) {
			    System.err.println(e);
			}finally{
			    if(out != null){
			        out.close();
			    }
			}
		}
	}
	public boolean doesitcontain(ArrayList<int[]> a, int[] point)
	{	for(int i=0;i<a.size();i++)
		{	if (a.get(i)[0]==point[0]&&a.get(i)[1]==point[1]&&a.get(i)[2]==point[2]){ return true;}
		}
		return false;
	}
	//factorial method for fun
	public static int factorial(int f) 
	{
	    return ((f == 0) ? 1 : f * factorial(f - 1)); 
	}  
	//Loop through all of the permutations of N symbols 
	public void LoopThroughPermutations(int N)
	{
		//int totalcount=0;
	    int[] old = new int[N];
	    int[] current=new int[N];
	    int[] tester=new int[N+1];							// you only need to loop through 1/2 the orientations since once you check that many, any other is a repeat
		for(int j1=0;j1<Math.pow(2,(2*genus-2))-1;j1++)				//loop through orientations and within each orientation class, test all the permutations
		{	
			String or=Integer.toBinaryString(j1);
			for(int j2=0;j2<or.length();j2++)
			{test2[2*genus-1-or.length()+j2]=Character.getNumericValue(or.charAt(j2));}	
			int numthatworks=0;		
			for (int i = 0; i < N; i++) old[i] = i;
			for (int i12 = 1; i12 < factorial(N); i12++) 
			{
				current = Arrays.copyOf(old, N);
				for(int i123=1;i123<N+1;i123++)
				{
					tester[0]=0;
					tester[i123]=current[i123-1]+1;
				}
				if((this.doesitfill(tester, test2)==true) && (tester[0]==0))
				{
					goodcombs.add(this.ArraytoString(tester)+ " , " +this.ArraytoString(test2));
					numthatworks++;		
				}
				int k, l;
				for (k = N - 2; current[k] >= current[k+1]; k--);
				for (l = N - 1; current[k] >= current[l]; l--);
				swap(current, k, l);
				for (int j = 1; k+j < N-j; j++) swap(current, k+j, N-j);
				old=current;								
			}
			if(numthatworks==0){ System.out.println("*************");}
			System.out.println(numthatworks/2 + "     " + this.ArraytoString(test2));
			if(numthatworks==0){ System.out.println("*************");}
		}
	}
	
	//permutation methods
	private static void swap(int[] is, int k, int l) {
	    int tmp_k = is[k];
	    int tmp_l = is[l];
	    is[k] = tmp_l;
	    is[l] = tmp_k;
	}
	public void permutation(String str) 
	{ 
		permutation("", str); 
	 }
	public void permutation(String prefix, String str) 
	{
	    int n = str.length();
	    if (n == 0) 
	    {
	    	if(this.doesitfill(StringtoArray("0"+prefix),test2)==true)
	    		{
	    			System.out.println("0"+prefix);
	    		}
	    }
	     else 
	     {
	        for (int i = 0; i < n; i++)
	           permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}
	//more generic helper methods
	public int[] StringtoArray(String s)
	{
		int[] sta=new int[s.length()];
		for(int i=0;i<s.length();i++)
		{
			sta[i]=Character.getNumericValue(s.charAt(i));
		}
		return sta;
	}
	public String ArraytoString(int[] a)
	{
		String s=new String();
		for(int i=0;i<a.length;i++)
		{
			s=s+"."+Integer.toString(a[i]);
		}
		return s;
	}
	
}
