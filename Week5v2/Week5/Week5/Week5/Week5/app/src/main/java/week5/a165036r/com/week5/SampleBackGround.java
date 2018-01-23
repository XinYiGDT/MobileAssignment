package week5.a165036r.com.week5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class SampleBackGround implements EntityBase
{

    private Bitmap bmp = null;
    private boolean isDone = false;
    private float xPos,yPos,offset;
    private SurfaceView view = null;
    private int renderLayer = 0;
    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void setIsDone(boolean _isDone) {
        isDone = _isDone;
    }


    @Override
    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.gamescene2);
        view = _view;
        offset = 0.0f;
    }

    @Override
    public void Update(float _dt) {
        offset += _dt*0.1f;
    }

    @Override
    public void Render(Canvas _canvas) {
        xPos = 0.5f * view.getWidth()*0.5f;
        yPos = 0.5f * view.getHeight()*1.0f;
        float xOffset = (float) Math.sin(offset)*bmp.getWidth()*0.3f;

        _canvas.drawBitmap(bmp,xPos-bmp.getWidth()*0.5f+xOffset,yPos-bmp.getHeight()*0.5f,null);

    }

    @Override
    public boolean IsInit() {
        return false;
    }

    @Override
    public int GetRenderLayer() {
        return renderLayer;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        renderLayer = _newLayer;
    }
    public static SampleBackGround Create()
    {
        SampleBackGround result = new SampleBackGround();
        EntityManager.Instance.AddEntity(result);
        return result;
    }
}
