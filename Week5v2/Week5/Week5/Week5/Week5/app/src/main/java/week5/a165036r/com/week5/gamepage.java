package week5.a165036r.com.week5;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class gamepage extends Activity
{

    public static gamepage Instance = null;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Instance = this;
        try {
            setContentView(new GameView(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public  boolean onTouchEvent(MotionEvent event)
    {
        int x = (int) event.getX();
        int y = (int) event.getY();

        TouchManager.Instance.Update(x,y,event.getAction());

        return true;

    }
}

