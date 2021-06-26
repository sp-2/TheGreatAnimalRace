
/******************************************************************************************
 * Author       : Soumya Pillai														      *														
 * Description	: This file describes the the RunnerThread class, which extends the       *    
 *                Thread class, and the implementation of its constructor and run method. *
 *                                                                                        *
 ******************************************************************************************/

	public class RunnerThread extends Thread {
		private Runner runner;
		
		/** Construct a RunnerThread object with the specified runner information */
	 /*   public RunnerThread(String runnerName, Double  runnersSpeed, Double  restPercentage)
	    {
	        this.runner = new Runner(runnerName, runnersSpeed, restPercentage);
	        super.setName(runnerName);
	    }*/
		
		 public RunnerThread(ThreadGroup group, String runnerName, Double  runnersSpeed, Double  restPercentage)
		    {
			    super(group,runnerName);
		        this.runner = new Runner(runnerName, runnersSpeed, restPercentage);    
		    }
	    
	    /** Implementation of run method from the Thread class */
		   public void run() {
			  //get current thread	
			   Thread ct = Thread.currentThread();
			   
			   //set initial distance to 0
			   int distance=0;
			   
			   System.out.println(runner.getName() + " started.");
		        
			   //increment distance as long as thread is not interrupted, distance<1000, 
			   //and rest percentage condition is satisfied
		       while(distance<1000 && !ct.isInterrupted()){
		        try
		        {           
		            Thread.sleep(100);    
		       
		        	if((int)(1+Math.random() * 101) >= runner.getRestPercentage()  && !ct.isInterrupted()){//run
		        		distance+=runner.getRunnersSpeed();
		        	    System.out.println(runner.getName() + " "+ distance);
		        			}
		        }
		        catch(InterruptedException e) {
		        	 //System.out.println(runner.getName() + " : You beat me fair and square. I only ran "+ distance+" miles."
		        	 System.out.println(runner.getName() + " : You beat me fair and square.");
		        	 return;
		        }
		        }

		      //have runner declare that he has finished and call finished function in MainApp 
		      if(distance>=1000){
		    	  System.out.println(runner.getName() + " : I finished!");
		    	  MainApp.finished(ct);
		      }

		   }
		}
