package model.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Lm;
import model.LmManage;

public class LmDAO {
	
	private JDBCUtil jdbcUtil = null;
	
	public LmDAO() {
		jdbcUtil = new JDBCUtil();	
	}
	
	
	/*public int create(Lm lm) throws SQLException {
	String sql = "INSERT INTO LITTLEMEETING VALUES (littlemeeting_seq.nextval, ?, ?, ?, SYSDATE, 0, ?, 0) ";		
	Object[] param = new Object[] {lm.getLittlemeeting_name(), lm.getTitle(), lm.getContents(), lm.getMax_num()};
								
	jdbcUtil.setSqlAndParameters(sql, param);
	// JDBCUtil �� insert���� �Ű� ���� ����
					
	try {
		int result= jdbcUtil.executeUpdate();
		return result;
	} catch (Exception ex) {
		jdbcUtil.rollback();
		ex.printStackTrace();
	} finally {		
		jdbcUtil.commit();
		jdbcUtil.close();	// resource ��ȯ
	}		
	return 0;			
}*/

	public int create(Lm lm) throws SQLException {
		
		String sql = "INSERT INTO LITTLEMEETING (littlemeeting_no, littlemeeting_name, title, contents, max_num, createtime, writer_id) "
					+ "VALUES (littlemeeting_seq.nextval, ?, ?, ?, ?, SYSDATE, ?)";
		
		/*
		String sql = "INSERT INTO LITTLEMEETING VALUES (littlemeeting_seq.nextval, ?, ?, ?, SYSDATE, 0, ?, 0) ";		
		*/
		Object[] param = new Object[] {lm.getLittlemeeting_name(), lm.getTitle(), lm.getContents(), lm.getMax_num(), lm.getWriter_id()};
								
		jdbcUtil.setSqlAndParameters(sql, param);
		// JDBCUtil �� insert���� �Ű� ���� ����
						
		String key[] = {"littlemeeting_no"};
		try {
			int result = jdbcUtil.executeUpdate(key);
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
	
	//��û
		public int apply(LmManage lman)throws SQLException{
			
			String sql = "UPDATE LITTLEMEETING "
					+ "SET count = count + 1 "
					+ "WHERE littlemeeting_no=?";
		
			Object[] param = new Object[] {lman.getLittlemeeting_no()};
			
			jdbcUtil.setSqlAndParameters(sql, param);
		
			try {
				int result = jdbcUtil.executeUpdate();
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

		//���
		public int cancel(int littlemeeting_no)throws SQLException{
			
			String sql = "UPDATE LITTLEMEETING "
					+ "SET count = count - 1 "
					+ "WHERE littlemeeting_no=?";
		
			Object[] param = new Object[] {littlemeeting_no};
			
			jdbcUtil.setSqlAndParameters(sql, param);
		
			try {
				int result = jdbcUtil.executeUpdate();
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

	//�� �ۼ��ڸ� delete������ ���� �ִ�. 
	public int delete(int littlemeeting_no) throws SQLException {
		String sql = "DELETE FROM LITTLEMEETING WHERE littlemeeting_no=? ";		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {littlemeeting_no});	// JDBCUtil�� delete���� �Ű� ���� ����

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
	
	
	
	//��ü littemeeting������ �˻��Ͽ� List�� ���� �� ��ȯ
	public List<Lm> findUserList() throws SQLException {
        String sql = "SELECT littlemeeting_no, littlemeeting_name, createtime, count, max_num " 
        		   + "FROM LITTLEMEETING "
        		   + "ORDER BY littlemeeting_no ";
	
	
		jdbcUtil.setSqlAndParameters(sql, null);		// JDBCUtil�� query�� ����
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			List<Lm> lmList = new ArrayList<Lm>();	// lm���� ����Ʈ ����
			while (rs.next()) {
				Lm lm = new Lm(	
					rs.getInt("littlemeeting_no"),
					rs.getString("littlemeeting_name"),
					rs.getDate("createtime"),
					rs.getInt("count"),
					rs.getInt("max_num"));
				lmList.add(lm);				// List�� lm ��ü ����
			}		
			return lmList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}	
		
	
	//�־��� littlemeeting_no�� �ش��ϴ� ������ DB���� ã�� Littlemeeting ������ Ŭ������ �����Ͽ� ��ȯ
	public Lm ShowLMDetail(int littlemeeting_no) throws SQLException {
        String sql = "SELECT littlemeeting_name, title, contents, createtime, count, max_num, writer_id "
        			+ "FROM LITTLEMEETING "
        			+ "WHERE littlemeeting_no=?";  
        
		jdbcUtil.setSqlAndParameters(sql, new Object[] {littlemeeting_no});	// JDBCUtil�� query���� �Ű� ���� ����
		Lm lm = null;
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����
			if (rs.next()) {						// �л� ���� �߰�
				lm = new Lm(
					littlemeeting_no,
					rs.getString("littlemeeting_name"),
					rs.getString("title"),
					rs.getString("contents"),
					rs.getDate("createtime"),
					rs.getInt("count"),					
					rs.getInt("max_num"),
					rs.getString("writer_id"));
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return lm;
	}


	public Lm findLm(int littlemeeting_no) {
		// TODO Auto-generated method stub
		String sql = "SELECT littlemeeting_name, title, contents, createtime, count, max_num "
    			+ "FROM LITTLEMEETING "
    			+ "WHERE littlemeeting_no=? ";              
	jdbcUtil.setSqlAndParameters(sql, new Object[] {littlemeeting_no});	// JDBCUtil�� query���� �Ű� ���� ����

	try {
		ResultSet rs = jdbcUtil.executeQuery();		// query ����
		if (rs.next()) {						// �л� ���� �߰�
			Lm lm = new Lm(		
				littlemeeting_no,
				rs.getString("littlemeeting_name"),
				rs.getString("title"),
				rs.getString("contents"),
				rs.getDate("createtime"),
				rs.getInt("count"),
				rs.getInt("max_num"));
			return lm;
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		jdbcUtil.close();		// resource ��ȯ
	}
	return null;
	}


	public int update(Lm lm) throws SQLException {
		String sql = "UPDATE LITTLEMEETING "
					+ "SET littlemeeting_name=?, title=?, contents=?, max_num=? "
					+ "WHERE littlemeeting_no=?";
		
		int littlemeeting_no = lm.getLittlemeeting_no();
		
		
		Object[] param = new Object[] {lm.getLittlemeeting_name(), lm.getTitle(), lm.getContents(), lm.getMax_num(), littlemeeting_no};				
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
	
	//LM Manage ���̺� ����
		public int createLmMan(LmManage lman) {
			// TODO Auto-generated method stub

			String sql = "INSERT INTO LITTLEMEETING_MANAGE(littlemeeting_no, join_check, apply_id ) "
					+ "VALUES (?, ?, ?) ";
			
			Object[] param = new Object[] { lman.getLittlemeeting_no(), lman.getJoin_check(), lman.getApply_id() };
							
			jdbcUtil.setSqlAndParameters(sql, param);
		// JDBCUtil �� insert���� �Ű� ���� ����
						
			
			try {
				int result = jdbcUtil.executeUpdate();
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
		
		//LM Manage ���̺� ����
		public int deleteLmMan(LmManage lman) {
			String sql = "DELETE FROM LITTLEMEETING_MANAGE WHERE littlemeeting_no = ? AND apply_id = ?";		
			jdbcUtil.setSqlAndParameters(sql, new Object[] {lman.getLittlemeeting_no(), lman.getApply_id()});	// JDBCUtil�� delete���� �Ű� ���� ����

			String key[] = {"littlemeeting_no"};
			try {
				int result = jdbcUtil.executeUpdate(key);
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
}
