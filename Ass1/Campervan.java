

public class Campervan {
	private int id;
	private String name;
	private String transmission;
	
	
	public Campervan() {}
	public Campervan(int id, String name, String transmission) {
		this.id = id;
		this.name = name;
		this.transmission = transmission;
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



	public String getTransmission() {
		return transmission;
	}



	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}



	public boolean isAvailable(){
		
		return true;
	}
}
