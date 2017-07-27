package com.megasystem.terminales.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.megasystem.terminales.R;
import com.megasystem.terminales.data.app.DUser;
import com.megasystem.terminales.entity.Action;
import com.megasystem.terminales.entity.app.User;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Login extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);
       /* loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goMain();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),R.string.cancel_login,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),R.string.error_login,Toast.LENGTH_LONG).show();
            }
        });*/
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    // get profile information
                                    User user = new DUser(Login.this).get();
                                    if (user == null){
                                        user = new User();
                                    }
                                    String name = "";
                                    String email = "";
                                    String uriPicture = "";
                                    if (object.getString("name") != null) {
                                        name = object.getString("name");
                                        user.setNombre(name);
                                    }
                                    if (object.getString("email") != null) {
                                        email = object.getString("email");
                                        user.setEmail(email);
                                    }
                                    if (object.getString("picture") != null) {

                                        JSONObject imagen = new JSONObject(object.getString("picture"));
                                        //user.setImage(imagen);
                                        JSONObject imagen2 = new JSONObject(imagen.getString("data"));
                                        uriPicture = imagen2.getString("url");
                                    }
                                    TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Login.this.TELEPHONY_SERVICE);
                                    user.setImei( telephonyManager.getDeviceId()) ;
                                    user.setAction(Action.Insert);
                                    DUser dalUser = new DUser(Login.this);
                                    dalUser.save(user);
                                    // redirect to main screen
                                    startActivity(new Intent(Login.this, Main.class));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,gender,birthday,email,picture");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) { Log.d(Login.class.getCanonicalName(), error.getMessage()); }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void goMain() {
        Intent intent = new Intent(this,Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        startActivity(intent);
    }
}
