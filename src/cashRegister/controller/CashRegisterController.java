package cashRegister.controller;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import cashRegister.model.CartEnum;
import cashRegister.model.CartModel;
import cashRegister.model.CashRegisterEnum;
import cashRegister.view.CartTemp;
import cashRegister.view.CashRegisterTemp;
import common.BaseController;
import customers.model.CustomerEnum;
import items.view.ItemsTemp;
import workers.model.WorkersEnum;
import workers.model.WorkersModel;
import workers.view.WorkersTemp;

public class CashRegisterController extends BaseController {
	
	private List<CartModel> cartListCM;
	
	private List<WorkersModel> workerAddListWM;
	
	private List<String> supplierList;
	
	public CashRegisterController() {
		
		cartListCM = new LinkedList<CartModel>();
		
		workerAddListWM = new LinkedList<WorkersModel>();
		
		supplierList = new LinkedList<String>();
	}
	
	public List<CartModel> getCartList() {
		return Collections.unmodifiableList(cartListCM);
	}
	
	public void saveCashRegister(CashRegisterTemp cashRegister) throws SQLException {

		String procCountSql = "{ call zavrsni.countCashRegister(?) }";
		CallableStatement csCount = con.prepareCall(procCountSql);

		String procInsertSql = "{ call zavrsni.saveCashRegister(?,?,?,?,?,?,?,?,?,?) }";
		CallableStatement csInsert = con.prepareCall(procInsertSql);

		ResultSet checkResult = null;

		csCount.setInt(1, cashRegister.getId());

		checkResult = csCount.executeQuery();
		checkResult.next();

		int count = checkResult.getInt(1);
		if (count == 0) {
			csInsert.setInt(CashRegisterEnum.ID.getKey(), cashRegister.getId());
			csInsert.setString(CashRegisterEnum.BILL_NUMBER.getKey(), cashRegister.getBillNumber());
			csInsert.setInt(CashRegisterEnum.CART_ID.getKey(), cashRegister.getCartID());
			csInsert.setInt(CashRegisterEnum.NUMBER_OF_ITEMS.getKey(), cashRegister.getNumberOfItems());
			csInsert.setString(CashRegisterEnum.AMOUNT_TOTAL.getKey(), cashRegister.getAmountTotal());
			csInsert.setDouble(CashRegisterEnum.DISCOUNT.getKey(), cashRegister.getDiscount());
			csInsert.setString(CashRegisterEnum.CUSTOMER.getKey(), cashRegister.getR1Client());
			csInsert.setString(CashRegisterEnum.WORKER.getKey(), cashRegister.getWorker());
			csInsert.setString(CashRegisterEnum.PAYMENT_METHOD.getKey(), cashRegister.getPaymentMethode());
			csInsert.setTimestamp(10, new Timestamp(System.currentTimeMillis()));

			csInsert.executeUpdate();
		}

		checkResult.close();
		csCount.close();
		csInsert.close();
	}
	
	public void saveCart(CartTemp cart) throws SQLException {
		
		String procCountSql = "{ call zavrsni.countCart(?) }";
		CallableStatement csCount = con.prepareCall(procCountSql);

		String procInsertSql = "{ call zavrsni.saveCart(?,?,?,?,?,?,?,?,?,?) }";
		CallableStatement csInsert = con.prepareCall(procInsertSql);

		ResultSet checkResult = null;

		csCount.setInt(1, cart.getId());

		checkResult = csCount.executeQuery();
		checkResult.next();

		int count = checkResult.getInt(1);
		if (count == 0) {
			csInsert.setInt(CartEnum.ID.getKey(), cart.getId());
			csInsert.setInt(CartEnum.CART_ID.getKey(), cart.getCartID());
			csInsert.setInt(CartEnum.ITEM_CODE.getKey(), cart.getItemCode());
			csInsert.setString(CartEnum.ITEM_NAME.getKey(), cart.getName());
			csInsert.setString(CartEnum.UNIT.getKey(), cart.getUnit());
			csInsert.setString(CartEnum.TAX.getKey(), cart.getTax());
			csInsert.setDouble(CartEnum.QUANTITY.getKey(), cart.getQuantity());
			csInsert.setString(CartEnum.SELLING_RP.getKey(), cart.getSellingRP());
			csInsert.setDouble(CartEnum.DISCOUNT.getKey(), cart.getDiscount());
			csInsert.setString(CartEnum.AMOUNT.getKey(), cart.getAmount());

			csInsert.executeUpdate();
		}

		checkResult.close();
		csCount.close();
		csInsert.close();
	}
	
	public ItemsTemp searchItemByBarcode(String barcode) throws SQLException {
		
		String procSql = "{ call zavrsni.searchItemByBarcode('"+barcode+"') }";
		CallableStatement csLoad = con.prepareCall(procSql);
		ResultSet result = csLoad.executeQuery();
		
		ItemsTemp item = null;
		
		while (result.next()) {
			item = new ItemsTemp(result.getInt(CartEnum.ITEM_CODE.getValue()),
					result.getString(CartEnum.ITEM_NAME.getValue()),
					result.getString(CartEnum.DISCOUNT.getValue()),
					result.getString(CartEnum.TAX.getValue()),
					result.getString(CartEnum.UNIT.getValue()),
					result.getString(CartEnum.SELLING_RP.getValue()));
		}

		result.close();
		csLoad.close();
		
		if (item == null)
			return null;
		
		return item;
	}
	
	public boolean checkWorkerLoginInCashRegister(WorkersTemp worker) {
		
		WorkersModel workersModel = new WorkersModel(worker.getName(), worker.getSurname(), worker.getPassword());
		
		if (checkWorkerLoginInCashRegister(workersModel) == true) {
			return true;
		}
		else if (checkWorkerLoginInCashRegister(workersModel) == false) {
			JOptionPane.showMessageDialog(null, "NEŠTO JE KRIVO UNESENO ILI RADNIK NE POSTOJI", "GREŠKA", JOptionPane.ERROR_MESSAGE);
		}
		
		return false;
	}
	
	public boolean checkWorkerLoginInCashRegister(WorkersModel workersModel) {

		for (int i = 0; i < workerAddListWM.size(); i++) {			
			if ( (workersModel.getName().equals(workerAddListWM.get(i).getName())) && (workersModel.getSurname().equals(workerAddListWM.get(i).getSurname())) && (workersModel.getPassword().equals(workerAddListWM.get(i).getPassword())) ) {
				return true;
			}
		}
		return false;
	}
	
	public void addItemToTableOfCashRegister(ItemsTemp itemTemp, double quantity, double discount, String amount) {
		
		CartModel item = new CartModel(itemTemp.getItemCode(), itemTemp.getName(), itemTemp.getUnit(), itemTemp.getTax(), quantity, itemTemp.getSellingRP(), discount, amount);
		
		cartListCM.add(item);
	}
	
	public void removeItemFromCashResgisterTable(int row_index) {
		cartListCM.remove(row_index);
	}
	
	public void removeFromState(int itemCode, double quantity) throws SQLException {
		
		String procSql = "{ call zavrsni.removeFromState('"+itemCode+"', '"+quantity+"') }";
		CallableStatement csUpdate = con.prepareCall(procSql);
		
		csUpdate.executeUpdate();
		
		csUpdate.close();
	}
	
	public void loadWorkers() throws SQLException {
		workerAddListWM.clear();
		
		String procSql = "{ call zavrsni.loadWorkers() }";
		CallableStatement cs = con.prepareCall(procSql);
		
		ResultSet result = cs.executeQuery();
		
		while(result.next()) {
			
			WorkersModel radnik = new WorkersModel(result.getInt(WorkersEnum.WORKER_ID.getValue()),
					result.getString(WorkersEnum.WORKER_NAME.getValue()), result.getString(WorkersEnum.WORKER_SURNAME.getValue()),
					result.getString(WorkersEnum.WORKER_OIB.getValue()), result.getInt(WorkersEnum.BIRTH_YEAR.getValue()),
					result.getString(WorkersEnum.SEX.getValue()), result.getString(WorkersEnum.PASSWORD.getValue()));
			
			workerAddListWM.add(radnik);
		}
		
		result.close();
		cs.close();
	}
	
	public List<String> loadSuppliers() throws SQLException {
		supplierList.clear();
		
		String procSql = "{ call zavrsni.loadSuppliers() }";
		CallableStatement cs = con.prepareCall(procSql);
		
		ResultSet result = cs.executeQuery();
		
		while(result.next()) {
			supplierList.add(result.getString(CustomerEnum.CUSTOMER_NAME.getValue()));
		}
		
		result.close();
		cs.close();
		
		return supplierList;
	}

}
