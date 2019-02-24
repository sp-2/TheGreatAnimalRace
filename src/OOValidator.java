/****************************************************************************************														
 * Description	: This file describes the the OOValidator class, with its               *
 *                methods								                                *
 *                                                                                      *
 ***************************************************************************************/


import java.util.Scanner;

public class OOValidator {
	
	//get int with prompt from user
	public static int getInt(Scanner sc, String prompt){
			int i = 0;
			boolean isValid = false;
			while (isValid == false)
			{
				System.out.print(prompt);
				if (sc.hasNextInt())
				{
					i = sc.nextInt();
					isValid = true;
				}
				else
				{
					System.out.println("Error! Invalid integer value. Try again.");
				}
				sc.nextLine();  // discard any other data entered on the line
			}
			return i;	
	}
	
	//get int within specified range
	public static int getIntWithinRange(Scanner sc, String prompt, int min, int max){
		int i = 0;
		boolean isValid = false;
		while (isValid == false)
		{
			i = getInt(sc,prompt);
			if (i < min)
				System.out.println(
					"Error! Number must be greater than " + min);
			else if (i > max)
				System.out.println(
					"Error! Number must be less than " + max);
			else
				isValid = true;
		}
		return i;
		
	}
	
	//get double with prompt from user
	public static double getDouble(Scanner sc, String prompt){
		double d = 0;
		boolean isValid = false;
		while (isValid == false)
		{
			System.out.print(prompt);
			if (sc.hasNextDouble())
			{
				d = sc.nextDouble();
				isValid = true;
			}
			else
			{
				System.out.println("Error! Invalid decimal value. Try again.");
			}
			sc.nextLine();  // discard any other data entered on the line
		}
		return d;
	}
	
	//get double within specified range
	public static double getDoubleWithinRange(Scanner sc, String prompt, double min, double max){
		double d = 0;
		boolean isValid = false;
		while (isValid == false)
		{
			d = getDouble(sc,prompt);
			if (d < min)
				System.out.println(
					"Error! Number must be greater than " + min);
			else if (d > max)
				System.out.println(
					"Error! Number must be less than " + max);
			else
				isValid = true;
		}
		return d;
	}
	
	//get string with prompt from user
	public static String getRequiredString(Scanner sc, String prompt){
		
		String s = "";
		boolean isValid = false;
		while (isValid == false)
		{
			System.out.print(prompt);
			s = sc.nextLine();
			if (s.equals(""))
				System.out.println(
					"Error! You did not enter anything!");
			else
				isValid = true;
		}
	
		return s;
	}
	
	//get string from user from choice given
	public static String getChoiceString(Scanner sc, String prompt, String s1, String s2){
		String s = "";
		boolean isValid = false;
		while (isValid == false)
		{
			s = getRequiredString(sc,prompt);
			if (!(s.equalsIgnoreCase(s1) || s.equalsIgnoreCase(s2)))
				System.out.println(
					"Error! Entry must be " + s1+ " or "+s2);
			else
				isValid = true;
		}
		return s;		
	}

}
