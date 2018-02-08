package fi.files.excel.template.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fi.files.excel.template.api.ExportExcelApi;
import fi.files.excel.template.domain.CellDomain;
import fi.files.excel.template.exception.FieldTypeFormatException;
import fi.files.excel.template.util.ExcelType;
import fi.files.excel.template.util.FieldType;
import fi.files.excel.template.util.ResourceType;

public class ExportExcelApiIplm<T> implements ExportExcelApi<T> {

	private InputStream getInputStream(String fileTemplate, ResourceType type) throws FileNotFoundException {
		if (type.equals(ResourceType.RESOURCE_STREAM)) {
			return getClass().getResourceAsStream(fileTemplate);
		}
		return new FileInputStream(fileTemplate);
	}

	private Workbook createWorkbook(String fileTemplate, ExcelType type, ResourceType resourceTypetype)
			throws IOException {
		if (type.equals(ExcelType.XLSX)) {
			InputStream is = getInputStream(fileTemplate, resourceTypetype);
			XSSFWorkbook wb = new XSSFWorkbook(is);
			return wb;
		} else {
			InputStream is = getInputStream(fileTemplate, resourceTypetype);
			HSSFWorkbook wb = new HSSFWorkbook(is);
			return wb;
		}
	}

	@Override
	public String createExcel(List<T> list, int rowData, String templatePath, String fileDestPath, ExcelType excelType,
			ResourceType resourceType) throws Exception {

		Workbook workbook = createWorkbookExcel(list, rowData, templatePath, fileDestPath, excelType, resourceType);
		File file = new File(fileDestPath);
		if (!file.exists())
			file.createNewFile();
		FileOutputStream fileOut = new FileOutputStream(fileDestPath);
		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		System.out.println("Done export file " + fileDestPath);
		return fileDestPath;
	}

	@Override
	public Workbook createWorkbookExcel(List<T> list, int rowData, String templatePath, String fileDestPath,
			ExcelType excelType, ResourceType resourceType) throws Exception {
		Workbook workbook = createWorkbook(templatePath, excelType, resourceType);
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(rowData);
		Iterator<Cell> cells = row.cellIterator();
		Map<Integer, CellDomain> map = new HashMap<>();
		while (cells.hasNext()) {
			Cell cell = cells.next();
			String cellData = cell.getStringCellValue();
			CellDomain domain = validateParam(cellData);
			map.put(cell.getColumnIndex(), domain);

		}
		System.out.println(map);
		// xoa row
		sheet.removeRow(row);

		for (int i = 0; i < list.size(); i++) {
			Row createRow = sheet.createRow(rowData + i);
			// chen data
			T t = list.get(i);
			Set<Integer> keys = map.keySet();
			for (Integer cellIndex : keys) {
				CellDomain domain = map.get(cellIndex);

				Cell createCell = createRow.createCell(cellIndex);
				if (domain.getValue().equals(FieldType.STT)) {
					createCell.setCellValue(String.valueOf(i + 1));
				} else {
					Object data = getData(t, domain);
					createCell.setCellValue(String.valueOf(data));
				}
			}
		}
		return workbook;
	}

	private CellDomain validateParam(String cellData) throws FieldTypeFormatException {
		String[] cellVal = cellData.split("\\|");
		if (cellVal.length < 2 || cellVal.length >= 4)
			throw new FieldTypeFormatException(cellData);
		if (isCorrectFieldType(cellVal[1].trim())) {
			CellDomain domain = new CellDomain();
			domain.setKey(cellVal[0].trim());
			domain.setValue(cellVal[1].trim());
			if (cellVal.length > 2) {
				domain.setPattern(cellVal[2].trim());
			}
			return domain;
		} else
			throw new FieldTypeFormatException(cellData);
	}
	

	private boolean isCorrectFieldType(String fieldType) {
		switch (fieldType) {
		case FieldType.STT:
			return true;
		case FieldType.STRING:
			return true;
		case FieldType.FLOAT:
			return true;
		case FieldType.LONG:
			return true;
		case FieldType.BOOLEAN:
			return true;
		case FieldType.INTEGER:
			return true;
		case FieldType.DATE:
			return true;
		case FieldType.DATETIME:
			return true;
		case FieldType.ZONEDDATETIME:
			return true;
		case FieldType.INSTANT:
			return true;
		case FieldType.TIME:
			return true;
		default:
			return false;
		}
	}

	private Object getData(T t, CellDomain domain) throws Exception{
		try {
			Class<? extends Object> testClass = t.getClass();
			Method m = testClass.getMethod(buildMethod(domain.getKey()), new Class[] {});
			switch (domain.getValue()) {
			case FieldType.STRING: {
				return (String) m.invoke(t, new Object[] {});
			}
			case FieldType.FLOAT: {
				return (Float) m.invoke(t, new Object[] {});
			}
			case FieldType.LONG: {
				return (Long) m.invoke(t, new Object[] {});
			}
			case FieldType.BOOLEAN: {
				return (Boolean) m.invoke(t, new Object[] {});
			}
			case FieldType.INTEGER: {
				return (Boolean) m.invoke(t, new Object[] {});
			}
			case FieldType.DATE: {
				// convert to date pattern
				Object obj =  m.invoke(t, new Object[] {});
				return getDateTime(obj, domain.getValue(), domain.getPattern());
			}
			case FieldType.DATETIME: {
				Object obj =  m.invoke(t, new Object[] {});
				return getDateTime(obj, domain.getValue(), domain.getPattern());
			}
			case FieldType.ZONEDDATETIME: {
				// convert to date time pattern
				Object obj =  m.invoke(t, new Object[] {});
				return getDateTime(obj, domain.getValue(), domain.getPattern());
			}
			case FieldType.TIME: {
				// convert to date time pattern
				Object obj =  m.invoke(t, new Object[] {});
				return getDateTime(obj, domain.getValue(), domain.getPattern());
			}

			default:
				return null;
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw e;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw e;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw e;
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String getDateTime(Object obj, String type, String pattern) {
		if (pattern == null) {
			if (type.equals(FieldType.TIME)) {
				pattern = "hh:mm:ss";
			} else if (type.equals(FieldType.DATE)) {
				pattern = "yyyy-MM-dd";
			} else {
				pattern = "yyyy-MM-dd hh:mm:ss";
			}
		}
		if (obj instanceof ZonedDateTime) {
			ZonedDateTime date = (ZonedDateTime) obj;
			return date.format(DateTimeFormatter.ofPattern(pattern));
		} else if (obj instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format((Date) obj);

		} else if (obj instanceof Instant) {
			Instant instant = (Instant) obj;
			Date date = new Date(instant.toEpochMilli());
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		return "";
	}

	public String buildMethod(String method) {
		return "get" + method.substring(0, 1).toUpperCase() + method.substring(1);
	}

}
