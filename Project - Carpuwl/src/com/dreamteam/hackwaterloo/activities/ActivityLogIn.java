package com.dreamteam.hackwaterloo.activities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.User;
import com.dreamteam.hackwaterloo.utils.server.CreateUserTask;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class ActivityLogIn extends SherlockFragmentActivity implements OnClickListener, TextWatcher {
    
    private static final String TAG = ActivityLogIn.class.getSimpleName();
    private static final String SHARED_PREF_LOG_IN = "com.dreamteam.hackwaterloo.fileNameSharedPrefLogin";
    private static final String KEY_LOGGED_IN = "com.dreamteam.hackwaterloo.keyLoggedIn";
    private static final int PHONE_NUMBER_LENGTH = 10;
    
    private Button mButtonFacebook;
    private EditText mEditTextPhoneInput;
    
    private SharedPreferences mSharedPref;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        
        mButtonFacebook = (Button) findViewById(R.id.log_in_button_facebook);
        mEditTextPhoneInput = (EditText) findViewById(R.id.log_in_edittext_phone_input);
        
        mButtonFacebook.setOnClickListener(this);
        mButtonFacebook.setEnabled(false);
        mEditTextPhoneInput.addTextChangedListener(this);
        mEditTextPhoneInput.setInputType(InputType.TYPE_CLASS_PHONE);
        
        mSharedPref = getSharedPreferences(SHARED_PREF_LOG_IN, MODE_PRIVATE);
        if (mSharedPref.getBoolean(KEY_LOGGED_IN, false)) {
            Log.d("ryan", "auto login");
        	submiteFacebookRequest();
        }
        logKeyHash();
    }
    
    private void logKeyHash() {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.dreamteam.hackwaterloo", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        	case R.id.log_in_button_facebook:
        		  submiteFacebookRequest();
        	}
    }
    
    private void submiteFacebookRequest() {
        Log.i(TAG, "attempt facebook login");
        Session.openActiveSession(this, true, new Session.StatusCallback() {

          @SuppressWarnings("deprecation")
          @Override
          public void call(Session session, SessionState statel, Exception exception) {
            if (session.isOpened()) {

              Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

                @Override
                public void onCompleted(GraphUser user, Response response) {
                  if (user != null) {
                      Log.d("Log","Hello " + user.getName() + "!");
                      Log.d("Log", "Hello " + user.getId() );
                      
                      // Get details, set the user data object
                      User.initInstance(Integer.parseInt(user.getId()), user.getName(), mEditTextPhoneInput.getText().toString());
                      
                      create_user(Integer.parseInt(user.getId()),user.getName(), mEditTextPhoneInput.getText().toString());
                      
                      mSharedPref.edit().putBoolean(KEY_LOGGED_IN, true).commit();
                      Intent intent = new Intent(ActivityLogIn.this, ActivityMain.class);  
                      startActivityForResult(intent, 1);
                  }
                }
              });
            }
            else{
                Log.d("Facebook", "Not logged in");
            }
          }
        });
    }
    
    private void create_user(int fb_pk, String name, String phone){
    	CreateUserTask createUserTask = new CreateUserTask(name, phone, fb_pk);
    	createUserTask.executeParallel();	
    }

    @Override
    public void afterTextChanged(Editable arg0) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence input, int start, int before, int count) {
        mButtonFacebook.setEnabled(input.length() == PHONE_NUMBER_LENGTH);
    }
    
}
