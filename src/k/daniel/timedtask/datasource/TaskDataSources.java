package k.daniel.timedtask.datasource;

import java.util.List;

import k.daniel.timedtask.bean.TimedTask;

public interface TaskDataSources {
	public List<TimedTask> getAllTask();
	public void setAllTask(List<TimedTask> timedTasks);
	public void addTask(TimedTask timedTask);
	public boolean deleteTask(TimedTask timedTask);
	public boolean deleteAllTask();
	public boolean updateTask(TimedTask timedTask);
	public boolean close();
	public int count();
}
