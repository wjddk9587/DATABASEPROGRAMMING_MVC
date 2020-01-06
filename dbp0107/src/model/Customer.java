package model;

/**
 * ����� ������ ���� �ʿ��� ������ Ŭ����. USERINFO ���̺�� ������
 */
public class Customer {
	private int customer_no;
	private int department_no;
	private char grade;
	private int certification_no;
	
	private String customerId;
	private String password;
	private String name;
	private String phone;
	private String email;
	private String dept_name;

	public Customer() { }		// �⺻ ������
	
	public Customer(int customer_no, int department_no, char grade, int certification_no, String customerId,
			String password, String name, String phone, String email) {
		super();
		this.customer_no = customer_no;
		this.department_no = department_no;
		this.grade = grade;
		this.certification_no = certification_no;
		this.customerId = customerId;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	
	
	
	public Customer(int department_no, String customerId, String password, String name, String email, String phone) {
		this.department_no = department_no;
		this.customerId = customerId;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	public Customer(String customerId, String password, String name, String email) {
		this.customerId = customerId;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public Customer(String customerId, String password, String name, String email, String phone) {
		this.customerId = customerId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public Customer(String customerId, String password) {
		super();
		this.customerId = customerId;
		this.password = password;
	}

	public void update(Customer updateCustomer) {
        this.password = updateCustomer.password;
        this.name = updateCustomer.name;
        this.email = updateCustomer.email;
        this.phone = updateCustomer.phone;
    }

	public Customer(String customerId, String password, String name, String email, String phone, String dept_name) {
		this.customerId = customerId;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.dept_name = dept_name;
	}

	public int getCustomer_no() {
		return customer_no;
	}




	public void setCustomer_no(int customer_no) {
		this.customer_no = customer_no;
	}




	public int getDepartment_no() {
		return department_no;
	}




	public void setDepartment_no(int department_no) {
		this.department_no = department_no;
	}




	public char getGrade() {
		return grade;
	}




	public void setGrade(char grade) {
		this.grade = grade;
	}




	public int getCertification_no() {
		return certification_no;
	}




	public void setCertification_no(int certification_no) {
		this.certification_no = certification_no;
	}




	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	/* ��й�ȣ �˻� */
	public boolean matchPassword(String password) {
		if (password == null) {
			return false;
		}
		return this.password.equals(password);
	}
	
	public boolean isSameCustomer(String userid) {
        return this.customerId.equals(userid);
    }




	@Override
	public String toString() {
		return "Customer [customer_no=" + customer_no + ", department_no=" + department_no + ", grade=" + grade
				+ ", certification_no=" + certification_no + ", customerId=" + customerId + ", password=" + password
				+ ", name=" + name + ", phone=" + phone + ", email=" + email + "]";
	}

	
}

