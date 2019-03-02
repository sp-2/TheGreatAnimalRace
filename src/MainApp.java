
/****************************************************************************************
 * @Author       : Soumya Pillai														    *														
 * Description	: This file describes the user interface, with its  	                *
 *                variables and methods.	            							    *
 *                                                                                      *
 ***************************************************************************************/

import java.util.ArrayList;
import java.util.Scanner;

public class MainApp implements iRunnerConstants {
	// declare class variables
	private static final ArrayList<Thread> RunnerThreadList = new ArrayList<Thread>();
	private static ThreadGroup group 	= new ThreadGroup("new Group");
    	private static RunnerDAO runnerDAO 	= null;
    	private static Scanner sc 		= null;
    	private static Thread main 		= null;
	public static void main(String[] args) {
		 //Thread main = Thread.currentThread();

		main = Thread.currentThread();
		
		//create object for scanner
		sc  =  new Scanner(System.in);

		//initialize choice setting
		int choice=0;	
		
		while(choice!=5){
			//display welcome message to the user
			System.out.println("\nWelcome to the Marathon Race Runner Program.");
			System.out.println();	
			
			//as long as user wants to continue, display main menu to user
			displayMainMenu();
			
			//get validated menu choice from user
			choice = OOValidator.getIntWithinRange(sc,"Enter your choice: ", 1, 5);
		
			switch (choice){
        		case 1: runMarathon("derby"); 		break; //get runner statistics from derby database
        		case 2: runMarathon("xml");   		break; //get runner statistics from xml file
        		case 3: runMarathon("txt");   		break; //get runner statistics from text file
        		case 4: runMarathonWithDefault();   break; //get runner statistics from hard-coded settings
        		case 5:	System.out.println("Thank you for using my Marathon Race Program"); break;          
			}
		}
	}
	
	
    /************************************************************************************
	 * Method Name: finished   						         		                    *
	 * Param	  : Thread																*
	 * Return Type: void													            *
	 * Definition : This Method declares the winner and interrupts all the 				*
	 *              other runners.								                        *				
	 *                                                                                  *
	 ***********************************************************************************/
	public static void finished(Thread ct){
		System.out.println("\nThe race is over!\nThe "+ct.getName() + " is the winner.\n");
		for(Thread t:RunnerThreadList){
			t.interrupt();
		}
		
		//for(Thread t:RunnerThreadList){
		//	try{
		//	t.join();
		//	}
		//	catch(InterruptedException e) {}
			
		//}
		//group.interrupt();

		main.interrupt();
		//main.notify();
	}
	
	
    /************************************************************************************
	 * Method Name: displayMainMenu   						         		            *
	 * Param	  : void																*
	 * Return Type: void													            *
	 * Definition : This Method displays the main menu.									*
	 *                                                                                  *
	 ***********************************************************************************/
	public static void displayMainMenu(){
		System.out.println("\nSelect your data source:");
		System.out.println("1. Derby database");
		System.out.println("2. XML file");
		System.out.println("3. Text file");
		System.out.println("4. Default two runners");
		System.out.println("5. Exit");
		System.out.println();
	}
	
	
    /************************************************************************************
	 * Method Name: displayMenu   						         		                *
	 * Param	  : String																*
	 * Return Type: void													            *
	 * Definition : This Method displays menu to view or modify runner database.		*				
	 *                                                                                  *
	 ***********************************************************************************/
	public static void displayMenu(String databaseType){
		System.out.println("COMMAND MENU (Connected to a \"" + databaseType + "\" database)\n");
		System.out.println("list    - List all runners");
		System.out.println("add     - Add a runner");
		System.out.println("updt    - Update a runner");
		System.out.println("del     - Delete a runner");
		System.out.println("help    - Show this menu");
		System.out.println("exit    - Exit this application\n");
	 }
	
	
    /************************************************************************************
	 * Method Name: updateDatabase   						         		            *
	 * Param	  : String																*
	 * Return Type: void													            *
	 * Definition : This Method calls appropriate methods to view or   		            *
	 *              modify database based on user request.                              *				
	 *                                                                                  *
	 ***********************************************************************************/
	public static void updateDatabase(String databaseType){
		String sel = "";
		while (!sel.equalsIgnoreCase("exit"))
			{
				displayMenu(databaseType);
				// get the input from the user
				sel = OOValidator.getRequiredString(sc,"Enter a command: ");
				System.out.println();

				if (sel.equalsIgnoreCase("list"))
					displayAllRunners();
				else if (sel.equalsIgnoreCase("add"))
					addRunner();
				else if (sel.equalsIgnoreCase("updt"))
					updateRunner();
				else if (sel.equalsIgnoreCase("del"))
					deleteRunner();
				else if (sel.equalsIgnoreCase("help"))
					displayMenu(databaseType);
				else if (sel.equalsIgnoreCase("exit"))
					System.out.println("Bye.\n");
				else
					System.out.println("Error! Not a valid command.\n");
			}
	}
	
	
    /************************************************************************************
	 * Method Name: displayAllRunners   						         		        *
	 * Param	  : void																*
	 * Return Type: void													            *
	 * Definition : This Method displays statistics of all the runners. 				*				
	 *                                                                                  *
	 ***********************************************************************************/
	public static void displayAllRunners(){
        System.out.println("\nRunner Statistics:\n");

        ArrayList<Runner> runners = runnerDAO.getRunners();
        Runner r = null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < runners.size(); i++)
        {
            r = runners.get(i);
            sb.append(StringUtils.padWithSpaces(r.getName(), NAME_SIZE + 4));
            sb.append(r.getRunnersSpeed());
            sb.append("\t");
            sb.append(r.getRestPercentage());
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

	
    /************************************************************************************
	 * Method Name: addRunner   						         		                *
	 * Param	  : void																*
	 * Return Type: void												             	*
	 * Definition : This Method adds a new runner into the database.              		*				
	 *                                                                                  *
	 ***********************************************************************************/
    public static void addRunner(){
        String name = OOValidator.getRequiredString(sc,"Enter Runners Name: ");
        double RunnersSpeed = OOValidator.getDouble(sc,"Enter Runners Speed: ");
        double RestPercentage = OOValidator.getDoubleWithinRange(sc,"Enter Rest Percentage: ",1,100);

        Runner runner = new Runner(name,RunnersSpeed,RestPercentage);
        
        runnerDAO.addRunner(runner);

        System.out.println();
        System.out.println(name + " has been added.\n");
    }

    
    /************************************************************************************
	 * Method Name: deleteRunner   						         		                *
	 * Param	  : void																*
	 * Return Type: void												            	*
	 * Definition : This Method deletes the runner from the database. 				    *			
	 *                                                                                  *
	 ***********************************************************************************/
    public static void deleteRunner(){
        String name = OOValidator.getRequiredString(sc,"Enter runner name to delete: ");

        Runner r = runnerDAO.getRunner(name);

        System.out.println();
        if (r != null) //no runner is trapped here
        {
            runnerDAO.deleteRunner(r);
            System.out.println(r.getName() + " has been deleted.\n");
        }
        else
        {
            System.out.println("No runner matches that name.\n");
        }
    }
    
    
    /************************************************************************************
	 * Method Name: updateRunner   						         		                *
	 * Param	  : void																*
	 * Return Type: void													            *
	 * Definition : This Method updates statistics of a runner in the database. 	    *				
	 *                                                                                  *
	 ***********************************************************************************/
    public static void updateRunner(){
        String name = OOValidator.getRequiredString(sc,"Enter runner name to update: ");

        Runner r = runnerDAO.getRunner(name);

        System.out.println();
        if (r != null) //no runner is trapped here
        {
        	double RunnersSpeed = OOValidator.getDouble(sc,"Enter Runners Speed: ");
            double RestPercentage = OOValidator.getDoubleWithinRange(sc,"Enter Rest Percentage: ",1,100);

            r.setRunnersSpeed(RunnersSpeed);
            r.setRestPercentage(RestPercentage);
            
            runnerDAO.updateRunner(r);
            System.out.println(r.getName() + " has been updated.\n");
        }
        else
        {
            System.out.println("No runner matches that name.\n");
        }
    }
    
    
    /************************************************************************************
	 * Method Name: runRace   						         		                    *
	 * Param	  : void																*
	 * Return Type: void													            *
	 * Definition : This Method starts the race.					                    *				
	 *                                                                                  *
	 ***********************************************************************************/
    public static void runRace(){
        //get all runners into array list
    	//ThreadGroup group = new ThreadGroup("new Group");
        ArrayList<Runner> runners = runnerDAO.getRunners();
        Runner r = null;

        for (int i = 0; i < runners.size(); i++)//for each runner,
        {
            r = runners.get(i); //get runner and runner information
            String name = r.getName();
            double speed =  r.getRunnersSpeed();
            double percent = r.getRestPercentage();
            Thread t2 = new RunnerThread(group, name, speed, percent);  //create new RunnerThread    	
        	t2.start();// start the thread
        	
        	RunnerThreadList.add(t2); //add thread into another array list
        }
    	
    }
    
    
    /************************************************************************************
	 * Method Name: runMarathon   						         		                *
	 * @Param	  : void																*
	 * @Return Type: void													            *
	 * Definition : This Method, based on user selection, either updates the database 	*
	 *              with new user information or runs the race from specified database.	*				
	 *                                                                                  *
	 ***********************************************************************************/
    public static void runMarathon(String databaseType){
    	String filename = "";
    	if(databaseType.equals("txt") || databaseType.equals("xml")){ 
    		filename = OOValidator.getRequiredString(sc,"Enter " + databaseType + " file name: ");
    	}
        runnerDAO = DAOFactory.getRunnerDAO(databaseType,filename);
    	displayAllRunners();
    	String modify = OOValidator.getChoiceString(sc,"Do you want to modify information in file: "+filename+". y/n? ","y","n");
    	System.out.println();
    	
        // display the command menu
        if(modify.equals("y")){
        	updateDatabase(databaseType);
        }
        else{
        	runRace();
        	try{ 
        		Thread.sleep(30000);}
        	 	catch(InterruptedException e) {}
        	}
    }
    
    
    /************************************************************************************
	 * Method Name: runMarathonWithDefault   						         		    *
	 * Param	  : void																*
	 * Return Type: void													            *
	 * Definition : This Method runs the marathon with default settings. 				*				
	 *                                                                                  *
	 ***********************************************************************************/
    public static void runMarathonWithDefault(){
    	Thread t2 = new RunnerThread(group, "Tortoise", 10.0, 0.0);          	
    	t2.start();// start the IO thread

    	Thread t3 = new RunnerThread(group, "Hare", 100.0, 90.0);
    	t3.start();// start the IO thread
    	
    	RunnerThreadList.add(t2);
    	RunnerThreadList.add(t3);
    	try{ 
    		Thread.sleep(20000);
    	}
    	 catch(InterruptedException e) {}
    
    }
}

