
/****************************************************************************************
 *                          														    *														
 * Description	: This file describes the RunnerDB class, with its methods.	 	        *
 *                                                                                      *
 ***************************************************************************************/

import java.util.*;
import java.sql.*;

public class RunnerDB  implements RunnerDAO
{
	
	
    /************************************************************************************
	* Method Name: connect   			    			         		                *
	* Param	     : void																    *
	* Return Type: Connection       													*
	* Definition : This Method connects to the database                                 *
	*              and returns the Connection object.                                   *				
	*                                                                                   *
	*************************************************************************************/
    private Connection connect()
    {
        Connection connection = null;
        try
        {
            String dbDirectory = "resources";
            System.setProperty("derby.system.home", dbDirectory);

            String url = "jdbc:derby:SoumyaDB";
            String user = "";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e)
        {
            System.err.println("Error loading database driver: " + e);
        }
        return connection;
    }

    
    /************************************************************************************
	* Method Name: getRunners   						         		                *
	* Param	     : void																    *
	* Return Type: ArrayList<Runner>													*
	* Definition : This Method reads all Runners stored in the database				    *
	*              into the array list and returns the array list.                      *				
	*                                                                                   *
	*************************************************************************************/
    public ArrayList<Runner> getRunners()
    {
        try
        {
            Connection connection = connect();
            ArrayList<Runner> products = new ArrayList<Runner>();

            String query = "SELECT runnerName, runnersSpeed, restPercentage "
                         + "FROM Runners ORDER BY runnerName ASC";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                String runnerName = rs.getString("runnerName");
                double runnersSpeed = rs.getDouble("runnersSpeed");
                double restPercentage = rs.getDouble("restPercentage");

                Runner r = new Runner(runnerName, runnersSpeed, restPercentage);
                products.add(r);
            }
            rs.close();
            ps.close();
            connection.close();
            return products;
        }
        catch(SQLException sqle)
        {
            //sqle.printStackTrace();  // for debugging
            return null;
        }
    }

    
    /************************************************************************************
	 * Method Name: getRunner   						         		                *
	 * Param	  : String																*
	 * Return Type: Runner          													*
	 * Definition : This Method returns Runner object associated with   				*
	 *              specified runner name. Returns null if runner does not exist.       *				
	 *                                                                                  *
	 ***********************************************************************************/
    public Runner getRunner(String runnerName)
    {
        try
        {
            Connection connection = connect();
            String selectRunner =
                "SELECT runnerName, runnersSpeed, restPercentage " +
                "FROM Runners " +
                "WHERE runnerName = ?";
            PreparedStatement ps = connection.prepareStatement(selectRunner);
            ps.setString(1, runnerName);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                double runnersSpeed = rs.getDouble("runnersSpeed");
                double restPercentage = rs.getDouble("restPercentage");
                Runner r = new Runner(runnerName, runnersSpeed, restPercentage);
                rs.close();
                ps.close();
                connection.close();
                return r;
            }
            else
                return null;
        }
        catch(SQLException sqle)
        {
            //sqle.printStackTrace();   // for debugging
            return null;
        }
    }

    
    /************************************************************************************
	 * Method Name: addRunner   						         		                *
	 * Param	  : Runner																*
	 * Return Type:	boolean											                    *
	 * Definition : This Method adds specified runner to the database.      		    *						
	 *                                                                                  *
	 ***********************************************************************************/
    public boolean addRunner(Runner r)
    {
        try
        {
            Connection connection = connect();
            String insert =
                "INSERT INTO Runners (runnerName, runnersSpeed, restPercentage) " +
                "VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(insert);
            ps.setString(1, r.getName());
            ps.setDouble(2, r.getRunnersSpeed());
            ps.setDouble(3, r.getRestPercentage());
            ps.executeUpdate();
            ps.close();
            connection.close();
            return true;
        }
        catch(SQLException sqle)
        {
            //sqle.printStackTrace();   // for debugging
            return false;
        }
    }

    
    /************************************************************************************
	 * Method Name: deleteRunner   						         		                *
	 * Param	  : Runner																*
	 * Return Type: boolean													            *
	 * Definition : This Method deletes specified runner from the database.      		*			
	 *                                                                                  *
	 ***********************************************************************************/  
    public boolean deleteRunner(Runner r)
    {
        try
        {
            Connection connection = connect();
            String delete =
                "DELETE FROM Runners " +
                "WHERE runnerName = ?";
            PreparedStatement ps = connection.prepareStatement(delete);
            ps.setString(1, r.getName());
            ps.executeUpdate();
            ps.close();
            connection.close();
            return true;
        }
        catch(SQLException sqle)
        {
            //sqle.printStackTrace();   // for debugging
            return false;
        }
    }

    
    /************************************************************************************
	 * Method Name: updateRunner   						         		                *
	 * Param	  : Runner																*
	 * Return Type: boolean																*
	 * Definition : This Method updates specified runner information in the database.   *				
	 *                                                                                  *
	 ***********************************************************************************/
    public boolean updateRunner(Runner r)
    {
        try
        {
            Connection connection = connect();
            String update =
                "UPDATE Runners SET " +
                    "runnersSpeed = ?, " +
                    "restPercentage = ? " +
                "WHERE runnerName = ?";
            PreparedStatement ps = connection.prepareStatement(update);
            ps.setDouble(1, r.getRunnersSpeed());
            ps.setDouble(2, r.getRestPercentage());
            ps.setString(3, r.getName());
            ps.executeUpdate();
            ps.close();
            connection.close();
            return true;
        }
        catch(SQLException sqle)
        {
            //sqle.printStackTrace();   // for debugging
            return false;
        }
    }
}