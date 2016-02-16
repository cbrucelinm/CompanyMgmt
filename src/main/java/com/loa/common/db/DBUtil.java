package com.loa.common.db;

 	import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.Properties;
    



import com.heroku.sdk.jdbc.DatabaseUrl;
import com.loa.common.properties.PropertyUtil;
    
    public class DBUtil {
    	
    	static Properties prop = new Properties();
    	
    	static {
    		try {
    			//init();
    			
    		} catch (Exception e ) {
    			e.printStackTrace();
    		}
    	}
    	
    	public static void init() {
	    	try {
	    		prop = (new PropertyUtil("jdbc")).getProp();
	    		Class.forName(prop.getProperty("jdbc.driverClassName" ) );
	    	} catch ( Exception e) {
	    		e.printStackTrace();
	    	}
    	}
    	
    	public static Connection getConnection() {
    		try {
    			System.out.println("abc");
    			System.out.println("xyz : " + DatabaseUrl.extract().getConnection());
    			return DatabaseUrl.extract().getConnection();
				//return DriverManager.getConnection(prop.getProperty("jdbc.url" ), prop.getProperty("jdbc.username"), prop.getProperty("jdbc.password"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
    	}
    	
    	public  LoaDbStatement prepareStatement( String query) {
    		LoaDbStatement stmt = new LoaDbStatement();
    		try {
				stmt.setStmt(db.prepareStatement(query));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     		return stmt ;
    	}
    	
    
    	
    	
    	Connection db ;
    	
    	public DBUtil() {
    		 db = getConnection();
    	}
    	
    	public void beginTransaction() {
   		 try {
				db.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    	}
    	
    	public void commitTransaction() {
      		 try {
   				db.commit();
   			} catch (SQLException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}

       	}
    	
    	public void rollbackTransaction() {
     		 try {
  				db.rollback();
  			} catch (SQLException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}

      	}
    	
    	public void freeDbUtil() {
    		try {
    			if( db != null ) {
    				db.close();
    			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	
    
    
    }
 
