package edition1;
/**
 *  @author Wither
 * 
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class Add_new_dialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 2748004864754193308L;
	
	public Student student;
	public String name;
	public String department;
	public String student_number;
	public String student_college;
	public String student_class;
	public String rota_day;
	public String rota_class;
	public String phone_number;
	public String birthday;
	public String gender;
	public String qnum;
	
	public String title;
	public JTextField text_name, text_sno, text_college, text_class,
	                          text_phonenum, text_birthday, text_qnum;
	public JComboBox<Object> comBox_department, comBox_day, comBox_class;
	public JRadioButton radio1, radio2;
    public ButtonGroup group;
	public JButton button_ok, button_cancel;
	public JCheckBox[][] check_box_group;
	public JLabel lab_name, lab_sno, lab_college, lab_class, lab_phonenum,
	                     lab_birthday, lab_department, lab_explination, lab_gender,
	                     lab_qnum, lab_time;
	public JLabel lab_titles[], lab_classes[];
	public JPanel items, upper_left, upper_right, hints, mid, buttons;
	public JTextArea textarea;
	
	public String titles[] = {"有课情况", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
	public String classes[] = {"第一大节", "第二大节", "第三大节", "第四大节", "第五大节"};
	boolean[][] ts =  {
			{true, true, true, true, true, true, true},
			{true, true, true, true, true, true, true},
			{true, true, true, true, true, true, true}, 
			{true, true, true, true, true, true, true}, 
			{true, true, true, true, true, true, true}
	};
	public Add_new_dialog(JFrame f,String s){
		//general setting
		super(f, s, true);
		this.title = s;
		this.setLocationRelativeTo(null);
		this.setSize(820, 740);
		this.setLayout(new BorderLayout());
		
		//items
		items = new JPanel();
		GridBagLayout item_layout = new GridBagLayout();
		items.setLayout(item_layout);
		
        //upper_left
			//TextField name
		upper_left = new JPanel();
		GridBagLayout layout_ul = new GridBagLayout();
		upper_left.setLayout(layout_ul);
        lab_name = new JLabel("    姓名:");
        upper_left.add(lab_name);
        text_name = new JTextField(20);
        upper_left.add(text_name);
        text_name.addActionListener(this);
    	    //TextField student number
        lab_sno = new JLabel("    学号:");
        upper_left.add(lab_sno);
        text_sno = new JTextField(20);
        upper_left.add(text_sno);
        text_sno.addActionListener(this);
    		//TextField college
        lab_college = new JLabel("    学院:");
        upper_left.add(lab_college);
        text_college = new JTextField(20);
        upper_left.add(text_college);
        text_college.addActionListener(this);
    		//TextField class
        lab_class = new JLabel("    班级:");
        upper_left.add(lab_class);
        text_class = new JTextField(20);
        upper_left.add(text_class);
        text_class.addActionListener(this);
    		//TextField name
        lab_phonenum = new JLabel("    电话:");
        upper_left.add(lab_phonenum);
        text_phonenum = new JTextField(20);
        upper_left.add(text_phonenum);
        text_phonenum.addActionListener(this);
    	    //TextField student number
        lab_birthday = new JLabel("    生日:");
        upper_left.add(lab_birthday);
        text_birthday = new JTextField(20);
        upper_left.add(text_birthday);
        text_birthday.addActionListener(this);
        //TextField student number
        lab_qnum = new JLabel("      QQ:");
        upper_left.add(lab_qnum);
        text_qnum = new JTextField(20);
        upper_left.add(text_qnum);
        text_qnum.addActionListener(this);
        items.add(upper_left);
        	//layout
        GridBagConstraints m= new GridBagConstraints();
        m.insets = new Insets(0, 0, 17, 10);
        m.fill = GridBagConstraints.BOTH;
        m.gridwidth = 1;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(lab_name, m);
        m.gridwidth = 0;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(text_name, m);
        m.gridwidth = 1;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(lab_sno, m);
        m.gridwidth = 0;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(text_sno, m);
        m.gridwidth = 1;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(lab_college, m);
        m.gridwidth = 0;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(text_college, m);
        m.gridwidth = 1;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(lab_class, m);
        m.gridwidth = 0;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(text_class, m);
        m.gridwidth = 1;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(lab_phonenum, m);
        m.gridwidth = 0;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(text_phonenum, m);
        m.gridwidth = 1;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(lab_birthday, m);
        m.gridwidth = 0;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(text_birthday, m);
        m.gridwidth = 1;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(lab_qnum, m);
        m.gridwidth = 0;
        m.weightx = 0;
        m.weighty = 0;
        layout_ul.setConstraints(text_qnum, m);
       
        //upper_right
        upper_right = new JPanel();
        GridBagLayout layout_ur = new GridBagLayout();
        upper_right.setLayout(layout_ur);
        lab_gender = new JLabel("性别:");
        upper_right.add(lab_gender);
        group = new ButtonGroup();
        radio1 = new JRadioButton("男");
        radio2 = new JRadioButton("女");
        group.add(radio1);
        group.add(radio2);
        upper_right.add(radio1);
        upper_right.add(radio2);
        radio1.addActionListener(this);
        radio2.addActionListener(this);
    	//Combo box department
        lab_department = new JLabel("部门:");
        upper_right.add(lab_department);
        comBox_department = new JComboBox<>();
        comBox_department.addItem("-请选择-");
        comBox_department.addItem("公关部");
        comBox_department.addItem("纪检部");
        comBox_department.addItem("权益部");
        comBox_department.addItem("事务部");
        comBox_department.addItem("文化部");
        upper_right.add(comBox_department);
        comBox_department.addActionListener(this);
        	//Combo box time shift
        lab_time = new JLabel("值班时间:");
        upper_right.add(lab_time);
        comBox_day = new JComboBox<>();
        comBox_day.addItem("-未安排-");
        comBox_day.addItem("星期一");
        comBox_day.addItem("星期二");
        comBox_day.addItem("星期三");
        comBox_day.addItem("星期四");
        comBox_day.addItem("星期五");
        comBox_day.addItem("星期六");
        comBox_day.addItem("星期日");
        upper_right.add(comBox_day);
        comBox_class = new JComboBox<>();
        comBox_class.addItem("-未安排-");
        comBox_class.addItem("第一大节");
        comBox_class.addItem("第二大节");
        comBox_class.addItem("第三大节");
        comBox_class.addItem("第四大节");
        comBox_class.addItem("第五大节");
        upper_right.add(comBox_class);
        comBox_day.addActionListener(this);
        comBox_class.addActionListener(this);
        //hints
        hints = new JPanel();
        hints.setLayout(new GridLayout(1, 1, 10, 10));
        hints.setBorder(BorderFactory.createTitledBorder("提示信息"));
        textarea = new JTextArea("请确保所有内容被填写\n勾选表示有课...", 5, 40);
        hints.add(textarea);
        upper_right.add(hints);
        //layout
        GridBagConstraints m1= new GridBagConstraints();
        m1.insets = new Insets(0, 0, 10, 7);
        m1.fill = GridBagConstraints.BOTH;
        m1.gridwidth = 1;
        m1.gridheight = 1;
        m1.weightx = 0;
        m1.weighty = 0;
        layout_ur.setConstraints(lab_gender, m1);
        m1.gridwidth = 1;
        m1.weightx = 0;
        m1.weighty = 0;
        layout_ur.setConstraints(radio1, m1);
        m1.gridwidth = 0;
        m1.weightx = 0;
        m1.weighty = 0;
        layout_ur.setConstraints(radio2, m1);
        m1.gridwidth = 1;
        m1.weightx = 0;
        m1.weighty = 0;
        layout_ur.setConstraints(lab_department, m1);
        m1.gridwidth = 0;
        m1.weightx = 0;
        m1.weighty = 0;
        layout_ur.setConstraints(comBox_department, m1);
        m1.gridwidth = 1;
        m1.weightx = 0;
        m1.weighty = 0;
        layout_ur.setConstraints(lab_time, m1);
        m1.gridwidth = 3;
        m1.weightx = 0;
        m1.weighty = 0;
        layout_ur.setConstraints(comBox_day, m1);
        m1.gridwidth = 0;
        m1.weightx = 0;
        m1.weighty = 0;
        layout_ur.setConstraints(comBox_class, m1);
        m1.gridwidth = 0;
        m1.weightx = 0;
        m1.weighty = 1;
        layout_ur.setConstraints(hints, m1);

        items.add(upper_right);
        
        //mid
        mid = new JPanel();
        mid.setLayout(new GridLayout(6, 8, 20, 20));
        check_box_group = new JCheckBox[5][7];
        lab_titles = new JLabel[8];
        lab_classes = new JLabel[6];
        for (int i = 0; i < 6; i++) {
        	if (i == 0) {
        		for (int k = 0; k < 8; k++) {
        			String str = titles[k];
        			lab_titles[k] = new JLabel(str);
        			lab_titles[k].setFont(new Font("黑体", Font.PLAIN, 20));
        			mid.add(lab_titles[k]);
        		}
        	}
        	else {
	        	for (int j = 0; j < 8; j++) {
	        		if (j == 0) {
	        			String str = classes[i-1];
	        			lab_classes[i-1] = new JLabel(str);
	        			lab_classes[i-1].setFont(new Font("黑体", Font.PLAIN, 20));
	        			mid.add(lab_classes[i-1]);
	        		}
	        		else {
	        			check_box_group[i-1][j-1] = new JCheckBox("");
	        			mid.add(check_box_group[i-1][j-1]);
	        		}
	    		}
        	}
		}
        items.add(mid);
        this.add(items);
        //item_layout
        GridBagConstraints m2= new GridBagConstraints();
        m2.insets = new Insets(0, 0, 10, 7);
        m2.fill = GridBagConstraints.BOTH;
        m2.gridwidth = 1;
        m2.weightx = 0;
        m2.weighty = 0;
        item_layout.setConstraints(upper_left, m2);
        m2.gridwidth = 0;
        m2.weightx = 0;
        m2.weighty = 0;
        item_layout.setConstraints(upper_right, m2);
        m2.gridwidth = 0;
        m2.weightx = 0;
        m2.weighty = 0;
        item_layout.setConstraints(mid, m2);
        //buttons
        buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        button_ok = new JButton("添加");
        button_cancel = new JButton("取消");
        buttons.add(button_ok);
        buttons.add(button_cancel);
        button_ok.addActionListener(this);
        button_cancel.addActionListener(this);
        this.add(BorderLayout.SOUTH, buttons);
        
        //general settings
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == button_ok) {
			try {
				RandomAccessFile raf=new RandomAccessFile(Time_killer_ui.file,"rw");
				long fileLength = raf.length();   
				raf.seek(fileLength);
				if (check()) {
					reset_ts();
					student = new Student(name, department, student_number, student_college,
							student_class, rota_day, rota_class, phone_number, birthday, ts, gender, qnum);
					raf.write(student.toString().getBytes("GBK"));
					raf.close();
					Time_killer_ui.textarea.setText("已添加\n"+student.print_toString());
					this.setVisible(false);
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == button_cancel) {
			Time_killer_ui.textarea.setText("已取消");
			this.setVisible(false);
		}
	}

	public boolean check() {
		String empty = "";
		name = text_name.getText();
		department = (String)comBox_department.getSelectedItem();
		student_number = text_sno.getText();
		student_college = text_college.getText();
		student_class = text_class.getText();
		rota_day = (String)comBox_day.getSelectedItem();
		rota_class = (String)comBox_class.getSelectedItem();
		phone_number = text_phonenum.getText();
		birthday = text_birthday.getText();
		gender = (radio1.isSelected()&&!radio2.isSelected()) ? "男":"女";
		qnum = text_qnum.getText();
		if	(name.equals(empty)) {
			textarea.setText("请输入名字");
			return false;
		}
		if	(department.equals("-请选择-")) {
			textarea.setText("请选择部门");
			return false;
		}
		if	(student_number.equals(empty)) {
			textarea.setText("请输入学号");
			return false;
		}
		if	(student_college.equals(empty)) {
			textarea.setText("请输入学院");
			return false;
		}
		if	(student_class.equals(empty)) {
			textarea.setText("请输入班级");
			return false;
		}
		if	(phone_number.equals(empty)) {
			textarea.setText("请输入电话");
			return false;
		}
		if	(birthday.equals(empty)) {
			textarea.setText("请输入生日");
			return false;
		}
		if (!radio1.isSelected()&&!radio2.isSelected()) {
			textarea.setText("请选择性别");
			return false;
		}
		if	(qnum.equals(empty)) {
			textarea.setText("请输入QQ号");
			return false;
		}
		if ((rota_day.equals("-未安排-")&&(!rota_class.equals("-未安排-")))||
			(!rota_day.equals("-未安排-")&&(rota_class.equals("-未安排-")))){
			textarea.setText("请输入完整的值班时间");
			return false;
		}
		if (!rota_day.equals("-未安排-")&&!rota_class.equals("-未安排-")) {
			int time = Find_by_time.cast_time(rota_class);
			int day = Find_by_time.cast_day(rota_day);
			if (check_box_group[time][day].isSelected()) {
				textarea.setText("在值班时间有课, 请核对");
				return false;
			}
		}
		return true;
	}
	
	public void reset_ts() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				ts[i][j] = check_box_group[i][j].isSelected();
			}
		}
	}
	
}
