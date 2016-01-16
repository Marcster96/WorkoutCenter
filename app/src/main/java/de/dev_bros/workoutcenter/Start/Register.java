package de.dev_bros.workoutcenter.Start;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import de.dev_bros.workoutcenter.R;

public class Register extends AppCompatActivity implements View.OnClickListener {


    private boolean[] valide;
    private EditText tbxUsername;
    private EditText tbxPassword;
    private EditText tbxEmail;
    private LinearLayout linearLayout;
    private ProgressBar prbStatus;
    private TextView lblStatus;
    private int controllStatus = 0;
    private Button btnRegistrien;

    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        valide = new boolean[3];

        tbxEmail = (EditText) findViewById(R.id.tbxEmail);
        tbxUsername = (EditText) findViewById(R.id.tbxUsername);
        tbxPassword = (EditText) findViewById(R.id.tbxPassword);
        lblStatus = (TextView) findViewById(R.id.lblStatus);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayoutRegister);
        prbStatus = (ProgressBar) findViewById(R.id.prgStatus);
        btnRegistrien = (Button) findViewById(R.id.btnRegistrierenReg);

        linearLayout.setOnClickListener(this);

        prbStatus.setMax(3);
        prbStatus.setScaleY(5f);

        pruefeStatus();
        checkValide();


        tbxPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                controllStatus = 0;
                String currentString = String.valueOf(s);

                if (currentString.length() != 0) {

                    boolean letters = false;
                    boolean digits = false;

                    for (int i = 0; i < currentString.length(); i++) {
                        if (Character.isLetter(currentString.charAt(i))) {
                            letters = true;
                            break;
                        }
                    }

                    for (int i = 0; i < currentString.length(); i++) {
                        if (Character.isDigit(currentString.charAt(i))) {
                            digits = true;
                            break;
                        }
                    }

                    if (digits == true) {
                        controllStatus += 1;
                    }

                    if (letters == true) {
                        controllStatus += 1;
                    }

                    if (currentString.length() >= 6) {
                        controllStatus += 1;
                    }
                }
                pruefeStatus();
                checkValide();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tbxUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String currentString = String.valueOf(s);
                if(currentString.length() >= 4){
                    valide[0] = true;
                    tbxUsername.setBackground(getResources().getDrawable(R.drawable.rounded_corner_blue));

                }else {
                    valide[0] = false;
                    tbxUsername.setBackground(getResources().getDrawable(R.drawable.rounded_corner_red));
                }
                checkValide();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tbxEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int valideCounter = 0; // wenn der zähler auf 3 ist ist die Email valide
                String currentString = String.valueOf(s);

                if(currentString.length() >= 6){
                    valideCounter+= 1;
                }

                if(currentString.contains("@")){
                    valideCounter += 1;
                }

                if(currentString.contains(".com") || currentString.contains(".de") || currentString.contains(".net")){
                    valideCounter+= 1;
                }

                if(valideCounter == 3){
                    tbxEmail.setBackground(getResources().getDrawable(R.drawable.rounded_corner_blue));
                    valide[2] = true;
                    checkValide();
                }else{
                    tbxEmail.setBackground(getResources().getDrawable(R.drawable.rounded_corner_red));
                    valide[2] = false;
                    checkValide();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void pruefeStatus(){
        prbStatus.setProgress(controllStatus);

        switch (controllStatus) {
            case 0:
                prbStatus.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                lblStatus.setTextColor(Color.RED);
                lblStatus.setText(R.string.Bad);
                tbxPassword.setBackground(getResources().getDrawable(R.drawable.rounded_corner_red));
                valide[1] = false;
                break;
            case 1:
                prbStatus.getProgressDrawable().setColorFilter(Color.rgb(255, 153, 0), PorterDuff.Mode.SRC_IN);
                lblStatus.setTextColor(Color.rgb(255, 153, 0));
                lblStatus.setText(R.string.Okay);
                tbxPassword.setBackground(getResources().getDrawable(R.drawable.rounded_corner_red));
                valide[1] = false;
                break;
            case 2:
                prbStatus.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN);
                lblStatus.setTextColor(Color.GREEN);
                lblStatus.setText(R.string.Gut);
                tbxPassword.setBackground(getResources().getDrawable(R.drawable.rounded_corner_red));
                valide[1] = false;
                break;
            case 3:
                prbStatus.getProgressDrawable().setColorFilter(Color.rgb(0, 230, 255), PorterDuff.Mode.SRC_IN);
                lblStatus.setTextColor(Color.rgb(0, 230, 255));
                lblStatus.setText(R.string.Perfekt);
                tbxPassword.setBackground(getResources().getDrawable(R.drawable.rounded_corner_blue));
                valide[1] = true;
                break;
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.linearLayoutRegister){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    public void onClickRegister(View view){
        ParseUser user = new ParseUser();
        user.setUsername(tbxUsername.getText().toString());
        user.setPassword(tbxPassword.getText().toString());
        user.setEmail(tbxEmail.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Intent i = new Intent(Register.this, Login.class);
                    i.putExtra(USERNAME_KEY,tbxUsername.getText().toString());
                    i.putExtra(PASSWORD_KEY,tbxPassword.getText().toString());
                    startActivity(i);
                }else{
                    Log.e("SignUp","Error - Code: " + e.getCode());
                }
            }
        });
    }


    private void checkValide(){
        boolean check = true;
        for(boolean value : valide){
            if(value == false){
                check = false;
            }
        }

        if(check){
            btnRegistrien.setEnabled(true);
            btnRegistrien.setBackground(getResources().getDrawable(R.drawable.corner_blue));
        }else{
            btnRegistrien.setEnabled(false);
            btnRegistrien.setBackground(getResources().getDrawable(R.drawable.corner_gray));
        }
    }
}
