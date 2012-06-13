package crisis.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.io.FileOutputStream;

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
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.ViewPart;
import org.jCharts.Chart;
import org.jCharts.chartData.PieChartDataSet;
import org.jCharts.encoders.PNGEncoder;
import org.jCharts.nonAxisChart.PieChart2D;
import org.jCharts.properties.ChartProperties;
import org.jCharts.properties.LegendProperties;
import org.jCharts.properties.PieChart2DProperties;

import crisis.application.CrisisItemGroup;
import crisis.application.CrisisItemGroup.ICrisisGroupListener;
import crisis.application.CrisisItemTransaction;

public class TransactionView extends ViewPart implements ISelectionListener,
		ICrisisGroupListener {

	public static final String ID = "crisis.views.transaction";

	private TableViewer tableViewer;

	private CrisisItemGroup group;

	private IAdapterFactory adapterFactory = new CrisisAdapterFactory();

	public TransactionView() {

		super();
	}

	@Override
	public void createPartControl(Composite parent) {

		// tableViewer = new TableViewer(parent, SWT.BORDER| SWT.MULTI |
		// SWT.V_SCROLL);
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

	
	public void run() throws Throwable
	{
		this.basicChart();
		this.zeroDegreeOffsetChart();
		this.borderStroke();
		this.borderPaint();
	}


	void exportImage( Chart chart, String fileName )
	{
	   String extension= ".png";
		FileOutputStream fileOutputStream;

		try
		{
			fileOutputStream= new FileOutputStream( fileName + extension );
			PNGEncoder.encode( chart, fileOutputStream );
			fileOutputStream.flush();
			fileOutputStream.close();
		}
		catch( Throwable throwable )
		{
			throwable.printStackTrace();
		}
	}
	/******************************************************************************************/
	private void outputChart( PieChart2DProperties pieChart2DProperties, String name ) throws Throwable
	{
		double[] data= { 81d, 55d, 39d, 20.6d };
		String[] labels= { "BMW M5", "BMW M3", "Viper GTS-R", "Corvette Z06" };
		Paint[] paints= { Color.lightGray, Color.green, Color.blue, Color.red };

		PieChartDataSet pieChartDataSet= new PieChartDataSet( "Cars That Own", data, labels, paints, pieChart2DProperties );

		PieChart2D pieChart2D= new PieChart2D( pieChartDataSet, new LegendProperties(), new ChartProperties(), 400, 350 );
		
		exportImage( pieChart2D, name );
	}


	/******************************************************************************************/
	private void basicChart() throws Throwable
	{
		PieChart2DProperties pieChart2DProperties= new PieChart2DProperties();
		this.outputChart( pieChart2DProperties, "pieChartBasic" );
	}


	/******************************************************************************************/
	private void zeroDegreeOffsetChart() throws Throwable
	{
		PieChart2DProperties pieChart2DProperties= new PieChart2DProperties();
		pieChart2DProperties.setZeroDegreeOffset( 45f );
		this.outputChart( pieChart2DProperties, "pieChartZeroOffset" );
	}


	/******************************************************************************************/
	private void borderStroke() throws Throwable
	{
		PieChart2DProperties pieChart2DProperties= new PieChart2DProperties();
		pieChart2DProperties.setBorderStroke( new BasicStroke( 4f ) );
		this.outputChart( pieChart2DProperties, "pieChartBorderStroke" );
	}


	/******************************************************************************************/
	private void borderPaint() throws Throwable
	{
		PieChart2DProperties pieChart2DProperties= new PieChart2DProperties();
		pieChart2DProperties.setBorderPaint( Color.gray );
		this.outputChart( pieChart2DProperties, "pieChartBorderPaint" );
	}
}
