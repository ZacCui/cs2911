import java.lang.String;
import java.util.Calendar;

public class Employee implements Cloneable{
	private String name;
	private double salary;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public String toString(){
		return getClass().getName() + ":name =" + name + " salary = " + salary;
	}
	
	public boolean equals(Object Obj){
		Employee e = (Employee) Obj;
		return (this.getName() == e.getName() 
				&& this.getSalary() == e.getSalary());
	}
	
	public Object clone(){
		try{
			Employee cloned = (Employee)super.clone();
			return cloned;
		}catch(CloneNotSupportedException e){
			return null;
		}
	}
	
	public static void main(String args[]){
		Employee e = new Employee();
		Manager m = new Manager();
		Calendar calendar = Calendar.getInstance();
		e.setName("Jack");
		e.setSalary(200);
		m.setName("Jack");
		m.setSalary(200);
		m.setHireDate(calendar.getTime());
		
		//the expected output of e.getClass().getName() is Employee 
		System.out.println(e.toString());
		//the expected output of m.getClass().getName() is Manager
		System.out.println(m.toString());
		
		//clone test
		Employee eCloned = (Employee) e.clone();
		System.out.println("eCloned Value is same as e " + e.equals(eCloned));
		System.out.println("e == eCloned ? " + (e == eCloned));
		System.out.println("Same Class ? " + (e.getClass() == eCloned.getClass()));
		
		//Manager == Employee ?
		System.out.println("Manager is equal to Employee vice " + e.equals(m));
		System.out.println("Manager is equal to Employee versa " + m.equals(e));
		
		//Clone Manager
		Manager mCloned = (Manager) m.clone();
		mCloned.setHireDate(null);
		System.out.println("Does original m changed by changing mCloned " + m.equals(mCloned));
		System.out.println("The value of changed one " + mCloned.getHireDate());
		System.out.println("The value of original one " + m.getHireDate());
		
		//Can Manager can be called by object Employee
		Employee emp = new Manager();
		emp.setName("Bob");
		emp.setSalary(666);
		System.out.println(emp);
		//YES
		
	}
	
}


