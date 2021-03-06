package kr.ac.cbnu.computerengineering.common.exception;

@SuppressWarnings("serial")
public class ExcelFormatException extends Exception {
	public ExcelFormatException() {
		super("Excel file format does not correct.");
	}
}
