package customers.view;

import java.util.EventListener;

public interface CustomersFormAddListener extends EventListener {
	public void addCustomer(CustomersTemp customer);
}
