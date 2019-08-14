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

public class Sign_table_get_input {

	public static String[][][] by_path(String filePath) throws IOException {
		String[][][] res= {
				{{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}}, 
				{{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}}, 
				{{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}}, 
				{{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}}, 
				{{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}}
		};
		int[][] name = {
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0}
		};
		BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(filePath), "GBK"));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String details[] = line.split("·");
			int time = Find_by_time.cast_time(details[6]);
			int day = Find_by_time.cast_day(details[5]);
			if ((day >= 6)||(time > 5)||(name[time][day] == 4)) continue;
			else res[time][day][name[time][day]++] += details[0];
		}
		br.close();
		return res;
	}

	public static String[][][] with_dep(String filePath) throws IOException {
		String[][][] res= {
				{{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}}, 
				{{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}}, 
				{{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}}, 
				{{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}}, 
				{{"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}, {"", "", ""}}
		};
		int[][] name = {
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0}
		};
		BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(filePath), "GBK"));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String details[] = line.split("·");
			int time = Find_by_time.cast_time(details[6]);
			int day = Find_by_time.cast_day(details[5]);
			if ((day >= 6)||(time > 5)||(name[time][day] == 3)) continue;
			else res[time][day][name[time][day]++] += details[0]+"·"+ details[1];
		}
		br.close();
		return res;
	}
}
