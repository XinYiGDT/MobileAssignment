package week5.a165036r.com.week5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by lim on 1/29/2018.
 */
//Created by XinYi
public class Instruction extends Activity implements View.OnClickListener{

    public final static Instruction Instance = new Instruction();
    //Define buttons
    private Button btn_back;
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

        setContentView(R.layout.instruction);
        btn_back  = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);//set Listener to this button ----> start Button
    }

    //Invoke a callback event in the view
    @Override
    public void onClick(View v) {
        //Intent = action to be performed.
        //Intent is an object that provides runtime binding.
        //new Instance of this object intent

        Intent intent = new Intent();

        if(v == btn_back)
        {
            GameSystem.Instance.setIsPaused(false);
            intent.setClass(this,MainMenu.class);
        }
        startActivity(intent);
    }



}
