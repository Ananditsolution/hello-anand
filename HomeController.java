package com.journaldev.spring.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;


import com.journaldev.spring.model.Customers;
import com.journaldev.spring.model.Dao;
import com.journaldev.spring.model.OrderDao;
import com.journaldev.spring.model.Regitration;
import com.journaldev.spring.model.User;



import com.journaldev.spring.model.QueryOfApplication;
import com.journaldev.spring.model.Product;
import com.journaldev.spring.model.Orders;


@RestController
//@Controller

public class HomeController {
//	Connection con;
	//ResultSet rs;
	@Autowired
	OrderDao db;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String user(@Validated User user, Model model) {
		System.out.println("User Page Requested");
		model.addAttribute("userName", user.getUserName());
		return "user";
	}
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	@ResponseBody
	public Regitration reg(HttpServletRequest re) throws SQLException, ClassNotFoundException {
	//public ModelAndView registration(@Validated Regitration reg, Model model) {
		System.out.println("Registration Page Requested");
		Regitration r=new Regitration();
		
		Dao d=new Dao();
	    ResultSet rs=d.connn().executeQuery("select *from registration");
		//QueryOfApplication qa= new QueryOfApplication();
		//qa.Show();
		
       while(rs.next())
       {
    	  r.setUser_name(rs.getString(1));
    	  r.setPassword(rs.getString(2));
    	  r.setConpassord(rs.getString(3));
       }
      /*
		QueryOfApplication qa= new QueryOfApplication();
		try {
	   if(con==null) {
	    qa.Show();
        while(qa.Show().next()) ;
        {
        	r.setUser_name(qa.Show().getString(1));
        	r.setPassword(qa.Show().getString(2));
        	r.setConpassord(qa.Show().getString(3));
        	
        	System.out.println("++++++++++++++++++"+qa.Show().getString(2)+qa.Show().getString(1)+qa.Show().getString(3));
        	
        }
	   
		}
	   else if(con!=null)
	   {
		   qa.Show().close();
	   }
	   
		
	  }
       catch(SQLException sel)
       {
    	  // con.close();
       }
		finally
		{
			qa.Show().close();
		}
        
	    //}
	   // else if(con!=null)
	    //{
	    	//con.close();
	   // }*/
        return r;
	}
	@RequestMapping(value = "/registired", method = RequestMethod.GET)
	@ResponseBody
	public Regitration update(HttpServletRequest re2) throws ClassNotFoundException, SQLException{
	//public ModelAndView registration(@Validated Regitration reg, Model model) {
		System.out.println("Registred Page Requested");
		Regitration r=new Regitration();
	    
	 //   QueryOfApplication qa= new QueryOfApplication(); 
		//Class.forName("com.mysql.jdbc.Driver");
		Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection con=DriverManager.getConnection(  
	    		"jdbc:mysql://localhost:3306/test","root","root");
	   String query="update registration SET password=? WHERE user_name=?";
	    PreparedStatement ptmt = con.prepareStatement(query);
	   
         String uname=re2.getParameter("uname");
	     String pass=re2.getParameter("pass");
	     //String conpass=re2.getParameter("conpass");
	     ptmt.setString(1, pass);;
	     ptmt.setString(2, uname);;
	   //  ptmt.setString(3, conpass);

	    ptmt.executeUpdate();
		
		return r;
	
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ResponseBody
	public Regitration delete(HttpServletRequest response) throws SQLException, ClassNotFoundException {
	//public ModelAndView registration(@Validated Regitration reg, Model model) {
		System.out.println("Registration Page Requested");
		Regitration r=new Regitration();
		//String uname=response.getParameter("user_name");
	   // Dao a=new Dao();
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/test","root","root");
		PreparedStatement ptmt = con.prepareStatement("delete from registration where user_name=?;");

		String uname=response.getParameter("uname");	
       //QueryOfApplication qa= new QueryOfApplication();
		
		ptmt.setString(1,uname);
		ptmt.executeUpdate();
		}
		 catch(Exception e){ System.out.println(e);}
		return r;
		
		
}
	@RequestMapping(value = "/alldata", method = RequestMethod.GET)
	@ResponseBody
	public Customers showall(HttpServletRequest re4) throws SQLException, ClassNotFoundException {
		//public ModelAndView registration(@Validated Regitration reg, Model model) {
			System.out.println("Registration Page Requested");
			Customers cus=new  Customers();
			ArrayList<String> list=new ArrayList<String>();
			   Dao d=new Dao();
			   ResultSet rs=d.connn().executeQuery("select *from registration");

			  while(rs.next()) 
	        {
			 list.add(rs.getString(1));
			 list.add(rs.getString(2));
			 list.add(rs.getString(3));
	    	}
	        
	        
	        cus.setList(list);
	        return cus;
		}
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	@ResponseBody
	public Regitration insert(HttpServletRequest re5) throws SQLException, ClassNotFoundException {
	
		
			    Regitration  r1=new Regitration();
			    
			    Class.forName("com.mysql.cj.jdbc.Driver");
				//Class.forName("com.mysql.jdbc.Driver");  

				Connection con=DriverManager.getConnection(  
				"jdbc:mysql://localhost:3306/test","root","root");  
				PreparedStatement ptmt=con.prepareStatement("insert into registration values(?,?,?)");
				String uname=re5.getParameter("uname");
			    String pass=re5.getParameter("pass");
			    String conpass=re5.getParameter("conpass");
			    ptmt.setString(1, uname);
			    ptmt.setString(2, pass);
			    ptmt.setString(3, conpass);
			    ptmt.executeUpdate();
			    
				return r1;
  
			
		}
	
	@RequestMapping(value = "/il", method = RequestMethod.POST)
	public Regitration in(@Validated Regitration r, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("User Page Requested");
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Class.forName("com.mysql.jdbc.Driver");  

		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/test","root","root");  
	//	PreparedStatement ptmt=con.prepareStatement("insert into registration values(?,?,?)");
	   String sql=	"insert into registration(user_name,password,conpassord) "
	   		+ "values('"+r.getUser_name()+"','"+r.getPassword()+"','"+r.getConpassord()+"')";
	   PreparedStatement ptmt=con.prepareStatement(sql);
	   ptmt.executeUpdate();
      // model.addAttribute("uname", r.getUser_name());
	   model.addAttribute(r);
	   
		return r;
	
      }
	
	@RequestMapping(value = "/di", method = RequestMethod.DELETE)
	 public Regitration din(@Validated Regitration r1, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("User Page Requested");
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Class.forName("com.mysql.jdbc.Driver");  

		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/test","root","root");  
	//	PreparedStatement ptmt=con.prepareStatement("insert into registration values(?,?,?)");
		 String sql=	"delete from registration where(user_name)"
		   		+ "=('"+r1.getUser_name()+"')";
		 PreparedStatement ptmt=con.prepareStatement(sql);
		 ptmt.executeUpdate();
		 model.addAttribute(r1);

		return r1;
	
	}
	/*@RequestMapping(value = "/pi", method = RequestMethod.PUT)
	public Regitration pin(@RequestBody Regitration r1, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("User Page Requested");
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Class.forName("com.mysql.jdbc.Driver");  

		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/test","root","root"); 
	
		//String sql=	"update registration set(password)"+"=('"+r1.getPassword()+"')"+"where(user_name)"+"=('"+r1.getUser_name()+"')";
		String query="update registration SET password='"+r1.getPassword()+"' WHERE user_name='"+r1.getUser_name()+"'";
		   		
		 PreparedStatement ptmt=con.prepareStatement(query);
		 ptmt.executeUpdate();
		 model.addAttribute(r1);
		 
		return r1;

}*/
@PostMapping(value="/ilmm")		
	//@RequestMapping(value = "/ilmm", method = RequestMethod.POST)
	public Regitration inll(@RequestBody Regitration r, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("User Page Requested");
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Class.forName("com.mysql.jdbc.Driver");  

		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/test","root","root");  
	//	PreparedStatement ptmt=con.prepareStatement("insert into registration values(?,?,?)");
	   String sql=	"insert into registration(user_name,password,conpassord) "
	   		+ "values('"+r.getUser_name()+"','"+r.getPassword()+"','"+r.getConpassord()+"')";
	   PreparedStatement ptmt=con.prepareStatement(sql);
	   ptmt.executeUpdate();
      // model.addAttribute("uname", r.getUser_name());
	   model.addAttribute(r);
	   
		return r;

}
	/*@RequestMapping(value = "/dill", method = RequestMethod.DELETE)
	public Regitration lldin(@RequestBody Regitration r1, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("User Page Requested");
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Class.forName("com.mysql.jdbc.Driver");  

		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/test","root","root");  
	//	PreparedStatement ptmt=con.prepareStatement("insert into registration values(?,?,?)");
		String sql=	"delete from registration where(user_name)"
		   		+ "=('"+r1.getUser_name()+"')";
		 PreparedStatement ptmt=con.prepareStatement(sql);
		 ptmt.executeUpdate();
		 model.addAttribute(r1);

		return r1;
	
	}*/
	@PostMapping(value = "/orders{MediaType.APPLICATION_XML_VALUE}" )
	public  void OrdersCreate (@RequestBody Orders d, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("Order create Page Requested");
		/*Class.forName("com.mysql.cj.jdbc.Driver");
		Class.forName("com.mysql.jdbc.Driver");  */

	//	Connection con=DriverManager.getConnection(  
	//	"jdbc:mysql://localhost:3306/test","root","root");  
		/*PreparedStatement ptmt=con.prepareStatement("insert into registration values(?,?,?)");
		java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
	    d.setDatecreated(sqlDate.toString());
	    d.setId(d.getIdcount());
	  String sql=	"insert into orders(usernane,id,datecreated,updt,item,quantity,state,changedby) "
	   		+ "values('"+d.getUsernane()+"','"+d.getId()+"','"+d.getDatecreated()+"','"+d.getUpdt()+"','"+d.getItem()+"','"+d.getQuantity()+"','"+d.getState()+"','"+d.getChangedby()+"')";
	   PreparedStatement ptmt=con.prepareStatement(sql);
	   ptmt.executeUpdate();
       model.addAttribute("uname", r.getUser_name());
	   model.addAttribute(d);*/
		db.saveOrder(d);
	
	   

	}
	@PutMapping(value = "/orders{MediaType.APPLICATION_XML_VALUE}" )
	public void OrderAccept (@RequestBody Orders d, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("Orders Accept Page Requested");
		/*Class.forName("com.mysql.cj.jdbc.Driver");
		Class.forName("com.mysql.jdbc.Driver");  

		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/test","root","root"); 
         String t="open";
	     String t1="inprogress";
	     String t2="complete";
	   // String s1=d.setState(rs.getString(7));
	// String s2  = (rs.getString(7));
 //System.out.println("****************"+s1);
  /*  Dao a=new Dao();
    ResultSet rs=a.connn().executeQuery("select *from orders");
   while(rs.next()) {
   if(t.equalsIgnoreCase(d.setState(rs.getString(7)))){
	  
	   
	     //String sql=	"update registration set(password)"+"=('"+r1.getPassword()+"')"+"where(user_name)"+"=('"+r1.getUser_name()+"')";
		 String query="update orders SET state='"+t1+"'  WHERE id='"+d.getId()+"'";
		   	
		 PreparedStatement ptmt=con.prepareStatement(query);
		 ptmt.executeUpdate();
		 model.addAttribute(d);
	    }
  
	  else if(t1.equalsIgnoreCase(d.setState(rs.getString(7))))
	   {
		   String query="update orders SET state='"+t2+"'  WHERE id='"+d.getId()+"'";
	   		
			PreparedStatement ptmt=con.prepareStatement(query);
			 ptmt.executeUpdate();
		    model.addAttribute(d);
	   }
  }*/
	     db.deleteOrder(d);
 
	
}
/*	@PutMapping(value = "/orders{MediaType.APPLICATION_XML_VALUE}" )
	public void OrderComplete (@RequestBody Orders d, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("User Page Requested");
		Class.forName("com.mysql.cj.jdbc.Driver");
		//Class.forName("com.mysql.jdbc.Driver");  

		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/test","root","root"); 
	
		//String sql=	"update registration set(password)"+"=('"+r1.getPassword()+"')"+"where(user_name)"+"=('"+r1.getUser_name()+"')";
		 String query="update orders SET state='"+d.getState()+"' WHERE id='"+d.getId()+"' && username='"+d.getUsernane()+"'";
		   		
		 PreparedStatement ptmt=con.prepareStatement(query);
		 ptmt.executeUpdate();
		 model.addAttribute(d);
		 
	
	}*/
	@DeleteMapping(value = "/orders{MediaType.APPLICATION_XML_VALUE}" )

	public void OrderClose(@RequestBody  Orders d, Model model) throws ClassNotFoundException, SQLException {
		System.out.println("Orders close Page Requested");
		/*Class.forName("com.mysql.cj.jdbc.Driver");
		Class.forName("com.mysql.jdbc.Driver");  

		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/test","root","root");  
      PreparedStatement ptmt=con.prepareStatement("insert into registration values(?,?,?)");
		String sql=	"delete from orders where(id)"
		   		+ "=('"+d.getId()+"')";
		 PreparedStatement ptmt=con.prepareStatement(sql);
		 ptmt.executeUpdate();
		 model.addAttribute(d);*/
		 db.deleteOrder(d);
 
	
	}


	@GetMapping(value = "/orders" )

  //  public  Orders OrderComplete(@PathVariable("id") int id) throws ClassNotFoundException, SQLException {
    public Orders OrderComplete( Orders d1, Model model) throws ClassNotFoundException, SQLException{ 
	//public ModelAndView registration(@Validated Regitration reg, Model model) {
		System.out.println("Orders complete Page Requested");
		ArrayList<String> list=new ArrayList<String>();
	//   Orders d1=new Orders();
	   Dao a=new Dao();
	    ResultSet rs=a.connn().executeQuery("select *from orders");
	
		
       while(rs.next())
       {
    	   
    	  /*d1.setUsernane(rs.getString(1));
    	  d1.setId(rs.getInt(2));
    	  d1.setDatecreated(rs.getString(3));
    	  d1.setUpdt(rs.getString(4));
    	  d1.setItem(rs.getString(5));
    	  d1.setQuantity(rs.getInt(6));
    	  d1.setState(rs.getString(7));
    	  d1.setChangedby(rs.getString(8));*/
    	   list.add(rs.getString(1));
    	   list.add(rs.getString(2));
    	   list.add(rs.getString(3));
    	   list.add(rs.getString(4));
    	   list.add(rs.getString(5));
    	   list.add(rs.getString(6));
    	   list.add(rs.getString(7));
    	   list.add(rs.getString(8));
    	   }
       d1.setList(list);
  	 
	return d1;
       
}
	@RequestMapping(value = "/up", method = RequestMethod.GET)
    @ResponseBody
    public Product uploadResources( HttpServletRequest servletRequest )
    {
        //Get the uploaded files and store them
    	Product product=new Product();
        List<MultipartFile> files = product.getImages();
        List<String> fileNames = new ArrayList<String>();
       // String path=servletRequest.getParameter("path");
        if (null != files && files.size() > 0)
        {
            for (MultipartFile multipartFile : files) {
 
                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);
                String path=servletRequest.getServletPath();
                File imageFile = new File(path, fileName);
               
                
                
                try
                {
                    multipartFile.transferTo(imageFile);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

     }
		return product;	
    }


}