package week5.a165036r.com.week5;

import android.graphics.Canvas;
import android.view.SurfaceView;

//Every entity must implement this
public interface EntityBase
{
    Vector3 Uipos1 = new Vector3(220,1550,0);
    Vector3 Uipos2 = new Vector3(500,1000,0);
    Vector3 Uipos3 = new Vector3(900,1550,0);

    boolean IsDone();
    void setIsDone(boolean _isDone);


    void Init(SurfaceView _view);
    void Update(float _dt);
    void Render(Canvas _canvas);

    boolean IsInit();

    int GetRenderLayer();

    void SetRenderLayer(int _newLayer);

}
