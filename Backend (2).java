import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;

public class Backend<T extends Comparable> implements BackendInterface {
	
	private IterableSortedCollection<SongInterface> tree;
	private int low;
	private int high;
	private int minSpeed;
	private List<String> titles = new ArrayList<>();
	private boolean getRangeCalled = false;
	private List<SongInterface> filteredSongs = new ArrayList<>();
	private List<SongInterface> filteredFastSongs = new ArrayList<>();
	
	
	public Backend(IterableSortedCollection<SongInterface> tree) {
		low = -1;
		high = -1;
		minSpeed = -1;
		this.tree = tree;
	}
	
	/**
     * Loads data from the .csv file referenced by filename.
     * @param filename is the name of the csv file to load data from
     * @throws IOException when there is trouble finding/reading file
     */
	public void readData(String filename) throws IOException{
				
		 // create a Scanner using try-with-resources, so the Scanner will always close when finished
	    try (Scanner scnr = new Scanner(new File(filename))) {
	      // remove the first header line
	      if (scnr.hasNextLine()) {
	        scnr.nextLine();
	      }
	 
	      // get data from every line
	      while (scnr.hasNextLine()) {
	        // get the line of data for one song
	        String data = scnr.nextLine();
	 
	        // keep track of the number of quotation marks found
	        int numQuotes = 0;
	        // keep track of the beginning character index of the field
	        int begIndex = 0;
	        // keep track of the field number
	        int fieldNum = 0;
	        // create an ArrayList to store the fields
	        ArrayList<Object> dataFields = new ArrayList<Object>();
	 
	        // check each character, determining where to split up the String
	        for (int i = 0; i < data.length(); i++) {
	          // count the number of quotation marks
	          if (data.charAt(i) == '"') {
	            numQuotes++;
	          }
	          // determine whether to split at the comma
	          if (data.charAt(i) == ',') {
	            // only split if an even number of quotation marks have been found
	            if (numQuotes % 2 == 0) {
	              String field = data.substring(begIndex, i);
	 
	              // change any double quotation marks to single quotation marks
	              field.replaceAll("\"\"", "\"");
	              // remove any beginning or ending quotation marks
	              if (field.charAt(0) == '"' && field.charAt(field.length() - 1) == '"') {
	                // only include indices 1 to field.length() - 2
	                field = field.substring(1, field.length() - 1);
	              }
	 
	              // parse the field as an int, if the field number is 3 or greater (we have reached the
	              // numerical data fields)
	              if (fieldNum >= 3) {
	                int num = Integer.parseInt(field);
	                // add the int to the list of fields
	                dataFields.add(num);
	              } else {
	                // add the String to the list of fields
	                dataFields.add(field);
	              }
	 
	              // update the field number
	              fieldNum++;
	              // update the begIndex to be the index of the character after the comma
	              begIndex = i + 1;
	 
	            }
	          }
	          // if the end of the data has been reached, then the final field is between the begIndex
	          // and final index
	          if (i == data.length() - 1) {
	            String field = data.substring(begIndex, data.length());
	 
	            // parse the field as an int since the last field is numerical
	            int num = Integer.parseInt(field);
	            // add the int to the list of fields
	            dataFields.add(num);
	          }
	        } // end of for each character
	 
	        // use ArrayList entries to make a Song object
	        String title = (String) dataFields.get(0);
	        String artist = (String) dataFields.get(1);
	        String genre = (String) dataFields.get(2);
	        int year = (Integer) dataFields.get(3);
	        int bpm = (Integer) dataFields.get(4);
	        int nrgy = (Integer) dataFields.get(5);
	        int dnce = (Integer) dataFields.get(6);
	        int dB = (Integer) dataFields.get(7);
	        int live = (Integer) dataFields.get(8);
	   
	 
	        // create the song object
	        SongInterface song = new Song(title, artist, genre, year, bpm, nrgy, dnce, dB, live);
	 
	        // add the song to the tree
	        this.tree.insert(song);
	        titles.add(song.getTitle());
	      } // end of while scnr.hasNextLine()
	    }
	    // catch possible exceptions caused by issues with the data file
	    catch (NullPointerException e) { // thrown by File if filename is null
	      throw new IOException("The filename was null: " + e.getMessage());
	    } catch (FileNotFoundException e) { // thrown by Scanner if the file is invalid
	      throw new IOException("There was trouble reading the file: " + e.getMessage());
	    } catch (NumberFormatException e) { // thrown by parseInt if the String is not numerical
	      throw new IOException(
	          "There was trouble converted a String to an Integer: " + e.getMessage());
	    }

	}
	/**
     * Retrieves a list of song titles for songs that have an Energy rating
     * within the specified range (sorted by Energy in ascending order).  If 
     * a minSpeed filter has been set using filterFastSongs(), then only songs
     * with speeds greater than or equal to minSpeed should be included in the 
     * list that is returned by this method.
     *
     * Note that either this energy range, or the resulting unfiltered list
     * of songs should be saved for later use by the other methods defined in 
     * this class.
     *
     * @param low is the minimum Energy rating of songs in the returned list
     * @param hight is the maximum Energy rating of songs in the returned list
     * @return List of titles for all songs in specified range 
     */
	public List<String> getRange(int low, int high) { 
		
		this.low = low;
		this.high = high;
		SongInterface lowerBound = new Song("","","",-1, -1, low, -1, -1, -1);
		
		if (filteredSongs != null) {
			filteredSongs.clear();
			//filteredFastSongs.clear();
		}
		
		//set the first bound / first song to start iterator
		tree.setIterationStartPoint(lowerBound);
		
		Iterator<SongInterface> iterator = tree.iterator();
		
		//iterate through tree
		while (iterator.hasNext()) {
			SongInterface song = iterator.next();
			if (song.getEnergy() <= high) {
				if (minSpeed != -1 && song.getBPM() >= minSpeed) {
					filteredSongs.add(song);
				}
				else {
					filteredSongs.add(song);
				}
			}
			else {
				break;
			}
		}
		
		getRangeCalled = true;
		
		List<String> songs = new ArrayList<>();
		for (SongInterface song : filteredSongs) {
			songs.add(song.getTitle());
		}
		
		return songs;		
	}
	
	/**
     * Filters the list of songs returned by future calls of getRange() and 
     * fiveMostDanceable() to only include fast songs.  If getRange() was 
     * previously called, then this method will return a list of song titles
     * (sorted in ascending order by Energy) that only includes songs with
     * speeds greater than or equal to minSpeed.  If getRange() was not 
     * previously called, then this method should return an empty list.
     *
     * Note that this minSpeed threshold should be saved for later use by the 
     * other methods defined in this class.
     *
     * @param minSpeed is the minimum speed of a returned song
     * @return List of song titles, empty if getRange was not previously called
     */
	public List<String> filterFastSongs(int minSpeed){
		this.minSpeed = minSpeed;

		if (!getRangeCalled) {
			return null;
		} 
		
		//iterate over created list in getRange method
		for (SongInterface song : filteredSongs) {
			if (song.getBPM() >= minSpeed) {
				//songs.add(song.getTitle());
				filteredFastSongs.add(song);
			}
			else {
				continue;
			}
		}
		
		List<String> songs = new ArrayList<>();
		for (SongInterface song: filteredFastSongs) {
			songs.add(song.getTitle());
		}
		
		return songs;
	}
	 /**
     * This method makes use of the attribute range specified by the most
     * recent call to getRange().  If a minSpeed threshold has been set by
     * filterFastSongs() then that will also be utilized by this method.
     * Of those songs that match these criteria, the five most danceable will
     * be returned by this method as a List of Strings in increasing order of 
     * energy.  Each string contains the danceability rating followed by a 
     * colon, a space, and then the song's title.
     * If fewer than five such songs exist, display all of them.
     *
     * @return List of five most danceable song titles and their energy
     * @throws IllegalStateException when getRange() was not previously called.
     */
	public List<String> fiveMostDanceable(){
	/*	
		 List<String> topSongs = new ArrayList<String>(); //List to return of top five songs
         int count = 0;
         List<SongInterface> songs = new ArrayList<>(); //List to iterate through and do elimination.

         if (!getRangeCalled) {
         throw new IllegalStateException("getRange() must be called before fiveMostDanceable()");
     }

         //copy filteredSongs into a new list exclusive for this method
         else if (minSpeed != -1) {
                 for (SongInterface song: filteredFastSongs) {
                         songs.add(song);
                 }
         }
         else {
                 for (SongInterface song: filteredSongs) {
                         songs.add(song);
                 }
         }

         if (songs.size() <= 5) {
                 for (int i = 0; i < songs.size(); i++) {
                         topSongs.add(songs.get(i).getEnergy() + ":" + songs.get(i).getTitle());
                 }

         }
         else {
                 while (count < 5) {
                 SongInterface songComparison = songs.get(0);
                 for(int i = 0; i < songs.size(); i++) {
                         if (songComparison.getDanceability() < songs.get(i).getDanceability()) {
                                 songComparison = songs.get(i);
                         }
                 }
                 topSongs.add(songComparison.getEnergy() + ":" + songComparison.getTitle());
                 songs.remove(songComparison);
                 count++;
                 }
         }
         
         return topSongs;
	*/


			
		List<String> topSongs = new ArrayList<String>(); //List to return of top five songs
		int count = 0;
		List<SongInterface> songs = new ArrayList<>(); //List to iterate through and do elimination.
		
		int countTwo = 0;
		List<SongInterface> energyList = new ArrayList<>();

		int songsSize = 0;		
		
		if (!getRangeCalled) {
	        	throw new IllegalStateException("getRange() must be called before fiveMostDanceable()");
	    	}
		
		//copy filteredSongs into a new list exclusive for this method
		else if (minSpeed != -1) {
			for (SongInterface song: filteredFastSongs) {
				songs.add(song);
			}
		}
		else {
			for (SongInterface song: filteredSongs) {
				songs.add(song);
			}
		}
		
		if (songs.size() <= 5) {
			for (int i = 0; i < songs.size(); i++) {
				//topSongs.add(songs.get(i).getEnergy() + ":" + songs.get(i).getTitle());
				energyList.add(songs.get(i));
			}
			songsSize = energyList.size();
		}
		else {
			while (count < 5) {
				
				SongInterface songComparison = songs.get(0);
				for(int i = 0; i < songs.size(); i++) {
					//SongInterface songComparison = filteredSongs.get(0);
					if (songComparison.getDanceability() < songs.get(i).getDanceability()) {
						songComparison = songs.get(i);
						break;			
					}
				}
				
				//topSongs.add(songComparison.getEnergy() + ":" + songComparison.getTitle());
				energyList.add(songComparison);
				songs.remove(songComparison);
				count++;
			}
			songsSize = energyList.size();
		}
		
				
		while (countTwo < songsSize) {//Iterate once again through top 5 to order them from least to most energy
			
			SongInterface songComparison = energyList.get(0);
			for(int i = 0; i < energyList.size(); i++) {
				if (songComparison.getEnergy() > energyList.get(i).getEnergy()) {
					songComparison = energyList.get(i);
				}
			}
			
			topSongs.add(songComparison.getEnergy() + ":" + songComparison.getTitle());
			energyList.remove(songComparison);
			countTwo++;
			
		}		
		
		/*
		for (int i = 0; i < energyList.size(); i++) {
			topSongs.add(energyList.get(i).getEnergy() + ":" + energyList.get(i).getTitle());
		}
		*/
		return topSongs;
	
		
	}
	
}
