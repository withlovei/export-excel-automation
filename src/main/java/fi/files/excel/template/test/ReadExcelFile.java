package fi.files.excel.template.test;

import java.util.ArrayList;
import java.util.List;

import fi.files.excel.template.api.ExportExcelApi;
import fi.files.excel.template.impl.ExportExcelApiIplm;
import fi.files.excel.template.util.ExcelType;
import fi.files.excel.template.util.ResourceType;

public class ReadExcelFile {
	
	public static List<User> createData(){
		List<User> users = new ArrayList<>();
		users.add(new User(1l, "Ngo van son", "Ha noi", true));
		users.add(new User(2l, "Ngo van 2", "Ha noi", true));
		users.add(new User(3l, "Ngo van 3", "Ha noi", false));
		users.add(new User(4l, "Ngo van 4", "Ha noi", true));
		return users;
	}
	
	public static void main(String[] args) throws Exception {
		String pathExternal = "/Volumes/Data/template.xls";
//		String pathExternal = "/template.xls";
		
		ExportExcelApi<User> api = new ExportExcelApiIplm<>();
		api.createExcel(createData(), 2, pathExternal, "/Volumes/Data/export.xls", ExcelType.XLS, ResourceType.EXTERNAL);
		
	}
}
