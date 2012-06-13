package crisis.application;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Models a determinate group for transactions
 * 
 * @author karp
 * 
 */
@XmlRootElement(name="group")
@XmlType()
public class CrisisItemGroup {

	@XmlID
	private final String id;
	
	@XmlElement()
	private String groupName;
	@XmlElement()
	private String description;
	@XmlIDREF
	private CrisisItemGroup parentGroup;
	@XmlElementWrapper(name="childrenGroupList")
	@XmlElement(name="group")
	@XmlIDREF
	private List<CrisisItemGroup> childrenGroups;
	@XmlElementWrapper(name="transactions")
	@XmlElement(name="tx")
	private List<CrisisItemTransaction> transactions;
	@XmlTransient
	private List<ICrisisGroupListener> listeners;
	
	private CrisisItemGroup() {

		this.id = String.valueOf(Crisis.getNewIdForGroup());
		this.listeners = new ArrayList<>();
		this.transactions = new ArrayList<>();
		this.childrenGroups = new ArrayList<>();
	}

	public CrisisItemGroup(String name, CrisisItemGroup parent) {

		this();
		this.groupName = name;
		this.parentGroup = parent;
		if (parent != null) {
			parent.addChildGroup(this);
		}
	}

	public String getDisplayName() {

		StringBuilder str = new StringBuilder();
		str.append(groupName);
		str.append(" ( ");
		str.append(getGroupNumberOfTransactions());
		str.append("/");
		str.append(getGroupTotal());
		str.append(" | ");
		str.append(getNumberOfTransactions());
		str.append("/");
		str.append(getTotal());
		str.append(" )");

		return str.toString();
	}

	public int getGroupNumberOfTransactions() {

		return transactions.size();
	}

	public int getNumberOfTransactions() {

		int nTx = transactions.size();
		for (CrisisItemGroup childGroup : childrenGroups) {
			nTx += childGroup.getNumberOfTransactions();
		}
		return nTx;
	}

	public Money getGroupTotal() {

		List<Money> moneyList = new ArrayList<>();
		for (CrisisItemTransaction tx : transactions) {
			moneyList.add(tx.getValue());
		}
		return Money.sum(moneyList);
	}

	public Money getTotal() {

		Money money = getGroupTotal();

		for (CrisisItemGroup childGroup : childrenGroups) {
			money = Money.sum(money, childGroup.getTotal());
		}
		return money;
	}

	public String getFullName() {

		StringBuilder builder = new StringBuilder();

		builder.append(groupName);

		return builder.toString();
	}

	protected void addTransaction(CrisisItemTransaction tx) {

		transactions.add(tx);
		groupChanged();
	}

	public void addChildGroup(CrisisItemGroup expenses) {

		childrenGroups.add(expenses);
		groupChanged();
	}

	/**
	 * Returns transactions this group's transactions.
	 * 
	 * @return
	 */
	public List<CrisisItemTransaction> getTransactions() {

		return transactions;
	}

	/**
	 * Returns this group and its children transaction's.
	 * 
	 * @return
	 */
	public List<CrisisItemTransaction> getAllTransactions() {

		List<CrisisItemTransaction> txs = new ArrayList<>();
		txs.addAll(getTransactions());
		for (CrisisItemGroup group : getChildrenGroups()) {
			txs.addAll(group.getAllTransactions());
		}
		return txs;
	}

	public List<CrisisItemGroup> getChildrenGroups() {

		return childrenGroups;
	}

	public CrisisItemGroup getParentGroup() {

		return parentGroup;
	}

	public String getName() {

		return groupName;
	}
	public void setGroupName(String name) {
		
		this.groupName = name;
	}
	
	public String getId() {
		
		return id;
	}
	
	private void groupChanged(){
		
		for (ICrisisGroupListener listener : listeners) {
			listener.groupChanged();
		}
		if(parentGroup != null) {
			parentGroup.groupChanged();
		}
	}
	
	public interface ICrisisGroupListener {
		
		public void groupChanged();
	}

	public void registerListener(ICrisisGroupListener listener) {

		listeners.add(listener);
	}	

	public void unregisterListener(ICrisisGroupListener listener) {

		listeners.remove(listener);
	}	

}
