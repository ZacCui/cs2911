import java.util.ArrayList;

public class Depot {
	private String location;
	private ArrayList<Campervan> campervan;
	
	public Depot(){}
	
	public Depot(String loc, String name, String Trans){
		this.location = loc;
		this.campervan = new ArrayList<Campervan>();
		Campervan C = new Campervan(0,name, Trans);
		this.campervan.add(C);
	}
	
	// add a car into current depot
	public void addCampervan(String name, String Trans ){
		Campervan C = new Campervan(this.getSize(),name, Trans);
		this.campervan.add(C);
	}
	
	public Depot(String location, ArrayList<Campervan> campervan) {
		this.location = location;
		this.campervan = campervan;
	}
	
	public int getNumOfA(){
		int i=0,num = 0;
		for(;this.campervan.size() > i; i++){
			if(this.campervan.get(i).getTransmission().contains("Automatic")){
				num++;
			}
		}
		return num;
	}
	
	public int getNumOfM(){
		return (this.campervan.size()-this.getNumOfA());
	}
	
	public int getSize(){
		return campervan.size();
	}
	
	public void addCampervan(Campervan C){
		campervan.add(C);
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public ArrayList<Campervan> getCampervan() {
		return campervan;
	}
	
	public void setCampervan(ArrayList<Campervan> campervan) {
		this.campervan = campervan;
	}
	
	public boolean isManual(int campID){
		return (this.campervan.get(campID).getTransmission().contains("Manual"));
	}
	
	public String getCampName(int index){
		return this.campervan.get(index).getName();
	}

}
