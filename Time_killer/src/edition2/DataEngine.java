package edition2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class DataEngine {
    private static final String[]  department = {"公关部", "纪检部","权益部","事务部","文化部"};
	
	public Root loadData(String filePath) {
		Root r = new Root();
		try {
			System.out.println("Start to load...");
			loadDep(r, filePath);
			System.out.println("Loaded!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return r;
	}
	
	private void loadDep(Root r, String filePath) throws Exception {
		Student gg_curr = r.gg;
		Student jj_curr = r.jj;
		Student qy_curr = r.qy;
		Student sw_curr = r.sw;
		Student wh_curr = r.wh;
		BufferedReader br =
				new BufferedReader(
						new InputStreamReader(
								new FileInputStream(filePath), "GBK"));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			Student temp = new Student(line);
			if (temp.department.equals(department[0])) {
				if (gg_curr == null) {
					r.gg = temp;
					gg_curr = temp;
				} else {
					gg_curr.nextDep = temp;
					gg_curr = temp;
				}
			} else if (temp.department.equals(department[1])) {
				if (jj_curr == null) {
					r.jj = temp;
					jj_curr = temp;
				} else {
					jj_curr.nextDep = temp;
					jj_curr = temp;
				}
			} else if (temp.department.equals(department[2])) {
				if (qy_curr == null) {
					r.qy = temp;
					qy_curr = temp;
				} else {
					qy_curr.nextDep = temp;
					qy_curr = temp;
				}
			} else if (temp.department.equals(department[3])) {
				if (sw_curr == null) {
					r.sw = temp;
					sw_curr = temp;
				} else {
					sw_curr.nextDep = temp;
					sw_curr = temp;
				}
			} else if (temp.department.equals(department[4])) {
				if (wh_curr == null) {
					r.wh = temp;
					wh_curr = temp;
				} else {
					wh_curr.nextDep = temp;
					wh_curr = temp;
				}
			}
		}
		br.close();
	}
}
