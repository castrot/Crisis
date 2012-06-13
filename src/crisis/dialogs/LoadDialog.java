package crisis.dialogs;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class LoadDialog extends Dialog {
	 
		public LoadDialog(Shell parentShell) {
			
			super(parentShell);
		}
		
//private File file;		
		protected Control createDialogArea(Composite parent) {
			
			Composite composite = new Composite(parent, SWT.NONE);
			GridLayout layout = new GridLayout(2, false);
			composite.setLayout(layout);
			getShell().setText("Load");
			
			// description
			FileDialog  fileDialog = new FileDialog(getShell(), SWT.NONE);
			
			fileDialog.open();
			return composite;
		}
	 
		protected void okPressed() {

			super.okPressed();
		}
	}


