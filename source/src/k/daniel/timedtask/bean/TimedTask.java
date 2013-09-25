package k.daniel.timedtask.bean;

import java.io.Serializable;

public class TimedTask implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = -1;
	private int hour;
	private int minute;
	// 任务类型
	private int taskType;
	// 是否启用
	private boolean avaliable;
	// 启用日期
	private boolean[] weekState;

	public TimedTask() {

	}

	public TimedTask(int hour, int minute, int taskType, boolean avaliable,
			boolean[] weekState) {
		super();
		id = -1;
		this.hour = hour;
		this.minute = minute;
		this.taskType = taskType;
		this.avaliable = avaliable;
		this.weekState = weekState;
	}

	public TimedTask(int id, int hour, int minute, int taskType,
			boolean avaliable, boolean[] weekState) {
		super();
		this.id = id;
		this.hour = hour;
		this.minute = minute;
		this.taskType = taskType;
		this.avaliable = avaliable;
		this.weekState = weekState;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public boolean isAvaliable() {
		return avaliable;
	}

	public void setAvaliable(boolean avaliable) {
		this.avaliable = avaliable;
	}

	public void setAvaliable(int avaliable) {
		if (avaliable == 1) {
			setAvaliable(true);
		} else {
			setAvaliable(false);
		}

	}

	public boolean[] getWeekState() {
		return weekState;
	}

	public void setWeekState(boolean[] weekState) {
		this.weekState = weekState;
	}

}
