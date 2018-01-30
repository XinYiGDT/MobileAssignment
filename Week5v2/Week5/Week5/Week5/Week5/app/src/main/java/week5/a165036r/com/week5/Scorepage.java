package week5.a165036r.com.week5;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;
import java.util.List;


//Created by XinYi
public class Scorepage extends Activity implements OnClickListener{

    private Button btn_back;

    int highscore = 0;

    // Facebook UI
    private Button btn_fbLogin;
    private Button btn_sharescore;

    boolean loggedin = false;
    private CallbackManager callbackManager;
    private LoginManager loginManager;

    TextView HighScore;

    ProfilePictureView profile_pic;

    List<String> PERMISSIONS = Arrays.asList("publish_actions");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        // Initalize for FB
        FacebookSdk.setApplicationId("148602315800459");
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        //AppEventsLogger.activateApp(this);


        setContentView(R.layout.scorepage);

       // requestWindowFeature(Window.FEATURE_NO_TITLE);// hide title
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //hide top bar

        // Define for back button
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        // Define fb button
        btn_fbLogin = (LoginButton) findViewById(R.id.fb_login_button);
        btn_fbLogin.setOnClickListener(this);

        // Define scoreshare button
        btn_sharescore = (Button) findViewById(R.id.btn_sharescore);
        btn_sharescore.setOnClickListener(this);

        profile_pic = (ProfilePictureView)findViewById(R.id.picture);
        callbackManager = CallbackManager.Factory.create();

        highscore =  100;//GameSystem.Instance.GetIntFromSave("Score");

        HighScore = (TextView)findViewById(R.id.score);

        if(HighScore != null)
        {
            String temp = "HighScore: " + highscore;
            HighScore.setText(temp);
        }

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    //User logged out
                    profile_pic.setProfileId("");
                } else {
                    profile_pic.setProfileId(Profile.getCurrentProfile().getId());
                }
            }
        };

        accessTokenTracker.startTracking();

        loginManager = LoginManager.getInstance();
        loginManager.logInWithPublishPermissions(this, PERMISSIONS);

        AccessTokenTracker accessTokenTracker1 = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null)
                {
                    profile_pic.setProfileId("");
                }
            }
        };

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker profileTracker;
            @Override
            public void onSuccess(LoginResult loginResult) {
                if(Profile.getCurrentProfile() == null)
                {
                    profile_pic.setProfileId("");
                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            profileTracker.stopTracking();
                            profile_pic.setProfileId(Profile.getCurrentProfile().getId());
                        }
                    };
                }
                else
                    profile_pic.setProfileId(Profile.getCurrentProfile().getId());
                shareScore();
            }
            @Override
            public void onCancel() {
                System.out.println("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                System.out.println("Login attempt failed.");
            }
        });

    }



    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        if (v == btn_back) {
            intent.setClass(this, MainMenu.class);
            startActivity(intent);
        }

        else if(v == btn_sharescore ) {

            AlertDialog.Builder alert_builder = new AlertDialog.Builder(Scorepage.this);

            alert_builder.setTitle("Share score on facebook");
            alert_builder.setMessage("Do you want to share your score of " + String.valueOf(highscore))
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            shareScore();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });


            alert_builder.show();

        }

    }

    // To share info on FB
    public void shareScore(){

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);


        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Thank you for playing MGP2017. Your final score is " + highscore)
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        ShareApi.share(content, null);
    }

    // FB to use the callback Manager to manage login
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //finish();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        //finish();
        super.onStop();
    }
}
