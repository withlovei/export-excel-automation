package fi.files.excel.template.exception;

public class FieldTypeNotMatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String data = "FieldType allow: STT(default), STRING, INTEGER, LONG, DOUBLE, FLOAT, DATE, DATETIME, ZONEDATETIME, INSTANT, TIME";
	
	public FieldTypeNotMatchException() {
	
	}
	
	public FieldTypeNotMatchException(String message) {
		super("Not allow field type: ["+message+"]. " + data );
	}
}
