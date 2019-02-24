
/****************************************************************************************
 *                          														    *														
 * Description	: This file describes the RunnerTxtFile class, with its variables, 	    *
 *                constructors and methods											    *
 *                                                                                      *
 ***************************************************************************************/

import java.util.*;
import java.io.*;
import java.nio.file.*;

public final class RunnerTxtFile implements RunnerDAO
	{
	    private ArrayList<Runner> Runners = null;
	    private Path RunnersPath = null;
	    private File RunnersFile = null;

	    private final String FIELD_SEP = "\t";

	    /** Construct a RunnerTxtFile object with the specified file */
	    public RunnerTxtFile(String filename)
	    {
	        //RunnersPath = Paths.get("D:/java_comp/final/src/FinalTextData.txt");
	    	RunnersPath = Paths.get(filename);
	        RunnersFile = RunnersPath.toFile();
	        Runners = this.getRunners();
	    }

	    
	    /************************************************************************************
		 * Method Name: getRunners   						         		                *
		 * Param	  : void																*
		 * Return Type: ArrayList<Runner>													*
		 * Definition : This Method reads all Runners stored in the file					*
		 *              into the array list and returns the array list.                     *				
		 *                                                                                  *
		 ***********************************************************************************/
	    public ArrayList<Runner> getRunners()
	    {
	        // if the Runners file has already been read, don't read it again
	        if (Runners != null)
	            return Runners;        

	        Runners = new ArrayList<>();        
	        
	        if (Files.exists(RunnersPath))  // prevent the FileNotFoundException
	        {
	            try (BufferedReader in = 
	                     new BufferedReader(
	                     new FileReader(RunnersFile)))
	            {
	                // read all Runners stored in the file
	                // into the array list
	                String line = in.readLine();
	                while(line != null)
	                {
	                    String[] columns = line.split(FIELD_SEP);
	                    String runnerName     = columns[0];	
	                    Double runnersSpeed   = Double.valueOf(columns[1]);
	                    Double restPercentage = Double.valueOf(columns[2]);

	                    Runner r = new Runner(runnerName, runnersSpeed, restPercentage);
	                    Runners.add(r);

	                    line = in.readLine();                    
	                }
	            }
	            catch(IOException e)
	            {
	                System.out.println(e);
	                return null;
	            }
	        }
	        return Runners;            
	    }
	    
	    
	    /************************************************************************************
		 * Method Name: getRunner   						         		                *
		 * Param	  : String																*
		 * Return Type: Runner          													*
		 * Definition : This Method returns Runner object associated with   				*
		 *              specified runner name. Returns null if runner does not exist.       *				
		 *                                                                                  *
		 ***********************************************************************************/
	    public Runner getRunner(String name)
	    {
	        for (Runner r : Runners)
	        {
	            if (r.getName().equals(name))
	                return r;
	        }
	        return null;
	    }
	    
	    
	    /************************************************************************************
		 * Method Name: addRunner   						         		                *
		 * Param	  : Runner																*
		 * Return Type:	boolean											                    *
		 * Definition : This Method adds specified runner to the arraylist      		    *
		 *              and writes modified arraylist into file.	                        *						
		 *                                                                                  *
		 ***********************************************************************************/
	    public boolean addRunner(Runner r)
	    {
	        Runners.add(r);
	        return this.saveRunners();
	    }
	    
	    
	    /************************************************************************************
		 * Method Name: deleteRunner   						         		                *
		 * Param	  : Runner																*
		 * Return Type: boolean													            *
		 * Definition : This Method deletes specified runner from the arraylist      		*
		 *              and writes modified arraylist into file.	                        *			
		 *                                                                                  *
		 ***********************************************************************************/
	    public boolean deleteRunner(Runner r)
	    {
	        Runners.remove(r);
	        return this.saveRunners();
	    }
	    
	    
	    /************************************************************************************
		 * Method Name: updateRunner   						         		                *
		 * Param	  : Runner																*
		 * Return Type: boolean																*
		 * Definition : This Method updates specified runner information and saves it 		*
		 *              into file.									                        *				
		 *                                                                                  *
		 ***********************************************************************************/
	    public boolean updateRunner(Runner newRunner)
	    {
	        // get the old Runner and remove it
	        Runner oldRunner = this.getRunner(newRunner.getName());
	        int i = Runners.indexOf(oldRunner);
	        Runners.remove(i);

	        // add the updated Runner
	        Runners.add(i, newRunner);

	        return this.saveRunners();
	    }
	    
	    /************************************************************************************
		 * Method Name: saveRunners   						         		                *
		 * Param	  : void																*
		 * Return Type: boolean																*
		 * Definition : This Method writes all Runners in the array list to the file.		*				
		 *                                                                                  *
		 ***********************************************************************************/
	    private boolean saveRunners()
	    {        
	    
		    try (PrintWriter out = new PrintWriter(
	                               new BufferedWriter(
	                               new FileWriter(RunnersFile))))
	        {

	            // write all Runners in the array list
	            // to the file
	            for (Runner r : Runners)
	            {
	                out.print(r.getName() + FIELD_SEP);
	                out.print(r.getRunnersSpeed() + FIELD_SEP);
	                out.println(r.getRestPercentage());
	            }
	        }
	        catch(IOException e)
	        {
	            System.out.println(e);
	            return false;
	        }

	        return true;
	    }	
	
}
