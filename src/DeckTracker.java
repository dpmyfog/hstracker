import java.io.*;
import java.util.*;
import java.awt.*;


public class DeckTracker {
	
	private static final String fileName = "decks.txt";
	private static int numDecks = 0;
	//COMMANDS
	private static final String EXIT = "exit";
	private static final String ADD_DECK = "add deck";
	private static final String DELETE_DECK = "delete deck";
	private static final String VIEW_DECK = "view deck";
	private static final String LIST_ALL_DECKS = "list all decks";
	private static final String LOG_WIN = "log win";
	private static final String LOG_LOSS = "log loss";
	private static final String VIEW_TOTAL_WR = "view total winrate";
	
	public static void main(String [] args) {
		//initialize file
		File decksFile = new File(fileName);
		numDecks = getNumDecks();
		//TODO: initialize AWT interface
		
		boolean done = false;
		Scanner scanner = new Scanner(System.in);
		String userInput = null;
		//main loop
		while(!done){
			System.out.println("Enter a command. Type \"exit\" to exit. Type \"help\" for a list of commands.");
			userInput = scanner.nextLine();
			if(userInput.equals(EXIT)){
				scanner.close();
				done = true;
			} else if(userInput.equals(ADD_DECK)){
				System.out.println("Add what deck?");
				userInput = scanner.nextLine();
				addDeck(decksFile, userInput);				
			} else if(userInput.equals(DELETE_DECK)){
				System.out.println("What deck?");
				userInput = scanner.nextLine();
				deleteDeck(userInput);
			} else if(userInput.equals(VIEW_DECK)) {
				System.out.println("What deck?");
				userInput = scanner.nextLine();
				System.out.println(viewDeck(userInput) + " with a win rate of " + getWinRate(userInput) + "%");
			} else if(userInput.equals(LIST_ALL_DECKS)) {
				System.out.println("All deck matchups: ");
				System.out.println(viewAllDecks());
			} else if(userInput.equals(LOG_WIN)){
				System.out.println("What deck?");
				userInput = scanner.nextLine();
				addWin(userInput);
			} else if(userInput.equals(LOG_LOSS)) {
				System.out.println("What deck?");
				userInput = scanner.nextLine();
				addLoss(userInput);
			} else {
				System.out.println("Command not found. Please enter a valid command.");
			}
		}
		
		
	}
	
	public static String viewDeck(String deckName){
		return readLine(findDeck(deckName));
	}
	
	public static int getNumDecks(){
		return Integer.parseInt(readLine(1));
	}
	
	public static int getNumWins(String deckName){
		int deckLine = findDeck(deckName);
		String line = readLine(deckLine);
		return Integer.parseInt(line.substring(line.indexOf(",") + 2, line.indexOf(",") + 3));
	}
	
	public static int getNumLosses(String deckName){
		int deckLine = findDeck(deckName);
		String line = readLine(deckLine);
		return Integer.parseInt(line.substring(line.indexOf("/") + 1, line.indexOf("/") + 2));
	}
	
	public static double getWinRate(String deckName){
		double losses = (double) getNumLosses(deckName);
		double wins = (double) getNumWins(deckName);
		
		return (double)((int)((wins/(wins+losses))*10000))/100;
		
	}
	
	public static String getDeckName(int line){
		//TODO: parse the deck name on the line number @var
		return "";
	}
	
	public static double getTotalWinLoss(){
		//Use getNumDecks and getDeckName to parse through the document and calculate the total win loss
		return 0.0;
	}
	
	public static String viewAllDecks(){
		String result = "";
		for(int i = 2; i <= numDecks + 1; i++) {
			result += readLine(i) + "\n";
		}
		return result;
	}
	
	/**
	 * 
	 * @param deckName the name of the deck to find
	 * @return an integer representing the line number in which the deck was found
	 */
	public static int findDeck(String deckName){
		
		for(int i = 2; i <= numDecks + 1; i++){
			String line = readLine(i);
			if(line == null) return -1;
			if(line.substring(0, line.indexOf(",")).equals(deckName)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 
	 * @param file: the file to which to activate the deck
	 * @param deckName: the name of the deck to add
	 * 
	 * The method will not add the deck if the deck name is already in the list
	 */
	public static void addDeck(File file, String deckName){
		if(findDeck(deckName) != -1){
			System.out.println("Deck already exists. No action was taken.");
		}
		else{
			String deckString = "";
			deckString += deckName + ", 0/0";
			appendLine(file, deckString);
			numDecks++;
			rewriteLine(1, numDecks + "\n");
		}
	}
	
	/**
	 * @param deckName The name of the deck to be deleted.
	 * 
	 */
	public static void deleteDeck(String deckName){
		int deckLine = findDeck(deckName); 
		if(deckLine == -1){
			System.out.println("Deck could not be found. No action was taken.");
		}
		else{
			numDecks--;
			rewriteLine(deckLine, "");
			rewriteLine(1,numDecks + "\n");
		}
		
	}
	
	public static void addWin(String deckName){
		int deckLine = findDeck(deckName); 
		if(deckLine == -1){
			System.out.println("Deck could not be found. No action was taken.");
		} else {
			int wins = getNumWins(deckName) + 1;
			int losses = getNumLosses(deckName);
			//reconstruct the line now
			String line = readLine(deckLine);
			String result = line.substring(0, 1 + line.indexOf(",")) + " " + wins + "/" + losses + "\n";
			rewriteLine(deckLine, result);
			System.out.println("Praise Yogg! Your win rate is now: " + getWinRate(deckName) + "% over " + (wins+losses) + " games.");
		}
	}
	
	public static void addLoss(String deckName){
		int deckLine = findDeck(deckName); 
		if(deckLine == -1){
			System.out.println("Deck could not be found. No action was taken.");
		} else {
			int wins = getNumWins(deckName);
			int losses = getNumLosses(deckName) + 1;
			//reconstruct the line now
			String line = readLine(deckLine);
			String result = line.substring(0, 1 + line.indexOf(",")) + " " + wins + "/" + losses + "\n";
			rewriteLine(deckLine, result);
			System.out.println("Rip in pepperonis. Your win rate is now: " + getWinRate(deckName) + "% over " + (wins+losses) + " games.");
		}
	}
	
	public static String readLine(int lineNumber){
		if(lineNumber == -1){
			return "line not found";
		}
		String line = null;
		try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            for(int i = 1; i < lineNumber; i++){
            	bufferedReader.readLine();
            }        
            line = bufferedReader.readLine();
            bufferedReader.close();       
        } catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");           
        } catch(IOException ex) {
        	ex.printStackTrace();                  	
        }
		return line;
	}
	
	
	/**
	 * 
	 * @param file The file to have the line appended
	 * @param msg The message to append to the end of the file
	 * 
	 * Appends the message msg to the end of file.
	 */
	public static void appendLine(File file, String msg){
		try{
		    PrintWriter writer = new PrintWriter(new FileWriter(file, true));
		    writer.println(msg);
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void rewriteLine(int lineNumber, String newLine){
		String result = "";
		for(int i = 1; i < lineNumber; i++){
			//copy all lines up until the line we wish to rewrite
			result += readLine(i) + "\n";
		} 
		result += newLine;
		int i = lineNumber + 1;
		while(readLine(i) != null){
			result += readLine(i) + "\n";
			i++;
		}
		//Copying lines is now complete	
		//Now we want to rewrite the whole document with the altered line
		try{
		    PrintWriter writer = new PrintWriter(new FileWriter(new File(fileName), false));
		    writer.print(result);
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		
	}
}
