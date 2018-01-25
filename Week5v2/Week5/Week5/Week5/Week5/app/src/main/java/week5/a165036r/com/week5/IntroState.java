package week5.a165036r.com.week5;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceView;

public class IntroState implements StateBase
{

    private float timer = 3.0f;
    private float rotate = 180.f;
    private Bitmap logo = null;


    @Override
    public String GetName() {
        return "Default";
    }

    @Override
    public void OnEnter(SurfaceView _view) {
        logo =  ResourceManager.Instance.GetBitmap(R.drawable.bin01);
    }

    @Override
    public void OnExit() {

    }

    @Override
    public void Render(Canvas _canvas) {
        //RenderLogo
        Matrix transform = new Matrix();
        transform.postTranslate(-logo.getWidth() *0.5f,-logo.getHeight() *0.5f);
        transform.postRotate(rotate);
        transform.postScale(timer,timer);
        transform.postTranslate(_canvas.getWidth() *0.5f,_canvas.getHeight() *0.5f);
        _canvas.drawBitmap(logo,transform,null);
    }

    @Override
    public void Update(float _dt) {
        timer -= _dt;
        rotate -= 5*_dt * 100;
        if(timer <=0.0f)
            StateManager.Instance.ChangeState("MainGame");
    }
}

