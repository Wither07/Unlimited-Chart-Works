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
import java.io.RandomAccessFile;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class All_in_one implements Comparator<String> {
	public static List<String> list;
	
	public All_in_one() {
		
	}
	
	public int compare(String o1, String o2) {
		return Collator.getInstance(Locale.CHINESE).compare(o1, o2); 
	}
	
	public static void getData(String filePath) throws IOException {
		if (list == null) {
			list = new ArrayList<String>();
		}
		BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(filePath), "GBK"));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			list.add(line);
		}
		br.close();
	}
	
	public static void inOne(String filePath) throws IOException {
		if(list == null) {
			return;
		}
		String[] data = new String[list.size()];
		data = list.toArray(data);
		Arrays.sort(data, new All_in_one());
		list = Arrays.asList(data);
		RandomAccessFile raf=new RandomAccessFile(filePath,"rw");
		long fileLength = raf.length();   
		raf.seek(fileLength);
		String res = list.toString();
		String processed = res.substring(1, res.length()-1);
		processed = processed.replace(", ", "\r\n");
		raf.write(processed.getBytes("GBK"));
		raf.write("\r\n".getBytes("GBK"));
		raf.close();
	}
	
	/*
	public static void main(String args[]) throws IOException {
		getData("C:\\Users\\Wither\\Desktop\\_.txt");
		getData("C:\\Users\\Wither\\Desktop\\1.txt");
		inOne("C:\\Users\\Wither\\Desktop\\3.txt");
	}
	*/
}
