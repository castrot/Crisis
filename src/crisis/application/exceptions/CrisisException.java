package crisis.application.exceptions;

public class CrisisException extends Exception {

	private Exception innerException;

	private String info;

	private CrisisException() {
	}

	protected CrisisException(String info, Exception innerException) {

		this.info = info;
		this.innerException = innerException;
	}
	
	@Override
	public void printStackTrace(){
		super.printStackTrace();
		System.err.println("Cause:");
		innerException.printStackTrace();
	}
}
