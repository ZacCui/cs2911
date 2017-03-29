import java.util.Calendar;

public class Booking {
	private String id;
	private int custID;
	private int campID[];
	private Calendar startDate;
	private Calendar endDate;
	public Booking() {}
	public Booking(String id, int custID, int numOfVans, int[] depotID, int[] campID, Calendar startDate,
			Calendar endDate) {
		this.id = id;
		this.custID = custID;
		this.campID = campID;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCustID() {
		return custID;
	}
	public void setCustID(int custID) {
		this.custID = custID;
	}
	public int[] getCampID() {
		return campID;
	}
	public void setCampID(int[] campID) {
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
	
	
	
}
