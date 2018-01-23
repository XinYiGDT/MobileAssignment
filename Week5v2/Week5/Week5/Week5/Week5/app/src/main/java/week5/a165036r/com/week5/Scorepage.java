package week5.a165036r.com.week5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.Arrays;
import java.util.List;



/**
 * Created by 163935T on 1/23/2018.
 */

public class Scorepage extends Activity implements OnClickListener {

    private Button btn_back;
    private Button btn_fbLogin;
    private Button btn_sharescore;

    boolean loggedin = false;
    private CallbackManager _callbackManager;
    private LoginManager _loginmanager;

    ProfilePictureView _profile_pic;

    int highscore = 0;

    List<String> PERMISSIONS = Arrays.asList("publish_actions");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitalize(this.getApplicationContext());
        setContent.View(R.layout.scorepage);

        btn_back = (Button) findViewById(R.id.btn_start);
        btn_back.setOnClickListener(this);

        btn_fbLogin = (Button) findViewById(R.id.fb_login_button);
        btn_fbLogin.setOnClickListener(this);

        btn_sharescore = (Button) findViewById(R.id.btn_sharescore);
        btn_sharescore.setOnClickListener(this);

        _profile_pic = (ProfilePictureView) findViewById(R.id.picture);
        _callbackManager = CallbackManager.Factory.create();

        //highscore= GameSystem.Instance.GetTntFromSave("Score"):

        AccessTokenTracker _accesstokentracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    _profile_pic.setProfileId(" ");
                } else {
                    _profile_pic.setProfileId(Profile.getCurrentProfile().getId());
                }
            }
        };
        _accesstokentracker.startTracking();

        _loginmanager = LoginManager.getInstance();
        _loginmanager.logInWithPublishPermissions(this, PERMISSIONS);

        _loginmanager.registerCallback(_callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                _profile_pic.setProfileId(Profile.getCurrentProfile().getId());
                //call method to share score
            }

            @Override
            public void onCancel() {
                System.out.println("Login attempt cancelled");
                //or print text on your screen using paint()
            }

            @Override
            public void onError(FacebookException error) {
                System.out.println("Login attempt failed");
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent _intent = new Intent();
        if (v == btn_back) {
            _intent.setClass(this, MainMenu.class);
            startActivity(_intent);
        } else if (v == btn_sharescore) {
            AlertDialog.Builder _alertBuilder = new AlertDialog.Builder(Scorepage.this);
            _alertBuilder.setTitle("Share on Facebook?");
            _alertBuilder.setMessage("Do you want to share on Facebook?");
            _alertBuilder.setCancelable(false);
            _alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //call method to score
                    shareScore();
                }
            });
            _alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //dialog cancel
                    dialog.cancel();
                }
            });
            _alertBuilder.show();
        }
    }

    public void shareScore() {
        Bitmap _image = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);

        SharePhoto _photo = new SharePhoto.Builder().setBitmap(_image).setCaption("Thank you for playing XX! Your highscore is " + highscore).build();

        SharePhotoContent _content = new SharePhotoContent.Builder().addPhoto(_photo).build();

        ShareApi.share(_content, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestory() {
        super.onDestroy();
    }


}
