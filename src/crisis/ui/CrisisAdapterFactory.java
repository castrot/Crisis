package crisis.ui;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

import crisis.application.CrisisItemGroup;

public class CrisisAdapterFactory implements IAdapterFactory {

	private IWorkbenchAdapter itemGroupAdapter = new IWorkbenchAdapter() {
		@Override
		public Object[] getChildren(Object o) {

			if (o instanceof CrisisItemGroup) {
				return ((CrisisItemGroup) o).getChildrenGroups().toArray();
			} else {
				return null;
			}
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel(Object o) {
			if (o instanceof CrisisItemGroup) {
				return ((CrisisItemGroup) o).getDisplayName();
			} else {
				return null;
			}
		}

		@Override
		public Object getParent(Object o) {
			if (o instanceof CrisisItemGroup) {
				return ((CrisisItemGroup) o).getParentGroup();
			} else {
				return null;
			}
		}
	};

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IWorkbenchAdapter.class
				&& adaptableObject instanceof CrisisItemGroup) {
			return itemGroupAdapter;
		}
		return null;
	}

	@Override
	public Class[] getAdapterList() {

		return new Class[] { IWorkbenchAdapter.class };
	}

}
