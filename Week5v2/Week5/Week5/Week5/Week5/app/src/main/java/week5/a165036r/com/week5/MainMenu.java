package week5.a165036r.com.week5;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainMenu extends Activity implements View.OnClickListener{

    //Define buttons
    private Button btn_start;
    private Button btn_credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        //Hide topMenu(Battery etc.)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.mainmenu);
        btn_start  = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);//set Listener to this button ----> start Button


    }

//Invoke a callback event in the view
    @Override
    public void onClick(View v) {
        //Intent = action to be performed.
        //Intent is an object that provides runtime binding.
        //new Instance of this object intent

        Intent intent = new Intent();

        if(v == btn_start)
        {
            intent.setClass(this,gamepage.class);
        }
        startActivity(intent);
    }
}
