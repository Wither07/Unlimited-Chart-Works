package edition1;
/**
 * @author Wither
 * 
 * 部门无课表行高60字号11
 * 总无课表行高180字号10
 */
import javax.swing.filechooser.FileSystemView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
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

public class Create_freetime_table {
	public static String desktop = FileSystemView.getFileSystemView().getHomeDirectory().getPath()+File.separator;

	private static final String[] day = {
			"", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
    private static final String[]  time = {
    		"第一大节", "第二大节","第三大节","第四大节","第五大节"};
    private static final String[]  department = {
    		"总", "公关部", "纪检部","权益部","事务部","文化部"};

	public static void create(String loadPath) throws IOException {
		boolean xlsx = true;
		
		try (Workbook wb = xlsx ? new XSSFWorkbook() : new HSSFWorkbook()) {
			
	        Map<String, CellStyle> styles = createStyles(wb);
	        
	        for (int department_index = 0; department_index < 6; department_index++) {
	        	//create a sheet for each department
                Sheet sheet = wb.createSheet(department[department_index]+"无课表");
                
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
                
                //the header row: centered text in 40pt font
                Row headerRow = sheet.createRow(0);
                headerRow.setHeightInPoints(40);
                Cell titleCell = headerRow.createCell(0);
                titleCell.setCellValue(department[department_index] + "无课表");
                titleCell.setCellStyle(styles.get("title"));
                sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$H$1"));
                
                Row weekdaysRow = sheet.createRow(1);
                weekdaysRow.setHeightInPoints(20);
                //column 0
                sheet.setColumnWidth(0, 14 * 256); //the column is 14 characters wide
                Cell weekdaysCell = weekdaysRow.createCell(0);
                weekdaysCell.setCellValue(day[0]);
                weekdaysCell.setCellStyle(styles.get("weekdays"));
                //column 1-8
                for (int i = 1; i < day.length; i++) {
                    //set column widths, the width is measured in units of 1/256th of a character width
                    sheet.setColumnWidth(i, 36 * 256); //the column is 36 characters wide
                    weekdaysCell = weekdaysRow.createCell(i);
                    weekdaysCell.setCellValue(day[i]);
                    weekdaysCell.setCellStyle(styles.get("weekdays"));
                }

                boolean sumup = (department_index == 0);
                if(!sumup) {
                	int rownum = 2;
                	String[][] inputs= Find_by_department.all_aviliable(loadPath, department[department_index]);
	                for (int t = 0; t < time.length; t++) {
	                	Row row = sheet.createRow(rownum++);
	                    row.setHeightInPoints(60);
	                    Cell title = row.createCell(0);
	                    title.setCellValue(time[t]);
	                    title.setCellStyle(styles.get("weekdays"));
						for (int d = 1; d < day.length; d++) {
							Cell input = row.createCell(d);
							//cast input
							String casted = inputs[t][d-1].replace("~", "，");
							if(casted.length()>2) {
								casted = casted.substring(0, casted.length()-1);
							}
							XSSFRichTextString info = new XSSFRichTextString(casted);
							input.setCellValue(info);
							input.setCellStyle(styles.get("department"));
						}
	                }
                }
                else {
                	int rownum = 2;
                	//get inputs
                	String[][][] inputs = new String[5][5][7];
                	for(int dep = 1; dep < department.length; dep++) {
                		inputs[dep - 1] = Find_by_department.all_aviliable(loadPath, department[dep]);
                	}
                	//set fonts
                	Map<String, XSSFFont> fonts = createFonts(wb);
                	
	                for (int t = 0; t < time.length; t++) {
	                	Row row = sheet.createRow(rownum++);
	                    row.setHeightInPoints(180);
	                    Cell title = row.createCell(0);
	                    title.setCellValue(time[t]);
	                    title.setCellStyle(styles.get("weekdays"));
						for (int d = 1; d < day.length; d++) {
							int[] last_character = {0, 0, 0, 0, 0};
							Cell input = row.createCell(d);
							//cast input
							String casted = "";
							for (int casting = 0; casting < 5; casting++) {
								casted += inputs[casting][t][d-1].replace("~", "，");
								last_character[casting] = inputs[casting][t][d-1].length();
							}
							if (casted.length()>1) {
								casted = casted.substring(0, casted.length()-1);
							}
							for (int casting = 0; casting < 5; casting++) {
								if(last_character[casting]!=0) {
									last_character[casting]--;
									break;
								}
							}
							for (int casting = 1; casting < 5; casting++) {
								last_character[casting] += last_character[casting-1];
							}
							XSSFRichTextString info = new XSSFRichTextString(casted);
							
							info.applyFont(0, last_character[0], fonts.get("公关部"));
							info.applyFont(last_character[0], last_character[1], fonts.get("纪检部"));
							info.applyFont(last_character[1], last_character[2], fonts.get("权益部"));
							info.applyFont(last_character[2], last_character[3], fonts.get("事务部"));
							info.applyFont(last_character[3], last_character[4], fonts.get("文化部"));
					
							input.setCellValue(info);
				            input.setCellStyle(styles.get("sum"));
						}	
	                }
	                Row footerRow = sheet.createRow(rownum);
			        footerRow.setHeightInPoints(20);
			        XSSFRichTextString RTString = new XSSFRichTextString("公关部 纪检部 权益部 事务部 文化部");
			        int first = 0;
			        int last = 3;
			        for (int index = 1; index < department.length; index++) {
			        	RTString.applyFont(first, last, fonts.get(department[index]));
			        	first+=4;
			        	last+=4;
		            }
			        Cell footerCell = footerRow.createCell(0);
			        footerCell.setCellValue(RTString);
			        footerCell.setCellStyle(styles.get("footer"));
			        for (int index = 1; index < 8; index++) {
			        	footerCell = footerRow.createCell(index);
			        	footerCell.setCellStyle(styles.get("footer"));
			        }
			        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$8:$H$8"));
				}
	        }
	        
	        // Write the output to a file
	        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
	        String random = df.format(new Date());
	        createDir(desktop+"Unlimited_Chart_Works");
            String file = desktop + "Unlimited_Chart_Works"+File.separator+"无课表_" + random + ".xls";
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
        titleFont.setFontHeightInPoints((short)20);
        titleFont.setColor(IndexedColors.BLACK.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
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
        styles.put("footer", style);
        
        Font weekdaysFont = wb.createFont();
        weekdaysFont.setFontHeightInPoints((short)16);
        weekdaysFont.setColor(IndexedColors.WHITE.getIndex());
        weekdaysFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(weekdaysFont);
        styles.put("weekdays", style);

        Font depFont = wb.createFont();
        depFont.setFontHeightInPoints((short)11);
        depFont.setBold(false);
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
        style.setFont(depFont);
        style.setWrapText(true);
        styles.put("department", style);

        Font sumFont = wb.createFont();
        sumFont.setFontHeightInPoints((short)10);
        sumFont.setBold(false);
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
        style.setFont(sumFont);
        style.setWrapText(true);
        styles.put("sum", style);
        
        return styles;
	}

	private static Map<String, XSSFFont> createFonts(Workbook wb) {
		
		Map<String, XSSFFont> fonts = new HashMap<>();
		
		XSSFFont f1 = (XSSFFont) wb.createFont();
		f1.setColor(IndexedColors.BLUE.getIndex());
		f1.setFontHeightInPoints((short)10);
		f1.setBold(false);
		fonts.put("公关部", f1);
        
		XSSFFont f2 = (XSSFFont) wb.createFont();
		f2.setColor(IndexedColors.GREEN.getIndex());
		f2.setFontHeightInPoints((short)10);
		f2.setBold(false);
		fonts.put("纪检部", f2);

		XSSFFont f3 = (XSSFFont) wb.createFont();
		f3.setColor(IndexedColors.ORANGE.getIndex());
		f3.setFontHeightInPoints((short)10);
		f3.setBold(false);
		fonts.put("权益部", f3);

		XSSFFont f4 = (XSSFFont) wb.createFont();
		f4.setColor(IndexedColors.PINK.getIndex());
		f4.setFontHeightInPoints((short)10);
		f4.setBold(false);
		fonts.put("事务部", f4);

		XSSFFont f5 = (XSSFFont) wb.createFont();
		f5.setColor(IndexedColors.BLACK.getIndex());
		f5.setFontHeightInPoints((short)10);
		f5.setBold(false);
		fonts.put("文化部", f5);
        
		return fonts;
	}
	
	public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {
            return false;  
        }
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }
        if (dir.mkdirs()) {
            return true;  
        } else {
            return false;  
        }  
    }
	
	public static void main(String args[]) {
		String loadPath = desktop + "DarkFlameContract.txt";
		try {
			create(loadPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
