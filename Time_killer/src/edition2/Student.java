package edition2;

/**
 * 
 * @author Wither
 *
 *
 *有课是true/1
 *无课是false/0
 */

public class Student {
	public String name;//0
	public String department;//1
	public String student_number;//2
	public String student_college;//3
	public String student_class;//4
	public String rota_day;//5
	public String rota_class;//6
	public String phone_number;//7
	public String birthday;//8
	public boolean[][] time_schedule =  {
				{true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true},
				{true, true, true, true, true, true, true}, 
				{true, true, true, true, true, true, true}, 
				{true, true, true, true, true, true, true}
		};//9
	public String gender;//10
	public String qnum;//11
	public Student nextDep;

	public Student() {
		this.name = "";
		this.department = "";
		this.student_number = "";
		this.student_college = "";
		this.student_class = "";
		this.rota_day = "";
		this.rota_class = "";
		this.phone_number = "";
		this.birthday = "";
		this.gender = "";
		this.qnum = "";
		this.nextDep = null;
	}
	public Student(String info) {
		String details[] = info.split("·");
		this.name = details[0];
		this.department = details[1];
		this.student_number = details[2];
		this.student_college = details[3];
		this.student_class = details[4];
		this.rota_day = details[5];
		this.rota_class = details[6];
		this.phone_number = details[7];
		this.birthday = details[8];
		this.time_schedule = set_ts(details[9]);
		this.gender = details[10];
		this.qnum = details[11];
		this.nextDep = null;
	}
	public Student(String sname, String dep, String sno, String scoll, 
			String sclass, String rday, String rclass,
			String pnum, String bir, boolean[][] tsch, String gender, String qnum) {
		this.name = sname;
		this.department = dep;
		this.student_number = sno;
		this.student_college = scoll;
		this.student_class = sclass;
		this.rota_day = rday;
		this.rota_class = rclass;
		this.phone_number = pnum;
		this.birthday = bir;
		this.time_schedule = tsch;
		this.gender = gender;
		this.qnum = qnum;
		this.nextDep = null;
	}
	public String timeSchedule_toString() {
		String res = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				if(this.time_schedule[i][j])
					res = res + "  X\t";
				else
					res = res +"无课\t";
			}
			res = res + "\n";
		}
		return res;
	}
	public static String timeSchedule_toString(String info) {
		String res = "";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				if(info.charAt( i * 7 + j ) == '1')
					res = res + "  X\t";
				else
					res = res +"无课\t";
			}
			res = res + "\n";
		}
		return res;
	}
	public String print_toString() {
		return "姓名:"+this.name +"\n性别:" + this.gender 
				   +"\n部门:"+this.department+"\n学号:"+this.student_number
				   +"\n学院:"+this.student_college+"\n班级:"+this.student_class+"\n值班时间:"+this.rota_day
	               +this.rota_class+"\n电话:"+this.phone_number + "\nQQ号:" + this.qnum
	               +"\n生日:"+this.birthday+"\n无课情况:\n"
	               +timeSchedule_toString();
	}
	public static String print_toString(String info) {
		String details[] = info.split("·");
		return "姓名:"+details[0] + "\n性别:" + details[10] +"\n部门:"+details[1]
				    +"\n学号:"+details[2] +"\n学院:"+details[3]+"\n班级:"+details[4]
				    +"\n值班时间:"+details[5] +details[6]+"\n电话:"+details[7] 
				    + "\nQQ号:"+ details[11] +"\n生日:"+details[8]+"\n无课情况:\n"
	               	+timeSchedule_toString(details[9]) ;
	}
	
	public String toString() {
		String res = name + "·" + department + "·" +student_number + "·"
	                       + student_college + "·" + student_class + "·" + rota_day + "·"
				           + rota_class + "·" + phone_number + "·" + birthday + "·";
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				if(this.time_schedule[i][j])
					res = res + "1";
				else
					res = res +"0";
			}
		}
		res = res + "·" + gender + "·" + qnum;
		res = res + "\r\n";
		return res;
	}
	public static boolean[][] set_ts(String data) {
		boolean selected;
		boolean ts[][] = new boolean[5][7];
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 7; ++j) {
				if (data.charAt( i * 7 + j ) == '1') {
					selected = true;
				}
				else {
					selected = false;
				}
				ts[i][j] = selected;
			}
		}
		return ts;
	}
	public void finalize() { }
	
}