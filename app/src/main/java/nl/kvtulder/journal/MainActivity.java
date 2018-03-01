package nl.kvtulder.journal;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity {

    // define constants
    private final static int POPUP_FAVORITE_ID = 1;
    private final static int POPUP_DELETE_ID = 2;

    //define variables

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set all the listeners
        listView = findViewById(R.id.listview);
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

    // Item click listener for the listview containing all the entries
    public class ListViewOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            // Journal entry clicked, show the detail activity
            JournalEntry entry = (JournalEntry) adapterView.getItemAtPosition(i);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("entry", entry);
            startActivity(intent);

        }
    }

    // long click listener for the main listview, shows a popup menu
    public class ListViewOniLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            // retrieve the clicked journal entry
            JournalEntry entry = (JournalEntry) adapterView.getItemAtPosition(i);

            // create new popup menu
            PopupMenu popupMenu = new PopupMenu(MainActivity.this,view);
            String favoriteString = (entry.getFavorite() ? "Unmark" : "Mark") + " as favorite";
            popupMenu.getMenu().add(Menu.NONE,POPUP_FAVORITE_ID,POPUP_FAVORITE_ID,favoriteString);
            popupMenu.getMenu().add(Menu.NONE,POPUP_DELETE_ID,POPUP_DELETE_ID,"Delete Item");
            popupMenu.setGravity(Gravity.END);

            // set listener to handle the actions and show
            popupMenu.setOnMenuItemClickListener(new MenuItemClickListener(entry));
            popupMenu.show();
            return true;
        }
    }

    // menu listener for the popup menu
    public class MenuItemClickListener implements PopupMenu.OnMenuItemClickListener{

        private JournalEntry entry;
        public MenuItemClickListener(JournalEntry entry)
        {
            this.entry = entry;
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            EntryDatabase db = EntryDatabase.getInstance(getApplicationContext());

            switch (menuItem.getItemId())
            {
                case POPUP_FAVORITE_ID:
                    entry.setFavorite(!entry.getFavorite(),db);
                    updateUI();
                    return true;
                case POPUP_DELETE_ID:
                    db.deleteEntry(entry.getId());
                    updateUI();
                    return true;
                default:
                    // unkown menu action, do nothing and return false
                    return false;
            }
        }
    }
}
