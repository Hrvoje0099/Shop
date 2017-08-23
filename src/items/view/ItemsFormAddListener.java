package items.view;

import java.util.EventListener;
import java.util.List;

public interface ItemsFormAddListener extends EventListener {
	public void addItem(ItemsTemp item);
	public List<String> loadSuppliers();
}
