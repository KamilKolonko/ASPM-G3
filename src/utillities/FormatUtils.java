package utillities;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

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
	for (File file : files) {
	    result.add(new Music(file.getName(),file.getPath()));
	}
	return result;
    }
    
    public static Music toMusic(File file) {
	return new Music(file.getName(),file.getPath());
    }
}
