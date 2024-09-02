import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class Frontend extends FrontendPlaceholder {
	private int min = -1;
	private int max = -1;
	private int speed = -1;
	private String filename = null;
	Scanner scnr;
	BackendInterface backend;
	public Frontend(Scanner in, BackendInterface backend) {
		this.backend = backend;
		this.scnr = in;

	}

     /**
     * Repeated gives the user an opportunity to issue new commands until
     * they select Q to quit.
     */
	public void runCommandLoop(){

		do{
			displayMainMenu();
			String input = scnr.nextLine().trim();
			char inputChar = input.charAt(0);
			switch(Character.toUpperCase(inputChar)){
				case 'R':
					readFile(scnr);
					break;
				case 'G':
					getValues(scnr);
					break;
				case 'F':
					setFilter();
					break;
				case 'D':
					topFive();
					break;
				case 'Q':
					return;
				default :
					System.out.println("Invalid input");
					break;		
			}

		}while(true);
	}

	public void displayMainMenu() {
		
		String menu = """
	    
	    ~~~ Command Menu ~~~
	        [R]ead Data
	        [G]et Songs by Energy [Min - Max]
	        [F]ilter Fast Songs (by Min Speed: speed)
	        [D]isplay Five Most Danceable
	        [Q]uit
	    Choose command:""";

	System.out.printf(menu + " ");
    }

    /**
     * Provides text-based user interface and error handling for the 
     * [R]ead Data command.
     */
	public void readFile(Scanner scnr) {
		System.out.print("Enter path to csv file to load: ");
		filename = scnr.nextLine().trim();
		try {
			backend.readData(filename);
		}catch (IOException e){
			System.out.println(e.getMessage());
			filename = null;
			return;
		}
		System.out.println("file successfully found");
		return;
	}
    /**
     * Provides text-based user interface and error handling for the 
     * [G]et Songs by Energy command.
     */
	public void getValues(Scanner scnr){
		if(filename == null){
			System.out.println("ERROR : File not read");
			return;
		}

		System.out.print("Enter range of values (MIN - MAX): ");
		String minMax = scnr.nextLine().trim();
		String result[] = minMax.split("-");
		
		if(result.length != 2){
			System.out.println("ERROR : Invalid input value (MIN - MAX)");
			return;
		}
		min = Integer.parseInt(result[0].trim());
		max = Integer.parseInt(result[1].trim());	
		List<String> songList = backend.getRange(min, max);
		
		// check songlist is empty
		if(songList.isEmpty()){
			System.out.println("ERROR : No songs found");
			return;
		}
		String output = songList.size() + " songs found between " + min + " - " +  max + "\n";
		for(int i = 0; i < songList.size(); i++){
			output += songList.get(i) + "\n";
		}
		System.out.print(output);
	}


    /**
     * Provides text-based user interface and error handling for the 
     * [F]ilter Fast Songs (by Min Speed) command.
     */
	public void setFilter(){
		if(filename == null){
			System.out.println("ERROR : File not read");
                	return;
                }

		System.out.print("Enter minimum speed: ");	
		String input = scnr.nextLine().trim();
		speed = Integer.parseInt(input);
		
		List<String> songList = backend.filterFastSongs(speed);
		
		if(songList.isEmpty()){
			System.out.println("ERROR : Range not specified");
			return;
		}
		System.out.printf("%d songs found between %d - %d at %d BMP or faster:", songList.size(), min, max, speed);	
		for(int i = 0; i < songList.size(); i++){
			System.out.println(songList.get(i));	
		}
		return;
	}

	
    /**
     * Provides text-based user interface and error handling for the 
     * [D]isplay Five Most Danceable command.
     */
	public void topFive(){	
		if(filename == null){
			System.out.println("ERROR : file not read");
			return;
		}

		if(min == -1 || max == -1){
			System.out.println("ERROR : Minimum and Maximum not specified");
			return;
		}
		List<String> songs = backend.fiveMostDanceable();
		String output = "Top Five Most Danceable found between " + min + " - " + max + " at 85 BMP or faster:";
		if(songs.isEmpty()){
			System.out.println("ERROR : No songs found");
			return;
		}
		System.out.println(output);
		for(int i = 0; i < songs.size(); i++){
	 		System.out.println(songs.get(i));	
		}
		return;
		
	}

}
