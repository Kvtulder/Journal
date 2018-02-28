package nl.kvtulder.journal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class SpinnerAdapter extends BaseAdapter {


    Mood[] objects;
    Context context;
    int resource;


    public SpinnerAdapter(@NonNull Context context, Mood[] objects, int resource) {
        this.objects = objects;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return objects.length;
    }

    @Override
    public Object getItem(int i) {
        return objects[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        ImageView moodImage = convertView.findViewById(R.id.moodImage);
        moodImage.setImageResource(objects[position].getSmiley());

        TextView moodText = convertView.findViewById(R.id.moodText);
        moodText.setText(String.valueOf(objects[position]).toLowerCase());

        return convertView;
    }
}
