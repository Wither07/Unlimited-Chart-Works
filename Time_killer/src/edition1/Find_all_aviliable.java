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

public class Find_all_aviliable {
	public static String[][] all_aviliable(String filePath) throws IOException {
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
			for (int i = 0; i < 35; ++i) {
				if (line.split("·")[9].charAt(i) == '0') {
					result[i / 7][i % 7]+=line.split("·")[0]+"~";
				}
			}
		}
		br.close();
		return result;
	}
	/*
	public static void main(String args[]) {
		String[][] res = null;
		try {
			res = all_aviliable("D:\\_JavaWorkspace\\Time_killer\\DarkFlameContract.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 7; ++j) {
				System.out.println(i+" "+j);
				System.out.println(res[i][j]);
			}
		}
	}
	*/
}
