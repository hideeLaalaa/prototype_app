package com.example.proto102;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private ListView mListView;
    private CourseDbAdapter xDbAdapter;
    private CourseCursorAdapter xCursorAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.super.onBackPressed();
            }
        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListView = (ListView) findViewById(R.id.courses_list_view);
        mListView.setDivider(null);
        xDbAdapter = new CourseDbAdapter(this);
        xDbAdapter.open();
//        if(savedInstanceState == null){
//            //clear all data
//            xDbAdapter.deleteAllCourses();
//            //adding few datas
//        insertSomeCourses();
//        }else{

            Toast.makeText(HomeActivity.this, " saved instance is not equals to null ",
                    Toast.LENGTH_SHORT).show();
//        }


        Cursor cursor = xDbAdapter.fetchAllCourses();
        //from column defined in the db
        String[] from = new String[]{
                CourseDbAdapter.COL_COURSE
        };
        //to the ids of views in the layout
        int[] to = new int[]{
                R.id.row_course
        };
        // adapting the cursor from and to the layout
        xCursorAdapter = new CourseCursorAdapter(
                //context
                this,
                //layout of d row
                R.layout.courses_row,
                //cursor
                cursor,
                //from
                from,
                //to
                to, 0
        );

        //The ArrayAdapter is d controller in model-view-cntrlr relationship
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                //content
                this,
                //layout
                R.layout.courses_row,
                //row(view)
                R.id.row_course,
                //data (model)
                new String[]{"AMB101","SYS221","TNT131"}
        );
        mListView.setAdapter(xCursorAdapter);
        xDbAdapter.close();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                xDbAdapter.open();
//                xDbAdapter.deleteCourseById((int)xCursorAdapter.getItemId(position));
//                xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());
//                Toast.makeText(HomeActivity.this, "Eleyi work "+ position,
//                        Toast.LENGTH_SHORT).show();
//                xDbAdapter.close();
            }

        });
        mListView.setLongClickable(true);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                xDbAdapter.open();
//                xDbAdapter.deleteCourseById((int)xCursorAdapter.getItemId(position));
//                xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());
//                xDbAdapter.close();
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                ListView modeListView = new ListView(HomeActivity.this);
                String[] modes = new String[]{"Duplicate Course","Delete Course"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(HomeActivity.this,
                        android.R.layout.simple_list_item_1,android.R.id.text1,modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
                        xDbAdapter.open();
                        String name = xDbAdapter.fetchCourseById((int)xCursorAdapter.getItemId(position)).getxCourse();
                        //duplicate course
                        if (position1==0){
                            if (courseExist(-1,name+"2")){
                                Toast.makeText(HomeActivity.this, name+"2 Course Exists",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                xDbAdapter.duplicateCourse(xDbAdapter.fetchCourseById((int)xCursorAdapter.getItemId(position)));
                            }
                            xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());

                        }//delete course
                        else{
                            xDbAdapter.deleteCourseById((int)xCursorAdapter.getItemId(position));
                            xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());
                            Toast.makeText(HomeActivity.this, name+" Deleted",
                                    Toast.LENGTH_SHORT).show();
                        }
                        xDbAdapter.close();
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });

    }

    private void insertSomeCourses() {
        xDbAdapter.createCourse("ELE 451",true,
                "Engr Mahmud","Course is 2 unit");
        xDbAdapter.createCourse("ELE 512",false,
                "Engr Ajiboye","Course is 3 unit");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_option,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_new:
                return true;
            case R.id.action_exit:
//                finish();
                finishAffinity();
                return true;
            default:
                return false;
        }
    }

    //checking for course existence in database
    public boolean courseExist(int xId,String courseName){
        Cursor cursor = xDbAdapter.fetchAllCourses();
        int total =  cursor.getCount();
        for (int i=0;i<total;i++){
            if (cursor.getInt(CourseDbAdapter.INDEX_ID)!=xId)
                if (cursor.getString(CourseDbAdapter.INDEX_COURSE).equals(courseName))
                    return true;
            cursor.moveToNext();
        }
        return false;
    }
}