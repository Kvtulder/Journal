package nl.kvtulder.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class EntryDatabase extends SQLiteOpenHelper {

    private static EntryDatabase instance;

    public EntryDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public static EntryDatabase getInstance(Context context)
    {
        if(instance == null)
            instance = new EntryDatabase(context,"journalEntries",null,8);

        return instance;
    }


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

        String test = "INSERT INTO journalEntries (title, content, mood) VALUES ('Welkom bij de Journal app!', 'Dit is je eerste journal', 'AWESOME');";
        sqLiteDatabase.execSQL(test);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // delete the old database
        String query = "DROP TABLE journalEntries";
        sqLiteDatabase.execSQL(query);

        // create a new one
        onCreate(sqLiteDatabase);
    }

    public Cursor selectAll() {
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM journalEntries",null);
        return cursor;
    }

    public void addEntry(JournalEntry entry){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title",entry.getTitle());
        values.put("content",entry.getContent());
        values.put("timestamp",entry.getTimestamp().toString());
        values.put("mood", String.valueOf(entry.getMood()));
        values.put("favorite", entry.getFavorite() ? 1 : 0);

        database.insert("journalEntries",null,values);
    }

    public void deleteEntry(int id)
    {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("journalEntries","_id=?",new String[]{String.valueOf(id)});
    }

}