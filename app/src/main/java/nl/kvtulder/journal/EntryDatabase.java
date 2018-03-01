package nl.kvtulder.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class EntryDatabase extends SQLiteOpenHelper {

    private static EntryDatabase instance;
    private static final String TABLENAME = "journalEntries";

    private EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // make class a singleton
    public static EntryDatabase getInstance(Context context)
    {
        // if no instance is found, create a new one
        if(instance == null)
            instance = new EntryDatabase(context,TABLENAME,null,10);

        return instance;
    }

    // create new table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE journalEntries (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "title TEXT NOT NULL," +
                "content TEXT NOT NULL," +
                "mood TEXT NOT NULL," +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "favorite INTEGER DEFAULT 0" +
                ");";

        sqLiteDatabase.execSQL(query);

        // insert the welcome message
        String welcome = "INSERT INTO " + TABLENAME + " (title, content, mood, favorite) VALUES " +
                "('Welkom bij de Journal app!', 'Dit is je eerste journal', 'AWESOME', 1);";
        sqLiteDatabase.execSQL(welcome);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // delete the old database
        String query = "DROP TABLE journalEntries";
        sqLiteDatabase.execSQL(query);

        // create a new one
        onCreate(sqLiteDatabase);
    }

    // returns all journal entries
    public Cursor selectAll() {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM journalEntries",null);
        return cursor;
    }

    // create a new journal entry
    public void addEntry(JournalEntry entry){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title",entry.getTitle());
        values.put("content",entry.getContent());
        values.put("timestamp",entry.getTimestamp().toString());
        values.put("mood", String.valueOf(entry.getMood()));
        values.put("favorite", entry.getFavorite() ? 1 : 0);

        database.insert(TABLENAME,null,values);
    }

    // delete a journal entry given the id
    public void deleteEntry(int id)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLENAME,"_id=?",new String[]{String.valueOf(id)});
    }

    // update the database
    public void setFavorite(int id, Boolean favorite)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("favorite",favorite ? 1 : 0);

        database.update(TABLENAME,values,"_id=?",new String[]{String.valueOf(id)});
    }

}