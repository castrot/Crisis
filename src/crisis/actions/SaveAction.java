package crisis.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import crisis.Application;
import crisis.application.Crisis;
import crisis.application.CrisisItemGroup;
import crisis.application.CrisisManager;
import crisis.application.exceptions.SaveException;
import crisis.dialogs.AddExpenseDialog;

public class SaveAction extends Action implements ISelectionListener,
		IWorkbenchAction {

	private final IWorkbenchWindow window;
	private IStructuredSelection selection;

	public SaveAction(IWorkbenchWindow window) {

		this.window = window;
		setText("&Save");
		setToolTipText("Save current modifications.");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				Application.PLUGIN_ID, crisis.IImageKeys.SAVE));
		window.getSelectionService().addSelectionListener(this);
	}

	@Override
	public void dispose() {

		window.getSelectionService().removeSelectionListener(this);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {

		if (incoming instanceof IStructuredSelection) {
			selection = (IStructuredSelection) incoming;
		}

	}

	public void run() {

		try {
			CrisisManager.save();
		} catch (SaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
