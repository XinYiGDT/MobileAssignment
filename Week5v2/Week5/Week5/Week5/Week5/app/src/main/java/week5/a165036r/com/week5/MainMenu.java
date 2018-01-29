package week5.a165036r.com.week5;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainMenu extends Activity implements View.OnClickListener{

    public final static MainMenu Instance = new MainMenu();
    //Define buttons
    private Button btn_start;
    private Button btn_ranking;
    private Button btn_instruction;
    //private final static Bundle Instance = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          //savedInstanceState;
        //Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        //Hide topMenu(Battery etc.)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.mainmenu);
        btn_start  = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);//set Listener to this button ----> start Button

        btn_instruction  = (Button)findViewById(R.id.btn_instruction);
        btn_instruction.setOnClickListener(this);

        btn_ranking  = (Button)findViewById(R.id.btn_ranking);
        btn_ranking.setOnClickListener(this);


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
            GameSystem.Instance.setIsPaused(false);
            intent.setClass(this,gamepage.class);
        }
        else if(v == btn_ranking)
        {
            GameSystem.Instance.setIsPaused(false);
            intent.setClass(this,Scorepage.class);
        }
        else if(v == btn_instruction)
        {
            GameSystem.Instance.setIsPaused(false);
            intent.setClass(this,Instruction.class);
        }
        startActivity(intent);
    }



}
