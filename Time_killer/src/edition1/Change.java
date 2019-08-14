package edition1;
/**
 * @author Wither
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Change {
	public static String exist(String name, String filePath) throws IOException {
		String result = "";
		BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(filePath), "GBK"));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String splited[] = line.split("·");
			if (name.equals(splited[0])) {
				result = line;
			}
		}
		br.close();
		return result;
	}
	
	public static boolean change_this(String name, String info, String filePath) throws IOException {
		String temp = "";
		boolean result = true;
		BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(filePath), "GBK"));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String splited[] = line.split("·");
			if (name.equals(splited[0])) {
				temp = temp + info;
				result = true;
			}
			else {
				temp = temp + line + "\r\n";
			}
		}
		br.close();
		BufferedWriter bw = 
				new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(filePath), "GBK"));
		bw.write(temp);
		bw.close();
		return result;
	}
}
