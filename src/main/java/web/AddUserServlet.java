package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import util.DBUtils;

public class AddUserServlet extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String name=request.getParameter("name");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw=response.getWriter();

		
		//将用户信息插入到数据库
		Connection conn=null;
		PreparedStatement stat=null;
		try {
			conn=DBUtils.getConn();
			stat=conn.prepareStatement("insert into t_user values(null,?,?,?)");
			stat.setString(1, name);
			stat.setString(2, password);
			stat.setString(3, email);
			stat.executeUpdate();
			pw.println("添加成功");
		} catch (Exception e) {
			/*
			 * 1.记日志
			 * 将异常的所有信息记录下来，一般会记录到文件里面
			 */
			e.printStackTrace();
			/*
			 * 2.看异常能否恢复，如果不能恢复(比如，数据库服务暂停，网络中断等等，一般把这样的异常称为系统异常)
			 * 则提示用户稍后重试，如果能够恢复，则立即恢复
			 */
			pw.println("系统繁忙，稍后再试");
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
		/*
		 * 如果没有调用pw.close方法，则容器会自动调用该方法
		 */
	//	pw.close();
	}

	
}
