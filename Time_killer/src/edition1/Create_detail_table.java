package edition1;
/**
 * @author Wither
 * 
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.filechooser.FileSystemView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Create_detail_table {
	public static String desktop = FileSystemView.getFileSystemView().getHomeDirectory().getPath() + File.separator;
	private static final int[] width = {
			9, 9, 9, 9, 19, 41, 17, 17, 14};
	private static final int[] info_index = {
			0, 10, 3, 4, 2, 7, 11};
	private static final String[] subtitle = {
			"部门", "序号", "姓名", "性别", "学院", "专业班级", "学号", "联系方式", "QQ"};
	private static final String[]  department = {
    		"公关部", "纪检部","权益部","事务部","文化部"};
	
	public static void create(String filePath) throws IOException {
		boolean xlsx = true;
		
		try (Workbook wb = xlsx ? new XSSFWorkbook() : new HSSFWorkbook()) {
			
			Map<String, CellStyle> styles = createStyles(wb);
			
			//create a sheet 
            Sheet sheet = wb.createSheet("通讯录");
			
            //turn off gridlines
            sheet.setDisplayGridlines(true);
            sheet.setPrintGridlines(false);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);

            //the following three statements are required only for HSSF
            sheet.setAutobreaks(true);
            printSetup.setFitHeight((short) 1);
            printSetup.setFitWidth((short) 1);
            
            //the header row: centered text in 20pt font
            Row headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(23);
            Cell titleCell = headerRow.createCell(0);
            titleCell.setCellValue("干事通讯录");
            titleCell.setCellStyle(styles.get("title"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$I$1"));
            
            Row subtitleRow = sheet.createRow(1);
            subtitleRow.setHeightInPoints(16);
            for (int i = 0; i < subtitle.length; i++) {
                //set column widths, the width is measured in units of 1/256th of a character width
                sheet.setColumnWidth(i, width[i] * 256); //the column is 36 characters wide
                Cell subtitleCell = subtitleRow.createCell(i);
                subtitleCell.setCellValue(subtitle[i]);
                subtitleCell.setCellStyle(styles.get("subtitle"));
            }
            
            Vector[] deps= {
            		new Vector<Object>(), 
            		new Vector<Object>(),
            		new Vector<Object>(),
            		new Vector<Object>(),
            		new Vector<Object>()};
            BufferedReader br =
    				new BufferedReader(
    						new InputStreamReader(
    								new FileInputStream(filePath), "GBK"));
    		for (String line = br.readLine(); line != null; line = br.readLine()) {
    			for(int index = 0; index < 5; index++) {
	    			if (line.split("·")[1].equals(department[index])) {
	    				deps[index].add(line);
	    				break;
	    			}
    			}
    		}
    		br.close();
    		
    		int rownum = 2;
    		int start = 3;
    		int end = 3;
    		for (int index = 0; index < 5; index++) {
    			end = deps[index].size() - 1;
    			for (int person = 0; person < deps[index].size(); person++) {
    				Row row = sheet.createRow(rownum++);
                    row.setHeightInPoints(16);
                    String[] info = (String[]) ((String) deps[index].get(person)).split("·");
    				if(person == 0) {
    					Cell title = row.createCell(0);
    					title.setCellValue(department[index]);
	                    title.setCellStyle(styles.get("subtitle"));
    				}
	                for (int items = 1; items < 9; items++) {
	                	Cell input = row.createCell(items);
	                	if (items == 1) {
	                		input.setCellValue(person + 1);
							input.setCellStyle(styles.get("details_f"));
	                	}
	                	else if ((items == 2)&&(info[info_index[1]].equals("男"))) {
	                		input.setCellValue(info[info_index[items-2]]);
							input.setCellStyle(styles.get("details_m"));
	                	}
	                	else {
		                  	input.setCellValue(info[info_index[items-2]]);
							input.setCellStyle(styles.get("details_f"));
	                	}
	                }
    			}
    			if(end > 0) {
    				sheet.addMergedRegion(CellRangeAddress.valueOf("$A$"+start+":$A$"+(start+end)));
    			}
    			start = start + end + 1;
    		}
    		
			// Write the output to a file
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
	        String random = df.format(new Date());
	        Create_freetime_table.createDir(desktop+"Unlimited_Chart_Works");
            String file = desktop + "Unlimited_Chart_Works"+File.separator+"通讯录_" + random + ".xls";
            if (wb instanceof XSSFWorkbook) file += "x";

            try (FileOutputStream out = new FileOutputStream(file)) {
                wb.write(out);
                System.out.println(file);
            }
		}
		
	}
	
	private static Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<>();

        short borderColor = IndexedColors.BLACK.getIndex();

        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)12);
        titleFont.setBold(true);
        titleFont.setFontName("微软雅黑 Light");
        titleFont.setColor(IndexedColors.BLACK.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(borderColor);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(titleFont);
        styles.put("title", style);

        Font subtitleFont = wb.createFont();
        subtitleFont.setFontHeightInPoints((short)12);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(borderColor);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(subtitleFont);
        styles.put("subtitle", style);

        Font maleFont = wb.createFont();
        maleFont.setFontHeightInPoints((short)11);
        maleFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(borderColor);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(maleFont);
        style.setWrapText(true);
        styles.put("details_m", style);
        
        Font femaleFont = wb.createFont();
        femaleFont.setFontHeightInPoints((short)11);
        femaleFont.setBold(false);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(borderColor);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(borderColor);
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(borderColor);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(borderColor);
        style.setFont(femaleFont);
        style.setWrapText(true);
        styles.put("details_f", style);
        
        return styles;
	}
	
	public static void main(String args[]) {
		String filePath = desktop + "_.txt";
		try {
			create(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
