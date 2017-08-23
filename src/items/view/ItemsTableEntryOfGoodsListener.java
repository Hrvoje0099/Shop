package items.view;

import java.util.EventListener;

public interface ItemsTableEntryOfGoodsListener extends EventListener {
	public void cleanEntryOfGoodsTableAfterSave();
	public void addToState(int itemCode, double amountInput);
}
