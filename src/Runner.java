/****************************************************************************************
 * Author       : Soumya Pillai														    *														
 * Description	: This file describes the the Runner class, with its variables, 	    *
 *                constructors and methods											    *
 *                                                                                      *
 ***************************************************************************************/
public class Runner {
		private String  runnerName;	
		private Double  runnersSpeed;
		private Double  restPercentage;
		
		/** default constructor */
		Runner(){
			runnerName     = "";	
			runnersSpeed   = 0.0;
			restPercentage = 0.0;		
		}
		
		/** Construct a Runner object with the specified runner information in String array */
		Runner(String[] runnerInfo){
			runnerName     = runnerInfo[0];	
			runnersSpeed   = Double.valueOf(runnerInfo[1]);
			restPercentage = Double.valueOf(runnerInfo[2]);		
		}
		
		/** Construct a Runner object with the specified runner information */
		Runner(String runnerName, Double  runnersSpeed, Double  restPercentage){
			this.runnerName     = runnerName;	
			this.runnersSpeed   = runnersSpeed;
			this.restPercentage = restPercentage;		
		}
		
		/** getter for Runner name */
		String getName(){
			return runnerName;
		}

		/** getter for runnersSpeed */
		Double getRunnersSpeed(){
			return runnersSpeed;
		}
		
		/** getter for restPercentage */
		Double getRestPercentage(){
			return restPercentage;
		}
		
		/** setter for runners name */
		void setName(String  runnersName){
			this.runnerName   = runnersName;
		}
		
		/** setter for runnersSpeed */
		void setRunnersSpeed(Double  runnersSpeed){
			this.runnersSpeed   = runnersSpeed;
		}
		
		/** setter for restPercentage */
		void setRestPercentage(Double  restPercentage){
			this.restPercentage   = restPercentage;
		}
		
}

