package crisis.application.exceptions;

public class LoadException extends CrisisException {

	
	
	public LoadException(String info, Exception innerException) {
		
		super(info, innerException);
	}
}
