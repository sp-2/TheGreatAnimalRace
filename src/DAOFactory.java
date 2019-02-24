
/****************************************************************************************
 * 														                                *														
 * Description	: This file describes the DAOFactory class, with its methods 	    *
 *                                                                                      *
 ***************************************************************************************/

public class DAOFactory {
	
	/************************************************************************************
	 * Method Name: getRunnerDAO						         		                *
	 * Param	  : String																*
	 * Return Type: RunnerDAO															*
	 * Definition : This Method maps the RunnerDAO interface                            *
     *              to the appropriate data storage mechanism and returns RunnerDAO.    *				
	 *                                                                                  *
	 ***********************************************************************************/
    public static RunnerDAO getRunnerDAO(String strDatabase,String filename)
    {
    	RunnerDAO rDAO; 
    		switch (strDatabase) {
    		case "xml":
    			rDAO = new RunnerXMLFile(filename);
    			break;
    		case "derby":
    			rDAO = new RunnerDB();
    			break;
    		case "txt":
            default:
    			rDAO = new RunnerTxtFile(filename);
    		}
        return rDAO;
    }
}
