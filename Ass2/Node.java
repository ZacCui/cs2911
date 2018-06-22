public class Node{
	private String name;
	private int unloadingCost;
	public Node(){}
	
	public Node(String location, int cost){
		this.name = location;
		this.unloadingCost = cost;
	}
	/**
	 * Get the Node location name
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set Node location name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * get Unloading cost
	 * @return
	 */
	public int getUnloadingCost() {
		return unloadingCost;
	}
	/**
	 * Set unloading cost
	 * @param unloadingCost
	 */
	public void setUnloadingCost(int unloadingCost) {
		this.unloadingCost = unloadingCost;
	}
	
	
}