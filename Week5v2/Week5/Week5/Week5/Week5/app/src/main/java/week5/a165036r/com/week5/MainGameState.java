package week5.a165036r.com.week5;

import android.graphics.Canvas;
import android.view.SurfaceView;

public class MainGameState implements StateBase
{
    //public final static MainGameState Instance = new MainGameState();
    float Timer = 0.0f;
    boolean binCanOnce = true;
    boolean binPlasticOnce = true;
    boolean binGeneralOnce = true;
    boolean pause  = false;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        EntityManager.Instance.init(_view);
        TouchManager.Instance.init();
        SampleBackGround.Create();

        SamplePauseButton.Create();
        Trash1.Create();
       // trash2.Create();
       // Trash3.Create();
        binPlastic.Create();
        AudioManager.Instance.PlayAudio((R.raw.fliesbuzzing));
    }

    @Override
    public void Update(float _dt) {
        Timer += _dt;

        if(!GameSystem.Instance.getIsPaused()) {

            if(Timer> 3 )
            {
                //binPlastic.Create();
                Timer = 0;
            }


//            if (Timer > 3.0f && binCanOnce) {
//                //SampleEntity.Create();
//                binCan.Create();
//                binCanOnce = false;
//                binPlasticOnce = true;
//                //timer = 0.0f;
//            } else if (Timer > 6.0f && binPlasticOnce) {
//                //SampleEntity.Create();
//                binPlastic.Create();
//                binPlasticOnce = false;
//                binGeneralOnce = true;
//                //timer = 0.0f;
//            } else if (Timer > 9.0f && binGeneralOnce) {
//                //SampleEntity.Create();
//                binGeneral.Create();
//                binGeneralOnce = false;
//                binCanOnce = true;
//                Timer = 0.0f;
//            }
            EntityManager.Instance.Update(_dt);

            if( TouchManager.Instance.IsDown() && TouchManager.Instance.touchCount == 0 && !TouchManager.Instance.objectAttached)
            {
                //System.out.println("hit");
                TouchManager.Instance.touchCount = 1;
                TouchManager.Instance.goForward = true;
                TouchManager.Instance.isChanging = true;
            }

            else if(TouchManager.Instance.isUp() && TouchManager.Instance.touchCount == 1)
            {
                TouchManager.Instance.touchCount = 0;
            }


        }
        else
        {
            EntityManager.Instance.UpdatePauseButton(_dt);
        }
    }

    @Override
    public void Render(Canvas _canvas) {
        EntityManager.Instance.Render(_canvas);
    }

    @Override
    public void OnExit() {

        Timer = 0.0f;
        binCanOnce = true;
        binPlasticOnce = true;
        binGeneralOnce = true;
        pause  = false;
    }
}
