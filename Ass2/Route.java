import java.util.ArrayList;

public class Route implements Comparable<Route>{
	private Node current;
	private String lastVisited;
	private int totalCost;
	private double fCost;
	private ArrayList<Node> routes;
	private ArrayList<Job> jobs;
	private double totalJobs;
	private double jobsLeft;
	
	
	public double getJobsLeft() {
		return jobsLeft;
	}
	public void setJobsLeft(double jobsLeft) {
		this.jobsLeft = jobsLeft;
	}
	public double getTotalJobs() {
		return totalJobs;
	}
	public void setTotalJobs(double totalJobs) {
		this.totalJobs = totalJobs;
	}
	public ArrayList<Job> getJobs() {
		return jobs;
	}
	public void setJobs(ArrayList<Job> jobs) {
		this.jobs = jobs;
	}
	public Route(){}
	public Route(Node current, int totalCost, int totalJobs) {
		this.current = current;
		this.totalCost = totalCost;
		this.totalJobs = totalJobs;
	}
	public Node getCurrent() {
		return current;
	}
	public void setCurrent(Node current) {
		this.current = current;
	}
	public int getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	public double getfCost() {
		return fCost;
	}
	public void setfCost(double fCost) {
		this.fCost = fCost;
	}
	public ArrayList<Node> getRoutes() {
		return routes;
	}
	public void setRoutes(ArrayList<Node> routes) {
		this.routes = routes;
	}
	public String getName(){
		return current.getName();
	}
	public String getLastVisited() {
		return lastVisited;
	}
	public void setLastVisited(String lastVisited) {
		this.lastVisited = lastVisited;
	}
	public double h(){
		return (this.totalCost*1.9*(this.jobsLeft/this.totalCost));
	}
	public double h1(){
		return current.getUnloadingCost()*(this.jobsLeft/this.totalJobs);
	}
	
	public void setFcost(){
		this.fCost = this.h() + this.totalCost;
	}
	@Override
	public int compareTo(Route o) {
		if(this.equals(o)){
			return 0;
		}else if(this.fCost > o.fCost) {
			return 1;
		}else {
			return -1;
		}
	}
}
