package crisis;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import crisis.ui.GroupView;
import crisis.ui.TransactionView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.setEditorAreaVisible(false);
		layout.addView(GroupView.ID, IPageLayout.LEFT, 0.4f, layout.getEditorArea());
		layout.addView(TransactionView.ID, IPageLayout.RIGHT, 0.6f, layout.getEditorArea());
	}
}
