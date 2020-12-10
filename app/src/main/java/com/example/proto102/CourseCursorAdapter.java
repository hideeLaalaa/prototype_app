package com.example.proto102;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class CourseCursorAdapter extends SimpleCursorAdapter {

    public CourseCursorAdapter(Context context, int layout, Cursor c,
                               String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    //to use a viewholder, you must override d fllg two methods and define a ViewHolder class

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder == null){
            viewHolder = new ViewHolder();
            viewHolder.colImp = cursor.getColumnIndexOrThrow(CourseDbAdapter.COL_CARRY);
            viewHolder.listTab=view.findViewById(R.id.row_tab);
            view.setTag(viewHolder);
        }

        if (cursor.getInt(viewHolder.colImp)>0){
            //if carryover course
            viewHolder.listTab.setBackgroundColor(context.getResources().getColor(R.color.red));
        }else {
            viewHolder.listTab.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
    }

    static class ViewHolder {
        //store the column index
        int colImp;
        //store the view
        View listTab;
    }



}
