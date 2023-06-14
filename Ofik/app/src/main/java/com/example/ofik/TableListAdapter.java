package com.example.ofik;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
public class TableListAdapter extends ArrayAdapter<String> {
    private ArrayList<String> data;
    private int intResourse;
    private Context context;
    private int intSelectedPosition = -1;
    public TableListAdapter(ArrayList<String> data, Context context, int intResourse) {
        super(context, R.layout.activity_notes, data);
        this.data = data;
        this.context = context;
        this.intResourse = intResourse;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(intResourse, parent, false);
        }
        View objView = super.getView(position, convertView, parent);

        if (position == this.intSelectedPosition) {
            objView.setBackgroundColor(Color.parseColor("#F5E8E4"));
        } else {
            objView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        }

        return objView;
    }

    public void setSelectedPosition(int intPos) {
        this.intSelectedPosition = intPos;
        notifyDataSetChanged();
    }
}
