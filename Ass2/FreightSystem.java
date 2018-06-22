import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class FreightSystem {
	private static ArrayList<String> Path = new ArrayList<String>();
	private static int Cost;

	public static void main(String args[]){
		Map M = new Map();
		ArrayList<Job> jobs = new ArrayList<Job>();
		Scanner sc = null;
	    try
	    {
	    	sc = new Scanner(new FileReader(args[0]));   
	    }
	    catch (FileNotFoundException e) {
	    	System.out.println("Error: No input file");
	    }
	    while(sc.hasNextLine()){
	    	String line = sc.nextLine();
	    	String[] words = readline(line);
	    	switch (words[0]){
			   case "Unloading" :
				   M.addNode(words[2], Integer.parseInt(words[1]));
				   break;
			   case "Cost" :
				   M.insertEdge(words[2], words[3], Integer.parseInt(words[1]));
				   break;
			   case "Job" :
				   M.addJob(new Job(words[1], words[2]));
				   break;
			   default :
				   break;
	    	}
	    }
	    M.Astar();
	   
        if (sc != null) sc.close();
	}
	
//=============================Helper functions ===========================
	public static String[] readline(String sc){
		String[] words = sc.split("\\s");
		return words;
	}
	
	
}
