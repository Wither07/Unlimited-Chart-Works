package edition1;
/**
 * @author Wither
 * 
 * 
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class Create_table_dialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = -2300807217978766705L;
	
	public String title;
	public JCheckBox ckbox_freetime;
	public JCheckBox ckbox_rota;
	public JCheckBox ckbox_detail;
	public JCheckBox ckbox_sign;
	public JButton button_ok;
	public JButton button_cancel;
	public JPanel button_area, check_area;
	public JPanel panel_freetime, panel_rota, panel_detail, panel_sign;
	public Create_table_dialog(JFrame f,String s) {
		super(f, s, true);
		this.title = s;
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setSize(260, 260);
		
		check_area = new JPanel();
		panel_freetime = new JPanel();
		panel_detail = new JPanel();
		panel_rota = new JPanel();
		panel_sign = new JPanel();
		check_area.setLayout(new GridLayout(4, 1, 0, 10));
		ckbox_freetime = new JCheckBox();
		ckbox_rota = new JCheckBox();
		ckbox_detail = new JCheckBox();
		ckbox_sign = new JCheckBox();
		panel_detail.add(ckbox_detail);
		panel_detail.add(new JLabel("通讯录"));
		panel_freetime.add(ckbox_freetime);
		panel_freetime.add(new JLabel("无课表"));
		panel_rota.add(ckbox_rota);
		panel_rota.add(new JLabel("值班表"));
		panel_sign.add(ckbox_sign);
		panel_sign.add(new JLabel("签到表"));
		ckbox_freetime.addActionListener(this);
		ckbox_detail.addActionListener(this);
		ckbox_rota.addActionListener(this);
		ckbox_sign.addActionListener(this);
		check_area.add(panel_detail);
		check_area.add(panel_freetime);
		check_area.add(panel_rota);
		check_area.add(panel_sign);
		this.add(check_area, BorderLayout.CENTER);
		
		button_area = new JPanel();
		button_area.setLayout(new FlowLayout());
		button_ok = new JButton("创建");
		button_cancel = new JButton("取消");
		button_area.add(button_ok);
		button_area.add(button_cancel);
		button_ok.addActionListener(this);
		button_cancel.addActionListener(this);
		this.add(button_area, BorderLayout.SOUTH);
		//general settings
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button_ok) {
			String report = "";
			if (ckbox_freetime.isSelected()) {
				try {
					Create_freetime_table.create(Time_killer_ui.text_core.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				report += "无课表\n";
			}
			
			if (ckbox_rota.isSelected()) {
				
				int response = JOptionPane.showConfirmDialog(
						this,
						"要重新排班嘛?", "提问", JOptionPane.YES_NO_OPTION);
				if(response==0) { 
					try {
						Create_rota_table.create(Time_killer_ui.text_core.getText(), true);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(this, "钥匙中的排班信息已经与生成的值班表同步", "说明", JOptionPane.WARNING_MESSAGE);
				}
				else if(response==1) { 
					try {
						Create_rota_table.create(Time_killer_ui.text_core.getText(), false);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				JOptionPane.showMessageDialog(this, "将根据现有钥匙中的排班做值班表", "说明", JOptionPane.WARNING_MESSAGE);
				report += "值班表\n";
			}
			
			if (ckbox_detail.isSelected()) {
				try {
					Create_detail_table.create(Time_killer_ui.text_core.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				report += "通讯录\n";
			}
			if (ckbox_sign.isSelected()) {
				try {
					JOptionPane.showMessageDialog(this, "将根据现有钥匙中的排班做值班签到表", "说明", JOptionPane.WARNING_MESSAGE);
					Create_sign_table.create(Time_killer_ui.text_core.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				report += "签到表\n";
			}
			if (report.equals("")) {
				Time_killer_ui.textarea.setText("没有选择表格");
			}
			else {
				Time_killer_ui.textarea.setText("已制作:\n" + report);
				this.setVisible(false);
			}
		}
		if (e.getSource() == button_cancel) {
			Time_killer_ui.textarea.setText("已取消");
			this.setVisible(false);
		}
	}
	
}
