package de.dev_bros.workoutcenter.Start;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import de.dev_bros.workoutcenter.Home.Home;
import de.dev_bros.workoutcenter.R;

public class Splash extends AppCompatActivity {


    public static final String AUTOLOGIN_KEY = "AUTOLOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imgLogo = (ImageView) findViewById(R.id.imgLogoRe);
        imgLogo.setAlpha(0f);

        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final Button btnRegisterieren = (Button) findViewById(R.id.btnRegistrieren);
        btnLogin.setVisibility(View.INVISIBLE);
        btnRegisterieren.setVisibility(View.INVISIBLE);
        btnLogin.setAlpha(0f);
        btnRegisterieren.setAlpha(0f);

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imgLogo, "alpha", .3f, 1f);
        fadeIn.setDuration(500);

        AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeIn);

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Intent iHome = getIntent();
                if(iHome.getStringExtra(Home.HOME_BACK) == null) {
                    if (ParseUser.getCurrentUser() == null) {
                        SharedPreferences sharedPreferences = Splash.this.getSharedPreferences("de.dev_bros.workoutcenter", Context.MODE_PRIVATE);
                        String autoLogin = sharedPreferences.getString(AUTOLOGIN_KEY, null);
                        if (autoLogin != null) {
                            String username = sharedPreferences.getString(Login.USERNAME_KEY, "");
                            String password = sharedPreferences.getString(Login.PASSWORD_KEY, "");
                            ParseUser.logInInBackground(username, password, new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (e == null) {
                                        Log.i("Auto_login", "Auto Login -> User hat Auto Login an");
                                        Intent i = new Intent(Splash.this, Home.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(i);
                                        Splash.this.finish();
                                    } else {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            btnLogin.setVisibility(View.VISIBLE);
                            btnRegisterieren.setVisibility(View.VISIBLE);

                            btnLogin.animate().alpha(1f).setDuration(500);
                            btnRegisterieren.animate().alpha(1f).setDuration(500);
                        }
                    } else {
                        Log.i("Auto_login", "Auto Login -> Parse user noch Aktiv");
                        Intent i = new Intent(Splash.this, Home.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        Splash.this.finish();
                    }
                }else{
                    btnLogin.setVisibility(View.VISIBLE);
                    btnRegisterieren.setVisibility(View.VISIBLE);

                    btnLogin.animate().alpha(1f).setDuration(500);
                    btnRegisterieren.animate().alpha(1f).setDuration(500);
                }
            }
        });

        mAnimationSet.start();

    }


    public void onClickBtnLogin(View view){
        Log.i("AppGenerell","BtnLogin tabbed");
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

    public void onClickBtnRegister(View view){
        Log.i("AppGenerell","btnRegister tabbed");
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }
}
