package edition1;
/**
 * @author Wither
 *
 *
 */
import java.io.IOException;

public class Find_by_name {
	public static String[][] find(String filePath, String name) throws IOException {
		String result[][] = new String[5][7];
		String info = Change.exist(name, filePath);
		if(!info.equals("")) {
			info = info.split("·")[9];
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < result[i].length; j++) {
					if(info.charAt(i * 7 + j) == '0') 
						result[i][j] = "无课";
					else
						result[i][j] = "有课";
				}
			}
		}
		return result;
	}
	/*
	public static void main(String args[]) {
		String[][] res = null;
		String name = "seb18";
		try {
			res = find("C:\\Users\\Wither\\Desktop\\DarkFlameContract.txt", name);
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
