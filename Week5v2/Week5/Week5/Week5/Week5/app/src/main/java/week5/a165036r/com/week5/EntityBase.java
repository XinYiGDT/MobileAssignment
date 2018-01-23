package week5.a165036r.com.week5;

import android.graphics.Canvas;
import android.view.SurfaceView;

//Every entity must implement this
public interface EntityBase
{
    boolean IsDone();
    void setIsDone(boolean _isDone);


    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render(Canvas _canvas);

    boolean IsInit();

    int GetRenderLayer();

    void SetRenderLayer(int _newLayer);
}
