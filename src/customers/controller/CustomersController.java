package customers.controller;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import common.BaseController;
import customers.model.CustomerEnum;
import customers.model.CustomersModel;
import customers.view.CustomersTemp;

public class CustomersController extends BaseController {
	
	private List<CustomersModel> customersAddListCM;
	
	private List<CustomersModel> customersSearchListCM;
	
	public CustomersController() {
		
		customersAddListCM = new LinkedList<CustomersModel>();
		
		customersSearchListCM = new LinkedList<CustomersModel>();
	}
	
	public List<CustomersModel> getCustomersAddList() {
		return Collections.unmodifiableList(customersAddListCM);
	}
	
	public List<CustomersModel> getCustomersSearchList() {
		return Collections.unmodifiableList(customersSearchListCM);
	}
	
	public void saveCustomer(CustomersTemp customerTemp) throws SQLException {
				
		// provjera dali već imamo klijenta sa istim ID-om ili OIB-om
		CustomersModel customerModelCheck = new CustomersModel(customerTemp.getId(), customerTemp.getOib());
		CustomersModel customerModelTrue = new CustomersModel(customerTemp.getId(), customerTemp.getName(), customerTemp.getAddress(), customerTemp.getCity(), customerTemp.getZipCode(), customerTemp.getCountry(), customerTemp.getPhone(), customerTemp.getFax(), customerTemp.getMail(), customerTemp.getMobilePhone(), customerTemp.getOib(), customerTemp.getContract(), customerTemp.getPerson() );	
				
		if (checkCustomerBeforeSave(customerModelCheck) == 0) {
			customersAddListCM.add(customerModelTrue);
			
			String procCountSql = "{ call zavrsni.countCustomers(?) }";
			CallableStatement csCount = con.prepareCall(procCountSql);
			
			String procInsertSql = "{ call zavrsni.saveCustomer(?,?,?,?,?,?,?,?,?,?,?,?,?,null) }";
			CallableStatement csInsert = con.prepareCall(procInsertSql);
			
			ResultSet checkResult = null;
			
			for(CustomersModel customer : customersAddListCM) {
				
				csCount.setInt(1, customer.getId());

				checkResult = csCount.executeQuery();
				checkResult.next();
				
				int count = checkResult.getInt(1);
				if(count == 0) {
					csInsert.setInt(CustomerEnum.CUSTOMER_ID.getKey(), customer.getId());
					csInsert.setString(CustomerEnum.CUSTOMER_NAME.getKey(), customer.getName());
					csInsert.setString(CustomerEnum.ADDRESS.getKey(), customer.getAddress());
					csInsert.setString(CustomerEnum.CITY.getKey(), customer.getCity());
					csInsert.setString(CustomerEnum.ZIP_CODE.getKey(), customer.getZipCode());
					csInsert.setString(CustomerEnum.COUNTRY.getKey(), customer.getCountry());
					csInsert.setString(CustomerEnum.PHONE.getKey(), customer.getPhone());
					csInsert.setString(CustomerEnum.FAX.getKey(), customer.getFax());
					csInsert.setString(CustomerEnum.MAIL.getKey(), customer.getMail());
					csInsert.setString(CustomerEnum.MOBILE_PHONE.getKey(), customer.getMobilePhone());
					csInsert.setString(CustomerEnum.OIB.getKey(), customer.getOib());
					csInsert.setString(CustomerEnum.CONTRACT.getKey(), customer.getContract());
					csInsert.setString(CustomerEnum.PERSON.getKey(), customer.getPerson());
					
					csInsert.executeUpdate();
					
					JOptionPane.showMessageDialog(null, "KLIJENT USPIJEŠNO UNESEN! ID: " + customer.getId(), "INFO", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			checkResult.close();
			csCount.close();
			csInsert.close();
		}
		else if (checkCustomerBeforeSave(customerModelCheck) == 1) {
			JOptionPane.showMessageDialog(null, "OBI VEĆ POSTOJI!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
		}
		else if (checkCustomerBeforeSave(customerModelCheck) == 2) {
			JOptionPane.showMessageDialog(null, "ID VEĆ POSTOJI!!", "GREŠKA", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public int checkCustomerBeforeSave(CustomersModel customer) {
		
		for (int i = 0; i < customersAddListCM.size(); i++) {			
			customersAddListCM.get(i);
			if ( (customer.getOib().equals(customersAddListCM.get(i).getOib())) ) {
				return 1;
			}
			else if ( (customer.getId() == (customersAddListCM.get(i).getId())) ) {
				return 2;
			}
		}
		return 0;
	}
		
	public void updateCustomer(CustomersTemp customer, int idKCustomer) throws SQLException {
		
		String procSql = "{ call zavrsni.updateCustomer('"+customer.getName()+"', '"+customer.getAddress()+"', '"+customer.getCity()+"', '"+customer.getZipCode()+"', '"+customer.getCountry()+"', '"+customer.getPhone()+"', '"+customer.getFax()+"', '"+customer.getMail()+"', '"+customer.getMobilePhone()+"', '"+customer.getContract()+"', '"+customer.getPerson()+"', '"+customer.getMessage()+"', '"+idKCustomer+"') }";
		CallableStatement csUpdate = con.prepareCall(procSql);
		
		csUpdate.executeUpdate();	
		
		JOptionPane.showMessageDialog(null, "KLIJENT USPJEŠNO IZMIJENJEN!", "INFO", JOptionPane.INFORMATION_MESSAGE);
		
		csUpdate.close();
	}
	
	public void loadCustomers() throws SQLException {
		customersAddListCM.clear();
		
		String procSql = "{ call zavrsni.loadCustomers() }";
		CallableStatement cs = con.prepareCall(procSql);
		
		ResultSet result = cs.executeQuery();
		
		while (result.next()) {
			CustomersModel customer = new CustomersModel(result.getInt(CustomerEnum.CUSTOMER_ID.getValue()),
					result.getString(CustomerEnum.CUSTOMER_NAME.getValue()), result.getString(CustomerEnum.ADDRESS.getValue()),
					result.getString(CustomerEnum.CITY.getValue()), result.getString(CustomerEnum.ZIP_CODE.getValue()),
					result.getString(CustomerEnum.COUNTRY.getValue()), result.getString(CustomerEnum.PHONE.getValue()),
					result.getString(CustomerEnum.FAX.getValue()), result.getString(CustomerEnum.MAIL.getValue()),
					result.getString(CustomerEnum.MOBILE_PHONE.getValue()), result.getString(CustomerEnum.OIB.getValue()),
					result.getString(CustomerEnum.CONTRACT.getValue()), result.getString(CustomerEnum.PERSON.getValue()));
			
			customersAddListCM.add(customer);
		}
		
		result.close();
		cs.close();
	}
	
 	public void searchCustomers(CustomersTemp customerTemp) throws SQLException {
 		
		String name = returnNullIfEmptys(customerTemp.getName());
		String address = returnNullIfEmptys(customerTemp.getAddress());
		String city = returnNullIfEmptys(customerTemp.getCity());
		String country = returnNullIfEmptys(customerTemp.getCountry());
		String phone = returnNullIfEmptys(customerTemp.getPhone());
		String fax = returnNullIfEmptys(customerTemp.getFax());
		String mail = returnNullIfEmptys(customerTemp.getMail());
		String mobilePhone = returnNullIfEmptys(customerTemp.getMobilePhone());
		String oib = returnNullIfEmptys(customerTemp.getOib());
		String contract = returnNullIfEmptys(customerTemp.getContract());
		String person = returnNullIfEmptys(customerTemp.getPerson());
		
		customersSearchListCM.clear();
		
		String procSql = "{ call zavrsni.searchCustomers('%"+name+"%', '%"+address+"%', '%"+city+"%', '%"+country+"%', '%"+phone+"%', '%"+fax+"%', '%"+mail+"%', '%"+mobilePhone+"%', '%"+oib+"%', '%"+contract+"%', '%"+person+"%') }";
		CallableStatement csSearch = con.prepareCall(procSql);
		
		ResultSet result = csSearch.executeQuery();
		
		while (result.next()) {
			CustomersModel customer = new CustomersModel(result.getInt(CustomerEnum.CUSTOMER_ID.getValue()),
					result.getString(CustomerEnum.CUSTOMER_NAME.getValue()), result.getString(CustomerEnum.ADDRESS.getValue()),
					result.getString(CustomerEnum.CITY.getValue()), result.getString(CustomerEnum.ZIP_CODE.getValue()),
					result.getString(CustomerEnum.COUNTRY.getValue()), result.getString(CustomerEnum.PHONE.getValue()),
					result.getString(CustomerEnum.FAX.getValue()), result.getString(CustomerEnum.MAIL.getValue()),
					result.getString(CustomerEnum.MOBILE_PHONE.getValue()), result.getString(CustomerEnum.OIB.getValue()),
					result.getString(CustomerEnum.CONTRACT.getValue()), result.getString(CustomerEnum.PERSON.getValue()));
			
			customersSearchListCM.add(customer);
		}
		
		result.close();
		csSearch.close();
	}
 	
	public void deleteCustomer(int row_index, int idCustomer) throws SQLException {
		
		String procSql = "{ call zavrsni.deleteCustomer('"+idCustomer+"') }";
		CallableStatement csDelete = con.prepareCall(procSql);
		
		csDelete.executeUpdate();
		customersAddListCM.remove(row_index);
		
		JOptionPane.showMessageDialog(null, "KLIJENT USPIJEŠNO IZBRISAN! ID: " + idCustomer, "INFO", JOptionPane.INFORMATION_MESSAGE);
		
		csDelete.close();
	}
	
	public CustomersTemp loadCustomerDetails(int idCustomer) throws SQLException {
		
		String procSql = "{ call zavrsni.loadCustomerDetails('"+idCustomer+"') }";
		CallableStatement csLoad = con.prepareCall(procSql);
		ResultSet result = csLoad.executeQuery();
		
		CustomersTemp customer = null;
		
		while (result.next()) {
			
			customer = new CustomersTemp(
					result.getInt(CustomerEnum.CUSTOMER_ID.getValue()), result.getString(CustomerEnum.CUSTOMER_NAME.getValue()),
					result.getString(CustomerEnum.ADDRESS.getValue()), result.getString(CustomerEnum.CITY.getValue()),
					result.getString(CustomerEnum.ZIP_CODE.getValue()), result.getString(CustomerEnum.COUNTRY.getValue()),
					result.getString(CustomerEnum.PHONE.getValue()), result.getString(CustomerEnum.FAX.getValue()),
					result.getString(CustomerEnum.MAIL.getValue()), result.getString(CustomerEnum.MOBILE_PHONE.getValue()),
					result.getString(CustomerEnum.OIB.getValue()), result.getString(CustomerEnum.CONTRACT.getValue()),
					result.getString(CustomerEnum.PERSON.getValue()), result.getString(CustomerEnum.MESSAGE.getValue()));
		}
		
		result.close();
		csLoad.close();
		
		return customer;
	}
	
	public String[] getAllCountries() {
		
		String[] countries = new String[Locale.getISOCountries().length];
		String[] countryCodes = Locale.getISOCountries();
		for (int i = 0; i < countryCodes.length; i++) {
			Locale obj = new Locale("", countryCodes[i]);
			countries[i] = obj.getDisplayCountry();
		}
		return countries;
	}
	
	public String[] getAllCountriesForSearch() {
		
		String[] countries = new String[Locale.getISOCountries().length + 1];
		String[] countryCodes = Locale.getISOCountries();
		for (int i = 0; i < countryCodes.length; i++) {
			Locale obj = new Locale("", countryCodes[i]);
			countries[i] = obj.getDisplayCountry();
		}
		countries[250] = "";
		return countries;
	}
	
	private String returnNullIfEmptys(String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		return value;
	}

}
