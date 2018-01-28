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
    private Bitmap ScaledBmp = null;
    private boolean isDone = false;
    private boolean isTouched = false;
    public Vector3 Pos = new Vector3(0,0,0);
    public Vector3 Vel = new Vector3(0,0,0);
    public Vector3 ghostPos = new Vector3(0,0,0);
    private float scaleX,scaleY;
    private float gravity = 0;
    private boolean released = false;
    //vibration for feedback
    private Vibrator _vibrator;
    private int uiPos = 1;
    private float speed = 3;
    private boolean moveForward = false;
    private boolean moveBack = false;
    private boolean isChanging = false;
    private Vector3[] ui = new Vector3[3];
    private int touchCount = 0;


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

        for(int i = 0; i< 3; ++i)
        {
            ui[i] = new Vector3();
        }

        ui[0].set(220,1550,0);
        ui[1].set(520,1100,0);
        ui[2].set(900,1550,0);




        scaleX = scaleY = 1;
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.cans2);
        ScaledBmp = Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*scaleX),(int)(bmp.getHeight()*scaleY),false);
        uiPos = 0;
        Pos.set(ui[uiPos]);
        gravity =100.f;


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

        if(isChanging)
        {
            Vector3 Target = new Vector3(0,0,0);
            if(moveForward) {

                if (uiPos == 2) {

                    uiPos = 0;
                    moveForward = false;
                }
                else
                {
                    uiPos++;
                    moveForward = false;
                }

            }

            else if(moveBack) {

                if (uiPos == 0) {

                    uiPos = 2;
                    moveBack = false;
                }
                else
                {
                    uiPos--;
                    moveBack = false;
                }

            }

            Target.x = ui[uiPos].x - Pos.x;
            Target.y = ui[uiPos].y - Pos.y;


            Pos.x += Target.x * _dt * 6;
            Pos.y += Target.y * _dt * 6;


            if(Pos.distance2(ui[uiPos])/10 < 100)
            {
                isChanging = false;
            }
        }


        if(isTouched )
        {

            // Pos.x = TouchManager.Instance.GetPosX();

            Pos.y = TouchManager.Instance.GetPosY();

        }


        if( TouchManager.Instance.HasTouch()&& !TouchManager.Instance.objectAttached && !released)
        {
            //Check Collision
            float imageRadius = bmp.getHeight() * 0.5f;
            if(Collision.SpheretoSphere(TouchManager.Instance.GetPosX(),TouchManager.Instance.GetPosY(),0.0f,Pos.x,Pos.y,imageRadius) && uiPos == 1)
            {
                //Pos.x = TouchManager.Instance.GetPosX();

                ghostPos.x = TouchManager.Instance.GetPosX();
                ghostPos.y = TouchManager.Instance.GetPosY();
                Vel.set(0,0,0);
                isTouched = true;
                TouchManager.Instance.objectAttached = true;
            }

        }

        if(TouchManager.Instance.IsDown() && touchCount == 0 && !isChanging && !TouchManager.Instance.objectAttached&& TouchManager.Instance.GetPosX()> 800)
        {
            isChanging = true;
            moveForward = true;
            touchCount = 1;
        }

        else if(TouchManager.Instance.IsDown() && touchCount == 0 && !isChanging && !TouchManager.Instance.objectAttached&& TouchManager.Instance.GetPosX()< 300)
        {
            isChanging = true;
            moveBack = true;
            touchCount = 1;
        }

        if(TouchManager.Instance.isUp() && touchCount ==1)
            touchCount = 0;

        if(Vel.x !=0 || Vel.y!=0 )
        {
            Vel.y += gravity *_dt *8.f;
            Pos.x += Vel.x *_dt * 6;
            Pos.y += Vel.y *_dt *6;
            scaleX -= 0.4f*_dt;
            scaleY -= 0.4f*_dt;
            if(scaleX <= 0.7f &&scaleY <= 0.7f)
            {

                scaleX = scaleY = 0.6f;
            }


            ScaledBmp = Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*scaleX),(int)(bmp.getHeight()*scaleY),false);



        }

        else if(TouchManager.Instance.isUp())
        {
            //TouchManager.Instance.objectAttached = false;

            if(isTouched)
            {
                Vel.x = ghostPos.x - Pos.x;
                Vel.y = ghostPos.y - Pos.y;
                released = true;
                //Vel.y += gravity *_dt *1;
            }

            isTouched = false;
        }

        if(Pos.x <=0 || Pos.y > 2000 || Pos.x < 40 )
        {
            Pos.set(ui[uiPos]);
            Vel.set(0,0,0);
            scaleX = 1;
            scaleY = 1;
            ScaledBmp = Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*scaleX),(int)(bmp.getHeight()*scaleY),false);
            TouchManager.Instance.objectAttached = false;
            released = false;
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        // _canvas.scale(0.8f,0.8f);
        //_canvas.drawBitmap(bmp,Pos.x - bmp.getWidth() * 0.4f,Pos.y - bmp.getHeight() * 0.4f,null);
        _canvas.drawBitmap(ScaledBmp,Pos.x - ScaledBmp.getWidth()*0.5f,Pos.y-ScaledBmp.getHeight() *0.5f,null);
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
        return "Trash3";
    }

    @Override
    public float GetPosX() {
        return Pos.x;
    }

    @Override
    public float GetPosY() {return Pos.y;}

    @Override
    public float GetRadius() {
        return bmp.getHeight()* 0.1f;
    }

    @Override
    public void OnHit(Collidable _other) {
        if(_other.GetType() == "binCan" && scaleX <= 0.7f ) {
            //TouchManager.Instance.objectAttached = false;
            Pos.set(ui[uiPos]);
            Vel.set(0,0,0);
            scaleX = 1;
            scaleY = 1;
            ScaledBmp = Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*scaleX),(int)(bmp.getHeight()*scaleY),false);
            TouchManager.Instance.objectAttached = false;
            TouchManager.Instance.isHit = true;
            released = false;
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
