package com.example.asus.androidscruminftel.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.asus.androidscruminftel.AndroidScrumINFTELActivity;
import com.example.asus.androidscruminftel.R;
import com.example.asus.androidscruminftel.connection.PostHttp;
import com.example.asus.androidscruminftel.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks,
        View.OnClickListener{

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private String userJson;
    private SharedPreferences sharedPref;
    String stringUrl = "http://192.168.1.148:8080/sigin";
    //String stringUrl = "http://secureuma.no-ip.org:8080/sigin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref =  getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String email = sharedPref.getString("email", "");
        String username = sharedPref.getString("username", "");
        String image = sharedPref.getString("image","");
        if (!email.equals("")) {
            AndroidScrumINFTELActivity.getInstance().setEmail(email);
            AndroidScrumINFTELActivity.getInstance().setUserName(username);
            AndroidScrumINFTELActivity.getInstance().setPhotoUrl(image);

            Intent intent = new Intent(this,MyProjectsActivity.class);
            startActivity(intent);

        }else{




            setContentView(R.layout.activity_login);
            findViewById(R.id.sign_in_button).setOnClickListener(this);

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            AndroidScrumINFTELActivity.getInstance().setmGoogleApiClient(new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build());
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(AndroidScrumINFTELActivity.getInstance().getmGoogleApiClient());
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            AndroidScrumINFTELActivity.getInstance().setUserName(acct.getDisplayName());
            AndroidScrumINFTELActivity.getInstance().setEmail(acct.getEmail());
            if(acct.getPhotoUrl() == null){
                AndroidScrumINFTELActivity.getInstance().setPhotoUrl("http://cdn2.iconfinder.com/data/icons/ios-7-icons/50/user_male2-512.png");
            }else {
                AndroidScrumINFTELActivity.getInstance().setPhotoUrl(acct.getPhotoUrl().toString());
            }

            Log.i("es", acct.getEmail());
//(String email, String idusuario, String nombre, String refreshToken)

            String urlImage;

            if(acct.getPhotoUrl()==null){
                urlImage="";
            } else{
                urlImage=acct.getPhotoUrl().toString();
            }

            User user;
            user = new User(acct.getDisplayName(), acct.getEmail(), urlImage);
            userJson = user.toJSON();

            sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("email", acct.getEmail());
            editor.putString("username", acct.getDisplayName());
            if(acct.getPhotoUrl() == null){
                editor.putString("image", "http://cdn2.iconfinder.com/data/icons/ios-7-icons/50/user_male2-512.png");
            }else {

                editor.putString("image",acct.getPhotoUrl().toString());
            }
            editor.commit();

            new PostHttp(getBaseContext()).execute(stringUrl,userJson.toString());

            Intent intent = new Intent(this,MyProjectsActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), R.string.common_google_play_services_sign_in_failed_text, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

}