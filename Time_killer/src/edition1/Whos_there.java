package edition1;
/**
 * @author Wither
 *which is 刘俊贤
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Whos_there {

	public static String find(String filePath) throws IOException {
		String res = "";
		BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(filePath), "GBK"));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			res = res + line.split("·")[0] + "\n";
		}
		br.close();
		return res;
	}
}
