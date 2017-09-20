package com.example.yonny.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient googleApiClient;
    private ImageButton signInButtonGoogle;//Google :V
    private ImageButton loginButton;
    private CallbackManager callbackManager;
    public static final int ini = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        KenBurnsView kbv = (KenBurnsView) findViewById(R.id.image);
        kbv.setTransitionListener(new KenBurnsView.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }
            @Override
            public void onTransitionEnd(Transition transition) {
            }
        });

        ////////LOGINN GOOGLE///
        GoogleSignInOptions go = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, go)
                .build();

        signInButtonGoogle = (ImageButton) findViewById(R.id.iniciar);
        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, ini);

            }
        });


        ////////LOGIN FACEBOOK///
        callbackManager = CallbackManager.Factory.create();
        loginButton = (ImageButton) findViewById(R.id.loginButton);
/*
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goMainScreen();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });*/
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ini) {
            GoogleSignInResult res = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSigInResult(res);
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    private void handleSigInResult(GoogleSignInResult res) {

        if (res.isSuccess()) {
            goMainScreen();
        } else {
            Toast.makeText(this, "No se ha podido iniciar Sesion", Toast.LENGTH_SHORT).show();
        }
    }
    /////google////
    private void goMainScreen() {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void omitirLogin(View view){
        Intent intent2 = new Intent(this,Main2Activity.class);
        startActivity(intent2);
    }
}

