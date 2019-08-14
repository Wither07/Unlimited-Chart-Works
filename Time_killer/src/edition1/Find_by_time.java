package edition1;
/**
 * @author Wither
 *
 *
 */
import java.io.IOException;

public class Find_by_time {
	public static int cast_time(String time) {
		int i_time = 9;
		switch(time) {
			case "第一大节":
				i_time = 0;
				break;
			case "第二大节":
				i_time = 1;
				break;
			case "第三大节":
				i_time = 2;
				break;
			case "第四大节":
				i_time = 3;
				break;
			case "第五大节":
				i_time = 4;
				break;
			default:
				break;
		}
		return i_time;
	}
	public static int cast_day(String day) {
		int i_day = 9;	
		switch(day) {
			case "星期一":
				i_day = 0;
				break;
			case "星期二":
				i_day = 1;
				break;
			case "星期三":
				i_day = 2;
				break;
			case "星期四":
				i_day = 3;
				break;
			case "星期五":
				i_day = 4;
				break;
			case "星期六":
				i_day = 5;
				break;
			case "星期日":
				i_day = 6;
				break;
			default:
				break;
		}
		return i_day;
	}
	public static int get_rota_time(int time, int day) {
		return day * 7 + time;
	}
	public static String[][] by_day_and_time
	(String filePath, String day, String time) throws IOException {
		String list = "";
		list = Find_all_aviliable.all_aviliable(filePath)[cast_time(time)][cast_day(day)];
		String result[][] = new String[1][1];
		result[0][0] = list;
		return result;
	}
	public static String[][] by_day
	(String filePath, String day) throws IOException {
		int casted_day = cast_day(day);
		String result[][] = new String[5][1];
		String temp[][] = Find_all_aviliable.all_aviliable(filePath);
		for (int i = 0; i < 5; i++) {
			result[i][0] = temp[i][casted_day];
		}
		return result;
	}
	public static String[][] by_time
	(String filePath, String time) throws IOException {
		String result[][] = new String[1][7];
		String temp[] = Find_all_aviliable.all_aviliable(filePath)[cast_time(time)];
		for (int i = 0; i < 7; i++) {
			result[0][i] = temp[i];
		}
		return result;
	}
	/*
	public static void main(String args[]) {
		String[][] res = null;
		String filePath = "D:\\_JavaWorkspace\\Time_killer\\DarkFlameContract.txt";
		String day = "星期一";
		String time = "第一大节";
		try {
			res = by_time(filePath, time);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < res.length; ++i) {
			for (int j = 0; j < res[i].length; ++j) {
				System.out.println(i+" "+j);
				System.out.println(res[i][j]);
				}
			}
		}
	*/
}
