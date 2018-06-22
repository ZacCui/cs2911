import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Booking {
	private String id;
	private Calendar startDate;
	private Calendar endDate;
	private ArrayList<String> location;
	private ArrayList<Integer> campID;
	
	public Booking() {}
	
	public Booking(String id, Calendar src, Calendar end){
		this.id = id;
		this.startDate = src;
		this.endDate = end;
	}
	
	public String getlocName(int index){
		return this.location.get(index);
	}
	
	public int getLocSize(){
		return this.location.size();
	}
	
	public ArrayList<String> getLocation() {
		return location;
	}
	
	public void setLocation(ArrayList<String> location) {
		this.location = location;
	}
	
	//Update the location and car data into booking
	public void createBooking(String loc, int carID){
		if(this.location == null){
			ArrayList<String> s = new ArrayList<String>();
			this.location = s;
		}
		if(this.campID == null){
			ArrayList<Integer> i = new ArrayList<Integer>();
			this.campID = i;
		}
		this.location.add(loc);
		this.campID.add(carID);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getCarID(int index){
		return this.campID.get(index);
	}
	
	public ArrayList<Integer> getCampID() {
		return campID;
	}
	
	public void setCampID(ArrayList<Integer> campID) {
		this.campID = campID;
	}
	
	public Calendar getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
	public Calendar getEndDate() {
		return endDate;
	}
	
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public boolean contain(String locID, int campID){
		int i = 0;
		if(this.location == null) return false;
		for(String l: this.location){
			if(l.equals(locID) && (this.campID.get(i).equals(campID))){
				return true;
			}
			i++;
		}
		return false;
	}
	
	public boolean checkSession(Calendar src, Calendar end){
		if(src.after(end)) return false;
		if(this.startDate.after(end)) return true;
		if(this.endDate.before(src)) return true;
		return false;
	}
	
	public Date getST(){
		return this.startDate.getTime();
	}
	
	public Date getET(){
		return this.endDate.getTime();
	}
	
}
