package crisis.actions;

import java.util.ArrayList;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import crisis.Application;
import crisis.application.Crisis;
import crisis.application.CrisisItemGroup;
import crisis.dialogs.CreateGroupDialog;
import crisis.dialogs.LoadDialog;

public class LoadAction extends Action implements ISelectionListener,
			IWorkbenchAction {

		private final IWorkbenchWindow window;
		private IStructuredSelection selection;

		public LoadAction(IWorkbenchWindow window) {

			this.window = window;
			setText("&Load");
			setToolTipText("");
			setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
					Application.PLUGIN_ID, crisis.IImageKeys.CREATE_GROUP));
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

//			CreateGroupDialog dialog = new CreateGroupDialog(window.getShell());
//			Object item = selection.getFirstElement();
//			CrisisItemGroup parentGroup = (CrisisItemGroup) item;
//			dialog.setParentGroup(parentGroup);
			LoadDialog dialog = new LoadDialog(window.getShell());
			int code = dialog.open();
			if (code == Window.OK) {
				
			}
		}

	}

