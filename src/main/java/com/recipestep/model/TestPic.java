package com.recipestep.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestPic/*" + "")
public class TestPic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TestPic() {
		super();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		InputStream in = null;

		// 網址傳參數進來測，如：http://localhost:8081/JustEat/TestPic/200002?step=1
		int pathInfo = Integer.parseInt(req.getPathInfo().substring(1)); // 200002
		int paraValue = Integer.parseInt(req.getParameter("step"));		 // 1
		System.out.println(pathInfo);
		System.out.println(paraValue);

		/*********************************/

		RecipeStepJDBCDAO dao = new RecipeStepJDBCDAO();
		byte[] picBuffer = null;
		
		try {

			List<RecipeStepVO> all = dao.getAllByRecipe(pathInfo);
			if (all.size() == 0 || all == null) {
				// 額外路徑資訊輸錯，沒有這個recipe編號
				in = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\none.jpg");
				picBuffer = new byte[in.available()];
				in.read(picBuffer);
				return;	// 不寫也沒差，其實已out到瀏覽器上了，後面的out都不會成功輸出
			}
//			else {
//				// 應該前面if都有補捉到了
//				in = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\none.jpg");
//				picBuffer = new byte[in.available()];
//				in.read(picBuffer);
//				out.write(picBuffer);
//			}
			
			RecipeStepVO vo = all.get(paraValue - 1);
//			if (vo == null) {
//				// 不會發生這個狀況，all.get()會先拋出IndexOutOfBoundsException
//				in = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\none.jpg");
//				picBuffer = new byte[in.available()];
//				in.read(picBuffer);
//				out.write(picBuffer);
//				return;
//			} else 
			if (vo.getRecipeStepPic() == null) {
				// 有這筆食譜步驟，但沒有上傳圖片
				in = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\null.jpg");
				picBuffer = new byte[in.available()];
				in.read(picBuffer);
				out.write(picBuffer);
			} else if (vo.getRecipeStepPic().length != 0) {
				// 一切正常，顯示圖片至瀏覽器
				picBuffer = vo.getRecipeStepPic();
				out.write(picBuffer);
			} else {
				in = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\none.jpg");
				picBuffer = new byte[in.available()];
				in.read(picBuffer);
				out.write(picBuffer);
			}
			
			
		} catch (IndexOutOfBoundsException e) {
			// 參數輸錯（超出索引值，List中沒有這個索引值的資料當然get不到）
			in = new FileInputStream("C:\\Users\\Tibame_T14\\Downloads\\none.jpg");
			picBuffer = new byte[in.available()];
			in.read(picBuffer);
			out.write(picBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println(req.getRequestURL());
	}

}
