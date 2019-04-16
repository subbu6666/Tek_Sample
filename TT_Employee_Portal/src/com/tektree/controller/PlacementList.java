package com.tektree.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tektree.ConnectionManager.ConnectionManager;
@WebServlet("/PlacementList")
public class PlacementList extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
		
		res.setContentType("text/html");
		PrintWriter pw=res.getWriter();
		HttpSession session = req.getSession(false);
		
		//Database Connection
		Connection con = null;
		try {
			con = ConnectionManager.getConnection();
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   	    
   	    String designation= "";
	    String application_id = "";
	    String doj = "";
	    String recruiter = "";
   	    String placement_status = "";
   	    String skill = "";
   	    
   	    designation = req.getParameter("desg");
   	    application_id = req.getParameter("appId");
   	    doj = req.getParameter("cn");
   	    recruiter = req.getParameter("rect");
   	    placement_status = req.getParameter("pstatus");
 	   
   	    System.out.println(recruiter);
   	    
   	    try {
   	    	
   	    Statement st2 = con.createStatement();	
   	    	
   	    String List1 = "SELECT * FROM \"tektree\".\"hr_employee_placement\" WHERE 1=1";
   	    
   	    //Designation
   	    if( designation != ""){
   	    	List1 = List1 + "AND UPPER(full_name)=UPPER('"+designation+"')";
   	    }
   	    
   	    //application_id
   	    if( application_id != "" ){	
   	    	List1 = List1 + "AND UPPER(application_id)=UPPER('"+application_id+"')";
	    }
   	    
   	    //DOJ
   	    if( doj != null ){	
   	    	List1 = List1 + "AND doj="+doj+")";
	    }
   	    
   	    //recruiter
   	    if( recruiter != "" ){	
   	    	List1 = List1 + "AND UPPER(recruiter)=UPPER('"+recruiter+"')";
	    }
   	    
     	//placement_status
   	    if( placement_status != "" ){	
   	    	List1 = List1 + "AND UPPER(placement_status)=UPPER('"+placement_status+"')";
	    }
   	    
   	    //skill
   	    if( skill != "" ){	
   	    	List1 = List1 + "AND UPPER(skill)=UPPER('"+skill+"')";
	    }
   	    
   	    ResultSet rs=st2.executeQuery(List1);
	    
	    ResultSetMetaData rmd = rs.getMetaData();
	    int col_count = rmd.getColumnCount();
	    
	    RequestDispatcher rd1 = req.getRequestDispatcher("TT_Placement_list2.html");
	         rd1.include(req, res);
	    
	    boolean empty = true;
	        
	    if(rs != null) {
   	    	pw.println("<html><head><link href=\"Resources1.css\" rel = \"stylesheet\"/></head><body>"
   	    			+ "<form action=\"GetXsl\" method=\"post\"><table>");
   	    	int rowNum = 0;
   	    	//Displaying Column Names
   	    	//String file = "D:/MyFirstExcel.xlsx";
   	        //XSSFWorkbook workbook = new XSSFWorkbook();
 	       // XSSFSheet sheet = workbook.createSheet("Resources List");
   	    	pw.println("<tr>");
			for(int i=2;i<=col_count-5;i++) {
				pw.println("<th>" + rmd.getColumnName(i).toUpperCase() + "</th>");
				
			}
			pw.println("<td></td>");
			pw.println("</tr>");
			int in=1;
   	    	while(rs.next()) {
   	    		pw.println("<tr>");
   	    	 int count = rs.getRow();
			int a[] = new int[count+1];
   	    		for(int i=2;i<=col_count-5;i++) {
   					//Cell cell = row.createCell(colNum++);
   					pw.println("<td>" + rs.getString(i) + "</td>");
   				 
   					//cell.setCellValue(rs.getString(i));
   				}
   	    	 a[in] = rs.getInt("employee_id");
   	    		pw.println("<td><input type='submit' formaction='EditDeployment' target='_self' name="+a[in]+" value='Edit'>"
						+ "<input type='submit' formaction='ViewDeployment' target='_self' name="+a[in]+"  value='View'></td>");
   				pw.println("</tr>");
   			
   				empty = false;	
   				in=in+1;
   	    	}
   	    	//FileOutputStream outputStream = new FileOutputStream(file);
            //workbook.write(outputStream);
           // workbook.close();
   	    	pw.println("</table></form></body></form></html>");
 	    
 	      if(empty) {
 	    	  pw.println("<html><body><h3 style=\"margin-left:500px\"><font color=\"red\">No records found</h3></body></html>");
 	    	
 	      }
 	   
   	    }
	      
   	    }
   	 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
}
}
