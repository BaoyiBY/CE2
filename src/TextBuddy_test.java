import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class TextBuddy_test{
	
	@Before
	public void setUp()
	{
		TextBuddy.createOrLoadFile(new String[] {"mytestingfile.txt"});
	}
	
	@Test
	public void testAdd()
	{
		String[] command = { "add", "EE2024 Assignment1" };
		String expected = "added to mytestingfile.txt: \"EE2024 Assignment1\"";
		assertEquals(expected, TextBuddy.commandExecution("mytestingfile.txt", command));		
	}
	
	@Test
	public void testAddEmptyInput()
	{
		String[] commands = {"" };
		String expected = "Invalid Command!";
		assertEquals(expected, TextBuddy.commandExecution("mytestingfile.txt", commands));
	}

	@Test 
	public void testDelete()
	{
		String[] addCommands = { "add", "EE2024 Assignment1" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommands);
		String[] addCommands2 = { "add", "jumped over the moon"};
		TextBuddy.commandExecution("mytestingfile.txt", addCommands2);
		
		String[] commands = { "delete", "2" };
		String expected = "deleted from mytestingfile.txt: \"jumped over the moon\"";
		assertEquals(expected, TextBuddy.commandExecution("  mytestingfile.txt", commands));
	}
	
	@Test
	public void testDeleteIndexOutOfBounce()
	{
		String[] addCommands = { "add", "EE2024 Assignment1" };
		TextBuddy.commandExecution("mytestfile.txt", addCommands);
		String[] addCommands2 = { "add", "jumped over the moon" };
		TextBuddy.commandExecution("mytestfile.txt", addCommands2);
		
		String[] command = { "delete", "5" };
		String expected = "This item do not exist!";
		assertEquals(expected, TextBuddy.commandExecution("mytestingfile.txt",command));
	}
	
	@Test
	public void testWrongCommand()
	{
		String[] addCommands = {"add", "EE2024 Assignment1" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommands);
		
		String[] command = { "del", "1"};
		String expected = "Invalid Command!";
		assertEquals(expected, TextBuddy.commandExecution("mytestingfile.txt", command));
	}
	
	//@Test
	//public void testDeleteEmptyInput()
	//{
		//String[] addCommands = { "add", "EE2024 Assignemnt1" };
		//TextBuddy.commandExecution("mytestingfile.txt", addCommands);
		//String[] addCommands2 = { "add", "jumped over the mood" };
		//TextBuddy.commandExecution("mytestingfile.txt", addCommands2);
		
		//String[] command = { "delete" };
		//String expected = "This is a invalid arguments!";
		//assertEquals(expected, TextBuddy.commandExecution("mytestingfile.txt", command));
	//}

	
			
	@Test
	public void testDisplay()
	{
		String[] addCommands = { "add", "EE2024 Assignment1" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommands);
		String[] addCommands2 = { "add", "jumped over the moon" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommands2);
		
		String[] command = { "display" };
		String expected = "1. EE2024 Assignment1\n2. jumped over the moon";
		assertEquals(expected, TextBuddy.commandExecution("mytestingfile.txt", command));
	}
	
	@Test 
	public void testClear()
	{
		String[] addCommands = { "add", "EE2024 Assignment1" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommands);
		String[] addCommands2 = { "add", "jumped over the moon" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommands2);
		
		String[] command = { "clear" };
		String expected = "all content deleted from mytestingfile.txt";
		assertEquals(expected, TextBuddy.commandExecution("mytestingfile.txt", command));
	}
	
	@Test
	public void testSearch()
	{
		String[] addCommands = { "add", "EE2024 Assignment1" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommands);
		String[] addCommand2 = { "add", "jumped over the moon" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommand2);
		
		String[] command = { "search", "j" };
		String expected = "2. jumped over the moon";
		assertEquals(expected, TextBuddy.commandExecution("mytestingfile.txt",command));
	}
	
	@Test
	public void testSearchItemDoNotExist()
	{
		String[] addCommands = { "add", "EE2024 Assignment1" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommands);
		String[] addCommand2 = { "add", "jumped over the moon" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommand2);
		
		String[] command = { "search", "1" };
		String expected = "No such item";
		assertEquals(expected, TextBuddy.commandExecution("mytestingfile.txt",command));
	}
	
	@Test
	public void testSort()
	{
		String[] addCommands = { "add", "EE2024 Assignment1" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommands);
		String[] addCommand2 = { "add", "jumped over the moon" };
		TextBuddy.commandExecution("mytestingfile.txt", addCommand2);
		
		String[] command = { "sort" };
		TextBuddy.commandExecution("mytestingfile.txt", command);
		String[] commandDisplay = { "display" };
		String expected = "1. EE2024 Assignment1\n2. jumped over the moon";
		assertEquals(expected, TextBuddy.commandExecution("mytestingfile.txt",commandDisplay));
	}
		
	//@After
	//public void tearDown()
	//{
		//String[] command = { "clear" };
		//TextBuddy.executeClear("mytestingfile.txt", command);
	//}
	
}