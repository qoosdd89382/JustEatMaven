<script type="text/javascript">

	function refresh() {
		src = "RandomNumber.jsp?id=" + Math.random();
	}
</script>

<%@ page contentType="charset=UTF-8" language="java" 
	import="java.awt.*"
	import="java.awt.image.BufferedImage" 
	import="java.util.*"
	import="javax.imageio.ImageIO" 
	pageEncoding="UTF-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	//在記憶體中建立影像
	int width = 120, height = 30;
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	//獲取畫筆
	Graphics g = image.getGraphics();
	//設定隨機背景色
	Random rcolor = new Random();
	Random gcolor = new Random();
	Random bcolor = new Random();
	int randrcolor = rcolor.nextInt(256);
	int randgcolor = rcolor.nextInt(256);
	int randbcolor = rcolor.nextInt(256);
	
	g.setColor(new Color(randrcolor,randgcolor,randbcolor));
	g.fillRect(0, 0, width, height);
	//取隨機產生的驗證碼(4位數字)
	Random rnd = new Random();
	int randNum = rnd.nextInt(8999) + 1000;
	String randStr = String.valueOf(randNum);
	//將驗證碼存入session
	session.setAttribute("RandomNumber", randStr);
	//將驗證碼顯示到影象中
	Random rcolor1 = new Random();
	Random gcolor1 = new Random();
	Random bcolor1 = new Random();
	int randrcolor1 = rcolor1.nextInt(256);
	int randgcolor1 = rcolor1.nextInt(256);
	int randbcolor1 = rcolor1.nextInt(256);
	g.setColor(new Color(randrcolor1,randgcolor1,randbcolor1));
	g.setFont(new Font("", Font.PLAIN, 30));
	g.drawString(randStr, 29, 25);
	//隨機產生干擾點，使驗證碼不易被其他程式探測到
	for (int i = 0; i < 200; i++) {

		int x = rnd.nextInt(width);
		int y = rnd.nextInt(height);
		g.drawOval(x, y, 1, 1);
	}
	//輸出影像到頁面
	ImageIO.write(image, "JPEG", response.getOutputStream());
	out.clear();
	out = pageContext.pushBody();
%>