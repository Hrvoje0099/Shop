package items.view;

import java.util.EventListener;

public interface ItemsFormEntryOfGoodsListener extends EventListener {
	public void addItemOnTableForEntryOfGoods(ItemsTemp e);
	public ItemsTemp loadItemForEntryOfGoods(String barcode);
}
