import java.util.Calendar;

public class Request {
	private String id;
	private int numOfA;
	private int numOfM;
	private Calendar startDate;
	private Calendar endDate;
	
	public Request(String id, int numOfA, int numOfM, Calendar startDate, Calendar endDate) {
		this.id = id;
		this.numOfA = numOfA;
		this.numOfM = numOfM;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Request(){}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getNumOfA() {
		return numOfA;
	}
	
	public void setNumOfA(int numOfA) {
		this.numOfA = numOfA;
	}
	
	public int getNumOfM() {
		return numOfM;
	}
	
	public void setNumOfM(int numOfM) {
		this.numOfM = numOfM;
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
