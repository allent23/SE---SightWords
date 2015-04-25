package allent23.sightwords;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.text.Spanned;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.view.ViewParent;
import android.widget.Button;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.Toast;


public class InputWords extends ActionBarActivity {

    List<String> list = new ArrayList<String>();
    ArrayList<EditText> edit_list = new ArrayList<EditText>();
    ArrayList<CheckBox> check_list = new ArrayList<CheckBox>();
    Context context = this;
    int bytecounter;
    String FILENAME = "SightWords";
    String[] temp;
    ScrollView scrollView;

//    OpenDictionaryAPI api = new OpenDictionaryAPI(context);
    //Dictionary dict = new Dictionary.TranslateAsTextListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputwords);

        Button back = (Button) findViewById(R.id.home);
        Button apply = (Button) findViewById(R.id.applywords);
        Button delete = (Button) findViewById(R.id.delete);
        Button add = (Button) findViewById(R.id.add);

        back.setOnClickListener(buttonListener);
        apply.setOnClickListener(buttonListener);
        delete.setOnClickListener(buttonListener);
        add.setOnClickListener(buttonListener);

        outputWords();

        for (EditText x : edit_list)
        {
            x.addTextChangedListener(textListener);
        }

        filter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener buttonListener;

    {
        buttonListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Button apply = (Button) findViewById(R.id.applywords);
                Button delete = (Button) findViewById(R.id.delete);
                Button edit = (Button) findViewById(R.id.add); //ADD WORDS button

                scrollView = (ScrollView) findViewById(R.id.scrollView);
                LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

                // Check which button was clicked
                switch (v.getId()) {
                    case R.id.home:

                        InputWords.this.finish();

                        break;

                    case R.id.applywords:

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set title
                        alertDialogBuilder.setTitle("Update");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Are you sure you want to update this list?")
                                .setCancelable(false)
                                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String FILENAME = "SightWords";
                                        try {

                                            File file = new File(FILENAME);
                                            // Store Serialized User Object in File
                                            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                                            //FileOutputStream fos = new FileOutputStream(file);
                                            String blank = " ";
                                            bytecounter = 0;
                                            for (EditText edittext : edit_list) {

                                                String string = edittext.getText().toString();

                                                try {
                                                    fos.write(string.getBytes());
                                                    fos.write(blank.getBytes());
                                                    bytecounter += (string.length() + 1);

                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            fos.close();


                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(getApplicationContext(), "All words have been added",
                                                Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();

                        break;

                    case R.id.delete:

                        int size = edit_list.size();

                        CheckBox[] check_arr = new CheckBox[size];
                        EditText[] edit_arr = new EditText[size];

                        check_list.toArray(check_arr);
                        edit_list.toArray(edit_arr);

                        for (int i = 0; i < size ; i++)
                        {
                            if (check_arr[i].isChecked())
                            {
                                EditText text =  edit_arr[i];
                                View viewParent =  (View) text.getParent();
                                viewParent.setVisibility(View.GONE);

                                edit_list.remove(text);
                            }
                        }

                        try
                        {
                            File file = new File(FILENAME);
                            // Store Serialized User Object in File
                            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);

                            String blank = " ";
                            bytecounter = 0;
                            for (EditText edittext : edit_list) {

                                String string = edittext.getText().toString();
                                try
                                {
                                    fos.write(string.getBytes());
                                    fos.write(blank.getBytes());
                                    bytecounter += (string.length() + 1);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            fos.close();


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "All selected words have been deleted",
                                Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.add:

                        addEditText(layout);

                        filter();

                        Toast.makeText(getApplicationContext(), "New field added!\nPlease enter a word", Toast.LENGTH_SHORT).show();

                        scrollView.fullScroll(View.FOCUS_DOWN);

                        break;

                }
            }

        };
    }

    public void outputWords()
    {
        FileInputStream in = null;
        try
        {
            LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
            in = openFileInput(FILENAME);
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                sb.append(line);
            }

            int counter = 0;
            String text = sb.toString();
            temp = text.split(" ");


            for(String x : temp)
            {
                if(!x.equals("") && x != null)
                    addEditText(layout);
            }

            for (EditText edittext : edit_list)
            {
                    edittext.setText(temp[counter]);
                    counter++;
            }


        } catch (FileNotFoundException e) {
        } catch (IOException ioe) {
            System.out.println("Exception while reading file " + ioe);
        } finally {
            // close the streams using close method
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }

    }


    private TextWatcher textListener;

    {
        textListener = new TextWatcher() {


            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }


            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };
    }

    public void filter() {
        for (EditText x : edit_list) {
            x.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);

            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence src, int start,
                                           int end, Spanned dst, int dstart, int dend)
                {

                    if (src.equals("")) { // for backspace
                        return src;
                    }

                    if (src.toString().matches("[a-zA-Z]*")) //put your constraints here
                    {
                        return src;
                    }
                    return "";

                }
            };
            x.setFilters(filters);
        }
    }

    public void addEditText(LinearLayout layout)
    {
        TableRow new_row = new TableRow(getApplicationContext());
        EditText new_edit = new EditText(getApplicationContext());
        CheckBox new_check = new CheckBox(getApplicationContext());

        new_row.setWeightSum(100);


        TableRow.LayoutParams rowprops = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);


        TableRow.LayoutParams checkprops = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);

        checkprops.weight = 90;

        TableRow.LayoutParams editprops = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

        editprops.weight = 10;

        new_row.setLayoutParams(rowprops);
        new_edit.setLayoutParams(editprops);
        new_check.setLayoutParams(checkprops);

        new_row.setGravity(Gravity.CENTER);

        new_check.setGravity(Gravity.CENTER);

        new_edit.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        new_edit.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        new_edit.setBackgroundColor(Color.argb(188, 46, 46, 46));
        new_check.setBackgroundColor(Color.argb(188, 155, 239, 255));

        new_edit.setHint("Input Word");
        new_edit.setTextSize(30);

        new_edit.addTextChangedListener(textListener);

        layout.addView(new_row);
        new_row.addView(new_edit);
        new_row.addView(new_check);

        edit_list.add(new_edit);
        check_list.add(new_check);
        System.out.println("check added");

    }
}
