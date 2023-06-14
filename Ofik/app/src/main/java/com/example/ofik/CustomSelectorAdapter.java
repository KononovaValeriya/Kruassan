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

public class CustomSelectorAdapter extends ArrayAdapter<String> {
    private Context objContext;
    private ArrayList<String> objData;
    private int intResourseId;
    private int intSelectedPosition = -1;

    public CustomSelectorAdapter(Context objContext, int intResourseId, ArrayList<String> objData) {
        super(objContext, R.layout.activity_schedule, objData);
        this.objData = objData;
        this.objContext = objContext;
        this.intResourseId = intResourseId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(intResourseId, parent, false);
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
