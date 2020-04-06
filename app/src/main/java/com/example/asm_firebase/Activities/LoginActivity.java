package com.example.asm_firebase.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asm_firebase.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    public static final int RC_GOOGLE = 0;
    private static final String EMAIL = "email";
    private ImageView imgCloud, imgStar;
    private GoogleSignInClient mGoogleSignInClient;
    private Animation animCloud, animStar;
    private Button btnLogin;
    private SignInButton btnGoogle;
    private CallbackManager callbackManager;
    private LoginButton btnFaceBook;
    private EditText edtUserName, edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        imgCloud = (ImageView) findViewById(R.id.login_ImgCloud);
        imgStar = (ImageView) findViewById(R.id.login_ImgStar);
        edtUserName = (EditText) findViewById(R.id.login_EdtUsername);
        edtPass = (EditText) findViewById(R.id.login_EdtPassword);
        btnLogin = (Button) findViewById(R.id.login_BtnLogin);
        btnGoogle = (SignInButton) findViewById(R.id.login_BtnGoogle);
        btnFaceBook = (LoginButton) findViewById(R.id.login_BtnFaceBook);

        btnGoogle.setSize(SignInButton.SIZE_STANDARD);
        FacebookSdk.sdkInitialize(getApplicationContext());
        btnFaceBook.setReadPermissions(Arrays.asList(EMAIL));
        callbackManager = CallbackManager.Factory.create();
        animCloud = AnimationUtils.loadAnimation(this, R.anim.animcloud);
        animStar = AnimationUtils.loadAnimation(this, R.anim.animstar);
        imgCloud.startAnimation(animCloud);
        imgStar.startAnimation(animStar);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUserName.getText().toString().isEmpty() ||
                        edtPass.getText().toString().isEmpty()) {
                    toast("Vui lòng điền đầy đủ thông tin");
                } else {
                    String userName = edtUserName.getText().toString();
                    String pass = edtPass.getText().toString();
                    if (userName.equalsIgnoreCase("ps10674") &&
                            pass.equalsIgnoreCase("123456")) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("type","local");
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });


        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.login_BtnGoogle:
                        signIn();
                        break;
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnFaceBook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {

            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE);
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (ApiException e) {

        }
    }

    public void getKeyHast() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.projects.projectsample",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    public void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
