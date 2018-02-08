package fi.files.excel.template.exception;

public class FieldTypeFormatException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FieldTypeFormatException() {
	}
	
	public FieldTypeFormatException(String message) {
		super("Fail to validate field [" + message+"]\n. FieldType format field:TYPE:pattern. Pattern optional for DATE, DATETIME, ZONEDATETIME, INSTANT, TIME");
	}
}
