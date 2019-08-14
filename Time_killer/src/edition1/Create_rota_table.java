package edition1;
/**
 * @author Wither
 * 
 * 
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Create_rota_table {
	public static String desktop = FileSystemView.getFileSystemView().getHomeDirectory().getPath()+File.separator;
	private static final String[] day = {
			"", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private static final String[] time = {
    		"第一大节", "第二大节","第三大节","第四大节","第五大节"};
	private static final String[]  department = {
    		"公关部", "纪检部","权益部","事务部","文化部"};
	
	public static void create(String filePath, boolean reset) throws IOException {
		if(reset) Auto_arrange.reset(filePath);
		boolean xlsx = true;
		int max = 3;
		try (Workbook wb = xlsx ? new XSSFWorkbook() : new HSSFWorkbook()) {
			
	        Map<String, CellStyle> styles = createStyles(wb);
	        
	        Sheet sheet = wb.createSheet("值班表");
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
	                sheet.setColumnWidth(i, 17 * 256); //the column is 8 characters wide
	                Cell titleCell = titleRow.createCell(i);
	                titleCell.setCellValue(day[i]);
	                titleCell.setCellStyle(styles.get("title"));
            	}
            }
            
            String[][][] input = Sign_table_get_input.with_dep(filePath);
            //set fonts
        	Map<String, XSSFFont> fonts = createFonts(wb);
        	
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
                	String[] casting = input[i / 3][j - 1][(rownum + 1) % 3].split("·");
                	XSSFRichTextString RTString = new XSSFRichTextString(casting[0]);
	               	if (!casting[0].equals("")) RTString.applyFont(fonts.get(casting[1]));
	               	Cell rowCell = row.createCell(j);
		            rowCell.setCellValue(RTString);
		            rowCell.setCellStyle(styles.get("name"));
               	}
            }
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$A$4"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$5:$A$7"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$8:$A$10"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$11:$A$13"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$14:$A$16"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$G$2:$G$4"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$G$5:$G$7"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$G$8:$G$10"));
            sheet.addMergedRegion(CellRangeAddress.valueOf("$G$11:$G$13"));
            
            Row footerRow = sheet.createRow(rownum);
            footerRow.setHeightInPoints(20);
            sheet.addMergedRegion(CellRangeAddress.valueOf("$A$17:$G$17"));
            XSSFRichTextString RTString = new XSSFRichTextString("公关部 纪检部 权益部 事务部 文化部");
            int first = 0;
            int last = 3;
            for (int index = 0; index < department.length; index++) {
            	RTString.applyFont(first, last, fonts.get(department[index]));
            	first+=4;
            	last+=4;
            }
            Cell footerCell = footerRow.createCell(0);
            footerCell.setCellValue(RTString);
        	footerCell.setCellStyle(styles.get("title"));
            for (int index = 1; index < 7; index++) {
            	footerCell = footerRow.createCell(index);
            	footerCell.setCellStyle(styles.get("title"));
            }
            
            // Write the output to a file
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
	        String random = df.format(new Date());
	        Create_freetime_table.createDir(desktop+"Unlimited_Chart_Works");
            String file = desktop + "Unlimited_Chart_Works"+File.separator+"值班表_" + random + ".xls";
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
	
	private static Map<String, XSSFFont> createFonts(Workbook wb) {
		
		Map<String, XSSFFont> fonts = new HashMap<>();
		
		XSSFFont f1 = (XSSFFont) wb.createFont();
		f1.setColor(IndexedColors.BLUE.getIndex());
		f1.setFontHeightInPoints((short)11);
		f1.setBold(false);
		fonts.put("公关部", f1);
        
		XSSFFont f2 = (XSSFFont) wb.createFont();
		f2.setColor(IndexedColors.GREEN.getIndex());
		f2.setFontHeightInPoints((short)11);
		f2.setBold(false);
		fonts.put("纪检部", f2);

		XSSFFont f3 = (XSSFFont) wb.createFont();
		f3.setColor(IndexedColors.ORANGE.getIndex());
		f3.setFontHeightInPoints((short)11);
		f3.setBold(false);
		fonts.put("权益部", f3);

		XSSFFont f4 = (XSSFFont) wb.createFont();
		f4.setColor(IndexedColors.PINK.getIndex());
		f4.setFontHeightInPoints((short)11);
		f4.setBold(false);
		fonts.put("事务部", f4);

		XSSFFont f5 = (XSSFFont) wb.createFont();
		f5.setColor(IndexedColors.BLACK.getIndex());
		f5.setFontHeightInPoints((short)10);
		f5.setBold(false);
		fonts.put("文化部", f5);
        
		return fonts;
	}
	
	public static void main(String args[]) {
		String filePath = desktop + "DarkFlameContract.txt";
		try {
			create(filePath, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
