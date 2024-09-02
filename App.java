import java.util.Scanner;

/**
 * Main entry point for running the iSongify app.
 */
public class App {
    public static void main(String[] args) {
	IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree<>(); 
	BackendInterface backend = new Backend(tree); //it was new backendplaceholder before

//        Scanner in = new Scanner(System.in, "utf-8");
	Scanner in = new Scanner(System.in);
	FrontendInterface frontend = new Frontend(in,backend);
//	Scanner in = new Scanner(System.in, "utf-8");
//	FrontendInterface frontend = new Frontend(in,backend);
	

	System.out.println("Welcome to iSongify");
	System.out.println("===================");
	
	frontend.runCommandLoop();

	System.out.println();
	System.out.println("===================");
	System.out.println("Thanks, and Goodbye");
    }
}
