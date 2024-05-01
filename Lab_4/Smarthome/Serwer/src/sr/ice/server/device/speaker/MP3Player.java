package sr.ice.server.device.speaker;

import Smarthome.*;
import com.zeroc.Ice.Current;

public class MP3Player extends Speaker implements IMP3Player {

    private Song song;
    private boolean isPaused;
    public MP3Player(String name) {
        super(name);
        song = new Song("Ty On i Ja", "TSA", 3.45, Genre.METAL);
        isPaused = false;
    }

    @Override
    public Song getSong(Current current) {
        System.out.println("[MP3Player] " + name + ", getSong: " + song + ",id: " + current.id.name + ", category: " + current.id.category);
        return null;
    }

    @Override
    public boolean setSong(Song song, Current current) throws InvalidSongException {
        System.out.println("[MP3Player] " + name + ", settSong: " + song + ",id: " + current.id.name + ", category: " + current.id.category);
        if (song == null) {
            throw new InvalidSongException("Song cannot be null!");
        }
        if (song.title == null || song.title.equals("")) {
            throw new InvalidSongException("Song title cannot be null or empty");
        }
        if (song.artist == null || song.artist.equals("")) {
            throw new InvalidSongException("Song artist cannot be null or empty");
        }
        if (song.duration <= 0) {
            throw new InvalidSongException("Song duration must be longer than 0");
        }
        if (song.genre == null) {
            throw new InvalidSongException("Song genre cannot be null");
        }
        this.song = song;

        return true;
    }

    @Override
    public boolean pause(Current current) throws PauseMP3Exception{
        System.out.println("[MP3Player] " + name + ", pause" + ",id: " + current.id.name + ", category: " + current.id.category);
        if (isPaused) {
            throw new PauseMP3Exception("Song is already paused!");
        }
        isPaused = true;
        return true;
    }

    @Override
    public boolean unpause(Current current) throws PauseMP3Exception{
        System.out.println("[MP3Player] " + name + ", unpause" + ",id: " + current.id.name + ", category: " + current.id.category);
        if (!isPaused) {
            throw new PauseMP3Exception("Song is already playing!");
        }
        isPaused = false;
        return true;
    }
}
