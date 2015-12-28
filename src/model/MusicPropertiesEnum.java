package model;

public enum MusicPropertiesEnum {
    ARTIST("Artists"), ALBUM("Albums"), TITLE("Titles"), YEAR("Years");
    
    private final String value;
    
    @Override
    public String toString() {
        return value;
    }

    MusicPropertiesEnum(final String value){
	this.value = value;
    }
}
