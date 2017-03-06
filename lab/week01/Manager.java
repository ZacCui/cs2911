import java.util.Date;

public class Manager extends Employee {
	private Date hireDate;
	
	public Date getHireDate() {
		return hireDate;
	}
	
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	
	public String toString(){
		return super.toString() + " Hire Date = " + hireDate;
	}
	
	public boolean equals(Manager m){
		return (super.equals(m) && 
				m.getHireDate() == this.getHireDate() );
	}
	
	public Object clone(){
		Manager cloned = (Manager)super.clone();
		return cloned;
	}
}