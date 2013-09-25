package k.daniel.timedtask.bean;

public class ExecutableTask {
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private long time;
	private int taskType;
	
	public ExecutableTask(long time, int taskType) {
		super();
		this.time = time;
		this.taskType = taskType;
	}
	public ExecutableTask() {
		// TODO Auto-generated constructor stub
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getTaskType() {
		return taskType;
	}
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
}
