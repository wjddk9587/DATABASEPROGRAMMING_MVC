package controller.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.Controller;
import model.service.CustomerManager;

public class LoginController implements Controller {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
       String customerId = request.getParameter("customerId");
      String password = request.getParameter("password");
      
      try {
         // �𵨿� �α��� ó���� ����
         CustomerManager manager = CustomerManager.getInstance();
         manager.login(customerId, password);
   
         // ���ǿ� ����� ���̵� ����
         HttpSession session = request.getSession();
            session.setAttribute(CustomerSessionUtils.USER_SESSION_KEY, customerId);
            
            return "redirect:/main";  
            
            
            
      } catch (Exception e) {
         /* CustomerNotFoundException�̳� PasswordMismatchException �߻� ��
          * �ٽ� login form�� ����ڿ��� �����ϰ� ���� �޼����� ���
          */
            request.setAttribute("loginFailed", true);
         request.setAttribute("exception", e);
            return "/customer/loginForm.jsp";         
      }   
    }
}