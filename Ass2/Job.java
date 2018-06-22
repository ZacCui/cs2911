
public class Job {
	private String start;
	private String end;
	public Job(){}
	public Job(String src, String end){
		this.start = src;
		this.end = end;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
}
