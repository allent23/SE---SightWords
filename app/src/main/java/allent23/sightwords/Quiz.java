package allent23.sightwords;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;


public class Quiz extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizlayout);

        Button back = (Button) findViewById(R.id.back);
        Button next = (Button) findViewById(R.id.next);
        RadioButton option1 = (RadioButton) findViewById(R.id.option1);
        RadioButton option2 = (RadioButton) findViewById(R.id.option2);


        back.setOnClickListener(buttonListener);
        next.setOnClickListener(buttonListener);
        option1.setOnClickListener(radioListener);
        option2.setOnClickListener(radioListener);
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
        buttonListener = new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                Button activities = (Button) v;

                Intent intent = new Intent(Quiz.this, MainMenu.class);
                startActivities(new Intent[]{intent});


            }
        };
    }

    private View.OnClickListener radioListener;

    {
        radioListener = new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                boolean checked = ((RadioButton) v).isChecked();

                RadioButton option1 = (RadioButton) findViewById(R.id.option1);
                RadioButton option2 = (RadioButton) findViewById(R.id.option2);

                // Check which radio button was clicked
                switch(v.getId()) {
                    case R.id.option1:
                        if (checked)
                            option1.setText("picked me #1!");
                            option2.setChecked(false);
                            break;
                    case R.id.option2:
                        if (checked)
                            option2.setText("picked me #2!");
                            option1.setChecked(false);
                            break;
                }

            }
        };
    }
}
