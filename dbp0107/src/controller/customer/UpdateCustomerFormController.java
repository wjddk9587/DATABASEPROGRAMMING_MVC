package controller.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.service.CustomerManager;
import model.Customer;

public class UpdateCustomerFormController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateCustomerController.class);

	@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String updateId = request.getParameter("customerId");
		
		log.debug("UpdateForm Request : {}", updateId);

		CustomerManager manager = CustomerManager.getInstance();
		Customer customer = manager.findCustomer(updateId);	// ����� ���� �˻�
		request.setAttribute("customer", customer);						
		
		HttpSession session = request.getSession();
		if (CustomerSessionUtils.isLoginCustomer(updateId, session) ||
			CustomerSessionUtils.isLoginCustomer("admin", session)) {
			// ���� �α����� ����ڰ� ���� ��� ������̰ų� �������� ��� -> ���� ����
			
			return "/customer/updateForm.jsp";   // �˻��� ����� ������ update form���� ����     
		}
		// else (���� �Ұ����� ���) ����� ���� ȭ������ ���� �޼����� ����
		request.setAttribute("updateFailed", true);
		request.setAttribute("exception", 
			new IllegalStateException("Ÿ���� ������ ������ �� �����ϴ�."));            
		return "/customer/view.jsp";	// ����� ���� ȭ������ �̵� (forwarding)
    }
}


