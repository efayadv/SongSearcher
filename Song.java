
public class Song implements SongInterface{
		
		private String artist;
		private String title;
		private String genre;
		private int year;
		private int bpm;
		private int energy;
		private int danceability;
		private int loudness;
		private int liveness;
		
		public Song(String title, String artist, String genre, int year, int bpm, int energy, int danceability, int loudness, int liveness) {
			this.title = title;
			this.artist = artist;
			this.genre = genre;
			this.year = year;
			this.bpm = bpm;
			this.energy = energy;
			this.danceability = danceability;
			this.loudness = loudness;
			this.liveness = liveness;
		}
		
		@Override
	 	public String getTitle() {// returns this song's title
	 		return title;
	 	}
		@Override
	    public String getArtist() {// returns this song's artist
	    	return artist;
	    }
		@Override
	    public String getGenres() {// returns string containing each of this song's genres
	    	return genre;
	    }
		@Override
	    public int getYear() {// returns this song's year in the Billboard
	    	return year;
	    }
		@Override
	    public int getBPM() {// returns this song's speed/tempo in beats per minute
	    	return bpm;
	    }
		@Override
	    public int getEnergy() {// returns this song's energy rating 
	    	return energy;
	    }
		@Override
	    public int getDanceability(){// returns this song's danceability rating
	    	return danceability;
	    }
		@Override
	    public int getLoudness() {// returns this song's loudness in dB
	    	return loudness;
	    }
		@Override
	    public int getLiveness() {// returns this song's liveness rating
	    	return liveness;
	    }

		@Override
		public int compareTo(SongInterface o) {
			// TODO Auto-generated method stub
			Song otherSong = (Song) o;

			// Compare the energy ratings of this song and the other song
			return Integer.compare(this.energy, otherSong.energy);
			//return 0;
		}
}
