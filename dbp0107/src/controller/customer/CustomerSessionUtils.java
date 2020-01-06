package controller.customer;

import javax.servlet.http.HttpSession;

public class CustomerSessionUtils {
    public static final String USER_SESSION_KEY = "customerId";

    /* ���� �α����� ������� ID�� ���� */
    public static String getLoginCustomerId(HttpSession session) {
        String customerId = (String)session.getAttribute(USER_SESSION_KEY);
        return customerId;
    }
    
    /* �α����� ���������� �˻� */
    public static boolean hasLogined(HttpSession session) {
        if (getLoginCustomerId(session) != null) {
            return true;
        }
        return false;
    }

    /* ���� �α����� ������� ID�� customerId���� �˻� */
    public static boolean isLoginCustomer(String customerId, HttpSession session) {
        String loginCustomer = getLoginCustomerId(session);
        if (loginCustomer == null) {
            return false;
        }
        return loginCustomer.equals(customerId);
    }
}