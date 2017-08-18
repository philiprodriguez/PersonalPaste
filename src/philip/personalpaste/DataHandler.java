package philip.personalpaste;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.HashMap;

public class DataHandler {
	public static final int MAX_PASTE_NAME_LENGTH = 100;
	public static final int MAX_PASTE_CONTENT_LENGTH = 1048576; // 1 MB
	public static final int MAX_PASTE_COUNT = 10000;
	
	private static File dataDirectory = null;
	
	public static synchronized void setDataDirectory(File f) throws Exception
	{
		if (!f.exists())
			throw new Exception("Proposed data directory does not exist!");
		if (!f.isDirectory())
			throw new Exception("Proposed data directory is not a directory!");
		dataDirectory = f;
	}
	
	public static synchronized File getDataDirectory()
	{
		return dataDirectory;
	}
	
	private static synchronized boolean isValidChar(char ch)
	{
		if (Character.isAlphabetic(ch))
			return true;
		if (Character.isDigit(ch))
			return true;
		if (ch == ' ')
			return true;
		return false;
	}
	
	public static synchronized void validateName(String name) throws Exception
	{
		if (name.length() < 1)
			throw new Exception("Name too short!");
		if (name.length() > MAX_PASTE_NAME_LENGTH)
			throw new Exception("Name too long!");
		for(int c = 0; c < name.length(); c++){
			if (!isValidChar(name.charAt(c)))
			{
				throw new Exception("Invalid name chars!");
			}
		}
	}
	
	public static synchronized void validateContent(String content) throws Exception
	{
		if (content.length() > MAX_PASTE_CONTENT_LENGTH)
			throw new Exception("Paste too large!");
	}
	
	public static synchronized void validateCount() throws Exception
	{
		if (getDataDirectory() == null)
			throw new Exception();
		if (getPasteCount() >= MAX_PASTE_COUNT)
		{
			throw new Exception("Data directory is full!");
		}
	}
	
	public static synchronized void setPaste(String name, String content) throws Exception
	{
		validateName(name);
		validateContent(content);
		validateCount();
		PrintWriter pw = new PrintWriter(new File(getDataDirectory().getAbsolutePath() + "/" + name + ".paste"));
		pw.print(content);
		pw.flush();
		pw.close();
	}
	
	public static synchronized String getPaste(String name) throws Exception
	{
		File paste = new File(getDataDirectory().getAbsolutePath() + "/" + name + ".paste");
		if (!paste.exists())
			throw new Exception("No such paste exists!");
		
		StringBuilder result = new StringBuilder();
		for(String line : Files.readAllLines(paste.toPath()))
			result.append(line + System.lineSeparator());
		
		return result.toString();
	}
	
	public static synchronized int getPasteCount()
	{
		if (getDataDirectory() == null)
			return -1;
		return getDataDirectory().listFiles().length;
	}
	
}
