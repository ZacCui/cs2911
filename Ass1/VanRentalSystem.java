import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class VanRentalSystem {
	private static HashMap<String, Depot> depot = new HashMap<String, Depot>();
	private static ArrayList<Booking> booking = new ArrayList<Booking>();
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH MMM dd");
	private static ArrayList<String> key = new ArrayList<String>();
	private static Request request;
	
	//main function
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
				   if(!key.contains(words[1])) key.add(words[1]);
				   updateData(words[1],words[2],words[3]);
				   break;
			   case "Request" :
				   // check the request is fulfilled before make bookings
				   try{
					   makeBooking(line);
				   }catch (ParseException e){
					   System.out.println("Booking rejected");
				   }
				   // make booking
				   break;
			   case "Cancel" :
				   delete(words[1]);
				   break;
			   case "Change" :
				   try{
					   makeBooking(line);
				   }catch (ParseException e){
					   System.out.println("Change rejected");
				   }
				   break;
			   case "Print" :
				   try{
					   print(words[1]);
				   }catch (ParseException e){
				   }
				   break;
			   default :
				   break;
	    	}
	    }
        if (sc != null) sc.close();
	}
	
	//=============================Helper functions ===========================
	
	/**
	 * Split a string into words
	 * @param  sc A normal string
	 * @return A string-array contains one-word string
	 */
	public static String[] readline(String sc){
		String[] words = sc.split("\\s");
		return words;
	}
	
	/**
	 * check the booking whether contains the request ID
	 * @param list A Array List with Booking type arraylist
	 * @param name String contain ID
	 * @return If Id already in the booking Array list, then return true. Otherwise false
	 */
	public static boolean contains(ArrayList<Booking> list, String name) {
	    for (Booking item : list) {
	        if (item.getId().equals(name)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * update depots and Campervans data
	 * @param loc String contains location information
	 * @param name String contains name of campervan
	 * @param Trans String contains Transimission
	 */
	public static void updateData(String loc, String name, String Trans){
		if(depot.get(loc) == null){
			Depot D = new Depot(loc,name,Trans);
			depot.put(loc, D);
		}else{
			depot.get(loc).addCampervan(name, Trans);
		}
	}
	
	/**
	 * * Store Request
	 * @param line The string contains Request information
	 * @throws ParseException Request rejected
	 */
	public static void updateRequest(String line)throws ParseException {
		String words[] = readline(line);
		// get the num of requested cars
		int numOfM = 0, numOfA = 0, i = 0;
		try{
			for(; words[i] != null; i++){
				if(words[i].contains("Manual")) {
					numOfM = Integer.parseInt(words[i - 1]);
				}
				if(words[i].contains("Automatic")) {
					numOfA = Integer.parseInt(words[i - 1]);
				}
			}
		}catch(ArrayIndexOutOfBoundsException e){ }
		
		// get the startDate
		String date = words[2]+" "+words[3]+" "+words[4];
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(sdf.parse(date));
		startDate.set(Calendar.YEAR, 2017);
		
		//get the endDate
		Calendar endDate = Calendar.getInstance();
		date = words[5]+" "+words[6]+" "+words[7];
		endDate.setTime(sdf.parse(date));
		endDate.set(Calendar.YEAR, 2017);
		
		// store into request
		request = new Request(words[1],numOfA,numOfM,startDate,endDate);
	}
	
	/**
	 * Make/change booking
	 * @param line The string contains Request information
	 * @throws ParseException Request rejected
	 */
	public static void makeBooking(String line) throws ParseException{
		String words[] = readline(line);
		if(words[0].equals("Change") && booking.size() == 0) {
			System.out.println("Change rejected");
			return;
		}
		updateRequest(line);
		int i, numOfA = request.getNumOfA(), numOfM = request.getNumOfM();
		Calendar startDate = request.getStartDate(), endDate = request.getEndDate();
		boolean entered = false;
		if(contains(booking,words[1]) && words[0].equals("Request")){
			System.out.println("Booking rejected");
			return;
		}
		// check depot
		for(String Key: key){
			if(numOfA == 0 && numOfM == 0) break;
			for(i = 0;depot.get(Key).getSize() > i; i++){
				if(!available(words[1],startDate,endDate,Key,i)) continue;
				if(((!contains(booking,words[1])) || words[0].equals("Change"))
						&& !entered){
					Booking b = new Booking(words[1], startDate, endDate);
					booking.add(b);
					entered = true;
				}
				if(depot.get(Key).isManual(i) && numOfM > 0){
					booking.get(booking.size()-1).createBooking(Key, i);
					numOfM--;
				}
				if( (!depot.get(Key).isManual(i)) && numOfA > 0){
					booking.get(booking.size()-1).createBooking(Key, i);
					numOfA--;
				}
			}
		}
		//check the booking can be fulfilled or not
		if(numOfA != 0 || numOfM != 0){
			if(words[0].equals("Request")){
				System.out.println("Booking rejected");
			}else{
				System.out.println("Change rejected");
			}
			if(entered){
			   booking.remove(booking.size()-1);
			}
			   return;
		}
		if(words[0].equals("Change")){
			int temp = findindex(words[1]);
			booking.remove(temp);
		}
		
		printBooking(line);
	}
	
	/**
	 * 
	 * @param id Booking id
	 * @param src StartDate
	 * @param end EndDate
	 * @param loc Depot location
	 * @param camp Campervan id
	 * @return If the campervan available to be booked, then return true. Otherwise false
	 */
	public static boolean available(String id,Calendar src, Calendar end, String loc, int camp){
		for(Booking b: booking){
			if(b.getId().equals(id)) continue;
			if(!b.contain(loc, camp)) continue;
			if(!b.checkSession(src, end)) return false;
		}
		return true;
	}
	
	/**
	 * Find the index of the booking has the special ID
	 * @param ID Booking id
	 * @return If find the index of the bookingid then return the index. Otherwise -1
	 */
	public static int findindex(String ID){
		int i;
		if(ID.contains("\\")){
			ID = ID.substring(0, ID.length()-1);
		}
		for(i = 0; booking.size() > i;i++){
			if(booking.get(i).getId().equals(ID)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Delete the booking with the input ID
	 * @param ID Booing ID
	 */
	
	// delete booking
	public static void delete(String ID){		
		int i = 0;
		if(ID.contains("\\")){
			ID = ID.substring(0, ID.length()-1);
		}
		for(;booking.size() > i;i++){
			if(booking.get(i).getId().equals(ID)){
				booking.remove(i);
				System.out.println("Cancel "+ID);
				return;
			}
		}
		System.out.println("Cancel rejected");
	}
	
	/**
	 * Print Booking detail after make/change booking
	 * @param line The string contains Request information
	 */
	
	// Print booking detail after the book has been made 
	public static void printBooking(String line){
		String words[] = readline(line);
		int index = findindex(words[1]);
		String last = "start";
		boolean changed = false;
		if(words[0].equals("Request")){
			System.out.print("Booking "+ words[1] + " ");
		}else if(words[0].equals("Change")){
			System.out.print("Change "+ words[1] + " ");
		}
		for(int i = 0;booking.get(index).getLocSize()>i;i++){
			if(last.equals("start")){
				last = booking.get(index).getlocName(i);
				System.out.print(last+" ");
				changed = true;
			}else if(!last.equals(booking.get(index).getlocName(i))){
				last = booking.get(index).getlocName(i);
				System.out.print("; "+last+" ");
				changed = true;
			}
			String car = depot.get(last).getCampName(booking.get(index).getCarID(i));
			if(changed){
				System.out.print(car);
			}else{
				System.out.print(", "+car);
			}
			changed = false;			
		}
		System.out.println();
	}
	/**
	 * Print out bookings of all campervans at loc, in order of campervan declarations, then date/time;
	 * @param loc Depot location
	 * @throws ParseException Request rejected
	 */
	
	// Print the booking within a particular depot
	public static void print(String loc) throws ParseException{
		if(booking.size() == 0) return;
		if(depot.size() == 0) return;
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm MMM dd");
		int i;
		if(loc.contains("\\")){
			loc = loc.substring(0, loc.length()-1);
		}
		for(i = 0; depot.get(loc).getSize() > i; i++){
			ArrayList<String> sL = new ArrayList<String>();
			for(Booking b: booking){
				if(!b.contain(loc, i)) continue;
				String a1 = sdf1.format(b.getST());
				String b1 = sdf1.format(b.getET());
				String s = (loc +" "+depot.get(loc).getCampName(i)+" "
						+a1+" "
						+b1);
				sL.add(s);
			}
			sort(sL);
			for(String item: sL){
				System.out.println(item);
			}
		}
	}
	/**
	 * Sort the booking information with the same campervan
	 * @param s String contains campervan bookings infomation
	 * @throws ParseException Do nothing
	 */
	
	// sort the print by its time order
	public static void sort(ArrayList<String> s) throws ParseException{
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm MMM dd");
		Calendar A = Calendar.getInstance();
		Calendar B = Calendar.getInstance();
		int swaps = 0, j;
		for(j = 0; s.size() > j; j++){
			swaps = 0;
			for(int i = 0; i < s.size()-1;i++){
				String[] temp = readline(s.get(i));
				String date = temp[2]+" "+temp[3]+" "+temp[4];
				A.setTime(sdf1.parse(date));
				temp = readline(s.get(i+1));
				date = temp[2]+" "+temp[3]+" "+temp[4];
				B.setTime(sdf1.parse(date));
				if(A.after(B)){
					Collections.swap(s, i, i+1);
					swaps++;
				}
			}
			if(swaps == 0) break;
		}			
	}
	
}
