package week5.a165036r.com.week5;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;



public class SamplePauseButton implements EntityBase
{
    private Bitmap bmp = null;
    private boolean isDone = false;
    private int xPos, yPos;
    private boolean isPressed = false;

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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.pause);
        xPos = 950;
        yPos = 130;
    }

    @Override
    public void Update(float _dt) {

        //for(EntityBase currEntity : Entity).
        if (TouchManager.Instance.IsDown() && !isPressed)
        {
            // Check collision here!!!
            float imgRadius = bmp.getHeight() * 0.5f;
            if (Collision.SpheretoSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius))
            {
                // Button clicked!
                GameSystem.Instance.setIsPaused(!GameSystem.Instance.getIsPaused());
                isPressed = true;

                if(PauseConfirmDialogFragment.IsShown)
                    return;

                PauseConfirmDialogFragment newPauseConfirm = new PauseConfirmDialogFragment();

                newPauseConfirm.show(gamepage.Instance.getFragmentManager(),"PauseConfirm");

            }


        }
        else if(TouchManager.Instance.isUp() && isPressed)
        {
            isPressed = false;
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        _canvas.drawBitmap(bmp, xPos - bmp.getWidth() * 0.5f, yPos - bmp.getHeight() * 0.5f, null);
    }

    @Override
    public boolean IsInit() {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer() {
        return week5.a165036r.com.week5.LayerConstants.UI_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    public static SamplePauseButton Create()
    {
        SamplePauseButton result = new SamplePauseButton();
        EntityManager.Instance.AddEntity(result);
        return result;
    }
}
