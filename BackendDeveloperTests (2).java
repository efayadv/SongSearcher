import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;

public class BackendDeveloperTests {
	
	private IterableSortedCollection<SongInterface> tree;
	
	/*
	 * Test the readData method's ability to read the songs.csv file
	 */
	@Test
	public void testReadData() {
		tree = new IterableRedBlackTree<SongInterface>();
		
		Backend backend = new Backend(tree);
		
		try {
			backend.readData("./songs.csv");
			Assertions.assertFalse(tree.isEmpty());
		} catch (IOException e) {
			e.printStackTrace();
			Assertions.fail();
		}
		
	}
	
	/*
	 * 
	 * test the method filterFastSongs by checking if the first element of the filtered list
	 * matches with the minimum required BPM for this list.
	 * 
	 */
	@Test
	public void testFilterFastSongs(){
		try {
			tree = new IterableRedBlackTree<SongInterface>();
                	Backend backend = new Backend(tree);
                	backend.readData("./songs.csv");
                	List<SongInterface> actualList = backend.filterFastSongs(100);
                	Assertions.assertEquals(100, actualList.get(0).getBPM());
		} catch (Exception e) {
			e.printStackTrace();
		//	Assertions.fail();		
		}
		
	}
	
	/*
	 * Test the fiveMostDanceable method and check if the fourth element filtered matches
	 * with the actual fourth most danceable song
	 * 
	 */ 
	@Test
	public void testMostDanceable(){
		try {
			tree = new IterableRedBlackTree<SongInterface>();
			Backend backend = new Backend(tree);
			backend.readData("./songs.csv");
			List<SongInterface> actualList = backend.fiveMostDanceable();
			//actualList = backend.fiveMostDanceable();
			Assertions.assertEquals("Bad Liar", actualList.get(4).getTitle());
		} catch (Exception e) {
			
		}	
	}
	/*
	 * Test the getRange method by inputing 0 and 20 as the range and check the number of elements
	 * filtered in that range
	 * 
	 */
	@Test
	public void testGetRange(){
		try {
			tree = new IterableRedBlackTree<SongInterface>();
			Backend backend = new Backend(tree);
			backend.readData("./songs.csv");
			List<SongInterface> list = backend.getRange(0, 20);
			Assertions.assertEquals(5, list.size());
		} catch (Exception e) {
			e.printStackTrace();
			//Assertions.fail();
		}
		
		
		
	}
	
	/*
	 * Test the getRange method after a minSpeed has been set by calling the filterFastSongs
	 * method first.
	 * 
	 */
	@Test
	public void testGetRange2() {
		try {
			tree = new IterableRedBlackTree<SongInterface>();
			Backend backend = new Backend(tree);
			backend.readData("./songs.csv");
			List<SongInterface> list = backend.getRange(40, 50);
			list = backend.filterFastSongs(150);
			Assertions.assertEquals(6, list.size());
		} catch (Exception e) {
			//Assertions.fail();
		}
	}

	/*
	 * Tests if the backend get respons to the users prompt to access songs between range.
	 */
	
	@Test
	public void testFrontendIntegration1() {
		tree = new IterableRedBlackTree<SongInterface>();
		Backend backend = new Backend(tree);
		
		//Scanner scan = new Scanner(System.in);
		TextUITester testR = new TextUITester("r\nsongs.csv");
		Scanner scan = new Scanner(System.in);
		//String command = scan.nextLine();
		//TextUITester testG = new TextUITester("G");
		Frontend frontend = new Frontend(scan, backend);
		frontend.readFile();
		//frontend.runCommandLoop();
		
		String output = testR.checkOutput();
		Assertions.assertTrue(output.contains("Enter path to csv file to load: "));		
		Assertions.assertTrue(output.contains("file successfully found"));		
		//Assertions.assertTrue(output.contains("2 songs found between 0 - 10"));
		//Assertions.assertTrue(output.contains("Million Years Ago"));
		//Assertions.assertTrue(output.contains("Start"));
		
	}

	/*
	 * Test the user input of an integer in the Filter fast songs method
	 */
	@Test
	public void testFrontendIntegration2() {
		tree = new IterableRedBlackTree<SongInterface>();
		Backend backend = new Backend(tree);
		
		try {
			backend.readData("./songs.csv");
		} catch(Exception e) {
			System.out.println("fail");
		}
		
		String user = "170";
		TextUITester text = new TextUITester(user);
		
		List<String> range = backend.getRange(0, 100);
		range = backend.filterFastSongs(Integer.parseInt(user));
		
		Assertions.assertFalse(range.isEmpty());
		
		
	}

	@Test
	public void partnerTest1(){
		IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree();
		BackendInterface backend = new BackendPlaceholder(tree);
		TextUITester tester = new TextUITester("r\nsongs.csv\nd");

		FrontendInterface frontend = new Frontend(new Scanner(System.in,"utf-8"), backend);
		frontend.runCommandLoop();	
		
		String output = tester.checkOutput();
		Assertions.assertTrue(output.contains("~~~ Command Menu ~~~"));		
		Assertions.assertFalse(output.contains("ERROR : Minimum and Maximum not specified"));
		

	}
	@Test
	public void partnerTest2(){
		IterableSortedCollection<SongInterface> tree = new ISCPlaceholder();
		BackendInterface backend = new BackendPlaceholder(tree);
		TextUITester tester = new TextUITester("r\nsongs.csv\nq");

		FrontendInterface fend = new Frontend(new Scanner(System.in,"utf-8"), backend);
		fend.runCommandLoop();	
		
		String output = tester.checkOutput();
		Assertions.assertTrue(output.contains("~~~ Command Menu ~~~"));		
		Assertions.assertTrue(output.contains("successfully"));

	}	
	
}
