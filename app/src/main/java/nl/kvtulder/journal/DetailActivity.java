package nl.kvtulder.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // retrieve the clicked journal entry
        Intent intent = getIntent();
        JournalEntry entry = (JournalEntry) intent.getSerializableExtra("entry");

        // fill in the details
        ImageView journalSmiley = findViewById(R.id.journalSmiley);
        journalSmiley.setImageResource(entry.getMood().getSmiley());

        TextView title = findViewById(R.id.journalTitle);
        title.setText(entry.getTitle());

        TextView detail = findViewById(R.id.journalDetail);
        detail.setText(entry.getTimestamp() + " ; " + entry.getMood().toString().toLowerCase() );

        TextView content = findViewById(R.id.journalContent);
        content.setText(entry.getContent());
    }
}
