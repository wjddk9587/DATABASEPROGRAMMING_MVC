package controller.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Controller;
import model.service.CustomerManager;
import model.service.CustomerNotFoundException;
import model.Customer;

public class ViewCustomerController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {			
    	// �α��� ���� Ȯ��
    	if (!CustomerSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/customer/login/form";		// login form ��û���� redirect
        }
    	
		CustomerManager manager = CustomerManager.getInstance();
		String customerId = request.getParameter("customerId");
		
		Customer customer = null;
		try {
			customer = manager.viewCustomer(customerId);	// ����� ���� �˻�
		} catch (CustomerNotFoundException e) {				
	        return "redirect:/customer/list";
		}	
		
		request.setAttribute("customer", customer);		// ����� ���� ����				
		return "/customer/view.jsp";				// ����� ���� ȭ������ �̵�
    }
}
