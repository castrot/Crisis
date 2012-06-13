package crisis.ui;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import crisis.application.Crisis;
import crisis.application.CrisisManager;
import crisis.application.Crisis.ICrisisListener;
import crisis.application.CrisisItemGroup;

public class GroupView extends ViewPart {

	public static final String ID = "crisis.views.group";

	private TreeViewer treeViewer;
	private IAdapterFactory adapterFactory = new CrisisAdapterFactory();

	public GroupView() {
		super();
	}

	@Override
	public void createPartControl(Composite parent) {

		
		
		treeViewer = new TreeViewer(parent, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL);
		Platform.getAdapterManager().registerAdapters(adapterFactory,
				CrisisItemGroup.class);
		getSite().setSelectionProvider(treeViewer);
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setContentProvider(new BaseWorkbenchContentProvider());
		treeViewer.setInput(CrisisManager.getCrisis().getGroupsRoot());

		getSite().setSelectionProvider(treeViewer);

		
		CrisisManager.getCrisis().registerListener(new ICrisisListener() {

			@Override
			public void groupsChanged() {
				refresh();

			}
		});
	}

	@Override
	public void setFocus() {

		treeViewer.getControl().setFocus();
	}

	private void refresh() {

		getSite().getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				treeViewer.refresh();
			}
		});
	}
}
