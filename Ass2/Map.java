import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Map{
	private ArrayList<Job> jobs;
	private ArrayList<Node> node;
	private HashMap<String, HashMap<String, Integer>> edge;
	
	
	public ArrayList<Job> getJobs() {
		return jobs;
	}

	public void setJobs(ArrayList<Job> jobs) {
		this.jobs = jobs;
	}

	public Map(){
		ArrayList<Node> node = new ArrayList<Node>();
		ArrayList<Job> jobs = new ArrayList<Job>();
		HashMap<String, HashMap<String, Integer>> edge = new HashMap<String, HashMap<String, Integer>>();
		this.node = node;
		this.edge = edge;
		this.jobs = jobs;
	}
	
	public void addJob(Job j){
		this.jobs.add(j);
	}
	
	public int getCost(String src, String end){
		return this.edge.get(src).get(end);
	}
	
	public void setNode(ArrayList<Node> node) {
		this.node = node;
	}

	public void setEdge(HashMap<String, HashMap<String, Integer>> edge) {
		this.edge = edge;
	}

	public void addNode(String name, int cost){
		Node n = new Node(name, cost);
		this.node.add(n);
	}
	
	public void addNode(Node n){
		this.node.add(n);
	}
	
	public void insertEdge(String a, String b, int v){
		if(!this.edge.containsKey(a)){
			HashMap<String, Integer> H = new HashMap<String, Integer>();
			H.put(b, v);
			this.edge.put(a, H);
		}else{
			this.edge.get(a).put(b, v);
		}
		if(!this.edge.containsKey(b)){
			HashMap<String, Integer> H1 = new HashMap<String, Integer>();
			H1.put(a, v);
			this.edge.put(b, H1);
		}else{
			this.edge.get(b).put(a, v);
		}
	}

	public ArrayList<Node> getNode() {
		return node;
	}

	public HashMap<String, HashMap<String, Integer>> getEdge() {
		return edge;
	}
	
	public String getNodeName(int index){
		return this.node.get(index).getName();
	}
	
	public int getNodeCost(int index){
		return this.node.get(index).getUnloadingCost();
	}
	
	public Node getNode(int index){
		return this.node.get(index);
	}
	
	public int getNumNodes(){
		return this.node.size();
	}
	
	public boolean connected(String src, String end){
		return this.edge.get(src).containsKey(end);
	}
	
	public ArrayList Astar(){
		Route curr = new Route();
		for(Node name : this.node){
			if(name.getName().equals("Sydney")){
				curr = new Route(name,0,this.jobs.size());
				curr.setJobs(this.jobs);
				ArrayList<Node> r = new ArrayList<Node>(); 
				r.add(name);
				curr.setRoutes(r);
				curr.setJobsLeft(this.jobs.size());
				curr.setFcost();
			}
		}
		PriorityQueue<Route> Q = new PriorityQueue<Route>();
		int numOfNodes = 0;
		Q.add(curr);
		while(!curr.getJobs().isEmpty()){
			curr = Q.remove();
			printRoutes(curr);
			printJobs(curr);
			numOfNodes++;
			if(curr.getJobs().isEmpty()) break;
			for(int i = 0; i < this.node.size();i++){
				if(!this.connected(curr.getName(), this.getNodeName(i))) continue;
				Route Next = new Route();
				int EdgeCost;
				if(isStart(curr.getJobs(), curr.getName())
						&& isEnd(curr.getJobs(), this.getNodeName(i))){
					//left job-1
					ArrayList<Job> newJob = new ArrayList<Job>();
					newJob.addAll(curr.getJobs());
					Next.setJobs(newJob);
					removeJob(Next.getJobs(),curr.getName(),this.getNodeName(i));
					Next.setJobsLeft(Next.getJobs().size());
				}else{
					ArrayList<Job> newJob = new ArrayList<Job>();
					newJob.addAll(curr.getJobs());
					Next.setJobs(newJob);
					Next.setJobsLeft(curr.getJobsLeft());
				}
				EdgeCost = this.getCost(curr.getName(), this.getNodeName(i));
				printJobs(Next);

				System.out.println(curr.getJobsLeft());
				Next.setCurrent(this.getNode(i));
				Next.setTotalJobs(curr.getTotalJobs());
				Next.setTotalCost(curr.getTotalCost()+this.getCost(curr.getName(), this.getNodeName(i)));
				ArrayList<Node> newRoutes = new ArrayList<Node>();
				newRoutes.addAll(curr.getRoutes());
				newRoutes.add(this.getNode(i));
				Next.setRoutes(newRoutes);
				Next.setLastVisited(curr.getName());
				//Next.setFcost();
				Next.setfCost(EdgeCost*Next.getJobsLeft()+curr.getTotalCost());
				//System.out.println(Next.getfCost());
				if(!Q.contains(Next)) Q.add(Next);
			}
		}
		int cost = caculateCost(curr.getTotalCost());
		for(int j =0; j<curr.getRoutes().size();j++){
			System.out.println(curr.getRoutes().get(j).getName());
		}
		System.out.println("Node expanded "+numOfNodes);
		System.out.println("Routes cost(without unloading) "+cost);
		print(curr);
		return null;
	}
	
	public boolean isStart(ArrayList<Job> jobs,String src){
		for(int i = 0; i < jobs.size(); i++){
			if(src.equals(jobs.get(i).getStart())){
				return true;
			}
		}
		return false;
	}
	
	public boolean isEnd(ArrayList<Job> jobs, String end){
		for(int i = 0; i < jobs.size(); i++){
			if(end.equals(jobs.get(i).getEnd())){
				return true;
			}
		}
		return false;
	}
	
	public void removeJob(ArrayList<Job> jobs, String src, String end){
		for(int i = 0; i < jobs.size(); i++){
			if(src.equals(jobs.get(i).getStart())
					&& end.equals(jobs.get(i).getEnd())){
				jobs.remove(i);
			}
		}
	}
	
	public void updateJobs(Route route){
		int j = 0;
		ArrayList<Job> temp = new ArrayList<Job>();
		temp.addAll(this.jobs);
		for(int i = 0; i<route.getRoutes().size()-1;i++){
			if(isStart(temp,route.getRoutes().get(i).getName())
				&& isEnd(temp,route.getRoutes().get(i+1).getName())){
				j++;
				removeJob(temp,route.getRoutes().get(i).getName(),route.getRoutes().get(i+1).getName());
			}
		}	
	}
	
	public void printRoutes(Route curr){
		for(int j =0; j<curr.getRoutes().size();j++){
			System.out.print(curr.getRoutes().get(j).getName()+"->");
		}
	}
	
	public void printJobs(Route curr){
		System.out.println();
		for(int j =0; j<curr.getJobs().size();j++){
			System.out.print(curr.getJobs().get(j).getStart()+"-"+curr.getJobs().get(j).getEnd()+" ");
		}
	}
	public int jobsCostLeft(Route curr){
	int cost = 0;
	for(int i = 0; i < curr.getJobs().size(); i++){
		cost =cost + this.getCost(curr.getJobs().get(i).getStart(), curr.getJobs().get(i).getEnd());
	}
	return cost;
	}
	
	public void print(Route curr){
		for(int i = 0; i<curr.getRoutes().size()-1;i++){
			if(isStart(this.jobs,curr.getRoutes().get(i).getName())
				&& isEnd(this.jobs,curr.getRoutes().get(i+1).getName())){
				System.out.println("Job"+" "+curr.getRoutes().get(i).getName()+ " to "+curr.getRoutes().get(i+1).getName());
			}else{
				System.out.println("Empty"+" "+curr.getRoutes().get(i).getName()+ " to "+curr.getRoutes().get(i+1).getName());
			}
		}	
	}
	
	public int caculateCost(int cost){
		int unloadingCost = 0;
		for(int i = 0; i<this.jobs.size();i++){
			for(int j = 0; j<this.node.size(); j++){
				if(this.node.get(j).getName().equals(this.jobs.get(i).getEnd())){
					unloadingCost = unloadingCost + this.node.get(j).getUnloadingCost();
				}
			}
		}
		return cost + unloadingCost;
	}
}