package k.daniel.timedtask.datasource;

public class TaskWeekUtil {
	/**
	 * Input a int state,return a boolean array with the binary state
	 * @param intState
	 * @return
	 */
	public static boolean[] getWeekState(int intState)
	{
		boolean weekState[]=new boolean[7];
		for (int j = 0; j < weekState.length; j++) {
			//判断第j位是否为0，若为0的话返回false,否则返回true
			weekState[j]=(intState&(1<<j))==0? false:true;
		
		}
		return weekState;
	}
	
	public static int getIntState(boolean weekState[])
	{
		int intState=0;
		for (int i = 0; i < weekState.length; i++) {
			if (weekState[i]) {
			intState=intState|(1<<i);
			}
		}

		return intState;
	}
}
