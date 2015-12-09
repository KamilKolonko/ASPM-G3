package model;

public class MusicModel {

    private static String mode = "default";// default, random, one song circle,
					   // list circle , play one song

    public static String getMode() {
	return mode;
    }

    public static void setMode(String mode) {
	MusicModel.mode = mode;
    }
}
