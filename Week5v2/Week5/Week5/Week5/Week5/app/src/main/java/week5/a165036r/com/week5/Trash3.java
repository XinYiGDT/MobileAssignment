package week5.a165036r.com.week5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.SurfaceView;

import java.util.Random;

public class Trash3 implements EntityBase , Collidable
{
    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos,yPos,xDir,yDir,lifeTime;
    private boolean isTouched = false;
    private boolean TouchedTrash = false;

    //vibration for feedback
    private Vibrator _vibrator;

    @Override
    public boolean IsDone()
    {
        return isDone;
    }

    @Override
    public void setIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.cans2);
        lifeTime = 5.0f;
        Random randGen = new Random();
        xPos = 900;
        yPos = 1550;
        //TouchedTrash = false;
        //xPos = 500;
        //yPos =500;
        TouchManager.Instance.objectAttached = false;
        //xDir = randGen.nextFloat() * 100.0f -50.0f;
        //yDir = randGen.nextFloat() * 100.0f - 50.0f;

        //If user click on object, object dies

        _vibrator = (Vibrator)_view.getContext().getSystemService (_view.getContext().VIBRATOR_SERVICE);
    }

    public void startVibrate(){
        if (Build.VERSION.SDK_INT >= 26)
        {
            _vibrator.vibrate(VibrationEffect.createOneShot(150,10));
        }
        else{
            long pattern[] = {0 , 50, 0};
            _vibrator.vibrate(pattern, -1);
        }
    }

    @Override
    public void Update(float _dt) {
        //lifeTime -= _dt;
        // if(lifeTime <=0)
        //setIsDone(true);

        //xPos += 2*_dt;
        //TouchedTrash =false;
        if(isTouched)
        {
            xPos = TouchManager.Instance.GetPosX();
            yPos = TouchManager.Instance.GetPosY();
        }

        if( TouchManager.Instance.HasTouch()&& !TouchManager.Instance.objectAttached )
        {
            //Check Collision
            float imageRadius = bmp.getHeight() * 0.5f;
            if(Collision.SpheretoSphere(TouchManager.Instance.GetPosX(),TouchManager.Instance.GetPosY(),0.0f,xPos,yPos,imageRadius))
            {
                isTouched = true;
                TouchManager.Instance.objectAttached = true;
                //xPos = TouchManager.Instance.GetPosX();
                //yPos = TouchManager.Instance.GetPosY();
                // setIsDone(true);
            }
        }

        else if(TouchManager.Instance.isUp())
        {
            TouchManager.Instance.objectAttached = false;
            isTouched = false;
            xPos = 900;
            yPos = 1550;

        }


    }

    @Override
    public void Render(Canvas _canvas) {
        // _canvas.scale(0.8f,0.8f);
        _canvas.drawBitmap(bmp,xPos - bmp.getWidth() * 0.5f,yPos - bmp.getHeight() * 0.5f,null);

    }

    @Override
    public boolean IsInit() {
        return bmp!=null;
    }

    @Override
    public int GetRenderLayer() {
        return 0;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    public static Trash3 Create()
    {
        Trash3 result = new Trash3();
        EntityManager.Instance.AddEntity(result);
        return  result;
    }

    @Override
    public String GetType() {
        return "Trash2";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {return yPos;}

    @Override
    public float GetRadius() {
        return bmp.getHeight()* 0.1f;
    }

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() == "binCan" ) {
            //TouchManager.Instance.objectAttached = false;
            // setIsDone(true);

            //add score
            int currscore = GameSystem.Instance.GetIntFromSave("Score");
            ++currscore;
            GameSystem.Instance.SaveEditBegin();
            GameSystem.Instance.SetIntInSave("Score", currscore);
            GameSystem.Instance.SaveEditEnd();

            startVibrate();
            AudioManager.Instance.PlayAudio(R.raw.eating1);
        }
        else
        {
            AudioManager.Instance.PlayAudio(R.raw.cough);
        }

    }
}
