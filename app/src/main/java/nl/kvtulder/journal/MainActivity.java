package nl.kvtulder.journal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set all the listeners
        ListView listView = findViewById(R.id.listview);
        listView.setOnItemClickListener(new ListViewOnItemClickListener());
        listView.setOnItemLongClickListener(new ListViewOniLongClickListener());

        // update the ui
        updateUI();
    }

    public void updateUI() {
        // retrieve all journal entries
        EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());
        Cursor cursor = db.selectAll();

        // fill the listview with the entries
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(new EntryAdapter(this,cursor));
    }

    // update ui when user comes back (ie when a user created a new journal entry)
    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    // callback for the add entry button. Start the input activity when clicked
    public void addEntryButtonClicked(View v)
    {
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        startActivity(intent);
    }


    public class ListViewOnItemClickListener implements AdapterView.OnItemClickListener {
        @SuppressLint("ResourceAsColor")
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            JournalEntry entry = (JournalEntry) adapterView.getItemAtPosition(i);

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("entry", entry);
            startActivity(intent);

        }
    }

    public class ListViewOniLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());
            JournalEntry entry = (JournalEntry) adapterView.getItemAtPosition(i);
            db.deleteEntry(entry.getId());
            updateUI();

            return true;
        }
    }

}
