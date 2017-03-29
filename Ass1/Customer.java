import java.util.Calendar;

public class Customer {
	private int id;
	private String name;
	
	public Customer() {}
	
	public Customer(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Booking makeBooking(){
		Booking book = new Booking();
		return book;
	}
	
	public void changeBooking(){
		
	}
	
	public void deleteBooking(){
		
	}
	
}
