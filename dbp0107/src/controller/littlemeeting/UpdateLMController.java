package controller.littlemeeting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import model.Lm;
import model.service.LmManager;

public class UpdateLMController implements Controller {
	
	
	  private static final Logger log = LoggerFactory.getLogger(UpdateLMController.class);

	    @Override
	    public String execute(HttpServletRequest request, HttpServletResponse response)	throws Exception {
	 
	    	int littlemeeting_no = Integer.parseInt(request.getParameter("littlemeeting_no"));
			
			if (request.getMethod().equals("GET")) {	
	    		// GET request: Ŀ�´�Ƽ ���� form ��û	
	    		LmManager manager = LmManager.getInstance();
				Lm lm  = manager.ShowLMDetail(littlemeeting_no);	// �����Ϸ��� Ŀ�´�Ƽ ���� �˻�
				request.setAttribute("Lm", lm);			
				
				return "/lm/updateForm.jsp";   // �˻��� ������ update form���� ����     
		    }	
	    	
	    	// POST request (Ŀ�´�Ƽ ������ parameter�� ���۵�)
	    	Lm lm = new Lm(
	    		littlemeeting_no,
	    		request.getParameter("littlemeeting_name"),
	    		request.getParameter("title"),
	    		request.getParameter("contents"),
	    		Integer.parseInt(request.getParameter("max_num")));

	    	log.debug("Update Community : {}", lm);

			LmManager manager = LmManager.getInstance();
			manager.updateLm(lm);			
			return "redirect:/littlemeeting/list";	
	       		
	    }
	
	

}
