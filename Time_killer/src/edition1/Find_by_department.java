package edition1;
/**
 * @author Wither
 *
 *
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Find_by_department {
	public static String[][] all_aviliable
	(String filePath, String department) throws IOException{
		String result[][] = {
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""},
				{"", "", "", "", "", "", ""}
		};
		BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(filePath), "GBK"));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String details[] = line.split("·");
			if (details[1].equals(department)) {
			for (int i = 0; i < 35; ++i) {
					if (details[9].charAt(i) == '0') {
						result[i / 7][i % 7]+=line.split("·")[0]+"~";
					}
				}
			}
		}
		br.close();
		return result;
	}
	public static String[][] by_time
	(String filePath, String department, String time) throws IOException {
		String result[][] = new String[1][7];
		String temp[] = all_aviliable(filePath, department)[Find_by_time.cast_time(time)];
		for (int i = 0; i < 7; i++) {
			result[0][i] = temp[i];
		}
		return result;
	}
	public static String[][] by_day
	(String filePath, String department, String day) throws IOException {
		int casted_day = Find_by_time.cast_day(day);
		String result[][] = new String[5][1];
		String temp[][] = all_aviliable(filePath, department);
		for (int i = 0; i < 5; i++) {
			result[i][0] = temp[i][casted_day];
		}
		return result;
	}
	public static String[][] by_day_and_time
	(String filePath, String department, String day, String time) throws IOException {
		String list = "";
		list = all_aviliable(filePath, department)[Find_by_time.cast_time(time)][Find_by_time.cast_day(day)];
		String temp[] = list.split("~");
		String result[][] = new String[1][1];
		result[0][0] = temp[0];
		for (int i = 1; i < temp.length; i++) {
			result[0][0] += "~" + temp[i];
		}
		return result;
	}
	/*
	public static void main(String args[]) {
		String[][] res = null;
		String department = "公关部";
		String day = "星期一";
		String time = "第一大节";
		try {
			res = by_day_and_time("C:\\Users\\Wither\\Desktop\\_.txt", department, day, time);
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
