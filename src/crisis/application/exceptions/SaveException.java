package crisis.application.exceptions;

public class SaveException extends CrisisException {

	public SaveException(String info, Exception innerException) {

		super(info, innerException);
	}
}
