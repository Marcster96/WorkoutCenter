package de.dev_bros.workoutcenter.Home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import de.dev_bros.workoutcenter.R;
import de.dev_bros.workoutcenter.Start.Splash;

public class Home extends AppCompatActivity {

    public static final String HOME_BACK = "HomeBACK";
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.tool_bar_home);
        setSupportActionBar(toolbar);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.aufnehmen){
            Toast.makeText(getApplicationContext(),"Es wurde Aufnehmen angeklickt",Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.vorhanden){
            Toast.makeText(getApplicationContext(),"Es wurde Gallerie angeklickt",Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id == R.id.logout){
            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        Intent i = new Intent(Home.this, Splash.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.putExtra(HOME_BACK, "HomeBack");
                        startActivity(i);
                        finish();
                    }else{
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }

       return super.onOptionsItemSelected(item);
    }
}
