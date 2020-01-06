package model.service;

import java.sql.SQLException;
import java.util.List;

import model.Customer;
import model.dao.CustomerDAO;

/**
 * ����� ���� API�� ����ϴ� �����ڵ��� ���� �����ϰ� �Ǵ� Ŭ����.
 * CustomerDAO�� �̿��Ͽ� �����ͺ��̽��� ������ ���� �۾��� �����ϵ��� �ϸ�,
 * �����ͺ��̽��� �����͵��� �̿��Ͽ� �����Ͻ� ������ �����ϴ� ������ �Ѵ�.
 * �����Ͻ� ������ ������ ��쿡�� �����Ͻ� �������� �����ϴ� Ŭ������ 
 * ������ �� �� �ִ�.
 */
public class CustomerManager {
	private static CustomerManager customerMan = new CustomerManager();
	private CustomerDAO customerDAO;
	private CustomerAnalysis customerAanlysis;

	private CustomerManager() {
		try {
			customerDAO = new CustomerDAO();
			customerAanlysis = new CustomerAnalysis(customerDAO);
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public static CustomerManager getInstance() {
		return customerMan;
	}
	
	public int create(Customer customer) throws SQLException, ExistingCustomerException {
		if (customerDAO.existingCustomer(customer.getCustomerId()) == true) {
			throw new ExistingCustomerException(customer.getCustomerId() + "�� �����ϴ� ���̵��Դϴ�.");
		}
		return customerDAO.create(customer);
	}

	public int update(Customer customer) throws SQLException {
		return customerDAO.update(customer);
	}	

	public int remove(String customerId) throws SQLException {
		return customerDAO.remove(customerId);
	}

	public Customer findCustomer(String customerId)
		throws SQLException, CustomerNotFoundException {
		Customer customer = customerDAO.findCustomer(customerId);
		
		if (customer == null) {
			throw new CustomerNotFoundException(customerId + "�� �������� �ʴ� ���̵��Դϴ�.");
		}		
		return customer;
	}
	
	public Customer viewCustomer(String customerId)
			throws SQLException, CustomerNotFoundException {
			Customer customer = customerDAO.viewCustomer(customerId);
			
			if (customer == null) {
				throw new CustomerNotFoundException(customerId + "�� �������� �ʴ� ���̵��Դϴ�.");
			}		
			return customer;
		}

	public List<Customer> findCustomerList() throws SQLException {
			return customerDAO.findCustomerList();
	}
	
	public List<Customer> findCustomerList(int currentPage, int countPerPage)
		throws SQLException {
		return customerDAO.findCustomerList(currentPage, countPerPage);
	}

	public boolean login(String customerId, String password)
		throws SQLException, CustomerNotFoundException, PasswordMismatchException {
		Customer customer = findCustomer(customerId);

		if (!customer.matchPassword(password)) {
			throw new PasswordMismatchException("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
		}
		return true;
	}

	public List<Customer> makeFriends(String customerId) throws Exception {
		return customerAanlysis.recommendFriends(customerId);
	}
	
	public CustomerDAO getCustomerDAO() {
		return this.customerDAO;
	}
}

