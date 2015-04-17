package allent23.sightwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by antonytom on 4/12/15.
 */

/*Things to look at
  - Is edit button necessary? should list be fully editable and only enable deleting?
  - Test out scroll view
  - Get emulator running
  - figure how to save words locally
*/
public class InputWords extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputwords);

        Button back = (Button) findViewById(R.id.back);
        Button apply = (Button) findViewById(R.id.applywords);
        Button delete = (Button) findViewById(R.id.delete);
        Button add = (Button) findViewById(R.id.edit);

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

                Button activities = (Button) v;

                Intent intent = new Intent(InputWords.this, MainMenu.class);
                startActivities(new Intent[]{intent});


            }

        };
    }
}
