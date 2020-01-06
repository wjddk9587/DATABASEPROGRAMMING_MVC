package model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Customer;

/**
 * ����� ������ ���� �����ͺ��̽� �۾��� �����ϴ� DAO Ŭ����
 * USERINFO ���̺� ����� ������ �߰�, ����, ����, �˻� ���� 
 */
public class CustomerDAO {
	private JDBCUtil jdbcUtil = null;
	
	public CustomerDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil ��ü ����
	}
	
	public int create(Customer customer) throws SQLException {
		String sql = "INSERT INTO CUSTOMER (customer_no, customerId, password, name, email, phone, department_no) "
				+ "VALUES (customer_seq.nextval, ?, ?, ?, ?, ?, ?)";		
		Object[] param = new Object[] {customer.getCustomerId(), customer.getPassword(), 
				customer.getName(), customer.getEmail(), customer.getPhone(),
				(customer.getDepartment_no()!=0) ? customer.getDepartment_no() : null};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil �� insert���� �Ű� ���� d����
						
		try {				
			int result = jdbcUtil.executeUpdate();	// insert �� ����
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource ��ȯ
		}		
		return 0;			
	}
	
	/*

	public int create(Customer customer) throws SQLException {
		String sql = "INSERT INTO CUSTOMER (customer_no, customerId, password, name, email, phone) "
					+ "VALUES (1, ?, ?, ?, ?, ?)";		
		Object[] param = new Object[] {customer.getCustomerId(), customer.getPassword(), 
				customer.getName(), customer.getEmail(), customer.getPhone()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil �� insert���� �Ű� ���� ����
						
		try {				
			int result = jdbcUtil.executeUpdate();	// insert �� ����
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource ��ȯ
		}		
		return 0;			
	}
*/
	/**
	 * ������ ����� ������ ����.
	 */
	public int update(Customer customer) throws SQLException {
		String sql = "UPDATE CUSTOMER "
					+ "SET password=?, name=?, email=?, phone=? "
					+ "WHERE customerid=?";
		Object[] param = new Object[] {customer.getPassword(), customer.getName(), 
					customer.getEmail(), customer.getPhone(), customer.getCustomerId()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil�� update���� �Ű� ���� ����
			
		try {				
			int result = jdbcUtil.executeUpdate();	// update �� ����
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource ��ȯ
		}		
		return 0;
	}

	/**
	 * ����� ID�� �ش��ϴ� ����ڸ� ����.
	 */
	public int remove(String customerId) throws SQLException {
		String sql = "DELETE FROM CUSTOMER WHERE customerid=?";		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {customerId});	// JDBCUtil�� delete���� �Ű� ���� ����

		try {				
			int result = jdbcUtil.executeUpdate();	// delete �� ����
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		}
		finally {
			jdbcUtil.commit();
			jdbcUtil.close();	// resource ��ȯ
		}		
		return 0;
	}

	/**
	 * �־��� ����� ID�� �ش��ϴ� ����� ������ �����ͺ��̽����� ã�� Customer ������ Ŭ������ 
	 * �����Ͽ� ��ȯ.
	 */
	public Customer findCustomer(String customerId) throws SQLException {
        String sql = "SELECT password, name, email, phone "
        			+ "FROM CUSTOMER "
        			+ "WHERE customerid=? ";              
		jdbcUtil.setSqlAndParameters(sql, new Object[] {customerId});	// JDBCUtil�� query���� �Ű� ���� ����

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����
			if (rs.next()) {						// �л� ���� �߰�
				Customer customer = new Customer(		// Customer ��ü�� �����Ͽ� �л� ������ ����
					customerId,
					rs.getString("password"),
					rs.getString("name"),
					rs.getString("email"),
					rs.getString("phone"));
				return customer;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	
	public Customer viewCustomer(String customerId) throws SQLException {
        String sql = "SELECT password, name, email, phone, d.dept_name AS dept_name "
        			+ "FROM CUSTOMER c LEFT OUTER JOIN DEPARTMENT d ON c.department_no = d.department_no "
        			+ "WHERE customerid=? ";              
		jdbcUtil.setSqlAndParameters(sql, new Object[] {customerId});	// JDBCUtil�� query���� �Ű� ���� ����

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����
			if (rs.next()) {						// �л� ���� �߰�
				Customer customer = new Customer(		// Customer ��ü�� �����Ͽ� �л� ������ ����
					customerId,
					rs.getString("password"),
					rs.getString("name"),
					rs.getString("email"),
					rs.getString("phone"),
					rs.getString("dept_name"));
				return customer;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}

	/**
	 * ��ü ����� ������ �˻��Ͽ� List�� ���� �� ��ȯ
	 */
	public List<Customer> findCustomerList() throws SQLException {
        String sql = "SELECT customerId, password, name, email, phone " 
        		   + "FROM CUSTOMER "
        		   + "ORDER BY customerId";
		jdbcUtil.setSqlAndParameters(sql, null);		// JDBCUtil�� query�� ����
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			List<Customer> customerList = new ArrayList<Customer>();	// Customer���� ����Ʈ ����
			while (rs.next()) {
				Customer customer = new Customer(			// Customer ��ü�� �����Ͽ� ���� ���� ������ ����
					rs.getString("customerId"),
					rs.getString("password"),
					rs.getString("name"),
					rs.getString("email"),
					rs.getString("phone"));	
				customerList.add(customer);				// List�� Customer ��ü ����
			}		
			return customerList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	
	/**
	 * ��ü ����� ������ �˻��� �� ���� �������� �������� ����� ����� ���� �̿��Ͽ�
	 * �ش��ϴ� ����� �������� List�� �����Ͽ� ��ȯ.
	 */
	public List<Customer> findCustomerList(int currentPage, int countPerPage) throws SQLException {
        String sql = "SELECT customerId, password, name, email, phone " 
        		   + "FROM CUSTOMER "
        		   + "ORDER BY customerId";
		jdbcUtil.setSqlAndParameters(sql, null,					// JDBCUtil�� query�� ����
				ResultSet.TYPE_SCROLL_INSENSITIVE,				// cursor scroll ����
				ResultSet.CONCUR_READ_ONLY);						
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();				// query ����			
			int start = ((currentPage-1) * countPerPage) + 1;	// ����� ������ �� ��ȣ ���
			if ((start >= 0) && rs.absolute(start)) {			// Ŀ���� ���� ������ �̵�
				List<Customer> customerList = new ArrayList<Customer>();	// Customer���� ����Ʈ ����
				do {
					Customer customer = new Customer(		// Customer ��ü�� �����Ͽ� ���� ���� ������ ����
						rs.getString("customerId"),
						rs.getString("password"),
						rs.getString("name"),
						rs.getString("email"),
						rs.getString("phone"));	
					customerList.add(customer);							// ����Ʈ�� Customer ��ü ����
				} while ((rs.next()) && (--countPerPage > 0));		
				return customerList;							
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}

	/**
	 * �־��� ����� ID�� �ش��ϴ� ����ڰ� �����ϴ��� �˻� 
	 */
	public boolean existingCustomer(String customerId) throws SQLException {
		String sql = "SELECT count(*) FROM CUSTOMER WHERE customerid=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {customerId});	// JDBCUtil�� query���� �Ű� ���� ����

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return false;
	}
	
}
