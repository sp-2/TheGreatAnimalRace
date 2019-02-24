
/****************************************************************************************
 *                          														    *														
 * Description	: This file describes the RunnerXMLFile class, with its variables, 	    *
 *                constructors and methods											    *
 *                                                                                      *
 ***************************************************************************************/

import java.util.*;
import java.io.*;
import java.nio.file.*;
import javax.xml.stream.*;  // StAX API

public class RunnerXMLFile implements RunnerDAO
{
    private Path runnersPath = null;
    private ArrayList<Runner> runners = null;

    /** Construct a RunnerXMLFile object with the specified file */
    public RunnerXMLFile(String filename)
    {
        //runnersPath = Paths.get("D:/java_comp/final/src/runners.xml");
    	runnersPath = Paths.get(filename);
        runners = this.getRunners();
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
        // create the XMLOutputFactory object
        XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        try
        {
            // create XMLStreamWriter object
            FileWriter fileWriter =
                new FileWriter(runnersPath.toFile());
            XMLStreamWriter writer =
                outputFactory.createXMLStreamWriter(fileWriter);

            //write the runners to the file
            writer.writeStartDocument("1.0");
            writer.writeStartElement("Runners");
            for (Runner r : runners)
            {
                writer.writeStartElement("Runner");
                writer.writeAttribute("Name", r.getName());

                writer.writeStartElement("RunnersSpeed");
                double runnersSpeed = r.getRunnersSpeed();
                writer.writeCharacters(Double.toString(runnersSpeed));
                writer.writeEndElement();
                
                writer.writeStartElement("RestPercentage");
                double restPercentage = r.getRestPercentage();
                writer.writeCharacters(Double.toString(restPercentage));
                writer.writeEndElement();

                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.flush();
            writer.close();
        }
        catch (IOException | XMLStreamException e)
        {
            System.out.println(e);
            return false;
        }
        return true;
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
        // if the XML file has already been read, don't read it again
        if (runners != null)
            return runners;        

        runners = new ArrayList<>();        
        Runner r = null;        
        if (Files.exists(runnersPath))  // prevent the FileNotFoundException
        {
            // create the XMLInputFactory object
            XMLInputFactory inputFactory = XMLInputFactory.newFactory();
            try
            {
                // create a XMLStreamReader object
                FileReader fileReader =
                    new FileReader(runnersPath.toFile());
                XMLStreamReader reader =
                    inputFactory.createXMLStreamReader(fileReader);

                // read the runners from the file
                while (reader.hasNext())
                {
                    int eventType = reader.getEventType();
                    switch (eventType)
                    {
                        case XMLStreamConstants.START_ELEMENT:
                            String elementName = reader.getLocalName();
                            if (elementName.equals("Runner"))
                            {
                                r = new Runner();
                                String name = reader.getAttributeValue(0);
                                r.setName(name);
                            }
                            if (elementName.equals("RunnersSpeed"))
                            {
                                String runnersSpeed_str = reader.getElementText();
                                double runnersSpeed = Double.parseDouble(runnersSpeed_str);
                                r.setRunnersSpeed(runnersSpeed);
                            }
                            if (elementName.equals("RestPercentage"))
                            {
                                String restPercentage_str = reader.getElementText();
                                double restPercentage = Double.parseDouble(restPercentage_str);
                                r.setRestPercentage(restPercentage);
                            }
                            break;
                        case XMLStreamConstants.END_ELEMENT:
                            elementName = reader.getLocalName();
                            if (elementName.equals("Runner"))
                            {
                                runners.add(r);
                            }
                            break;
                        default:
                            break;
                    }
                    reader.next();
                }
            }
            catch (IOException | XMLStreamException e)
            {
                System.out.println(e);
                return null;
            }
        }
        return runners;
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
        for (Runner r : runners)
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
        runners.add(r);
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
        runners.remove(r);
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
        // get the old runner and remove it
        Runner oldRunner = this.getRunner(newRunner.getName());
        int i = runners.indexOf(oldRunner);
        runners.remove(i);

        // add the updated runner
        runners.add(i, newRunner);

        return this.saveRunners();
    }
}