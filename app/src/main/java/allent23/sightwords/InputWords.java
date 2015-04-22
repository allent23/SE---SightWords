package allent23.sightwords;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class InputWords extends ActionBarActivity {

    List<String> list = new ArrayList<String>();
    boolean yes = false;

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

        EditText word_3 = (EditText) findViewById(R.id.editText3);
        word_3.addTextChangedListener(textListener);

        EditText word_4 = (EditText) findViewById(R.id.editText4);
        word_4.addTextChangedListener(textListener);

        EditText word_5 = (EditText) findViewById(R.id.editText5);
        word_5.addTextChangedListener(textListener);

        EditText word_6 = (EditText) findViewById(R.id.editText6);
        word_6.addTextChangedListener(textListener);

        EditText word_7 = (EditText) findViewById(R.id.editText7);
        word_7.addTextChangedListener(textListener);

        EditText word_8 = (EditText) findViewById(R.id.editText8);
        word_8.addTextChangedListener(textListener);

        EditText word_9 = (EditText) findViewById(R.id.editText9);
        word_9.addTextChangedListener(textListener);

        EditText word_10 = (EditText) findViewById(R.id.editText10);
        word_10.addTextChangedListener(textListener);

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);


        back.setOnClickListener(buttonListener);
        apply.setOnClickListener(buttonListener);
        delete.setOnClickListener(buttonListener);
        add.setOnClickListener(buttonListener);


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

                // Check which button was clicked
                switch (v.getId()) {
                    case R.id.applywords:

                        for(int i = 0; i < list.size();i++)
                        {
                            System.out.println(list.toArray()[i]);
                        }
                        break;

                    case R.id.delete:
                        break;

                }
            }

        };
    }
    private TextWatcher textListener;

    {
        textListener = new TextWatcher() {

            public void afterTextChanged(Editable s)
            {
                String word = s.toString();
                list.add(word);
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