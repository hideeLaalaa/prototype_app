package com.example.proto102;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private ListView mListView;
    private CourseDbAdapter xDbAdapter;
    private CourseCursorAdapter xCursorAdapter;
    private ArrayAdapter<String> arrayAdapter;
    Toolbar toolbar;
    private int noChecked = 0;
    private int clicked = 0;
    private SearchView searchView;

    //defining sort types
    public static final int SORT_DEFAULT = 0;
    public static final int SORT_NAME = 1;
    public static final int SORT_UNIT = 2;
    public static final int SORT_CARRY = 3;
    //defining sort modes
    public static final int ASC = 0;
    public static final int DESC = 1;

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



            mListView = (ListView) findViewById(R.id.courses_list_view);
        mListView.setDivider(null);
//        this.deleteDatabase("dba_course");
        xDbAdapter = new CourseDbAdapter(this);
        xDbAdapter.open();
//        if(savedInstanceState == null){
            //clear all data
//            xDbAdapter.deleteAllCourses();
//            adding few datas
            xDbAdapter.createCourse("ABE 543",2,false,"Mr Ajala","Test on monday");
//             insertSomeCourses();
//        }else{

        Toast.makeText(HomeActivity.this, " saved instance is not equals to null ",
                    Toast.LENGTH_SHORT).show();
//        }


        Cursor cursor = xDbAdapter.fetchAllCourses();
        //from column defined in the db
        String[] from = new String[]{
                CourseDbAdapter.COL_COURSE,CourseDbAdapter.COL_UNIT
        };
        //to the ids of views in the layout
        int[] to = new int[]{
                R.id.row_course,R.id.unit_listView
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
        arrayAdapter = new ArrayAdapter<String>(
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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                clicked++;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (clicked==1){
//                            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
//                            ListView modeListView = new ListView(HomeActivity.this);
//                            String[] modes = new String[]{"Duplicate Course","Delete Course"};
//                            ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(HomeActivity.this,
//                                    android.R.layout.simple_list_item_1,android.R.id.text1,modes);
//                            modeListView.setAdapter(modeAdapter);
//                            builder.setView(modeListView);
//                            final Dialog dialog = builder.create();
//                            dialog.show();
//                            modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
//                                    xDbAdapter.open();
//                                    String name = xDbAdapter.fetchCourseById((int)xCursorAdapter.getItemId(position)).getxCourse();
//                                    //duplicate course
//                                    if (position1==0){
//                                        if (courseExist(-1,name+"2")){
//                                            Toast.makeText(HomeActivity.this, name+"2 Course Exists",
//                                                    Toast.LENGTH_SHORT).show();
//                                        }else {
//                                            xDbAdapter.duplicateCourse(xDbAdapter.fetchCourseById((int)xCursorAdapter.getItemId(position)));
//                                        }
//                                        xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());
//
//                                    }//delete course
//                                    else{
//                                        xDbAdapter.deleteCourseById((int)xCursorAdapter.getItemId(position));
//                                        xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());
//                                        Toast.makeText(HomeActivity.this, name+" Deleted",
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//                                    xDbAdapter.close();
//                                    dialog.dismiss();
//                                }
//                            });
                            Intent intent = new Intent(HomeActivity.this,courseActivity.class);
                            startActivity(intent);
                        }else if (clicked==2){
                            xDbAdapter.open();
                            Toast.makeText(HomeActivity.this, "Clicked Twice ",
                                    Toast.LENGTH_SHORT).show();
                            int xId = getIdFromPosition(position);
                            Course course = xDbAdapter.fetchCourseById(xId);
                            fireCustomDialog(course);
                            xDbAdapter.close();
                        }
                        clicked=0;
                    }
                },500);

            }

        });

        multiChoice();


    }

    private void insertSomeCourses() {
        xDbAdapter.createCourse("ELE 451",2,true,
                "Engr Mahmud","Course is 2 unit");
        xDbAdapter.createCourse("ELE 512",3,false,
                "Engr Ajiboye","Course is 3 unit");
    }

    //--CUSTOM DIALOG METHOD
    private void fireCustomDialog(final Course course){
        //custom dialog
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.custom_dialog);
        dialog.setContentView(course!=null ? R.layout.redefined_dialog : R.layout.custom_dialog2);

        final TextView titleView = (TextView)dialog.findViewById(R.id.title_textView);
        final EditText courseText = (EditText)dialog.findViewById(R.id.courseCode_text);
        final EditText lecturerText = (EditText)dialog.findViewById(R.id.lecturer_text);
        final EditText unitText = (EditText)dialog.findViewById(R.id.unit_text);
//        final EditText reminderText = (EditText)dialog.findViewById(R.id.note_textview);
        final CheckBox carryCheck = (CheckBox)dialog.findViewById(R.id.carry_toggle);
        final Button saveButton = (Button)dialog.findViewById(R.id.save_button);
        final Button cancelButton = (Button)dialog.findViewById(R.id.cancel_button);
        LinearLayout rootLayout = (LinearLayout)dialog.findViewById(R.id.custom_root_layout);
        FrameLayout container = (FrameLayout) dialog.findViewById(R.id.container);
        ImageView remove_reminder = (ImageView) dialog.findViewById(R.id.remove_note);
        ListView cardView;
        final boolean isEditOperation = (course!=null);

        courseText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                saveButton.setEnabled(courseText.getText().length()>3?true:false);
                if(courseText.getText().length()<=3)
                    courseText.setError("course must be >3 characters");
                else
                    courseText.setError(null);

                return false;
            }
        });

        dialog.show();
        //---EDIT OPERATION
        {
            if (isEditOperation){
                carryCheck.setChecked(course.getxCarry()==1);
                courseText.setText((course.getxCourse()));
                lecturerText.setText(course.getxLecturer());
                unitText.setText(""+course.getxUnit());
//                reminderText.setText(course.getxNote());
                titleView.setText("Edit Course");
//                rootLayout.setBackgroundColor(getResources().getColor(R.color.edit_custom_color));



            }else
                courseText.setError("course must be >3 characters");



            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    xDbAdapter.open();
                    String code = courseText.getText().toString();
//                    String notes = reminderText.getText().toString();
                    String notes = "";
                    String teach = lecturerText.getText().toString();
                    int unit =  Integer.parseInt(unitText.getText().toString().equals("")?"0":unitText.getText().toString());
                    if (isEditOperation){
                                Course courseEdited = new Course(
                                course.getxId(),
                                code,
                                unit,
                                carryCheck.isChecked()?1:0,
                                teach,
                                notes);
                        xDbAdapter.updateCourse(courseEdited);
                    }
                    else{//IF ITS A COURSE
                        xDbAdapter.createCourse(code,unit,carryCheck.isChecked(),teach,notes);
                    }
                    xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());
                    xDbAdapter.close();
                    dialog.dismiss();
                }
            });

        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        container.setClickable(true);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager.isAcceptingText()){
                    inputMethodManager.hideSoftInputFromWindow(dialog.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    Toast.makeText(HomeActivity.this, " container",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
//        rootLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(HomeActivity.this, " rootLay",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

//        dialog.getWindow().setGravity(Gravity.BOTTOM);
//        dialog.getWindow().getAttributes().gravity=Gravity.BOTTOM;
//        dialog.getWindow().getAttributes().y=0;
//        dialog.getWindow().getAttributes().x=0;
//        dialog.getWindow().getAttributes().softInputMode=InputMethodManager.HIDE_NOT_ALWAYS;
//        dialog.getWindow().getAttributes().verticalMargin=0;
//        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_option,menu);
        toolbar.getMenu().findItem(R.id.sort_oldest).setChecked(true);
        //--Associate searchable configuration with the searchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Here...");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        //--SEARCH LISTENER QUERYTEXTLISTENER
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                xDbAdapter.open();
//                query=query.toUpperCase(Locale.ENGLISH);
                Toast.makeText(HomeActivity.this, "Text: "+query,
                        Toast.LENGTH_SHORT).show();
//                arrayAdapter.getFilter().filter(newText);
//                xCursorAdapter.getFilter().filter(query);
                xCursorAdapter.changeCursor(xDbAdapter.fetchCourseCursor(query));
                xDbAdapter.close();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String xQuery = "";
                xQuery+=searchView.getQuery();
                Toast.makeText(HomeActivity.this, "Text: "+xQuery,
                        Toast.LENGTH_SHORT).show();
                xDbAdapter.open();
                xCursorAdapter.changeCursor(xDbAdapter.fetchCourseCursor((String) xQuery));
                xDbAdapter.close();
                return true;
            }

        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                xDbAdapter.open();
                xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());
                xDbAdapter.close();
            return false;
            }

        });

        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()){
            searchView.setIconified(true);
            xDbAdapter.open();
            xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());
            xDbAdapter.close();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MenuItem item1;
        switch (item.getItemId()){
            case R.id.action_new:
                fireCustomDialog(null);
                return true;
            case R.id.action_exit:
//                finish();
                finishAffinity();
                return true;
            case R.id.menu_item_delete_course:
                finish();
                return true;
            case R.id.action_sort:
                return true;
            case R.id.sort_name:
                xDbAdapter.open();
                xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses(SORT_NAME,ASC));
                xDbAdapter.close();
                break;
            case R.id.sort_oldest:
                xDbAdapter.open();
                xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses(SORT_DEFAULT,ASC));
                xDbAdapter.close();
                break;
            case R.id.sort_unit:
                xDbAdapter.open();
                xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses(SORT_UNIT,ASC));
                xDbAdapter.close();
                break;
            case  R.id.sort_carry:
                xDbAdapter.open();
                xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses(SORT_CARRY,ASC));
                xDbAdapter.close();
                break;
        }
            item.setChecked(true);
        Toast.makeText(HomeActivity.this,"Toolbar size: "+toolbar.getMenu().size(),Toast.LENGTH_SHORT);

        item1 = toolbar.getMenu().findItem(R.id.sort_name);
        if (item1!=item)
            item1.setChecked(false);
        item1 = toolbar.getMenu().findItem(R.id.sort_carry);
        if (item1!=item)
            item1.setChecked(false);
        item1 = toolbar.getMenu().findItem(R.id.sort_unit);
        if (item1!=item)
            item1.setChecked(false);
        item1 = toolbar.getMenu().findItem(R.id.sort_oldest);
        if (item1!=item)
            item1.setChecked(false);
        return true;
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

    public void multiChoice(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){

            mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                    if (checked){
                        noChecked++;
                    }
                    else{
                        noChecked--;

                    }
                    mode.setTitle(noChecked+" Selected");
                }

                @SuppressLint("WrongConstant")
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    toolbar.setVisibility(View.INVISIBLE);
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.cam_menu,menu);
                    mode.setTitle(noChecked+" Selected");
//                    mode.getCustomView().setForeground(toolbar.getForeground());
//                    mode.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);


                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    xDbAdapter.open();
                    switch (item.getItemId()){
                        case R.id.menu_item_delete_course:
                            for (int nC = xCursorAdapter.getCount()-1;nC>=0;nC--){
                                if (mListView.isItemChecked(nC)){
                                    xDbAdapter.deleteCourseById(getIdFromPosition(nC));
                                }
                            }
                            mode.finish();
                            xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());
                            return true;
                    }
                    xDbAdapter.close();
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    toolbar.setVisibility(View.VISIBLE);
                    noChecked=0;
                }

            });
        }
    }

    private int getIdFromPosition(int nC){
        return (int) xCursorAdapter.getItemId(nC);
    }

}


//LONG CLICKABLE ENABLING
//        mListView.setLongClickable(true);
//                mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//@Override
//public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
//        ListView modeListView = new ListView(HomeActivity.this);
//        String[] modes = new String[]{"Duplicate Course","Delete Course"};
//        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(HomeActivity.this,
//        android.R.layout.simple_list_item_1,android.R.id.text1,modes);
//        modeListView.setAdapter(modeAdapter);
//        builder.setView(modeListView);
//final Dialog dialog = builder.create();
//        dialog.show();
//        modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//@Override
//public void onItemClick(AdapterView<?> parent, View view, int position1, long id) {
//        xDbAdapter.open();
//        String name = xDbAdapter.fetchCourseById((int)xCursorAdapter.getItemId(position)).getxCourse();
//        //duplicate course
//        if (position1==0){
//        if (courseExist(-1,name+"2")){
//        Toast.makeText(HomeActivity.this, name+"2 Course Exists",
//        Toast.LENGTH_SHORT).show();
//        }else {
//        xDbAdapter.duplicateCourse(xDbAdapter.fetchCourseById((int)xCursorAdapter.getItemId(position)));
//        }
//        xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());
//
//        }//delete course
//        else{
//        xDbAdapter.deleteCourseById((int)xCursorAdapter.getItemId(position));
//        xCursorAdapter.changeCursor(xDbAdapter.fetchAllCourses());
//        Toast.makeText(HomeActivity.this, name+" Deleted",
//        Toast.LENGTH_SHORT).show();
//        }
//        xDbAdapter.close();
//        dialog.dismiss();
//        }
//        });
//        return true;
//        }
//        });


//--- THIS IS TO SET FILTER TO CURSOR ADAPTER
//   yourCustomCursorAdapter.setFilterQueryProvider(new FilterQueryProvider() {
//
//// constraint is the variable which is passed from your text change listener
//public Cursor runQuery(CharSequence constraint) {
//        // if the search string is empty, then return the original cursor with all results from the original query
//        if(constraint.equals(""))
//        {
//        return myCursor;
//        }
//        MatrixCursor filteredValues = new MatrixCursor(new String[]{/*Same columns as your original cursor myCursor*/);
//
//        while(myCursor.moveToNext()) {
//        String smth = myCursor.getString(myCursor.getColumnIndex(validColumnIndex);
//        String smth2 = ....
//        //Some condition to check previous data is not matched and only then add row
//        if (smth.toLowerCase().contains(constraint.toString().toLowerCase())) {
//        filteredValues.addRow(new String[]{smth, ...});
//        }
//        }
//        // do not forget next line since you manipulated your original cursor with moveToNext
//        yourCursor.moveToFirst();
//
//        // If your search value was not found at all return the last valid search result
//        if(filteredValues.getCount() == 0)
//        {
//        return lastValidSearchResult;
//        }
//        // else you can save the new correct search result and return the results
//        lastValidSearchResult = filteredValues;
//        return filteredValues; //now your adapter will have the new filtered content
//        }});