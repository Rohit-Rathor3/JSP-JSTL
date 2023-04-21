package com.nt.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.nt.model.Employee;

public class EmployeeDAOImpl implements IEmployeeDAO {
	private static final String GET_EMPS_BY_DESGS="SELECT EMPNO,ENAME,JOB,SAL FROM EMP WHERE  JOB=?";
	

	@Override              //getEmpByDesg
	public List<Employee> getEmpsByDesg(String desg)throws Exception {
		List<Employee> list=null;
		try( //get Pooled connection
				Connection con=getPooledConnection();
				//create PreparedStatement object
			   PreparedStatement ps=con.prepareStatement(GET_EMPS_BY_DESGS)){
		
		   //set Query param value
		   ps.setString(1, desg);
		   //execute the query
		   try(ResultSet rs=ps.executeQuery()){
			     //convert  RS object record List of Employee objs
			    list=new ArrayList();
			    while(rs.next()) {
			    	Employee  e=new Employee();
			    	e.setEno(rs.getInt(1));
			    	e.setEname(rs.getString(2));
			    	e.setDesg(rs.getString(3));
			    	e.setSalary(rs.getFloat(4));
			    	list.add(e);
			    }//while
		   }//try2
		   
		}//try1
		catch(SQLException se) {
			se.printStackTrace();
			throw se;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}//method
	
	
	//prviate are taken as helper methods.
	private  Connection   getPooledConnection() throws Exception {
		Connection con=null;
		Statement st=null;

			//register JDBC driver by loading JDBC driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","System","Rohit123");
			System.out.println("Connection Created");
			return con;
			
	}//method
}
