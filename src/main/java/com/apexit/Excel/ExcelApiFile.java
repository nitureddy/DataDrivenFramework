package com.apexit.Excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;


public class ExcelApiFile{
	
	public FileInputStream fis = null;
	public FileOutputStream fos = null;
    public XSSFWorkbook workbook = null;
    public XSSFSheet sheet = null;
    public XSSFRow row = null;
    public XSSFCell cell = null;
    String xlFilePath;
    private InputStream oFileReader;
	private Workbook oExcelWorkbook;
	
    public ExcelApiFile(String xlFilePath) throws Exception
    {
    	this.xlFilePath = xlFilePath;
        fis = new FileInputStream(xlFilePath);
        workbook = new XSSFWorkbook(fis);
        fis.close();
    }
 
    
	public String getCellData(String sheetName, String colName, int rowNum)
    {
        try
        {
            int col_Num = -1;
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(0);
            for(int i = 0; i < row.getLastCellNum(); i++)
            {
                if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
                    col_Num = i;
            }
 
            row = sheet.getRow(rowNum - 1);
            cell = row.getCell(col_Num);
            cell.setCellType(CellType.STRING);
            if(cell.getCellType() == CellType.STRING)
                return cell.getStringCellValue();
            else if(cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA)
            {
                String cellValue = String.valueOf(cell.getNumericCellValue());
                if(DateUtil.isCellDateFormatted(cell))
                {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = cell.getDateCellValue();
                    cellValue = df.format(date);
                }
                return cellValue;
            }else if(cell.getCellType() == CellType.BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail("row "+rowNum+" or column "+colName +" does not exist  in Excel");
            return "row "+rowNum+" or column "+colName +" does not exist  in Excel";
        }
    }
    
    
    public boolean setCellData(String sheetName, String colName, int rowNum, String value)
    {
        try
        {
            int col_Num = -1;
            sheet = workbook.getSheet(sheetName);
 
            row = sheet.getRow(0);
            for (int i = 0; i < row.getLastCellNum(); i++) {
                if (row.getCell(i).getStringCellValue().trim().equals(colName))
                {
                    col_Num = i;
                }
            }
 
            sheet.autoSizeColumn(col_Num);
            row = sheet.getRow(rowNum - 1);
            if(row==null)
                row = sheet.createRow(rowNum - 1);
 
            cell = row.getCell(col_Num);
            if(cell == null)
                cell = row.createCell(col_Num);
 
            cell.setCellValue(value);
 
            fos = new FileOutputStream(xlFilePath);
            workbook.write(fos);
            fos.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return  false;
        }
        return true;
    }
    
    public String getColumnData(String sheetName,int colNum,int rowNum)
    {
        try
        {
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowNum);
            cell = row.getCell(colNum);
            if(cell.getCellType() == CellType.STRING)
                return cell.getStringCellValue();
            else if(cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA)
            {
                String cellValue  = String.valueOf(cell.getNumericCellValue());
                if (DateUtil.isCellDateFormatted(cell))
                {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy");
                    Date date = cell.getDateCellValue();
                    cellValue = df.format(date);
                }
                return cellValue;
            }else if(cell.getCellType() == CellType.BLANK)
                return "";
            else
                return String.valueOf(cell.getBooleanCellValue());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "row "+rowNum+" or column "+colNum +" does not exist  in Excel";
        }
    }

	public  Object[][] createArrayData(String sFileName,String sSheetName){

		String dataArray[][]=null;
		openExcelWorkbook(sFileName);
		int rows=getRowCountOfSheet(sSheetName);
		int cells=getCellCount(sSheetName,2);
		dataArray=new String[rows][cells];		
		for (int i=2;i<=rows;i++) {
		
		for (int j=1;i<=cells;j++) {
	   dataArray[i][j]=getColumnData(sSheetName,i,j);
        System.out.print(dataArray[i][j]);
		}
	    }
           return dataArray;
        }
	public void openExcelWorkbook(String sFileName) {
		try {
			oFileReader =new FileInputStream(sFileName);
			oExcelWorkbook=WorkbookFactory.create(oFileReader);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    }
	
	public int getRowCountOfSheet(String sSheetName) {
		
		try {
			Sheet oSheet;
			oSheet=oExcelWorkbook.getSheet(sSheetName);
				return oSheet.getPhysicalNumberOfRows();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	    }
	
	public int getCellCount(String sSheetName,int iRow) {
		try {
		Sheet oSheet;
		oSheet=oExcelWorkbook.getSheet(sSheetName);
        Row oRow;
        oRow=oSheet.getRow(iRow);
        return oRow.getLastCellNum();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

}
