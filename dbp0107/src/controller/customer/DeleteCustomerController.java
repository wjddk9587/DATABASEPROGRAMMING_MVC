package controller.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.Customer;
import model.service.CustomerManager;

public class DeleteCustomerController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(DeleteCustomerController.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)   throws Exception {
      String deleteId = request.getParameter("customerId");
       log.debug("Delete Customer : {}", deleteId);

      CustomerManager manager = CustomerManager.getInstance();      
      HttpSession session = request.getSession();   
   
      if ((CustomerSessionUtils.isLoginCustomer("admin", session) &&    // �α����� ����ڰ� �������̰�    
          !deleteId.equals("admin"))                     // ���� ����� �Ϲ� ������� ���, 
            ||                                     // �Ǵ� 
         (!CustomerSessionUtils.isLoginCustomer("admin", session) &&  // �α����� ����ڰ� �����ڰ� �ƴϰ� 
           CustomerSessionUtils.isLoginCustomer(deleteId, session))) { // �α����� ����ڰ� ���� ����� ��� (�ڱ� �ڽ��� ����)
            
         manager.remove(deleteId);            // ����� ���� ����
         if (CustomerSessionUtils.isLoginCustomer("admin", session))   // �α����� ����ڰ� ������    
            return "redirect:/customer/list";      // ����� ����Ʈ�� �̵�
         else                            // �α����� ����ڴ� �̹� ������
            return "redirect:/customer/logout";      // logout ó��
      }
      
      /* ������ �Ұ����� ��� */
      Customer customer = manager.findCustomer(deleteId);   // ����� ���� �˻�
      request.setAttribute("customer", customer);                  
      request.setAttribute("deleteFailed", true);
      String msg = (CustomerSessionUtils.isLoginCustomer("admin", session)) 
               ? "�ý��� ������ ������ ������ �� �����ϴ�."      
               : "Ÿ���� ������ ������ �� �����ϴ�.";                                       
      request.setAttribute("exception", new IllegalStateException(msg));            
      return "/customer/view.jsp";      // ����� ���� ȭ������ �̵� (forwarding)   
   }
}