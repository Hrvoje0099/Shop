package bills.controller;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import bills.model.BillsEnum;
import bills.model.BillsModel;
import bills.view.BillsTemp;
import cashRegister.model.CartEnum;
import cashRegister.model.CartModel;
import common.BaseController;
import workers.model.WorkersEnum;

public class BillsController extends BaseController {
	
	private List<String> workerList;
	
	private List<CartModel> cartListCM;
	
	private List<BillsModel> billsListBM;
	
	
	public BillsController() {
		
		workerList = new LinkedList<String>();
		
		cartListCM = new LinkedList<CartModel>();
		
		billsListBM = new LinkedList<BillsModel>();
	}
	
	public List<BillsModel> getBillsList() {
		return Collections.unmodifiableList(billsListBM);
	}
	
	public List<CartModel> getCartList() {
		return Collections.unmodifiableList(cartListCM);
	}
	
	public void loadBills() throws SQLException {
		billsListBM.clear();
		
		String procSql = "{ call zavrsni.loadBills() }";
		
		try (CallableStatement cs = con.prepareCall(procSql)) {
			
			try (ResultSet result = cs.executeQuery()) {
				
				while (result.next()) {
					BillsModel bill = new BillsModel(result.getDate(BillsEnum.DATA_AND_TIME_OF_SALE.getValue()), result.getTime(BillsEnum.DATA_AND_TIME_OF_SALE.getValue()), result.getString(BillsEnum.BILL_NUMBER.getValue()), result.getInt(BillsEnum.CART_ID.getValue()), result.getInt(BillsEnum.NUMBER_OF_ITEMS.getValue()), result.getString(BillsEnum.AMOUNT_TOTAL.getValue()), result.getDouble(BillsEnum.DISCOUNT_TOTAL.getValue()), result.getString(BillsEnum.CUSTOMER.getValue()), result.getString(BillsEnum.WORKER.getValue()), result.getString(BillsEnum.PAYMENT_METHOD.getValue()));
					billsListBM.add(bill);
				}
			}
		}
		
	}
	
	public void loadBillToTable(int cartID) throws SQLException {
		cartListCM.clear();
		
		String procSql = "{ call zavrsni.loadBill('"+cartID+"') }";
		
		try (CallableStatement cs = con.prepareCall(procSql)) {
			
			try (ResultSet result = cs.executeQuery()) {
				
				while(result.next()) {
					CartModel cart = new CartModel(result.getInt(CartEnum.ITEM_CODE.getValue()), result.getString(CartEnum.ITEM_NAME.getValue()), result.getString(CartEnum.UNIT.getValue()), result.getString(CartEnum.TAX.getValue()), result.getDouble(CartEnum.QUANTITY.getValue()), result.getString(CartEnum.SELLING_RP.getValue()), result.getDouble(CartEnum.DISCOUNT.getValue()), result.getString(CartEnum.AMOUNT.getValue()));
					cartListCM.add(cart);
				}
			}
		}
		
	}
	
	public BillsTemp loadBillToFields(int cartID) throws SQLException {
		
		String procSql = "{ call zavrsni.loadBillByCartID('"+cartID+"') }";
		
		BillsTemp bill = null;

		try (CallableStatement cs = con.prepareCall(procSql)) {
			
			try (ResultSet result = cs.executeQuery()) {
				
				while(result.next()) {
					bill = new BillsTemp(result.getDate(BillsEnum.DATA_AND_TIME_OF_SALE.getValue()), result.getTime(BillsEnum.DATA_AND_TIME_OF_SALE.getValue()), result.getString(BillsEnum.BILL_NUMBER.getValue()), result.getInt(BillsEnum.CART_ID.getValue()), result.getInt(BillsEnum.NUMBER_OF_ITEMS.getValue()), result.getString(BillsEnum.AMOUNT_TOTAL.getValue()), result.getString(BillsEnum.CUSTOMER.getValue()), result.getString(BillsEnum.WORKER.getValue()), result.getString(BillsEnum.PAYMENT_METHOD.getValue()));
				}
			}	
		}
			
		return bill;
	}

	public void loadBillsByDate(String date) throws SQLException {
		billsListBM.clear();
		
		String procSql = "{ call zavrsni.loadBillsByDate('%"+date+"%') }";
		
		try (CallableStatement cs = con.prepareCall(procSql)) {
			
			try (ResultSet result = cs.executeQuery()) {
				
				while(result.next()) {
					BillsModel bill = new BillsModel(result.getDate(BillsEnum.DATA_AND_TIME_OF_SALE.getValue()), result.getTime(BillsEnum.DATA_AND_TIME_OF_SALE.getValue()), result.getString(BillsEnum.BILL_NUMBER.getValue()), result.getInt(BillsEnum.CART_ID.getValue()), result.getInt(BillsEnum.NUMBER_OF_ITEMS.getValue()), result.getString(BillsEnum.AMOUNT_TOTAL.getValue()), result.getDouble(BillsEnum.DISCOUNT_TOTAL.getValue()), result.getString(BillsEnum.CUSTOMER.getValue()), result.getString(BillsEnum.WORKER.getValue()), result.getString(BillsEnum.PAYMENT_METHOD.getValue()));
					billsListBM.add(bill);
				}
			}
		}
		
	}
	
	public void loadBillsByDateAndWorker(String date, String worker) throws SQLException {
		billsListBM.clear();
		
		String procSql = "{ call zavrsni.loadBillsByDateAndWorker('%"+date+"%', '"+worker+"') }";
		
		try (CallableStatement cs = con.prepareCall(procSql)) {
			
			try (ResultSet result = cs.executeQuery()) {
				
				while(result.next()) {
					BillsModel bill = new BillsModel(result.getDate(BillsEnum.DATA_AND_TIME_OF_SALE.getValue()), result.getTime(BillsEnum.DATA_AND_TIME_OF_SALE.getValue()), result.getString(BillsEnum.BILL_NUMBER.getValue()), result.getInt(BillsEnum.CART_ID.getValue()), result.getInt(BillsEnum.NUMBER_OF_ITEMS.getValue()), result.getString(BillsEnum.AMOUNT_TOTAL.getValue()), result.getDouble(BillsEnum.DISCOUNT_TOTAL.getValue()), result.getString(BillsEnum.CUSTOMER.getValue()), result.getString(BillsEnum.WORKER.getValue()), result.getString(BillsEnum.PAYMENT_METHOD.getValue()));
					billsListBM.add(bill);
				}
			}
		}

	}
	
	public List<String> loadWorkersReviewSales() throws SQLException {
		workerList.clear();
		
		String procSql = "{ call zavrsni.loadWorkers() }";
		
		try (CallableStatement cs = con.prepareCall(procSql)) {
			
			try (ResultSet result = cs.executeQuery()) {
				
				while(result.next()) {
					workerList.add(result.getString(WorkersEnum.WORKER_NAME.getValue()) + " " + result.getString(WorkersEnum.WORKER_SURNAME.getValue()));
				}
			}
		}
		
		return workerList;
	}

}
