package week5.a165036r.com.week5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Random;

public class SampleEntity implements EntityBase , Collidable
{
    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos,yPos,xDir,yDir,lifeTime;
    private boolean isTouched = false;
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
        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.ship2_1);
        lifeTime = 5.0f;
        Random randGen = new Random();


        xPos = randGen.nextFloat() * _view.getWidth();
        yPos = randGen.nextFloat() * _view.getHeight();

        //xDir = randGen.nextFloat() * 100.0f -50.0f;
        //yDir = randGen.nextFloat() * 100.0f - 50.0f;

        //If user click on object, object dies

    }

    @Override
    public void Update(float _dt) {
        //lifeTime -= _dt;
       // if(lifeTime <=0)
            //setIsDone(true);



        if(isTouched )
        {
            xPos = TouchManager.Instance.GetPosX();
            yPos = TouchManager.Instance.GetPosY();
        }

        if( TouchManager.Instance.HasTouch()  && !TouchManager.Instance.objectAttached )
        {
            //Check Collision
            float imageRadius = bmp.getHeight() * 0.5f;
            TouchManager.Instance.objectAttached = true;
            if(Collision.SpheretoSphere(TouchManager.Instance.GetPosX(),TouchManager.Instance.GetPosY(),0.0f,xPos,yPos,imageRadius))
            {
                isTouched = true;
                //xPos = TouchManager.Instance.GetPosX();
                //yPos = TouchManager.Instance.GetPosY();
               // setIsDone(true);
            }

        }

        else if(TouchManager.Instance.isUp())
        {
            isTouched = false;
            TouchManager.Instance.objectAttached = false;
        }


    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(bmp,xPos - bmp.getWidth() * 0.5f,yPos - bmp.getHeight() * 0.5f,null);
    }

    @Override
    public boolean IsInit() {
        return false;
    }

    @Override
    public int GetRenderLayer() {
        return 0;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {

    }

    public static SampleEntity Create()
    {
        SampleEntity result = new SampleEntity();
        EntityManager.Instance.AddEntity(result);
        return  result;
    }

    @Override
    public String GetType() {
        return "SampleEntity";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {return yPos;}

    @Override
    public float GetRadius() {
        return bmp.getHeight()* 0.5f;
    }

    @Override
    public void OnHit(Collidable _other) {
      //  if(_other.GetType() == "SampleEntity")
            //setIsDone(true);
    }
}

