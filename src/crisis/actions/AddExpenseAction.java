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
import crisis.application.CrisisItemGroup;
import crisis.dialogs.AddExpenseDialog;

public class AddExpenseAction extends Action implements ISelectionListener,
		IWorkbenchAction {

	private final IWorkbenchWindow window;
	private IStructuredSelection selection;

	public AddExpenseAction(IWorkbenchWindow window) {

		this.window = window;
		setText("&Add Transaction");
		setToolTipText("Register a new transaction.");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				Application.PLUGIN_ID, crisis.IImageKeys.ADD_EXPENSE));
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
		
		Object item = selection.getFirstElement();
		CrisisItemGroup group = (CrisisItemGroup) item;
		
		AddExpenseDialog dialog = new AddExpenseDialog(window.getShell());
		
		dialog.setGroupPreselection(group);
		

		int code = dialog.open();
		if (code == Window.OK) {
		}
	}
}
