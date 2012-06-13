package crisis;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import crisis.actions.AddExpenseAction;
import crisis.actions.CreateGroupAction;
import crisis.actions.LoadAction;
import crisis.actions.SaveAction;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IWorkbenchAction exitAction;
	private IWorkbenchAction createGroupAction;
	private IWorkbenchAction addExpenseAction;
	private IWorkbenchAction saveAction;
	private IWorkbenchAction loadAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);

	}

	protected void makeActions(IWorkbenchWindow window) {

		exitAction = ActionFactory.QUIT.create(window);
		register(exitAction);

		createGroupAction = new CreateGroupAction(window);
		// register(createGroupAction);
		addExpenseAction =  new AddExpenseAction(window);
		// register(addExpenseAction);
		saveAction = new SaveAction(window);
		
		loadAction = new LoadAction(window);
	}

	protected void fillMenuBar(IMenuManager menuBar) {

		MenuManager mainMenu = new MenuManager("&Crisis", "crisis");
		
		mainMenu.add(addExpenseAction);
		mainMenu.add(createGroupAction);
		
		mainMenu.add(loadAction);
		
		mainMenu.add(exitAction);

		
		
		menuBar.add(mainMenu);
	}

	protected void fillCoolBar(ICoolBarManager coolBar) {

		IToolBarManager toolbar = new ToolBarManager(coolBar.getStyle());
		coolBar.add(toolbar);
		
		ActionContributionItem addExpenseActionCI = new ActionContributionItem(addExpenseAction);
		addExpenseActionCI.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolbar.add(addExpenseActionCI);
		
		ActionContributionItem createGroupActionCI = new ActionContributionItem(createGroupAction);
		createGroupActionCI.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolbar.add(createGroupActionCI);
		
		toolbar.add(new Separator());
		
		ActionContributionItem saveActionCI = new ActionContributionItem(saveAction);
		saveActionCI.setMode(ActionContributionItem.MODE_FORCE_TEXT);
		toolbar.add(saveActionCI);
	}

}
