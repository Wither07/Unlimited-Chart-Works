package edition1;
/**
 * 
 * @author Wither
 *
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

public class Aio_ui extends JDialog implements ActionListener {
	public JList<Object> list;
	public JButton select, delete, aio;
	public JTextField filename;
	public JPanel p1, p2, p3;
	public DefaultListModel<Object> dlm;
	public JFileChooser fch;
	
	public Aio_ui() {
		this.setSize(520, 360);
        this.setTitle("All in one");
		this.setLayout(new BorderLayout(5, 5));
        
        fch = new JFileChooser(
				new File(FileSystemView.getFileSystemView().getHomeDirectory().getPath(),""));
		fch.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fch.setFileFilter(new ExtensionFileFilter("txt", "文本文件(.txt)"));
        
        p1 = new JPanel();
        p1.setBorder(BorderFactory.createTitledBorder("合成列表"));
        p1.setLayout(new GridLayout(1, 1));
		list = new JList<Object>();
		dlm = new DefaultListModel<Object>(); 
		p1.add(list);
		this.add(p1, BorderLayout.CENTER);
		
		p3 = new JPanel();
		p3.setLayout(new GridLayout(2, 1));
		p3.add(new JLabel("输出的文件路径："));
		String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
		filename = new JTextField(desktopPath + "\\DarkFlameContract.txt");
		p3.add(filename);
		this.add(p3, BorderLayout.SOUTH);
		
		p2 = new JPanel();
		p2.setLayout(new GridLayout(4, 1, 30, 30));
		select = new JButton("选择文件");
		p2.add(select);
		select.addActionListener(this);
		delete = new JButton("取消选中");
		p2.add(delete);
		delete.addActionListener(this);
		aio = new JButton("合成");
		p2.add(aio);
		aio.addActionListener(this);
		this.add(p2, BorderLayout.EAST);
		
        setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == select) {
			if (fch.showOpenDialog(fch)==0)
	        {
				File file = fch.getSelectedFile();
	        	String path = file.getAbsolutePath();
	        	if (file.exists()) {
	        		dlm.add(0, path);
	        	}
	        }
			list.setModel(dlm);
		}
		if(e.getSource() == delete) {
			int index = list.getSelectedIndex();
			dlm.remove(index);
			list.setModel(dlm);
		}
		if(e.getSource() == aio) {
			File file = new File(filename.getText());
			if(!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			for(int i = 0; i < dlm.getSize(); i++) {
				String p = (String) dlm.getElementAt(i);
				try {
					All_in_one.getData(p);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			try {
				All_in_one.inOne(file.getAbsolutePath());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, "已在指定文件中添加内容", "提示", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
		}
	}
}
