package controller.customer;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.Controller;
import model.Customer;
import model.service.CustomerManager;

public class ListCustomerController implements Controller {
   // private static final int countPerPage = 100;   // �� ȭ�鿡 ����� ����� ��

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)   throws Exception {
      // �α��� ���� Ȯ��
       if (!CustomerSessionUtils.hasLogined(request.getSession())) {
            return "redirect:/customer/login/form";      // login form ��û���� redirect
        }
       
       /*
       String currentPageStr = request.getParameter("currentPage");   
      int currentPage = 1;
      if (currentPageStr != null && !currentPageStr.equals("")) {
         currentPage = Integer.parseInt(currentPageStr);
      }      
       */
       
      CustomerManager manager = CustomerManager.getInstance();
      List<Customer> customerList = manager.findCustomerList();
      // List<Customer> customerList = manager.findCustomerList(currentPage, countPerPage);

      // customerList ��ü�� ���� �α����� ����� ID�� request�� �����Ͽ� ����
      request.setAttribute("customerList", customerList);            
      request.setAttribute("curCustomerId", 
            CustomerSessionUtils.getLoginCustomerId(request.getSession()));      

      // ����� ����Ʈ ȭ������ �̵�(forwarding)
      return "/customer/list.jsp";        
    }
}