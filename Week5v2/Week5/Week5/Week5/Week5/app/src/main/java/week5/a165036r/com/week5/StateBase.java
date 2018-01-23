package week5.a165036r.com.week5;

import android.graphics.Canvas;
import android.view.SurfaceView;

/**
 * Created by 165036R on 1/9/2018.
 */

public interface StateBase {
    String GetName();

    void OnEnter(SurfaceView _view);
    void OnExit();
    void Render(Canvas _canvas);
    void Update(float _dt);
}
