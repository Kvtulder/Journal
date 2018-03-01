package nl.kvtulder.journal;

import java.io.Serializable;
import java.sql.Timestamp;

public class JournalEntry implements Serializable {
    private int id;
    private String title;
    private String content;
    private Mood mood;
    private Timestamp timestamp;
    private Boolean favorite;

    public JournalEntry(int id, String title, String content, Mood mood, Timestamp timestamp,Boolean favorite) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.mood = mood;
        this.timestamp = timestamp;
        this.favorite = favorite;
    }

    // overload method for when id is unknown
    public JournalEntry(String title, String content, Mood mood, Timestamp timestamp, Boolean favorite) {
        this.title = title;
        this.content = content;
        this.mood = mood;
        this.timestamp = timestamp;
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Mood getMood() {
        return mood;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite, EntryDatabase db) {
        this.favorite = favorite;
        db.setFavorite(id,favorite);

    }
}
