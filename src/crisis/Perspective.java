package crisis;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import crisis.ui.GroupView;
import crisis.ui.TransactionView;
import crisis.ui.TxChartView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.addView(GroupView.ID, IPageLayout.LEFT, 0.4f, layout.getEditorArea());
		IFolderLayout folder = layout.createFolder("TxViewsFolder", IPageLayout.RIGHT, 0.6f, layout.getEditorArea());
		folder.addView(TransactionView.ID);
		folder.addView(TxChartView.ID);
		
	}
}
