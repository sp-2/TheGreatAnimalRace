
/**********************************************************************************************
 * 																					          *														
 * Description	: This file describes the interface iRunnerReader                       	  *
 *                and its variables	and methods												  *
 *                                                                                            *
 **********************************************************************************************/

import java.util.ArrayList;
public interface iRunnerReader {
	Runner getRunner(String code);
    ArrayList<Runner> getRunners();

}
