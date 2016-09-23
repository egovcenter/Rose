package egovframework.com.uss.ion.apu;

public class ApprovalException extends Exception {
	private static final long serialVersionUID = -5170719813416072691L;
	public static final int GENERAL_CODE = 0;
	public static final int FORM_IN_USE = 101;
	
	private int errorCode = GENERAL_CODE;
	
	public ApprovalException() {
		super();
	}
	public ApprovalException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	public ApprovalException(String message, Throwable cause) {
		super(message, cause);
	}
	public ApprovalException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}
	public ApprovalException(String message) {
		super(message);
	}
	public ApprovalException(Throwable cause) {
		super(cause);
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
}
