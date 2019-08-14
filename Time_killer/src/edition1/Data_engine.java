package edition1;
/**
 * 
 * @author Wither
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Data_engine {
	private static final String[] day = {
			"", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private static final String[] time = {
    		"第一大节", "第二大节","第三大节","第四大节","第五大节"};
    private static final int[] dd = {
    		1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 6
    };
    private static final int[] tt = {
    		0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4
    };
    
	public static AvlTree load(String filePath) throws IOException {
		AvlTree root = new AvlTree();
		BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(filePath), "GBK"));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			root.insert(line);
		}
		br.close();
		return root;
	}
	
	public static boolean change(AvlTree t, String name, int changeToShift) {
	    Node n = t.root;    
		while(n != null) {
			int compareResult = AvlTree.compare(n.data.split("·")[0], name);
	        if( compareResult > 0 )  
	            n = n.left;  
	        else if( compareResult < 0 )  
	            n = n.right;  
	        else{
	        	Student st = new Student(n.data);
	        	st.rota_day = day[dd[changeToShift]];
	        	st.rota_class = time[tt[changeToShift]];
	        	String a = st.toString();
	        	n.data = a.substring(0, a.length()-2);
	        	return true;
	        }
	    }  
	    return false;
	}
	
	public static void save(AvlTree t, String filePath) throws IOException {
		BufferedWriter out =
				new BufferedWriter(
						new OutputStreamWriter(
								new FileOutputStream(filePath), "GBK"));
        out.write(t.travel(t.root));
        out.close();
	}
	
}
