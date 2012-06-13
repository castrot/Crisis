package crisis.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(namespace = "crisis")
public class Crisis {

	@XmlElement
	private static long groupCounter;
	@XmlElement
	private static long txCounter;
	@XmlIDREF
	private CrisisItemGroup groupsRoot;
	@XmlElementWrapper(name = "groupList")
	@XmlElement(name = "group")
	@XmlIDREF
	private List<CrisisItemGroup> groupList;
	@XmlTransient
	private List<ICrisisListener> listeners;

	public Crisis() {
		this.listeners = new ArrayList<ICrisisListener>();
	}

	public Crisis(String str) {

		this();
		this.groupsRoot = new CrisisItemGroup("<NONE>", null);
		this.groupList = new ArrayList<CrisisItemGroup>();
		addGroup(groupsRoot);
		addGroup(new CrisisItemGroup("Expenses", groupsRoot));
		addGroup(new CrisisItemGroup("Profits", groupsRoot));
	}

	public void addGroup(CrisisItemGroup item) {

		groupList.add(item);

		for (ICrisisListener listener : listeners) {
			listener.groupsChanged();
		}
	}

	public void addTransaction(CrisisItemGroup group, CrisisItemTransaction tx) {

		group.addTransaction(tx);

		for (ICrisisListener listener : listeners) {
			listener.groupsChanged();
		}
	}

	public Object getGroupsRoot() {

		return groupsRoot;
	}

	public List<CrisisItemGroup> getGroups() {

		return groupList;
	}

	public void setGroups(List<CrisisItemGroup> groupList) {

		this.groupList = groupList;
	}

	public interface ICrisisListener {

		public void groupsChanged();
	}

	public void registerListener(ICrisisListener listener) {

		listeners.add(listener);
	}

	public void unregisterListener(ICrisisListener listener) {

		listeners.remove(listener);
	}

	public static long getNewIdForGroup() {

		return groupCounter++;
	}

	public static long getNewIdForTransacton() {

		return txCounter++;
	}

}
