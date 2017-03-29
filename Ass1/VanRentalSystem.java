import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class VanRentalSystem {
	private static ArrayList<Customer> customer;
	private static HashMap<String, Depot> depot;
	private static HashMap<String, Booking> booking;
	
	
	public static void main(String args[]){
		Scanner sc = null;
	    try
	    {
	    	sc = new Scanner(new FileReader(args[0]));   
	    }
	    catch (FileNotFoundException e) {
	    	System.out.println("Error: No input file");
	    }
	   
	    // input data in to Depot and Campervans
	    while(sc.hasNextLine()){
	    	String line = sc.nextLine();
	    	String[] words = readline(line);
	    	switch (words[0]){
			   case "Location" :
				   //update depot and campervans
				   updateData(words[1],words[2],words[3]);
				   break;
			   case "Request" :
				   // check the request is fulfilled before make bookings
				   // make booking
				   break;
			   case "delete" :
				   break;
			   case "change" :
				   break;
			   case "print" :
				   break;
			   default :
				   break;
	    	}
	    	sc.nextLine();  
	    }
	    // check the request is leagel or not 
	    
	    // check depot has the campervan
	    
	    // check the campervan is available or not
	      
	  
        if (sc != null) sc.close();
	}
	
	//=============================Helpful functions ===========================
	// print the the bookings with 
	public void print(Depot depot){
		
	}
	
	// split the line int words
	public static String[] readline(String sc){
		String[] words = sc.split("\\s+");
		return words;
	}
	
	public static boolean contains(ArrayList<Depot> list, String name) {
	    for (Depot item : list) {
	        if (((Depot) item).getLocation() == name) {
	            return true;
	        }
	    }
	    return false;
	}
	
	// update depots and Campervans data
	public static void updateData(String loc, String name, String Trans){
		if(depot.get(loc) == null){
			Depot D = new Depot(loc,name,Trans);
			depot.put(loc, D);
		}else{
			depot.get(loc).addCampervan(name, Trans);;
		}
	}
	
	public static boolean canFulfilled(String line){
		String words[] = readline(line);
		// get the num of requested cars
		int numOfM = 0, numOfA = 0, i = 0;
		for(; words[i] != null; i++){
			if(words[i] == "Manual") numOfM = Integer.parseInt(words[i - 1]);
			if(words[i] == "Automatic") numOfA = Integer.parseInt(words[i - 1]);
		}
		
					
		
		// check depot
		
		// check car
		return true;
	}
}
