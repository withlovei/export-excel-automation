package fi.files.excel.template.api;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import fi.files.excel.template.util.ExcelType;
import fi.files.excel.template.util.ResourceType;

public interface ExportExcelApi<T> {

	public String createExcel(List<T> list, int rowData, String templatePath,
			String fileDestPath, ExcelType excelType, ResourceType resourceType) throws Exception;

	public Workbook createWorkbookExcel(List<T> list, int rowData, String templatePath,
			String fileDestPath, ExcelType excelType, ResourceType resourceType) throws Exception;

}
