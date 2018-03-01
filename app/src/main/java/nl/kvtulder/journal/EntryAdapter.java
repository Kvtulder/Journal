package nl.kvtulder.journal;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import java.sql.Timestamp;


public class EntryAdapter extends ResourceCursorAdapter {

    public EntryAdapter(Context context, Cursor c) {
        super(context, R.layout.entry_row, c);
    }


    // Overide the get item method to return a journal entry instead of a cursor
    @Override
    public Object getItem(int position) {

        // get the cursor
        Cursor cursor = (Cursor) super.getItem(position);

        // get all details of the journal and return the result
        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String content = cursor.getString(cursor.getColumnIndex("content"));
        Mood mood = Mood.valueOf(cursor.getString(cursor.getColumnIndex("mood")));
        Timestamp timestamp = Timestamp.valueOf(cursor.getString(cursor.getColumnIndex("timestamp")));
        Boolean favorite = cursor.getInt(cursor.getColumnIndex("favorite")) == 1;
        return new JournalEntry(id,title,content,mood,timestamp,favorite);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // fill in the details

        TextView title = view.findViewById(R.id.journalTitle);
        TextView timestamp = view.findViewById(R.id.journalTimestamp);

        ImageView smiley = view.findViewById(R.id.journalImage);
        Mood mood = Mood.valueOf(cursor.getString(cursor.getColumnIndex("mood")));
        smiley.setImageResource(mood.getSmiley());

        title.setText(cursor.getString(cursor.getColumnIndex("title")));
        timestamp.setText(cursor.getString(cursor.getColumnIndex("timestamp")));

        Boolean favorite = cursor.getInt(cursor.getColumnIndex("favorite")) == 1;
        ImageView star = view.findViewById(R.id.star);
        star.setVisibility(favorite ? View.VISIBLE : View.INVISIBLE);

    }


}
