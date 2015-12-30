package utillities;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;

import model.Music;

public class FormatUtils {

    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int SECONDS_IN_HOUR = SECONDS_IN_MINUTE * MINUTES_IN_HOUR;

    public static String secondsToTime(double seconds) {
	int hoursResult = (int) seconds / SECONDS_IN_HOUR;
	int minutesResult = (int) seconds / SECONDS_IN_MINUTE - (hoursResult * MINUTES_IN_HOUR);
	int secondsResult = (int) seconds - ((hoursResult * SECONDS_IN_HOUR) + (minutesResult * SECONDS_IN_MINUTE));

	DecimalFormat formatter = new DecimalFormat("00");
	formatter.format(hoursResult);
	formatter.format(minutesResult);
	formatter.format(secondsResult);

	return formatter.format(hoursResult) + ":" + formatter.format(minutesResult) + ":"
		+ formatter.format(secondsResult);
    }

    public static String millisecondsToTime(double milliseconds) {
	return secondsToTime((int) (milliseconds / 1000));
    }

    public static ArrayList<Music> toMusicList(File[] files) {
	ArrayList<Music> result = new ArrayList<>();
	try {
	    AudioFile f;
	    for (File file : files) {
		f = AudioFileIO.read(file);
		Tag tag = f.getTag();
		String year = tag.getFirst(FieldKey.YEAR);
		if(year.length() >=4){
		    year = year.substring(0, 4);
		} 
		result.add(new Music(file.getName(), tag.getFirst(FieldKey.ARTIST), tag.getFirst(FieldKey.ALBUM),
			tag.getFirst(FieldKey.TITLE), year, file.getPath()));
	    }
	} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
		| InvalidAudioFrameException e) {
	    e.printStackTrace();
	}
	return result;
    }

    public static Music toMusic(File file) {
	return new Music(file.getName(), file.getPath());
    }
}
