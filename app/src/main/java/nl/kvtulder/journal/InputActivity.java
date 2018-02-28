package nl.kvtulder.journal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.sql.Timestamp;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Spinner spinner = findViewById(R.id.moodDropdown);
        spinner.setAdapter(new SpinnerAdapter(this,Mood.values(),R.layout.spinner_item));
    }

    public void saveButtonClicked(View view) {


        EditText editTitle = findViewById(R.id.editTitle);
        String title = editTitle.getText().toString();

        EditText editContent = findViewById(R.id.editContent);
        String content = editContent.getText().toString();

        Spinner spinner = findViewById(R.id.moodDropdown);
        Mood mood = (Mood) spinner.getSelectedItem();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        JournalEntry entry = new JournalEntry(title,content, mood,timestamp,false);

        EntryDatabase.getInstance(this).addEntry(entry);
        this.finish();
    }
}
