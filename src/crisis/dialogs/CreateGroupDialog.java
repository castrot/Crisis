package crisis.dialogs;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import crisis.application.CrisisItemGroup;
import crisis.application.CrisisManager;

public class CreateGroupDialog extends Dialog {

	Map<Integer, CrisisItemGroup> groupMap = new HashMap<>();
	CrisisItemGroup parentGroup = null;
	private Combo parentGroupCombo = null;
	private Text newGroupNameText = null;
	private Text comment = null;

	public String getGroupName() {

		return "";
	}

	public CreateGroupDialog(Shell parentShell) {

		super(parentShell);
	}

	public void setParentGroup(CrisisItemGroup group) {

		parentGroup = group;
	}

	public CrisisItemGroup getParentGroup() {

		return parentGroup;
	}

	protected Control createDialogArea(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		getShell().setText("Create Group");

		// name
		Label label = new Label(composite, SWT.NONE);
		label.setText("&Name:");
		label.setLayoutData(new GridData(GridData.END, GridData.CENTER, false,
				false));
		newGroupNameText = new Text(composite, SWT.BORDER);

		// group
		Label userIdLabel = new Label(composite, SWT.NONE);
		userIdLabel.setText("&Parent Group:");
		userIdLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));
		parentGroupCombo = new Combo(composite, SWT.BORDER);
		int i = 0;
		for (CrisisItemGroup group : CrisisManager.getCrisis().getGroups()) {
			parentGroupCombo.add(group.getName(), i);
			groupMap.put(i, group);
			if (group == parentGroup) {
				parentGroupCombo.select(i);
			}
			i++;
		}

		// comment
		Label labelComment = new Label(composite, SWT.NONE);
		labelComment.setText("&Comment:");
		labelComment.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));
		comment = new Text(composite, SWT.NONE);

		// parentGroupCombo.getSelectionIndex()
		return composite;
	}

	protected void okPressed() {

		String newGroupName = newGroupNameText.getText();

		CrisisItemGroup parentGroup = groupMap.get(parentGroupCombo
				.getSelectionIndex());

		CrisisManager.getCrisis().addGroup(
				new CrisisItemGroup(newGroupName, parentGroup));

		super.okPressed();
	}
}
