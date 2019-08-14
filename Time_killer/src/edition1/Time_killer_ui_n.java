package edition1;

/**
 * 
 * @author Wither
 *
 *which is 刘俊贤
 *
 *这个是ui的主界面
 *
 *添加QQ号码和性别信息
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.util.*;

public class Time_killer_ui_n extends JFrame implements ActionListener, MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1675731524901279665L;
	
	public static JMenuBar menubar;
	public static JMenu menu;
	public static JMenuItem item1, item2, item3, item4, item5, help, info, quit;
	public static JToolBar toolbar;
	public static JTextField text_path;
	public static JTextField text_name;
	public static JTextField text_core;
	public static JButton button_go, 
	                       button_change, button_add, button_delete, button_find,
	                       button_info, button_switch, button_create,
	                       button_core;
	public static JComboBox<Object> comBox_department, comBox_day, comBox_class;
	
	public static JTable table;
	public static DefaultTableModel tablemodel; 
	public static JTextArea textarea;
	public String titles_day[] = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
	public static String result[][] = {};
	
	public static JLabel lab_name, lab_department;
	public static JPanel left, right;
	public static JPanel l_upper, l_mid, l_downer, l_com;
	public static JPanel r1, r2;
	public static JSplitPane split;
	
	public static int quick_quit_counter;
	public static boolean quick_quit_flag = false;
	
	public static JFileChooser fchooser;
	public static File file;
	public static boolean unlockable = false;
	
	public Time_killer_ui_n() {
		//General settings
		this.setTitle("Unlimited Chart Works!");
		this.setSize(1200, 750);
		this.setLocationRelativeTo(null);
		//Set icon
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image image=tk.createImage("D:\\_JavaWorkspace\\Time_killer\\config\\NOTITLE.png");
		this.setIconImage(image); 
		//File chooser
		Time_killer_ui_n.fchooser = new JFileChooser(
				new File(FileSystemView.getFileSystemView().getHomeDirectory().getPath(),""));
		Time_killer_ui_n.fchooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		Time_killer_ui_n.fchooser.setFileFilter(new ExtensionFileFilter("txt", "文本文件(.txt)"));
		
		//Add menu bar
        menubar = new JMenuBar();
        menu = new JMenu("菜单");
        item1 = new JMenuItem("添加");
        item2 = new JMenuItem("显示详细信息");
        item3 = new JMenuItem("换班");
        item4 = new JMenuItem("生成Excel表格");
        item5 = new JMenuItem("查看被钥匙储存的人    ");
        help = new JMenuItem("帮助");
        info = new JMenuItem("相关信息");
        quit = new JMenuItem("退出");
        item1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
        item2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
        item3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_MASK));
        item4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, InputEvent.CTRL_MASK));
        item5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, InputEvent.CTRL_MASK));
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
        info.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4); 
        menu.add(item5); 
        menu.addSeparator();//cross line
        menu.add(help);
        menu.add(info);
        menu.addSeparator();//cross line
        menu.add(quit);
        menubar.add(menu);
        setJMenuBar(menubar);
        item1.addActionListener(this);
        item2.addActionListener(this);
        item3.addActionListener(this);
        item4.addActionListener(this);
        item5.addActionListener(this);
        help.addActionListener(this);
        info.addActionListener(this);
        quit.addActionListener(this);
        
        //Add tool bar
        toolbar = new JToolBar();
        this.getContentPane().add(toolbar,"North");
        toolbar.add(text_path=new JTextField("在此处输入咒文..."));
        toolbar.add(button_go=new JButton("      Go      "));
        button_go.addActionListener(this);
        text_path.addActionListener(this);
        
        //left side
        left = new JPanel();
        left.setLayout(new GridLayout(3, 1, 0, 0));
        	//left upper
        l_upper = new JPanel();
        l_upper.setBorder(BorderFactory.createTitledBorder("查找条件"));
        GridBagLayout l_u_layout = new GridBagLayout();
        l_upper.setLayout(l_u_layout);
        	//TextField name
        lab_name = new JLabel("    姓名:");
        l_upper.add(lab_name);
        text_name = new JTextField(15);
        l_upper.add(text_name);
        text_name.addActionListener(this);
        	//Combo box department
        lab_department = new JLabel("    部门:");
        l_upper.add(lab_department);
        comBox_department = new JComboBox<>();
        comBox_department.addItem("-不限-");
        comBox_department.addItem("公关部");
        comBox_department.addItem("纪检部");
        comBox_department.addItem("权益部");
        comBox_department.addItem("事务部");
        comBox_department.addItem("文化部");
        l_upper.add(comBox_department);
        	//Combo box time shift
        l_com = new JPanel();
        l_com.add(new JLabel("    时间:"));
        comBox_day = new JComboBox<>();
        comBox_day.addItem("-不限-");
        comBox_day.addItem("星期一");
        comBox_day.addItem("星期二");
        comBox_day.addItem("星期三");
        comBox_day.addItem("星期四");
        comBox_day.addItem("星期五");
        comBox_day.addItem("星期六");
        comBox_day.addItem("星期日");
        l_com.add(comBox_day);
        l_com.add(new JLabel("-"));
        comBox_class = new JComboBox<>();
        comBox_class.addItem("-不限-");
        comBox_class.addItem("第一大节");
        comBox_class.addItem("第二大节");
        comBox_class.addItem("第三大节");
        comBox_class.addItem("第四大节");
        comBox_class.addItem("第五大节");
        l_com.add(comBox_class);
        l_upper.add(l_com);
        	//Grid bag left
        GridBagConstraints u= new GridBagConstraints();
        u.insets = new Insets(0, 0, 21, 5);
        u.fill = GridBagConstraints.HORIZONTAL;
        u.gridwidth = 5;
        u.weightx = 0;
        u.weighty = 0;
        l_u_layout.setConstraints(lab_name, u);
        u.gridwidth = 0;
        u.weightx = 0;
        u.weighty = 0;
        l_u_layout.setConstraints(text_name, u);
        u.gridwidth = 5;
        u.weightx = 0;
        u.weighty = 0;
        l_u_layout.setConstraints(lab_department, u);
        u.gridwidth = 0;
        u.weightx = 0;
        u.weighty = 0;
        l_u_layout.setConstraints(comBox_department, u);
        u.gridwidth = 0;
        u.weightx = 0;
        u.weighty = 0;
        l_u_layout.setConstraints(l_com, u);
        	//left middle
        l_mid = new JPanel();
        l_mid.setBorder(BorderFactory.createTitledBorder("操作"));
        GridBagLayout l_m_layout = new GridBagLayout();
        l_mid.setLayout(l_m_layout);
        button_change = new JButton("修改");
        button_add = new JButton("添加");
        button_delete = new JButton("删除");
        button_find = new JButton("查找");
        button_info = new JButton("显示详细信息");
        button_switch = new JButton("换班");
        button_create = new JButton("生成Excel表格");
        l_mid.add(button_change);
        l_mid.add(button_add);
        l_mid.add(button_delete);
        l_mid.add(button_find);
        l_mid.add(button_info);
        l_mid.add(button_switch);
        l_mid.add(button_create);
        button_change.addActionListener(this);
        button_add.addActionListener(this);
        button_delete.addActionListener(this);
        button_find.addActionListener(this);
        button_info.addActionListener(this);
        button_switch.addActionListener(this);
        button_create.addActionListener(this);
        	//Grid bag middle
        GridBagConstraints m= new GridBagConstraints();
        m.insets = new Insets(0, 0, 10, 7);
        m.fill = GridBagConstraints.BOTH;
        m.gridwidth = 1;
        m.weightx = 0;
        m.weighty = 0;
        l_m_layout.setConstraints(button_change, m);
        m.gridwidth = 1;
        m.weightx = 0;
        m.weighty = 0;
        l_m_layout.setConstraints(button_add, m);
        m.gridwidth = 1;
        m.weightx = 0;
        m.weighty = 0;
        l_m_layout.setConstraints(button_delete, m);
        m.gridwidth = 0;
        m.weightx = 0;
        m.weighty = 0;
        l_m_layout.setConstraints(button_find, m);
        m.gridwidth = 0;
        m.weightx = 0;
        m.weighty = 0;
        l_m_layout.setConstraints(button_info, m);
        m.gridwidth = 0;
        m.weightx = 0;
        m.weighty = 0;
        l_m_layout.setConstraints(button_switch, m);
        m.gridwidth = 0;
        m.weightx = 0;
        m.weighty = 0;
        l_m_layout.setConstraints(button_create, m);
        	//left downer
        	//Text area information
        l_downer = new JPanel();
        l_downer.setBorder(BorderFactory.createTitledBorder("控制台"));
        l_downer.setLayout(new GridLayout(1, 1, 0, 0));
      	textarea = new JTextArea("消息在这里显示...\n\n"
      			+ "为了解除这个程序的封印,请选择解除\n"
      			+ "封印的钥匙(DarkFlameContract.txt)", 5, 30);
      	l_downer.add(textarea);
      	l_downer.add(new JScrollPane(Time_killer_ui_n.textarea));
      	textarea.addMouseListener(this);
      		//left side
		left.add(l_upper);
		left.add(l_mid);
		left.add(l_downer);
		
        //right side
        right = new JPanel();
        BorderLayout r_layout = new BorderLayout(5, 0);
        right.setLayout(r_layout);
			//Table
        r1 = new JPanel();
        r1.setBorder(BorderFactory.createTitledBorder("ヽ*(·д·)ノ"));
		r1.setLayout(new GridLayout(1, 1, 0, 0));
		tablemodel = new DefaultTableModel(result, titles_day);
		table = new JTable(Time_killer_ui_n.tablemodel);
		table.setRowHeight(98);
		r1.add(new JScrollPane(table));
		tablemodel.setRowCount(5); 
		table.getTableHeader().setFont(new Font("黑体", Font.PLAIN, 20));
	//	table.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());   		
		    //core condition
		r2 = new JPanel();
		r2.setBorder(BorderFactory.createTitledBorder("解除封印的钥匙路径"));
		r2.setLayout(new BorderLayout(5, 0));
		text_core = new JTextField("请选择解除封印的钥匙(DarkFlameContract.txt)", 30);
		button_core = new JButton("选择解除封印的钥匙");
		r2.add(BorderLayout.CENTER, text_core);
		r2.add(BorderLayout.EAST, button_core);
		text_core.setEnabled(false);
		button_core.addActionListener(this);
		    //right side
		right.add(BorderLayout.CENTER, r1);
		right.add(BorderLayout.SOUTH, r2);
		
        //Add split line
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		split.setDividerLocation(350);
		//split.setEnabled(false);
		split.setVisible(true);
		this.getContentPane().add(split);
		
        //General settings
		Disable_all();//封印
      	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
      	this.setVisible(true);
	}
	
	class TableCellTextAreaRenderer extends JTextArea implements TableCellRenderer { 
		public TableCellTextAreaRenderer() { 
			setLineWrap(true); 
			setWrapStyleWord(true); 
		} 

		public Component getTableCellRendererComponent(JTable table, Object value, 
		boolean isSelected, boolean hasFocus, int row, int column) { 
		// 计算当下行的最佳高度 
		int maxPreferredHeight = 0; 
		for (int i = 0; i < table.getColumnCount(); i++) { 
			setText("" + table.getValueAt(row, i)); 
			setSize(table.getColumnModel().getColumn(column).getWidth(), 0); 
			maxPreferredHeight = Math.max(maxPreferredHeight, getPreferredSize().height); 
		} 

		if (table.getRowHeight(row) != maxPreferredHeight) // 少了这行则处理器瞎忙 
			table.setRowHeight(row, maxPreferredHeight); 

		setText(value == null ? "" : value.toString()); 
		return this; 
		} 
	} 

	public void Disable_all() {
		item1.setEnabled(false);
		item2.setEnabled(false);
		item3.setEnabled(false);
		item4.setEnabled(false);
		item5.setEnabled(false);
		text_name.setEnabled(false);
	    button_change.setEnabled(false);
	    button_add.setEnabled(false);
	    button_delete.setEnabled(false);
	    button_find.setEnabled(false);
		button_info.setEnabled(false);
		button_switch.setEnabled(false);
		button_create.setEnabled(false);
		comBox_department.setEnabled(false);
		comBox_day.setEnabled(false);
		comBox_class.setEnabled(false);
	}
	public void Enable_some() {
		item1.setEnabled(true);
		item2.setEnabled(true);
		item3.setEnabled(true);
		item4.setEnabled(false);
		item5.setEnabled(true);
		text_name.setEnabled(true);
	    button_change.setEnabled(false);
	    button_add.setEnabled(true);
	    button_delete.setEnabled(false);
	    button_find.setEnabled(true);
		button_info.setEnabled(true);
		button_switch.setEnabled(true);
		button_create.setEnabled(false);
		comBox_department.setEnabled(true);
		comBox_day.setEnabled(true);
		comBox_class.setEnabled(true);
	}
	public void Enable_all() {
		item1.setEnabled(true);
		item2.setEnabled(true);
		item3.setEnabled(true);
		item4.setEnabled(true);
		item5.setEnabled(true);
		text_name.setEnabled(true);
	    button_change.setEnabled(true);
	    button_add.setEnabled(true);
	    button_delete.setEnabled(true);
	    button_find.setEnabled(true);
		button_info.setEnabled(true);
		button_switch.setEnabled(true);
		button_create.setEnabled(true);
		comBox_department.setEnabled(true);
		comBox_day.setEnabled(true);
		comBox_class.setEnabled(true);
	}
	
	public void mouseClicked(MouseEvent arg0) { 
		Time_killer_ui_n.quick_quit_flag = true;
	}
	public void mouseEntered(MouseEvent arg0) { 
		if(Time_killer_ui_n.quick_quit_flag) {
			Time_killer_ui_n.quick_quit_counter++;
			if(Time_killer_ui_n.quick_quit_counter == 3)
			{
				this.setExtendedState(JFrame.ICONIFIED);
				Time_killer_ui_n.quick_quit_counter = 0;
				Time_killer_ui_n.quick_quit_flag = false;
			}
		}
	}
	public void mouseExited(MouseEvent arg0) { }
	public void mousePressed(MouseEvent arg0) { }
	public void mouseReleased(MouseEvent arg0) { }
	
	public void actionPerformed(ActionEvent e) {
		//menu bar
		if (e.getSource().equals(Time_killer_ui_n.help)) {
			JOptionPane.showMessageDialog(this,
					   "             帮助\n"
					+ "使用这个程序首先需要选择\n"
					+ "解除封印的钥匙(DarkFlameContract.txt)\n"
					+ "文件可以从事务部获得\n"
					+ "如果没有就让他们录入信息生成一个\n"
					+ "如果他们不录入就给中心部长打小报告\n"
					+ "详细的帮助文档可以在事务部处获得\n\n"
					+ "         ヽ*(·д·)ノ      ", "帮助", JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getSource().equals(Time_killer_ui_n.info)) {
			JOptionPane.showMessageDialog(this,
					   "致山东大学威海公寓管理中心。\n"
					+ "                --by Wither  \n"
					+ "漏洞反馈：liujunxian126@126.com\n\n"
					+ "     ヽ*(·д·)ノ      ", "相关信息", JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getSource().equals(Time_killer_ui_n.quit)) {
			int response = JOptionPane.showConfirmDialog(this, "要退出程序嘛？", "询问", JOptionPane.YES_NO_OPTION);
			if(response==0) { 
				this.setTitle("那就退出");
				System.exit(0);
			}
			else if(response==1) { 
				this.setTitle("那就不退出了");
			}
		}
		if (e.getSource().equals(Time_killer_ui_n.item5)) {
			String whosthere = "";
			try {
				whosthere = Whos_there.find(text_core.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Time_killer_ui_n.textarea.setText("已展开钥匙:\n" + whosthere);
		}
		//tool bar
		if ((e.getSource().equals(Time_killer_ui_n.button_go))||
			 (e.getSource().equals(Time_killer_ui_n.text_path))){
			String command = Time_killer_ui_n.text_path.getText();
			if ((command.equals("Unlimited Chart Works!"))&&(unlockable)) {
		    	Enable_all();
		    	text_path.setText("完全解封咒文已生效, 请谨慎使用解封的功能");
			} else if (command.equals("AIO")) {
				text_path.setText("测试阶段, 请谨慎使用...");
				new Aio_ui();
			}
			else
				this.setTitle(command);
		}
		//left part
		if (e.getSource().equals(Time_killer_ui_n.button_change)) {
			Time_killer_ui_n.textarea.setText("修改中...");
			if (Time_killer_ui_n.text_name.getText().equals("")) {
				Time_killer_ui_n.textarea.setText("请在信息条目中输入\n要修改的人的姓名...");
			}
			else {
				try {
					if (!Change.exist(Time_killer_ui_n.text_name.getText(), text_core.getText()).equals("")) {
						Make_change_dialog dialog =new Make_change_dialog(this, "修改");
						dialog.setModal(false);
					}
					else {
						Time_killer_ui_n.textarea.setText("没有找到对象...");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		if ((e.getSource().equals(Time_killer_ui_n.button_add))||
			 (e.getSource().equals(Time_killer_ui_n.item1))) {
			Time_killer_ui_n.textarea.setText("编辑中...");
			Add_new_dialog dialog =new Add_new_dialog(this, "添加");
			dialog.setModal(false);
		}
		if (e.getSource().equals(Time_killer_ui_n.button_delete)) {
			boolean exist = false;
			boolean deleted = false;
			Time_killer_ui_n.textarea.setText("即将执行删除操作,请谨慎选择...");
			try {
				exist = Delete.exist(Time_killer_ui_n.text_name.getText(), text_core.getText());
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			if (Time_killer_ui_n.text_name.getText().equals("")) {
				Time_killer_ui_n.textarea.setText("请在信息条目中输入\n要删除的人的姓名...");
			}
			else {
				if (exist) {
					int response = JOptionPane.showConfirmDialog(this, "已找到对象,是否删除?", "警告!此操作不可恢复!", JOptionPane.YES_NO_OPTION);
					if (response == 0) {
						try {
							deleted = Delete.delete_this(Time_killer_ui_n.text_name.getText(), text_core.getText());
						} catch (IOException e1) {
							e1.printStackTrace();
						} finally {
							if (deleted) {
								Time_killer_ui_n.textarea.setText("已删除");
							}
							else {
								Time_killer_ui_n.textarea.setText("删除失败!");
							}
						}
					}
					else {
						Time_killer_ui_n.textarea.setText("操作已取消");
					}
				}
				else {
					Time_killer_ui_n.textarea.setText("没有找到对象...");
				}
			}
		}
		if ((e.getSource().equals(Time_killer_ui_n.button_find)) ||
	     (e.getSource().equals(Time_killer_ui_n.text_name))) {
			String name = text_name.getText();
			String department = (String) comBox_department.getSelectedItem();
			String day = (String) comBox_day.getSelectedItem();
			String time = (String) comBox_class.getSelectedItem();
			String filePath = Time_killer_ui_n.text_core.getText();
			try {
				if (!name.equals("")&&Change.exist(name, filePath).equals("")) {
					Time_killer_ui_n.textarea.setText("没有找到对象...");
				}
				else {
					try {
						Time_killer_ui_n.result = Find.give_a_result(name, department, day, time, filePath);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					Time_killer_ui_n.r1.removeAll();
					Time_killer_ui_n.r1.setVisible(false);
					Time_killer_ui_n.tablemodel = new DefaultTableModel(result, titles_day);
					Time_killer_ui_n.table = new JTable(Time_killer_ui_n.tablemodel);
					Time_killer_ui_n.table.setRowHeight(98);
					table.getTableHeader().setFont(new Font("楷体", 0, 25));
					Time_killer_ui_n.r1.add(new JScrollPane(table));
					table.setDefaultRenderer(Object.class, new TableCellTextAreaRenderer());   
					if ((!day.equals("-不限-"))&&(!time.equals("-不限-"))) {
						Time_killer_ui_n.tablemodel.setRowCount(5);
					}
					Time_killer_ui_n.r1.setVisible(true);
					Time_killer_ui_n.textarea.setText("已完成查找");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if ((e.getSource().equals(Time_killer_ui_n.button_info))||
		     (e.getSource().equals(Time_killer_ui_n.item2)))
		    {
			if (Time_killer_ui_n.text_name.getText().equals("")) {
				Time_killer_ui_n.textarea.setText("请在信息条目中输入\n要显示的人的姓名...");
			}
			else {
				String res = "";
				try {
					res = Change.exist(Time_killer_ui_n.text_name.getText(), Time_killer_ui_n.text_core.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(!res.equals("")) {
					Time_killer_ui_n.textarea.setText(Student.print_toString(res));
				}
				else {
					Time_killer_ui_n.textarea.setText("没有找到对象...");
				}
			}
		}
		if ((e.getSource().equals(Time_killer_ui_n.button_switch)) ||
		     (e.getSource().equals(Time_killer_ui_n.item3))) {
			String solution = "";
			try {
				solution = Switch.give_solution(Time_killer_ui_n.text_name.getText(), Time_killer_ui_n.text_core.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(!solution.equals("")) {
				Time_killer_ui_n.textarea.setText("换班方案:\n"+solution);
			}
			else {
				Time_killer_ui_n.textarea.setText("没有找到对象...");
			}
		}
		if ((e.getSource().equals(Time_killer_ui_n.button_create))||
		     (e.getSource().equals(Time_killer_ui_n.item4))) {
			Time_killer_ui_n.textarea.setText("请选择需要创建的表格...\n"
					+ "将自动在桌面上生成文件夹\n"
					+ "Unlimited_Chart_Works\n"
					+ "并在其中生成表格, \n"
					+ "请及时核对表格内容并归档\n");
			Create_table_dialog dialog = new Create_table_dialog(this, "创建表格");
			dialog.setModal(false);
		}
		//right part
		if (e.getSource().equals(Time_killer_ui_n.button_core)) {
			if (Time_killer_ui_n.fchooser.showOpenDialog(Time_killer_ui_n.fchooser)==0)
	        {
	        	Time_killer_ui_n.file = Time_killer_ui_n.fchooser.getSelectedFile();
	        	Time_killer_ui_n.text_core.setText(Time_killer_ui_n.file.getAbsolutePath());
	        	if (file.exists()) {
	        		Enable_some();
	        		unlockable = true;
	        		Time_killer_ui_n.textarea.setText("正在解除封印...\n封印解除!\n");
	        	}
	        }
		}
		
	}
	
	private static void Init_global_font(Font font) {  
		  FontUIResource fontRes = new FontUIResource(font);  
		  for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {  
		  Object key = keys.nextElement();  
		  Object value = UIManager.get(key);  
		  if (value instanceof FontUIResource) {  
			  UIManager.put(key, fontRes);  
			  }
		  }
	}
	
	public static void main(String args[]) {
		Init_global_font(new Font("黑体", Font.PLAIN, 20));  //统一设置字体
		new Time_killer_ui_n();
	}
}