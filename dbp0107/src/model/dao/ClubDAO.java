package model.dao;

import java.sql.*;
import java.util.*;

import model.Club;

public class ClubDAO {
	private JDBCUtil jdbcUtil = null;
	
	public ClubDAO() {
		jdbcUtil = new JDBCUtil();
	}
	
	/* ���ο� Club ���� */
	public int create(Club club) throws SQLException {
		String sql = "INSERT INTO CLUB (club_no, club_name, title, contents, createtime, department_no) "
				+ "VALUES (club_seq.nextval, ?, ?, ?, SYSDATE, ?)";	
		Object[] param = new Object[] {club.getClub_name(), club.getTitle(), club.getContents(), 
				(club.getDepartment_no()!=0) ? club.getDepartment_no() : null };
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
	
	/* �̰� generate key ���ϴ� create
	 public int create(Club club) throws SQLException {
		String sql = "INSERT INTO Club VALUES (?, ?, ?, ?, SYSDATE)";		
		Object[] param = new Object[] {club.getClub_no(), club.getClub_name(), 
					club.getTitle(), club.getContents(),
						(club.getClub_no()!=0) ? club.getClub_no() : null };				
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
	
	
	/* ���� Club ���� */
	public int update(Club club) throws SQLException {
		String sql = "UPDATE CLUB "
					+ "SET deptartment_no=?, club_name=?, title=?, contents=?"
					+ "WHERE club_no=?";
		Object[] param = new Object[] {club.getDepartment_no(), club.getClub_name(), club.getTitle(), 
						club.getContents(), club.getClub_no()};
		
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
	
	/* ���� Club ���� */
	public int remove(int club_no) throws SQLException {
		String sql = "DELETE FROM CLUB WHERE club_no=?";		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {club_no});	// JDBCUtil�� delete���� �Ű� ���� ����

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
	
	/* DB�� ����� club ��ϵ��� �ҷ���  */
	public List<Club> clubList() throws SQLException {
					// ���Ƹ��� �а��� �ҼӵǾ����� ������ (dept_no == NULL�̸� �߾ӵ��Ƹ��� ���)
        String sql = "SELECT club_no, club_name, NVL2(dept_name, dept_name, '�߾ӵ��Ƹ�') AS dept_name "
        		   + "FROM CLUB c LEFT OUTER JOIN DEPARTMENT d ON c.department_no = d.department_no "
        		   + "ORDER BY club_no";        
		jdbcUtil.setSqlAndParameters(sql, null);		// JDBCUtil�� query�� ����
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			List<Club> clubList = new ArrayList<Club>();	// Community���� ����Ʈ ����
			while (rs.next()) {
				Club club = new Club(			// Community ��ü�� �����Ͽ� ���� ���� ������ ����
						rs.getInt("club_no"),
						rs.getString("club_name"),
						rs.getString("dept_name"));
				clubList.add(club);				// List�� Community ��ü ����
			}		
			return clubList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	
	/* clubNo�� �ش� �Խñ��� �� ������ �ҷ���   Club ������ Ŭ������ �����ؼ� ��ȯ */
	public Club showDetail(int club_no) {
		String sql = "SELECT club_name, NVL2(dept_name, dept_name, '�߾ӵ��Ƹ�') AS dept_name, title, contents, createtime "
					+ "FROM CLUB c LEFT OUTER JOIN DEPARTMENT d ON c.department_no = d.department_no "
     				+ "WHERE club_no = ?";              
		jdbcUtil.setSqlAndParameters(sql, new Object[] {club_no});	// JDBCUtil�� query���� �Ű� ���� ����
		Club club = null;
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����
			if(rs.next()) {
				club = new Club(
						club_no, 
						rs.getString("club_name"),
						rs.getString("dept_name"),
						rs.getString("title"),
						rs.getString("contents"),
						rs.getDate("createtime"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return club;
	}
	
	/* customerId�� �������  �а� ���� ��õ ���Ƹ��� �ҷ���    Club ����Ʈ�� �����ؼ� ��ȯ */
	public List<Club> showRecommend(String customerId) throws SQLException {
		// dept_no ��� �а� ���� ��õ ���Ƹ� �ҷ����� sql
		String sql = "SELECT club_name, title, club_no "
        			+ "FROM CLUB c LEFT OUTER JOIN CUSTOMER c1 ON c.department_no = c1.department_no "
        			+ "WHERE customerId = ?"; 
		
		jdbcUtil.setSqlAndParameters(sql, new Object[] {customerId});	// JDBCUtil�� query���� �Ű� ���� ����
		
		// dept_no is null�̸� random���� 3�� ��õ ���Ƹ� �ҷ����� sql
		String sql2 = "select club_name, title, club_no from(select club_name, title, club_no from club where department_no is null order by DBMS_RANDOM.value) where rownum < 4";        
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����
			
			// if dept_no ��� �а� ���� ��õ ���Ƹ�  ���� O --> using isBeforeFirst()
			// isBeforeFirst() : ù row �ٷ� �� O --> TRUE / ù row �� X or ��� row�� X --> FALSE
			if(rs.isBeforeFirst()) {
				List<Club> clubList = new ArrayList<Club>();	
				while (rs.next()) {
					Club club = new Club(			
						customerId, 
						rs.getString("club_name"),
						rs.getString("title"),
						rs.getInt("club_no"));
					clubList.add(club);			
				}		
				return clubList;					
			}
			// if dept_no ��� �а� ���� ��õ ���Ƹ�  ���� X
			else {
				jdbcUtil.setSqlAndParameters(sql2, null);
				ResultSet rs2 = jdbcUtil.executeQuery();
				
				List<Club> clubList = new ArrayList<Club>();	
				while (rs2.next()) {
					Club club = new Club(	
						customerId, 
						rs2.getString("club_name"),
						rs2.getString("title"), 
						rs2.getInt("club_no"));
					clubList.add(club);			
				}		
				return clubList;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	
	public boolean existingClub(int club_no) throws SQLException {
		String sql = "SELECT count(*) FROM CLUB WHERE club_no=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {club_no});	// JDBCUtil�� query���� �Ű� ���� ����

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
	
	/* findClub & findClubList �κ��� ������ �ʿ�!!!!! �������� */
	public Club findClub(int club_no) throws SQLException {
        String sql = "SELECT deptartment_no, club_name "
        			+ "FROM CLUB "
        			+ "WHERE club_no=? ";              
		jdbcUtil.setSqlAndParameters(sql, new Object[] {club_no});	// JDBCUtil�� query���� �Ű� ���� ����

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query ����
			if (rs.next()) {						
				Club club = new Club(		
					club_no,
					rs.getInt("deptartment_no"),
					rs.getString("club_name"),
					null,
					null,
					null);
				return club;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	
	public List<Club> findClubList() throws SQLException {
		String sql = "SELECT club_no, deptartment_no, club_name"
     		   + "FROM CLUB "
     		   + "ORDER BY club_no";        
		jdbcUtil.setSqlAndParameters(sql, null);		// JDBCUtil�� query�� ����
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query ����			
			List<Club> clubList = new ArrayList<Club>();	// Community���� ����Ʈ ����
			while (rs.next()) {
				Club club = new Club(			// Community ��ü�� �����Ͽ� ���� ���� ������ ����
						rs.getInt("club_no"),
						rs.getInt("deptartment_no"),
						rs.getString("club_name"),
						null,
						null,
						null);
				clubList.add(club);				// List�� Community ��ü ����
			}		
			return clubList;					
			
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
	public List<Club> findClubList(int currentPage, int countPerPage) throws SQLException {
        String sql = "SELECT club_no, deptartment_no, club_name " 
        		   + "FROM CLUB "
        		   + "ORDER BY club_no";
		jdbcUtil.setSqlAndParameters(sql, null,					// JDBCUtil�� query�� ����
				ResultSet.TYPE_SCROLL_INSENSITIVE,				// cursor scroll ����
				ResultSet.CONCUR_READ_ONLY);						
		
		try {
			ResultSet rs = jdbcUtil.executeQuery();				// query ����			
			int start = ((currentPage-1) * countPerPage) + 1;	// ����� ������ �� ��ȣ ���
			if ((start >= 0) && rs.absolute(start)) {			// Ŀ���� ���� ������ �̵�
				List<Club> clubList = new ArrayList<Club>();
				do {
					Club club = new Club(			// Community ��ü�� �����Ͽ� ���� ���� ������ ����
							rs.getInt("club_no"),
							rs.getInt("deptartment_no"),
							rs.getString("club_name"),
							null,
							null,
							null);
					clubList.add(club);								// ����Ʈ�� User ��ü ����
				} while ((rs.next()) && (--countPerPage > 0));		
				return clubList;							
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource ��ȯ
		}
		return null;
	}
	
	
	
	
}