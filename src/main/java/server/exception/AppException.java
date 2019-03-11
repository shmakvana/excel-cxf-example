package server.exception;

public class AppException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AppException(String exceptionMsg)
	{
		super(exceptionMsg);
	}
	
}
