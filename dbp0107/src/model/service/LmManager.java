package model.service;

import java.sql.SQLException;

import java.util.List;

import model.Lm;
import model.LmManage;
import model.dao.LmDAO;

public class LmManager {
	
	
	private static LmManager lmMan = new LmManager();
	private LmDAO lmDAO;
	
	private LmManager() {
		try {
			lmDAO = new LmDAO();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static LmManager getInstance() {
		return lmMan;
	}

	// �Ҹ��� �� ���
	public int createLM(Lm lm) throws SQLException {
		// TODO Auto-generated method stub
		return lmDAO.create(lm);
	}

	
	public Lm findLm(int littlemeeting_no) {
		Lm lm = lmDAO.findLm(littlemeeting_no);
		
		return lm;
	}
	
	
	
	//�Ҹ��� ��ü �� ����� �����ش�. 
	public List<Lm> findLMList() throws SQLException {
		// TODO Auto-generated method stub
		return lmDAO.findUserList();
	}

	
	//3������ ����� ���� customer�� ���� ȭ���� �޶�����.
	//�Ҹ��� �� ���� �����ش�.  
	public Lm ShowLMDetail(int littlemeeting_no) throws SQLException{
		// TODO Auto-generated method stub
		return lmDAO.ShowLMDetail(littlemeeting_no);
		
	}
	
	
	//�Ҹ����� ���� ���� �Ѵ�.(�� �ۼ��� ����)
	public int delete(int littlemeeting_no)throws SQLException{
		
		return lmDAO.delete(littlemeeting_no);
	}

	
	public int updateLm(Lm lm) throws SQLException {
		// TODO Auto-generated method stub
		return lmDAO.update(lm);
	}
	
	//�߰��� �κ�
	//LmMan ����
	public int createLmMan(LmManage lman) throws SQLException {
		// TODO Auto-generated method stub
		return lmDAO.createLmMan(lman);
	}

	//LmMan ����
	public int deleteLmMan(LmManage lman) throws SQLException {
		// TODO Auto-generated method stub
		return lmDAO.deleteLmMan(lman);
	}

	public int apply(LmManage lman)throws SQLException{
		return lmDAO.apply(lman);
	}
	
	public int cancel(int littlemeeting_no)throws SQLException{
		return lmDAO.cancel(littlemeeting_no);
	}
	
}