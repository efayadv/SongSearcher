import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Scanner;
import java.util.List;

public class FrontendDeveloperTests{
	@Test
	public void test1(){
		IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree();
		BackendInterface backend = new Backend(tree);
		TextUITester tester = new TextUITester("r\nsongs.csv\nq");

		FrontendInterface fend = new Frontend(new Scanner(System.in,"utf-8"), backend);
		fend.runCommandLoop();	
		
		String output = tester.checkOutput();
		Assertions.assertTrue(output.contains("~~~ Command Menu ~~~"));		
		Assertions.assertFalse(output.contains("ERROR"));
		Assertions.assertTrue(output.contains("successfully"));

	}

	@Test
	public void test2(){
		IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree();
                BackendInterface backend = new Backend(tree);
                TextUITester tester = new TextUITester("r\nsongs.csv\ng\n0 - 20\nq");

                FrontendInterface fend = new Frontend(new Scanner(System.in), backend);
                fend.runCommandLoop();

                String output = tester.checkOutput();
                Assertions.assertTrue(output.contains("~~~ Command Menu ~~~"));
                Assertions.assertFalse(output.contains("ERROR"));
                Assertions.assertTrue(output.contains("songs found between"));
		Assertions.assertTrue(output.contains("Say Something"));
	}

	@Test
	public void test3(){
		IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree();
                BackendInterface backend = new Backend(tree);
                TextUITester tester = new TextUITester("r\nsongs.csv\ng\n80 - 100\nf\n180\nq");

                FrontendInterface fend = new Frontend(new Scanner(System.in), backend);
                fend.runCommandLoop();

                String output = tester.checkOutput();
                Assertions.assertTrue(output.contains("~~~ Command Menu ~~~"));
                Assertions.assertFalse(output.contains("ERROR"));
                Assertions.assertTrue(output.contains("BPM or faster:"));
		Assertions.assertTrue(output.contains("Brave"));
		Assertions.assertTrue(output.contains("How Ya Doin'? (feat. Missy Elliott)"));
	}


	@Test
	public void test4(){
		IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree();
                BackendInterface backend = new Backend(tree);
                TextUITester tester = new TextUITester("r\nsongs.csv\ng\n80 - 100\nf\n170\nd\nq");

                FrontendInterface fend = new Frontend(new Scanner(System.in), backend);
                fend.runCommandLoop();

                String output = tester.checkOutput();
                Assertions.assertTrue(output.contains("~~~ Command Menu ~~~"));
                Assertions.assertFalse(output.contains("ERROR"));
                Assertions.assertTrue(output.contains("Five Most Danceable found"));
		Assertions.assertTrue(output.contains("82:Antisocial (with Travis Scott)"));

	}

	@Test
	public void test5(){
		IterableSortedCollection<SongInterface> tree = new IterableRedBlackTree();
                BackendInterface backend = new Backend(tree);
                TextUITester tester = new TextUITester("r\nsongs.csv\nd\nq");

                FrontendInterface fend = new Frontend(new Scanner(System.in), backend);
                fend.runCommandLoop();

                String output = tester.checkOutput();
                Assertions.assertTrue(output.contains("~~~ Command Menu ~~~"));
                Assertions.assertTrue(output.contains("ERROR"));	

	}
	
	@Test
	public void integrationTest1(){
		BackendInterface back = new Backend(new IterableRedBlackTree());
	       	try{
                        back.readData("songs.csv");
                }catch(Exception e){
                        Assertions.assertFalse(true);
                }
		List<String> songs = back.getRange(90,100);
		Assertions.assertTrue(songs.size() == 46);
		Assertions.assertTrue(songs.contains("Rich Boy"));
		Assertions.assertTrue(songs.contains("Hello"));
		Assertions.assertFalse(songs.contains("Hey, Soul Sister"));
		Assertions.assertFalse(songs.contains("Love Me Again"));
	
	}

	@Test
	public void integrationTest2(){
	BackendInterface back = new Backend(new IterableRedBlackTree());
                try{
			back.readData("songs.csv");
		}catch(Exception e){
			Assertions.assertFalse(true);
		}
		List<String> songs = back.getRange(90,100);
		List<String> songss = back.filterFastSongs(130);
                Assertions.assertTrue(songss.size() == 13);
                Assertions.assertTrue(songss.contains("How Ya Doin'? (feat. Missy Elliott)"));
                Assertions.assertTrue(songss.contains("Part Of Me"));
                Assertions.assertFalse(songss.contains("I Like It"));
                Assertions.assertFalse(songss.contains("Booty"));
		Assertions.assertFalse(songss.contains("My Way"));
		Assertions.assertFalse(songss.contains("Hey, Soul Sister"));		
	
	}

	@Test
	public void partnerTest1(){
	BackendInterface back = new Backend(new IterableRedBlackTree());
		try{
			back.readData("songs.csv");
		}catch(Exception e){
			Assertions.assertFalse(true);
		}
		List<String> songs = back.getRange(80, 100);
		List<String> songss = back.filterFastSongs(170);
		List<String> topFive = back.fiveMostDanceable();
		Assertions.assertTrue(topFive.size() == 5);
		Assertions.assertTrue(topFive.contains("Despacito - Remix"));
		Assertions.assertTrue(topFive.contains("Brave"));
		
	}
	

	@Test
	public void partnerTest2(){
	
	BackendInterface back = new Backend(new IterableRedBlackTree());
                try{
                        back.readData("songs.csv");
                }catch(Exception e){
                        Assertions.assertFalse(true);
                }
                List<String> songs = back.getRange(0, 20);
                List<String> songss = back.filterFastSongs(100);
                List<String> topFive = back.fiveMostDanceable();
                Assertions.assertTrue(topFive.size() == 3);
                Assertions.assertTrue(topFive.contains("Start"));
                Assertions.assertTrue(topFive.contains("Not About Angels"));
	}

}


