package nl.ru.ai.jcc99;

public class Util
{
	private static int numberOfErrors=0;

	public static void error(String format, Object ... args )
	{
		String message=String.format(format, args);
		System.err.println(message);
		numberOfErrors++;
	}
	
	public static boolean foundErrors()
	{
		return numberOfErrors!=0;
	}

}
