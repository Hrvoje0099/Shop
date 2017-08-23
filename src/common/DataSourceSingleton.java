package common;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class DataSourceSingleton {
	
	private static DataSourceSingleton instance = null;
	private static DataSource dataSource;

    private DataSourceSingleton() {
    	initDataSource();
    }
    
    public static DataSourceSingleton getInstance() {
        if (instance == null) {
            synchronized(DataSourceSingleton.class) {
                if (instance == null) {
                    instance = new DataSourceSingleton();
                }
            }
        }
        return instance;
    }
    
    private void initDataSource() {
    	
		Properties props = new Properties();
		
		try (InputStream istream = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")) {
			if (istream == null) 
				throw new FileNotFoundException("Could not find JDBC properties");
			props.load(istream);
			dataSource = BasicDataSourceFactory.createDataSource(props);
			System.out.println("open datasource");

		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1, "GREŠKA", JOptionPane.ERROR_MESSAGE);
		}	
    }

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
    
}
