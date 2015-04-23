package allent23.sightwords;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableRow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputWords extends ActionBarActivity {

    List<String> list = new ArrayList<String>();
    ArrayList<EditText> arr = new ArrayList<EditText>();
    Context context = this;
    int bytecounter;
    String FILENAME = "SightWords";
    String[] temp = new String[10];
    String[] temp2 = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputwords);

        Button back = (Button) findViewById(R.id.home);
        Button apply = (Button) findViewById(R.id.applywords);
        Button delete = (Button) findViewById(R.id.delete);
        Button add = (Button) findViewById(R.id.edit);


        EditText word_2 = (EditText) findViewById(R.id.editText2);
        word_2.addTextChangedListener(textListener);
        arr.add(word_2);

        EditText word_3 = (EditText) findViewById(R.id.editText3);
        word_3.addTextChangedListener(textListener);
        arr.add(word_3);

        EditText word_4 = (EditText) findViewById(R.id.editText4);
        word_4.addTextChangedListener(textListener);
        arr.add(word_4);

        EditText word_5 = (EditText) findViewById(R.id.editText5);
        word_5.addTextChangedListener(textListener);
        arr.add(word_5);

        EditText word_6 = (EditText) findViewById(R.id.editText6);
        word_6.addTextChangedListener(textListener);
        arr.add(word_6);

        EditText word_7 = (EditText) findViewById(R.id.editText7);
        word_7.addTextChangedListener(textListener);
        arr.add(word_7);

        EditText word_8 = (EditText) findViewById(R.id.editText8);
        word_8.addTextChangedListener(textListener);
        arr.add(word_8);

        EditText word_9 = (EditText) findViewById(R.id.editText9);
        word_9.addTextChangedListener(textListener);
        arr.add(word_9);

        EditText word_10 = (EditText) findViewById(R.id.editText10);
        word_10.addTextChangedListener(textListener);
        arr.add(word_10);

        outputWords();

        back.setOnClickListener(buttonListener);
        apply.setOnClickListener(buttonListener);
        delete.setOnClickListener(buttonListener);
        add.setOnClickListener(buttonListener);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_SPACE:
            {
                event.isCanceled();
                System.out.println("I hit space");
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
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
                Button edit = (Button) findViewById(R.id.edit); //ADD WORDS button
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.rel_layout);

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
                                            for (EditText edittext : arr) {

                                                String string = edittext.getText().toString();

                                                try
                                                {
                                                    fos.write(string.getBytes());
                                                    fos.write(blank.getBytes());
                                                    bytecounter += (string.length() + 1);

                                                }
                                                catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            fos.close();


                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
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

                        FileInputStream in = null;

                        try {
                            in = openFileInput(FILENAME);
                            InputStreamReader inputStreamReader = new InputStreamReader(in);
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                            StringBuilder sb = new StringBuilder();
                            String line;

                            while ((line = bufferedReader.readLine()) != null)
                            {
                                sb.append(line);
                            }

                            String temp = sb.toString();

                            String[] text = temp.split(" ");

                            for (String x : text)
                            {
                                if (!x.equals("") && x != null )
                                    System.out.println("Word: " + x);
                            }

                        }
                        catch (FileNotFoundException e) {
                            System.out.println("File not found" + e);
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


            for (String x : temp)
            {
                if ((!x.equals("")) && (x != null))
                {
                    temp2[counter] = x;
                    counter++;
                }
            }
            counter = 0;
            for (EditText edittext : arr)
            {
                if(counter <= (temp2.length - 1))
                {
                    edittext.setText(temp2[counter]);
                    counter++;
                }

            }


        }
        catch (FileNotFoundException e) {
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


            public void afterTextChanged(Editable s)
            {

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }


            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }
        };
    }
}