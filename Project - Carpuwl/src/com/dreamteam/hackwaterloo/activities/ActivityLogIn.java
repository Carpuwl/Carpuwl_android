package com.dreamteam.hackwaterloo.activities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Intent;
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
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.dreamteam.carpuwl.R;
import com.dreamteam.hackwaterloo.User;
import com.dreamteam.hackwaterloo.utils.server.CreateUserTask;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

public class ActivityLogIn extends SherlockFragmentActivity implements OnClickListener, TextWatcher {
    
    private static final String TAG = ActivityLogIn.class.getSimpleName();
    private static final String SHARED_PREF_LOG_IN = "com.dreamteam.hackwaterloo.fileNameSharedPrefLogin";
    private static final String KEY_LOGGED_IN = "com.dreamteam.hackwaterloo.keyLoggedIn";
    private static final int PHONE_NUMBER_LENGTH = 10;
    
    private EditText mEditTextPhoneInput;
    private LoginButton loginButton;
    
    private UiLifecycleHelper uiHelper;
    
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        
        LoginButton loginButton = (LoginButton) findViewById(R.id.authButton);
        mEditTextPhoneInput = (EditText) findViewById(R.id.log_in_edittext_phone_input);
        
        //loginButton.setEnabled(false);
        mEditTextPhoneInput.addTextChangedListener(this);
        mEditTextPhoneInput.setInputType(InputType.TYPE_CLASS_PHONE);
        
        logKeyHash();
        
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
    }
    
    private void logKeyHash() {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.dreamteam.carpuwl", PackageManager.GET_SIGNATURES);
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
    
    @SuppressWarnings("deprecation")
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            
            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
				
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if(user != null){
						User.initInstance(Integer.parseInt(user.getId()), user.getName(), mEditTextPhoneInput.getText().toString());
						create_user(Integer.parseInt(user.getId()), user.getUsername(), mEditTextPhoneInput.getText().toString());
					}					
				}
			});
            
            Intent intent = new Intent(ActivityLogIn.this, ActivityMain.class);
            startActivity(intent);
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
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
        //loginButton.setEnabled(input.length() == PHONE_NUMBER_LENGTH);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
    
}
