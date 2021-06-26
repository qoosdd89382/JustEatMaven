package com.eventinfo.controller;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eventinfo.model.EventInfoService;
import com.eventinfo.model.EventInfoVO;

@WebServlet("/Event/EventInfoForOnePic/*")
public class EventInfoForOnePic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EventInfoForOnePic() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("images/gif");
		ServletOutputStream out = response.getOutputStream();

		byte[] picBuffer = null;
		InputStream in = null;
		BufferedInputStream bis = null;

		EventInfoVO eventInfoVO = null;
		EventInfoService eventInfoSvc = new EventInfoService();
		try {
			eventInfoVO = eventInfoSvc.getEventID(Integer.parseInt(request.getParameter("eventID")));
			
		if (eventInfoVO.getEventPic() == null) {
			in = getServletContext().getResourceAsStream("/image/null.png");
			bis = new BufferedInputStream(in);
			picBuffer = new byte[bis.available()];
			in.read(picBuffer);
			out.write(picBuffer);
		} else if (eventInfoVO.getEventPic().length != 0) {
			picBuffer = eventInfoVO.getEventPic();
			out.write(picBuffer);
		} else {
			in = getServletContext().getResourceAsStream("/image/notfound.png");
			bis = new BufferedInputStream(in);
			picBuffer = new byte[bis.available()];
			in.read(picBuffer);
			out.write(picBuffer);
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		if (bis != null) {
			try {
				bis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
