package week5.a165036r.com.week5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Random;

public class binGeneral implements EntityBase, Collidable
{
    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos,yPos,xDir,yDir,lifeTime, movementSpeed, ranPos;
    private Sprite spritesheet = null;

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
    public void Init(SurfaceView _view)
    {
        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.bin03_anim);
        lifeTime = 3.0f;
        Random ranGen = new Random();

        spritesheet = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.bin03_anim), 1, 4, 5);

        ranPos = ranGen.nextInt(2)+1;
        // xPos = 1000.f;//ranGen.nextFloat()* _view.getWidth();
        // xDir = -1.f;//ranGen.nextFloat()*-1.0f+1.0f;//ranGen.nextFloat()*100.0f - 50.0f;
        yPos = 700.f;
        yDir = 0;

        if(ranPos ==1)
        {
            xPos = 10.f;
            xDir = 1.0f;
        }
        else
        {
            xPos = 1000.f;
            xDir = -1.0f;
        }


        movementSpeed =100.f;
    }

    @Override
    public void Update(float _dt)
    {
        lifeTime -=_dt;
        if(lifeTime <=0.0f)
        {

            setIsDone(true);
        }

        xPos += xDir*_dt*movementSpeed;
        spritesheet.Update(_dt);
        //yPos +=yDir*_dt;

        //if user click on the object, remove the object (it dies)
        //if(android.getTouch.... etc)
        //handle the touch and check collision with click/touch
        /*if(TouchManager.Instance.IsDown())
        {
            float imgRadius = bmp.getHeight() * 0.5f;
            if(Collision.SphereToSphere(TouchManager.Instance.GetPosX(),TouchManager.Instance.GetPosY(),0.0f,xPos,yPos,imgRadius))
            {
                SetIsDone(true);
            }
        }*/
    }

    @Override
    public void Render(Canvas _canvas)
    {
       // _canvas.drawBitmap(bmp,xPos-bmp.getWidth()*0.5f,yPos-bmp.getHeight()*0.5f,null);
        spritesheet.Render(_canvas, (int)xPos, (int)yPos);
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

    public static binGeneral Create()
    {
        //SampleEntity.Create();
        binGeneral result = new binGeneral();
        EntityManager.Instance.AddEntity(result);
        return result;
    }

    @Override
    public String GetType() {
        return "binGeneral";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {
        return bmp.getHeight()*0.1f;
    }

    @Override
    public void OnHit(Collidable _other)
    {
        //if(_other.GetType()=="SampleEntity")
        //{
        //     SetIsDone(true);
        // }
    }
}

