package de.dev_bros.workoutcenter.Start;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import de.dev_bros.workoutcenter.Home.Home;
import de.dev_bros.workoutcenter.R;


public class Login extends AppCompatActivity implements View.OnClickListener{


    private EditText tbxUser;
    private EditText tbxPassword;
    private Login context;
    private SharedPreferences sp;
    private CheckBox chbxRememberLogin;
    private CheckBox chbxAutoLogin;

    public static final String USERNAME_KEY = "USERNAME_KEY";
    public static final String PASSWORD_KEY= "PASSWORD_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        sp = this.getSharedPreferences("de.dev_bros.workoutcenter", Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutLogin);
        linearLayout.setOnClickListener(this);

        tbxUser = (EditText) findViewById(R.id.tbxUsername);
        tbxPassword = (EditText) findViewById(R.id.tbxPassword);
        chbxAutoLogin = (CheckBox) findViewById(R.id.chbxAutoLogin);
        chbxRememberLogin = (CheckBox) findViewById(R.id.chbxRememberPw);


        String username = "";
        String password = "";

        username = sp.getString(USERNAME_KEY,"");
        password= sp.getString(PASSWORD_KEY,"");



        Intent intent = getIntent();
        if(intent.getStringExtra(Register.USERNAME_KEY) != null && intent.getStringExtra(Register.PASSWORD_KEY) != null) {
            username = intent.getStringExtra(Register.USERNAME_KEY);
            password = intent.getStringExtra(Register.PASSWORD_KEY);
        }

        Log.i("Saved", "Username: " + sp.getString(USERNAME_KEY,"") + " Password: " + sp.getString(PASSWORD_KEY,""));
        tbxUser.setText(username.toString());
        tbxPassword.setText(password.toString());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.linearLayoutLogin){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    public void onClickBtnLogin(View view){
        ParseUser.logInInBackground(tbxUser.getText().toString(), tbxPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    if(chbxRememberLogin.isChecked()){
                        sp.edit().remove(USERNAME_KEY).apply();
                        sp.edit().remove(PASSWORD_KEY).apply();


                        sp.edit().putString(USERNAME_KEY, tbxUser.getText().toString()).apply();
                        sp.edit().putString(PASSWORD_KEY, tbxPassword.getText().toString()).apply();

                    }else{
                        sp.edit().remove(USERNAME_KEY).apply();
                        sp.edit().remove(PASSWORD_KEY).apply();
                    }

                    if(chbxAutoLogin.isChecked()){
                        sp.edit().remove(Splash.AUTOLOGIN_KEY).apply();
                        sp.edit().putString(Splash.AUTOLOGIN_KEY,"AutoLogin").apply();

                        sp.edit().remove(USERNAME_KEY).apply();
                        sp.edit().remove(PASSWORD_KEY).apply();


                        sp.edit().putString(USERNAME_KEY, tbxUser.getText().toString()).apply();
                        sp.edit().putString(PASSWORD_KEY, tbxPassword.getText().toString()).apply();
                    }else{
                        sp.edit().remove(Splash.AUTOLOGIN_KEY).apply();
                    }


                    Log.i("Login Saves",sp.getString(USERNAME_KEY,"") + " " + sp.getString(PASSWORD_KEY,"") + " Auto -> " + sp.getString(Splash.AUTOLOGIN_KEY,""));
                    Log.i("Login", "User - " + user.getUsername() + " is logged in");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    Intent i = new Intent( Login.this, Home.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    Login.this.finish();

                }else{
                    if(e.getCode() == 101){
                        Toast.makeText(getApplicationContext(),R.string.FalschesPasswort,Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
