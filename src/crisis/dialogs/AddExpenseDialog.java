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
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import crisis.application.CrisisItemGroup;
import crisis.application.CrisisItemTransaction;
import crisis.application.CrisisManager;
import crisis.application.Date;
import crisis.application.Money;

public class AddExpenseDialog extends Dialog {

	private Map<Integer, CrisisItemGroup> groupMap = new HashMap<>();
	private Combo parentGroupCombo;
	private DateTime dateTime;
	private Text value;
	private Text description;
	private Text comment;
	private CrisisItemGroup preSelectedGroup;

	public AddExpenseDialog(Shell parentShell) {

		super(parentShell);
	}

	protected Control createDialogArea(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		getShell().setText("Add Transaction");

		// description
		Label labelDescription = new Label(composite, SWT.NONE);
		labelDescription.setText("&Description:");
		labelDescription.setLayoutData(new GridData(GridData.END,
				GridData.CENTER, false, false));
		description = new Text(composite, SWT.NONE);

		// group
		Label labelGroup = new Label(composite, SWT.NONE);
		labelGroup.setText("&Group:");
		labelGroup.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));
		parentGroupCombo = new Combo(composite, SWT.READ_ONLY);

		int i = 0;
		for (CrisisItemGroup group : CrisisManager.getCrisis().getGroups()) {
			groupMap.put(i, group);
			parentGroupCombo.add(group.getName(), i);
			if (group == preSelectedGroup) {
				parentGroupCombo.select(i);
			}
		}

		// date
		Label labelDate = new Label(composite, SWT.NONE);
		labelDate.setText("&Date:");
		labelDate.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));
		dateTime = new DateTime(composite, SWT.NONE);

		// value
		Label labelValue = new Label(composite, SWT.NONE);
		labelValue.setText("&Value:");
		labelValue.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));
		value = new Text(composite, SWT.NONE);

		// comment
		Label labelComment = new Label(composite, SWT.NONE);
		labelComment.setText("&Comment:");
		labelComment.setLayoutData(new GridData(GridData.END, GridData.CENTER,
				false, false));
		comment = new Text(composite, SWT.NONE);

		return composite;
	}

	protected void okPressed() {

		// CrisisItemGroup group = newGroupName.getSelectionIndex();
		Date date = new Date(dateTime.getYear(), dateTime.getMonth(),
				dateTime.getDay());
		Money money = new Money(Double.valueOf(value.getText()));

		CrisisItemGroup group = groupMap.get(parentGroupCombo
				.getSelectionIndex());

		CrisisItemTransaction tx = new CrisisItemTransaction(date,
				description.getText(), money, comment.getText());

		CrisisManager.getCrisis().addTransaction(group, tx);

		super.okPressed();
	}

	public void setGroupPreselection(CrisisItemGroup group) {

		this.preSelectedGroup = group;
	}
}
