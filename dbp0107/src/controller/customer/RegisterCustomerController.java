package controller.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.Customer;
import model.Department;
import model.service.ExistingCustomerException;
import model.service.MainManager;
import model.service.CustomerManager;

public class RegisterCustomerController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(RegisterCustomerController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if (request.getMethod().equals("GET")) {
  		  MainManager man = MainManager.getInstance();
  		  
  		  List<Department> departList = man.departList();
  		  request.setAttribute("departList", departList); return "/customer/registerForm.jsp";
  		  
   		}
    	//POST��
    	
      Customer customer = new Customer(
    	 Integer.parseInt(request.getParameter("department_no")),
         request.getParameter("customerId"),
         request.getParameter("password"),
         request.getParameter("name"),
         request.getParameter("email"),
         request.getParameter("phone"));
      try {
         CustomerManager manager = CustomerManager.getInstance();
         manager.create(customer);
         log.debug("Create Customer : {}", customer);
           return "redirect:/customer/list";      // ���� �� ����� ����Ʈ ȭ������ redirect
           
      } catch (ExistingCustomerException e) {      // ���� �߻� �� ȸ������ form���� forwarding
            request.setAttribute("registerFailed", true);
         request.setAttribute("exception", e);
         request.setAttribute("customer", customer);
         return "/customer/registerForm.jsp";
      }
    }
}