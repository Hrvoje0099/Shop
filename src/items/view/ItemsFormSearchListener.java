package items.view;

import java.util.EventListener;
import java.util.List;

public interface ItemsFormSearchListener extends EventListener {
	public void searchItem(ItemsTemp item);
	public List<String> loadSuppliers();
}
