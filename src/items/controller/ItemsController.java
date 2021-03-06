package items.controller;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import common.BaseController;
import customers.model.CustomerEnum;
import items.model.EntryOfGoodsModel;
import items.model.ItemsEnum;
import items.model.ItemsModel;
import items.view.ItemsTemp;

public class ItemsController extends BaseController {
	
	private List<ItemsModel> itemsAddListIM;
	private List<ItemsModel> itemsSearchListIM;
	private List<EntryOfGoodsModel> itemsEntryOfGoodsListEOGM;
	private List<String> supplierList;
	
	public ItemsController() {
		itemsAddListIM = new LinkedList<ItemsModel>();
		itemsSearchListIM = new LinkedList<ItemsModel>();
		itemsEntryOfGoodsListEOGM = new LinkedList<EntryOfGoodsModel>();
		supplierList = new LinkedList<String>();
	}

	public List<ItemsModel> getItemsAddList() {
		return Collections.unmodifiableList(itemsAddListIM);
	}
	
	public List<ItemsModel> getItemsSearchList() {
		return Collections.unmodifiableList(itemsSearchListIM);
	}
	
	public List<EntryOfGoodsModel> getEntryOfGoodsList() {
		return Collections.unmodifiableList(itemsEntryOfGoodsListEOGM);
	}
	
	public void saveItem(ItemsTemp itemTemp) throws SQLException {
		
		// provjera dali već imamo artikl sa istom ŠIFROM
		ItemsModel itemsModelCheck = new ItemsModel(itemTemp.getItemCode(), itemTemp.getName(), itemTemp.getBarcode1(), itemTemp.getBarcode2(), itemTemp.getSupplier(), itemTemp.getDiscount(), itemTemp.getTax(), itemTemp.getUnit(), itemTemp.getPurchaseWP(), itemTemp.getPurchaseRP(), itemTemp.getSellingWP(), itemTemp.getSellingRP(), itemTemp.getMargin(), itemTemp.getItemState() );
		ItemsModel itemsModelTrue = new ItemsModel(itemTemp.getItemCode(), itemTemp.getName(), itemTemp.getBarcode1(), itemTemp.getBarcode2(), itemTemp.getSupplier(), itemTemp.getDiscount(), itemTemp.getTax(), itemTemp.getUnit(), itemTemp.getPurchaseWP(), itemTemp.getPurchaseRP(), itemTemp.getSellingWP(), itemTemp.getSellingRP(), itemTemp.getMargin(), itemTemp.getItemState() );
		
		if (checkItemBeforeSave(itemsModelCheck) == false) {
			itemsAddListIM.add(itemsModelTrue);

			String procCountSql = "{ call zavrsni.countItems(?) }";
			String procInsertSql = "{ call zavrsni.saveItem(?,?,?,?,?,?,?,?,?,?,?,?,?,null,0) }";
			
			try (CallableStatement csCount = con.prepareCall(procCountSql)) {
				
				try (CallableStatement csInsert = con.prepareCall(procInsertSql)) {
					
					for(ItemsModel item : itemsAddListIM) {

						csCount.setInt(1, item.getItemCode());
						
						try (ResultSet checkResult = csCount.executeQuery()) {
							
							checkResult.next();
							
							int count = checkResult.getInt(1);
							if(count == 0) {
								
								csInsert.setInt(ItemsEnum.ITEM_CODE.getKey(), item.getItemCode());
								csInsert.setString(ItemsEnum.ITEM_NAME.getKey(), item.getName());
								csInsert.setString(ItemsEnum.BARCODE_1.getKey(), item.getBarcode1());
								csInsert.setString(ItemsEnum.BARCODE_2.getKey(), item.getBarcode2());
								csInsert.setString(ItemsEnum.SUPPLIER.getKey(), item.getSupplier());
								csInsert.setString(ItemsEnum.DISCOUNT.getKey(), item.getDiscount());
								csInsert.setString(ItemsEnum.TAX.getKey(), item.getTax());
								csInsert.setString(ItemsEnum.UNIT.getKey(), item.getUnit());
								csInsert.setString(ItemsEnum.PURCHASE_WP.getKey(), item.getPurchaseWP());
								csInsert.setString(ItemsEnum.PURCHASE_RP.getKey(), item.getPurchaseRP());
								csInsert.setString(ItemsEnum.SELLING_WP.getKey(), item.getSellingWP());
								csInsert.setString(ItemsEnum.SELLING_RP.getKey(), item.getSellingRP());
								csInsert.setString(ItemsEnum.MARGIN.getKey(), item.getMargin());
								
								csInsert.executeUpdate();
								
								JOptionPane.showMessageDialog(null, "ARTIKL USPIJEŠNO UNESEN! ŠIFRA: " + item.getItemCode(), "INFO", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
				}
			}
			
		}
		else if (checkItemBeforeSave(itemsModelCheck) == true)
			JOptionPane.showMessageDialog(null, "ŠIFRA VEĆ POSTOJI!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
	}
	
	public boolean checkItemBeforeSave(ItemsModel item) {
		
		for (int i = 0; i < itemsAddListIM.size(); i++) {
			if ( (item.getItemCode() == (itemsAddListIM.get(i).getItemCode())) ) {
				return true;
			}
		}
		return false;
	}
		
	public void updateItem(ItemsTemp item, int itemCode) throws SQLException {
		
		String procSql = "{ call zavrsni.updateItem('"+item.getName()+"', '"+item.getSupplier()+"', '"+item.getBarcode1()+"', '"+item.getBarcode2()+"', '"+item.getDiscount()+"', '"+item.getTax()+"', '"+item.getUnit()+"', '"+item.getPurchaseWP()+"', '"+item.getPurchaseRP()+"', '"+item.getSellingWP()+"', '"+item.getSellingRP()+"', '"+item.getMargin()+"', '"+item.getMessage()+"', '"+itemCode+"') }";
		
		try (CallableStatement csUpdate = con.prepareCall(procSql)) {
			
			csUpdate.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "ARTIKL USPJEŠNO IZMIJENJEN!", "INFO", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void loadItems() throws SQLException {
		itemsAddListIM.clear();
		
		String procSql = "{ call zavrsni.loadItems() }";
		
		try (CallableStatement cs = con.prepareCall(procSql)) {
			
			try (ResultSet result = cs.executeQuery()) {
				
				while(result.next()) {

					ItemsModel item = new ItemsModel(result.getInt(ItemsEnum.ITEM_CODE.getValue()),
							result.getString(ItemsEnum.ITEM_NAME.getValue()), result.getString(ItemsEnum.BARCODE_1.getValue()),
							result.getString(ItemsEnum.BARCODE_2.getValue()), result.getString(ItemsEnum.SUPPLIER.getValue()),
							result.getString(ItemsEnum.DISCOUNT.getValue()), result.getString(ItemsEnum.TAX.getValue()),
							result.getString(ItemsEnum.UNIT.getValue()), result.getString(ItemsEnum.PURCHASE_WP.getValue()),
							result.getString(ItemsEnum.PURCHASE_RP.getValue()), result.getString(ItemsEnum.SELLING_WP.getValue()),
							result.getString(ItemsEnum.SELLING_RP.getValue()), result.getString(ItemsEnum.MARGIN.getValue()),
							result.getDouble(ItemsEnum.ITEM_STATE.getValue()));
					
					itemsAddListIM.add(item);
				}
			}
		}
	}
	
	public void searchItems(ItemsTemp itemTemp) throws SQLException {
		
		String name = returnNullIfEmptys(itemTemp.getName());
		String barcode = returnNullIfEmptys(itemTemp.getBarcode1());
		String supplier = returnNullIfEmptys(itemTemp.getSupplier());
		
		itemsSearchListIM.clear();
		
		String procSql = "{ call zavrsni.searchItems('"+itemTemp.getItemCode()+"', '%"+name+"%', '%"+barcode+"%', '%"+supplier+"%') }";
		
		try (CallableStatement csSearch = con.prepareCall(procSql)) {
			
			try (ResultSet result = csSearch.executeQuery()) {
				
				while(result.next()) {
					
					ItemsModel item = new ItemsModel(result.getInt(ItemsEnum.ITEM_CODE.getValue()),
							result.getString(ItemsEnum.ITEM_NAME.getValue()), result.getString(ItemsEnum.BARCODE_1.getValue()),
							result.getString(ItemsEnum.BARCODE_2.getValue()), result.getString(ItemsEnum.SUPPLIER.getValue()),
							result.getString(ItemsEnum.DISCOUNT.getValue()), result.getString(ItemsEnum.TAX.getValue()),
							result.getString(ItemsEnum.UNIT.getValue()), result.getString(ItemsEnum.PURCHASE_WP.getValue()),
							result.getString(ItemsEnum.PURCHASE_RP.getValue()), result.getString(ItemsEnum.SELLING_WP.getValue()),
							result.getString(ItemsEnum.SELLING_RP.getValue()), result.getString(ItemsEnum.MARGIN.getValue()),
							result.getDouble(ItemsEnum.ITEM_STATE.getValue()));
					
					itemsSearchListIM.add(item);
				}
			}
		}
	}
	
	public void deleteItem(int row_index, int itemCode) throws SQLException {
		
		itemsAddListIM.remove(row_index);
		
		String procSql = "{ call zavrsni.deleteItem('"+itemCode+"') }";
		
		try (CallableStatement csDelete = con.prepareCall(procSql)) {
			
			csDelete.executeUpdate();
			
			JOptionPane.showMessageDialog(null, "ARTIKL USPIJEŠNO IZBRISAN! ŠIFRA: " + itemCode, "INFO", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public ItemsTemp loadItemDetails(int itemCode) throws SQLException {
		
		String procSql = "{ call zavrsni.loadItemDetails('"+itemCode+"') }";
		
		ItemsTemp item = null;
		
		try (CallableStatement csLoad = con.prepareCall(procSql)) {
			
			try (ResultSet result = csLoad.executeQuery()) {
				
				while (result.next()) {
					item = new ItemsTemp(result.getInt(ItemsEnum.ITEM_CODE.getValue()),
							result.getString(ItemsEnum.ITEM_NAME.getValue()), result.getString(ItemsEnum.BARCODE_1.getValue()),
							result.getString(ItemsEnum.BARCODE_2.getValue()), result.getString(ItemsEnum.SUPPLIER.getValue()),
							result.getString(ItemsEnum.DISCOUNT.getValue()), result.getString(ItemsEnum.TAX.getValue()),
							result.getString(ItemsEnum.UNIT.getValue()), result.getString(ItemsEnum.PURCHASE_WP.getValue()),
							result.getString(ItemsEnum.PURCHASE_RP.getValue()), result.getString(ItemsEnum.SELLING_WP.getValue()),
							result.getString(ItemsEnum.SELLING_RP.getValue()), result.getString(ItemsEnum.MARGIN.getValue()), 
							result.getString(ItemsEnum.MESSAGE.getValue()),
							result.getDouble(ItemsEnum.ITEM_STATE.getValue()));
				}
			}
		}
				
		return item;
	}
	
	public void addItemForEntryOfGoods(ItemsTemp item) {

		EntryOfGoodsModel entryOfGoodsModel = new EntryOfGoodsModel(item.getItemCode(), item.getName(), item.getBarcode1(),
				item.getBarcode2(), item.getSupplier(), item.getDiscount(), item.getTax(), item.getUnit(), item.getPurchaseWP(),
				item.getPurchaseRP(), item.getSellingWP(), item.getSellingRP(), item.getMargin(), item.getAmountInput());

		itemsEntryOfGoodsListEOGM.add(entryOfGoodsModel);
	}
	
	public void cleanEntryOfGoodsTableAfterSave(int row_index) {
		itemsEntryOfGoodsListEOGM.remove(row_index);
	}
	
	public ItemsTemp loadItemForEntryOfGoods(String barcode) throws SQLException {
		
		String procSql = "{ call zavrsni.loadItemForEntryOfGoods('"+barcode+"') }";
		
		ItemsTemp item = null;
		
		try (CallableStatement csLoad = con.prepareCall(procSql)) {
			
			try (ResultSet result = csLoad.executeQuery()) {
				
				while (result.next()) {
					
					item = new ItemsTemp(result.getInt(ItemsEnum.ITEM_CODE.getValue()),
							result.getString(ItemsEnum.ITEM_NAME.getValue()), result.getString(ItemsEnum.BARCODE_1.getValue()),
							result.getString(ItemsEnum.BARCODE_2.getValue()), result.getString(ItemsEnum.SUPPLIER.getValue()),
							result.getString(ItemsEnum.DISCOUNT.getValue()), result.getString(ItemsEnum.TAX.getValue()),
							result.getString(ItemsEnum.UNIT.getValue()), result.getString(ItemsEnum.PURCHASE_WP.getValue()),
							result.getString(ItemsEnum.PURCHASE_RP.getValue()), result.getString(ItemsEnum.SELLING_WP.getValue()),
							result.getString(ItemsEnum.SELLING_RP.getValue()), result.getString(ItemsEnum.MARGIN.getValue()));
				}
			}
		}

		if (item == null)
			return null;
		
		return item;
	}
	
	public void addToState(int itemCode, double amountInput) throws SQLException {
		
		String procSql = "{ call zavrsni.addToState('"+itemCode+"', '"+amountInput+"') }";
		
		try (CallableStatement csUpdate = con.prepareCall(procSql)) {
			
			csUpdate.executeUpdate();
		}
	}
	
	public List<String> loadSuppliers() throws SQLException {
		supplierList.clear();
		
		String procSql = "{ call zavrsni.loadSuppliers() }";
		
		try (CallableStatement cs = con.prepareCall(procSql)) {
			
			try (ResultSet result = cs.executeQuery()) {
				
				while(result.next()) {
					supplierList.add(result.getString(CustomerEnum.CUSTOMER_NAME.getValue()));
				}
			}
		}
		
		return supplierList;
	}
	
	private String returnNullIfEmptys(String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		return value;
	}

}
