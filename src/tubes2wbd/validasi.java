package tubes2wbd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class validasi
 */
public class validasi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
	final String DB_URL="jdbc:mysql://localhost/wbd1";
	//  Database credentials
	final String USER = "root";
	final String PASS = "";   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public validasi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String data = request.getParameter("q");
		String type = request.getParameter("num");
		String pass = null;
		if (request.getParameter("pass") != null) {
			pass = request.getParameter("pass");
		}
		
		PrintWriter out = response.getWriter();
		
		switch (type) {
		case "1": // Full name validator
			String regex = "^[\\S.]+ [\\S.]+$";
			if (data.matches(regex))
				out.print(0);
			else
				out.print(1);
			break;
		case "2": // Username validator 
			try{
			    Class.forName("com.mysql.jdbc.Driver");
			    Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		        Statement stmt = conn.createStatement();
		        String sql = "SELECT * FROM anggota WHERE username = '"+data+"'";
		        regex = "^([A-Za-z0-9]{5,20})$";
		        boolean samewithpass = false;
		        if (pass.equals(data)) samewithpass = true;
		        
		        ResultSet rs = stmt.executeQuery(sql);
		        boolean rsb = rs.next();
				//int r = stmt.executeUpdate(sql);
				if (!rsb && data.matches(regex) && !samewithpass) {out.print(0);}
				else if (rsb) {out.print(1);}
				else if (samewithpass) {out.print(2);}
				else {out.print(3);}
	      	}catch(SQLException se){}catch(Exception e){}//end try
	      	
			break;
		case "3": // password validator
			boolean samewithpass = false;
			if (pass.equals(data)) samewithpass = true;
			if (data.length() > 7 && !samewithpass) out.print(0);
			else {
				if (samewithpass) out.print(1);
				else out.print(2);
			}
			break;
		case "4": // copassword validator
			if (pass.equals(data)) out.print(0);
			else out.print(1);
			break;
		case "5": // email validator
			regex = "^[a-zA-Z0-9\\_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9.]{2,}$";
			if (data.matches(regex)) {
				out.print(0);
			}
			else {
				out.print(1);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
