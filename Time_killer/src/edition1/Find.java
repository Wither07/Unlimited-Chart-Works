package edition1;
/**
 * @author Wither
 *
 *
 */
import java.io.IOException;

public class Find {

	public static String[][] give_a_result
			(String name, 
			String department, 
			String day, 
			String time, 
			String filePath) throws IOException {
		String result[][] = {
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""}
			};
		if (!name.equals("")) {
			result = Find_by_name.find(filePath, name);
		}
		else {
			if ((department.equals("-不限-"))&&(day.equals("-不限-"))&&(time.equals("-不限-"))) {
				result = Find_all_aviliable.all_aviliable(filePath);
			}
			if ((department.equals("-不限-"))&&(day.equals("-不限-"))&&(!time.equals("-不限-"))) {
				result = Find_by_time.by_time(filePath, time);
			}
			if ((department.equals("-不限-"))&&(!day.equals("-不限-"))&&(time.equals("-不限-"))) {
				result = Find_by_time.by_day(filePath, day);
			}
			if ((department.equals("-不限-"))&&(!day.equals("-不限-"))&&(!time.equals("-不限-"))) {
				result = Find_by_time.by_day_and_time(filePath, day, time);
			}
			if ((!department.equals("-不限-"))&&(day.equals("-不限-"))&&(time.equals("-不限-"))) {
				result = Find_by_department.all_aviliable(filePath, department);
			}
			if ((!department.equals("-不限-"))&&(day.equals("-不限-"))&&(!time.equals("-不限-"))) {
				result = Find_by_department.by_time(filePath, department, time);
			}
			if ((!department.equals("-不限-"))&&(!day.equals("-不限-"))&&(time.equals("-不限-"))) {
				result = Find_by_department.by_day(filePath, department, day);
			}
			if ((!department.equals("-不限-"))&&(!day.equals("-不限-"))&&(!time.equals("-不限-"))) {
				result = Find_by_department.by_day_and_time(filePath, department, day, time);
			}
		}
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				result[i][j] = result[i][j].replace("~", " ");
			}
		}
		return result;
	}

}
