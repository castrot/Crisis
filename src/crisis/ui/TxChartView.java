package crisis.ui;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;

import crisis.application.CrisisItemGroup;
import crisis.application.CrisisItemGroup.ICrisisGroupListener;
import crisis.application.CrisisItemTransaction;

public class TxChartView extends ViewPart implements ISelectionListener,
		ICrisisGroupListener {

	public static final String ID = "crisis.views.tx.chart";

	private TableViewer tableViewer;

	private CrisisItemGroup group;

	private IAdapterFactory adapterFactory = new CrisisAdapterFactory();

	public TxChartView() {

		super();
	}

	@Override
	public void createPartControl(Composite parent) {

		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		Platform.getAdapterManager().registerAdapters(adapterFactory,
				CrisisItemGroup.class);
		tableViewer.setLabelProvider(new WorkbenchLabelProvider());
		// Create the columns
		// Not yet implemented
		// createColumns(parent);

		// Make lines and make header visible
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		TableViewerColumn col1 = new TableViewerColumn(tableViewer, SWT.NONE);
		col1.getColumn().setWidth(100);
		col1.getColumn().setText("Date");
		col1.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				CrisisItemTransaction tx = (CrisisItemTransaction) element;
				return tx.getDate().toString();
			}
		});
		TableViewerColumn col2 = new TableViewerColumn(tableViewer, SWT.NONE);
		col2.getColumn().setWidth(200);
		col2.getColumn().setText("Description");
		col2.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				CrisisItemTransaction tx = (CrisisItemTransaction) element;
				return tx.getDescription();
			}
		});
		TableViewerColumn col3 = new TableViewerColumn(tableViewer, SWT.NONE);
		col3.getColumn().setWidth(100);
		col3.getColumn().setText("Value");
		col3.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				CrisisItemTransaction tx = (CrisisItemTransaction) element;
				return tx.getValue().toString();
			}
		});

		getSite().getWorkbenchWindow().getSelectionService()
				.addSelectionListener(this);

		setGroup(group);

	}

	@Override
	public void setFocus() {

		tableViewer.getControl().setFocus();

	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection incoming) {

		IStructuredSelection selection = null;

		if (incoming instanceof IStructuredSelection) {
			selection = (IStructuredSelection) incoming;
		}
		if (selection.size() == 1
				&& selection.getFirstElement() instanceof CrisisItemGroup) {
			unsetGroup(group);
			group = (CrisisItemGroup) selection.getFirstElement();
			setGroup(group);
		}
	}

	@Override
	public void groupChanged() {

		unsetGroup(group);
		setGroup(group);
	}

	private void setGroup(CrisisItemGroup group) {

		if (group != null) {
			group.registerListener(this);
			tableViewer.add(group.getAllTransactions().toArray());
		}
	}

	private void unsetGroup(CrisisItemGroup group) {

		if (group != null) {
			group.unregisterListener(this);
			tableViewer.remove(group.getAllTransactions().toArray());
		}
	}

}
