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
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Create_sign_table {
	public static String desktop = FileSystemView.getFileSystemView().getHomeDirectory().getPath()+File.separator;
	private static final String[] day = {
			"", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private static final String[] time = {
    		"第一大节", "第二大节","第三大节","第四大节","第五大节"};
    private static final String[]  department = {
    		"公关部", "纪检部","权益部","事务部","文化部"};
    private static final String[] titles = {
    		"部门", "姓名","签到"};
    
	public static void create(String filePath) throws IOException {
		boolean xlsx = true;
		int max = 3;
		try (Workbook wb = xlsx ? new XSSFWorkbook() : new HSSFWorkbook()) {
			
	        Map<String, CellStyle> styles = createStyles(wb);
	        
	        Sheet sheet = wb.createSheet("值班签到表");
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
	        
            //header for titles
            Row titleRow = sheet.createRow(0);
            titleRow.setHeightInPoints(24);
            for (int i = 0; i < day.length; i++) {
            	if(i == 0) {
            		sheet.setColumnWidth(i, 14 * 256);
            		Cell titleCell = titleRow.createCell(i);
	                titleCell.setCellValue(day[i]);
	                titleCell.setCellStyle(styles.get("title"));
            	} else {
	                //set column widths, the width is measured in units of 1/256th of a character width
	                sheet.setColumnWidth(i * 2 - 1, 8 * 256); //the column is 8 characters wide
	                sheet.setColumnWidth(i * 2, 8 * 256); //the column is 8 characters wide
	                sheet.addMergedRegion(new CellRangeAddress(0, 0, i * 2 - 1, i * 2));
	                Cell titleCell = titleRow.createCell(i * 2 - 1);
	                titleCell.setCellValue(day[i]);
	                titleCell.setCellStyle(styles.get("title"));
	                titleCell = titleRow.createCell(i * 2);
	                titleCell.setCellStyle(styles.get("title"));
            	}
            }
            String[][][] input = Sign_table_get_input.by_path(filePath);
            
            //create table
            int rownum = 1;
            for (int i = 0; i < time.length * max; i++) {
            	Row row = sheet.createRow(rownum++);
                row.setHeightInPoints(23);
                if(rownum % 3 == 2) {
                	Cell titleCell = row.createCell(0);
                	titleCell.setCellValue(time[i / 3]);
                	titleCell.setCellStyle(styles.get("title"));
                }
                for (int j = 1; j < day.length; j++) {
	               	Cell rowCell = row.createCell(j * 2 - 1);
		            rowCell.setCellValue(new XSSFRichTextString(input[i / 3][j - 1][(rownum + 1) % 3]));
		            rowCell.setCellStyle(styles.get("name"));
		            rowCell = row.createCell(j * 2);
		            rowCell.setCellStyle(styles.get("name"));
               	}
                
            }
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$A$4"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$5:$A$7"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$8:$A$10"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$11:$A$13"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$14:$A$16"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$L$2:$M$4"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$L$5:$M$7"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$L$8:$M$10"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$L$11:$M$13"));
            
	        sheet = wb.createSheet("签到表");
	        //turn off gridlines
            sheet.setDisplayGridlines(true);
            sheet.setPrintGridlines(false);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);

            //the following three statements are required only for HSSF
            sheet.setAutobreaks(true);
            printSetup.setFitHeight((short) 1);
            printSetup.setFitWidth((short) 1);
            
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
	    				deps[index].add(line.split("·")[0]);
	    				break;
	    			}
    			}
    		}
    		br.close();
            
            //the header row: centered text in 16pt font
            Row headerRow = sheet.createRow(0);
            headerRow.setHeightInPoints(16);
            for (int i = 0; i < titles.length; i++) {
            	Cell titleCell = headerRow.createCell(i);
            	titleCell.setCellValue(titles[i]);
            	titleCell.setCellStyle(styles.get("name"));
            }
            
            int start = 2;
            int end = 2;
            rownum = 1;
            for (int index = 0; index < 5; index++) {
    			end = deps[index].size() - 1;
    			for (int person = 0; person < deps[index].size(); person++) {
    				Row row = sheet.createRow(rownum++);
                    row.setHeightInPoints(16);
                    if (person == 0) {
                    	Cell depCell = row.createCell(0);
                    	depCell.setCellValue(department[index]);
                    	depCell.setCellStyle(styles.get("name"));
                    }
                    Cell inputCell = row.createCell(1);
                    inputCell.setCellValue(new XSSFRichTextString((String) deps[index].get(person)));
                    inputCell.setCellStyle(styles.get("name"));
                    inputCell = row.createCell(2);
                    inputCell.setCellStyle(styles.get("name"));
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
            String file = desktop + "Unlimited_Chart_Works"+File.separator+"签到表_" + random + ".xls";
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
        titleFont.setFontHeightInPoints((short)14);
        titleFont.setBold(true);
        titleFont.setFontName("微软雅黑 Light");
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
        
        Font nameFont = wb.createFont();
        nameFont.setFontHeightInPoints((short)11);
        nameFont.setBold(false);
        nameFont.setFontName("微软雅黑 Light");
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
        style.setFont(nameFont);
        style.setWrapText(true);
        styles.put("name", style);
        
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
