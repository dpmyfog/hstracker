import java.io.*;
import java.awt.*;


public class DeckTracker {
	
	private static final String fileName = "decks.txt";
	
	public static void main(String [] args) {
		//initialize file
		File decksFile = new File(fileName);
		
		//initialize AWT interface
		
		
	}
	
	public static String readLine(int lineNumber){
		String line = null;
		try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            for(int i = 1; i < lineNumber; i++){
            	bufferedReader.readLine();
            }        
            line = bufferedReader.readLine();
            bufferedReader.close();       
        }
		
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
        	ex.printStackTrace();                   	
        }
		return line;
	}
	
	public static void writeLine(File file, String msg){
		try{
		    PrintWriter writer = new PrintWriter(new FileWriter(file, true));
		    writer.println(msg);
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	

}
