package accountManager.model;

/**
 * OverdrawException extends {@link Exception}
 * It's used to prevent the user from withdraw an amount bigger than Account current balance.
 * It's Constructor accept a {@link String} message to be displayed when the exception activates.
 * @author jocar
 */
public class OverdrawException extends Exception {
	public OverdrawException() { super(); }
	public OverdrawException(String s) { super(s); }
}
