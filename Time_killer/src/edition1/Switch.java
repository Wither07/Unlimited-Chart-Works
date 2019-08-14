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
import java.util.ArrayList;
import java.util.List;

public class Switch {
	public static String info;
	public static List<String> mid_person;
	public static int cast(String info) {
		String details[] = info.split("·");
		String rota_day = details[5];
		String rota_class = details[6];
		int day = 100;
		int classes = 100;
		switch(rota_day) {
			case "星期一":
				day = 0;
				break;
			case "星期二":
				day = 1;
				break;
			case "星期三":
				day = 2;
				break;
			case "星期四":
				day = 3;
				break;
			case "星期五":
				day = 4;
				break;
			case "星期六":
				day = 5;
				break;
			case "星期日":
				day = 6;
				break;
			default:
				break;
		}
		switch(rota_class) {
		case "第一大节":
			classes = 0;
			break;
		case "第二大节":
			classes = 1;
			break;
		case "第三大节":
			classes = 2;
			break;
		case "第四大节":
			classes = 3;
			break;
		case "第五大节":
			classes = 4;
			break;
		default:
			break;
	}
		int res = day + classes * 7;
		return res;
		//res = 800表示均未安排值班时间失败
		//res = 100、107、114、121、128表示大节未安排
		//res = 700、701、702、703、704、705、706表示星期未安排
	}
	public static void get_info(String name, String filePath) throws IOException {
		info = Change.exist(name, filePath);
	}
	public static String double_switch(String name, String filePath) throws IOException {
		get_info(name, filePath);
		int a_rota = cast(info);
		if(a_rota < 100) {
			if (!info.equals("")) {
				String solution = "两人换班:\n";
				String a_ts = info.split("·")[9];
				BufferedReader br =
						new BufferedReader(
								new InputStreamReader(
										new FileInputStream(filePath), "GBK"));
				for (String line = br.readLine(); line != null; line = br.readLine()) {
					if ((!line.equals(""))&&
							(!name.equals(line.split("·")[0]))) {
						String splited[] = line.split("·");
						int b_rota = cast(line);
						if (b_rota < 100) {
							if ((splited[9].charAt(a_rota) == '0')&&
									(a_ts.charAt(b_rota) == '0')) {
								solution = solution + "跟 " + splited[0] + " 换班\n";
							}
						}
						else if(b_rota == 800) {
							if (splited[9].charAt(a_rota) == '0') {
								solution = solution + "让 " + splited[0] + " 替班\n";
							}
						}
					}
				}
				br.close();
				if (solution.equals("两人换班:\n")) {
					solution = solution + "无方案\n";
				}
				return solution;
			}
			else {
				return "";
			}
		}
		else {
			return info.split("·")[0] + "不值班!";
		}
	}
	public static void find_mid(String name, String filePath) throws IOException {
		get_info(name, filePath);
		if (!info.equals("")) {
			mid_person = new ArrayList<String>();
			int rota = cast(info);
			BufferedReader br =
					new BufferedReader(
							new InputStreamReader(
									new FileInputStream(filePath), "GBK"));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				if (rota<100)	 {
					if ((!line.equals(""))&&
					(!name.equals(line.split("·")[0]))) {
						String splited[] = line.split("·");
						if (splited[9].charAt(rota) == '0') {
							mid_person.add(line);
						}
					}
				}	
			}
			br.close();
		}
	}
	public static String find_fin(String mid, String filePath) throws IOException {
	 	String solution = "";
	 	if (!info.equals("")) {
		 	String info_ts = info.split("·")[9];
		 	int mid_rota = cast(mid);
			BufferedReader br =
					new BufferedReader(
							new InputStreamReader(
									new FileInputStream(filePath), "GBK"));
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				if (mid_rota < 100) {	
					if ((!line.equals(""))&&
						(!info.split("·")[0].equals(line.split("·")[0]))&&
						(!line.split("·")[0].equals(mid.split("·")[0]))) {
						String splited[] = line.split("·");
						int line_rota = cast(line);
						if (line_rota < 100) {
							if ((splited[9].charAt(mid_rota) == '0')&&
									(info_ts.charAt(line_rota) == '0')) {
								solution = solution + splited[0] + " ";
							}
						}
					}
				}
			}
			br.close();
			return solution;
	 	}
	 	else {
	 		return "";
	 	}
	}
    public static String triple_switch(String name, String filePath) throws IOException {
    	int a_rota = cast(info);
		if(a_rota < 100) {
	    	if (!info.equals("")) {
	    		String solution = "三人换班:\n";
		    	String fin = "";
		    	find_mid(name, filePath);
				for ( int i=0; i<mid_person.size(); i++) {   
					String mid_student = mid_person.get(i);
					fin = find_fin(mid_student, filePath);
					if (!fin.equals("")) {
						solution = solution + "跟 " + mid_student.split("·")[0] + 
								 " 换班后,跟 " + fin +"中的一人换班\n";
					}
				}
				if (solution.equals("三人换班:\n")) {
					solution = solution + "无方案\n";
				}
				return solution;
	    	}
			else {
		 		return "";
		 	}
        } else {
        	return "";
        }
	}
    public static String give_solution(String name, String filePath) throws IOException {
		return double_switch(name, filePath)+triple_switch(name, filePath);
    }
    
    public static void main(String args[]) {
    	try {
			System.out.println(give_solution("阿拉拍", "C:\\Users\\Wither\\Desktop\\DarkFlameContract.txt"));
			System.out.println("Finished");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
