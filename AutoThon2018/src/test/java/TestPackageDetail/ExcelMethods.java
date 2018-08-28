package TestPackageDetail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelMethods {

	public Map<String, List<String>> retrieveDataFromExcel(String filePath, String sheetName) {
		// TODO Auto-generated method stub
        
        Workbook wObj=null;
        Map<String,List<String>>excelValues = new HashMap<String, List<String>>(); 
        
        try {
        InputStream io = new FileInputStream(new File(filePath));
        if(filePath.split("\\.")[1].equalsIgnoreCase("xlsx")) {
              wObj = new XSSFWorkbook(io);
        }else if (filePath.split("\\.")[1].equalsIgnoreCase("xls")){
        	wObj = new HSSFWorkbook(io);
        }
        Sheet sheetObj=wObj.getSheet(sheetName);
        Row firstRow=sheetObj.getRow(0);
        int columnHeaderCount=firstRow.getLastCellNum();
        for(int j=0;j<=columnHeaderCount-1;j++) {
        	String columnHeader=firstRow.getCell(j).getStringCellValue();
            int rowCnt=sheetObj.getLastRowNum();
            List<String>columnValues=new ArrayList<String>();
            
            for (int i=1;i<=rowCnt;i++) {
            	Cell cellObj=sheetObj.getRow(i).getCell(j,Row.RETURN_NULL_AND_BLANK);
            	switch (cellObj.getCellType()) {
            	case (Cell.CELL_TYPE_STRING):
            		columnValues.add(i-1,cellObj.getStringCellValue());
            	break;
            	case (Cell.CELL_TYPE_BLANK):
            		columnValues.add(i-1,"");
            	break;
            	case (Cell.CELL_TYPE_NUMERIC):
            		Double dbl=cellObj.getNumericCellValue();
        		    columnValues.add(i-1,dbl.toString());
        		    break;
            	}
            }
            excelValues.put(columnHeader, columnValues);
            
        }
        
        
        }catch(Exception e) {
        	
        }
	
	return excelValues;
	}
}
