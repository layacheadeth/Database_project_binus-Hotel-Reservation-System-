package sample.database;

public class description {
    int id;
    String text_describe;
    int listing_id;

    public description(int id, String text_describe, int listing_id) {
        this.id = id;
        this.text_describe = text_describe;
        this.listing_id = listing_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText_describe() {
        return text_describe;
    }

    public void setText_describe(String text_describe) {
        this.text_describe = text_describe;
    }

    public int getListing_id() {
        return listing_id;
    }

    public void setListing_id(int listing_id) {
        this.listing_id = listing_id;
    }
}
