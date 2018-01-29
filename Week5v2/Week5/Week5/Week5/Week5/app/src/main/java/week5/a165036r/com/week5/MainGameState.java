package week5.a165036r.com.week5;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class MainGameState extends Activity implements StateBase
{
    //public final static MainGameState Instance = new MainGameState();
    float Timer = 0.0f;
    float Timer2 = 2.0f;
    boolean binCanOnce = true;
    boolean binPlasticOnce = true;
    boolean binGeneralOnce = true;
    boolean health1 = true;
    boolean health2 = true;
    boolean health3 = true;
    boolean pause  = false;
    private Bitmap bmp1 = null;
    private Bitmap bmp2 = null;
    private Bitmap bmp3 = null;
    private Bitmap ScaledBmp1 = null;
    private Bitmap ScaledBmp2 = null;
    private Bitmap ScaledBmp3 = null;

    float posSX = 0.0f,posSY = 0.0f;
    float posBin1X = 0.0f,posBinY = 0.0f;
    float posBin2X = 0.0f;
    float posBin3X = 0.0f;
    float scaleX=0.0f,scaleY = 0.0f;
    int score = 0;
    String scoreText= "0";
    boolean missed = false;
    int missCount = 0;

    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        EntityManager.Instance.init(_view);
        SampleBackGround.Create();

        SamplePauseButton.Create();
        Trash1.Create();
        trash2.Create();
        Trash3.Create();

        score =  0;
        posSX = 100;
        posSY = 130;
        posBinY = 130;
        posBin1X = 60;
        posBin2X = 170;
        posBin3X = 290;


        scaleX=scaleY = 0.5f;

        bmp1 = BitmapFactory.decodeResource(_view.getResources(),R.drawable.bin01);
        bmp2 = BitmapFactory.decodeResource(_view.getResources(),R.drawable.bin02);
        bmp3 = BitmapFactory.decodeResource(_view.getResources(),R.drawable.bin03);

        ScaledBmp1 = Bitmap.createScaledBitmap(bmp1,(int)(bmp1.getWidth()*scaleX),(int)(bmp1.getHeight()*scaleY),false);
        ScaledBmp2 = Bitmap.createScaledBitmap(bmp2,(int)(bmp2.getWidth()*scaleX),(int)(bmp2.getHeight()*scaleY),false);
        ScaledBmp3 = Bitmap.createScaledBitmap(bmp3,(int)(bmp3.getWidth()*scaleX),(int)(bmp3.getHeight()*scaleY),false);

        AudioManager.Instance.PlayAudio((R.raw.fliesbuzzing));
    }

    @Override
    public void Update(float _dt) {
        Timer += _dt;

        score = GameSystem.Instance.GetIntFromSave("Score");
        missed = GameSystem.Instance.getMissed();

        if(!GameSystem.Instance.getIsPaused()) {

           if (Timer > 3.0f && binCanOnce) {
                //SampleEntity.Create();
                binCan.Create();
                binCanOnce = false;
                binPlasticOnce = true;
                //timer = 0.0f;
            } else if (Timer > 12.0f && binPlasticOnce) {
                //SampleEntity.Create();
                binPlastic.Create();
                binPlasticOnce = false;
                binGeneralOnce = true;
                //timer = 0.0f;
            } else if (Timer > 18.0f && binGeneralOnce) {
                //SampleEntity.Create();
                binGeneral.Create();
                binGeneralOnce = false;
                binCanOnce = true;
                Timer = 0.0f;
            }

            if(missed)
            {
                missCount+=1;
                GameSystem.Instance.setIsMissed(false);
                missed = false;
            }

            if(missCount == 1)
            {
                health1 = false;
            }
            else if(missCount == 2)
            {
                health2 = false;
            }
            else if(missCount == 3)
            {
                health3 = false;
               Timer2 -= _dt;
                //StateManager.Instance.ChangeState("ScorePage");
            }

            if(Timer2<=0)
            {
                if(EndGameConfirmDialogFragment.IsShown)
                    return;

                EndGameConfirmDialogFragment newEndGameConfirm = new EndGameConfirmDialogFragment();

                newEndGameConfirm.show(gamepage.Instance.getFragmentManager(),"EndGameConfirm");
               // StateManager.Instance.ChangeState("ScorePage");
            }

//scoreText =  String.format("Score:%.2fm", score);

            EntityManager.Instance.Update(_dt);
        }
        else
        {
            EntityManager.Instance.UpdatePauseButton(_dt);
        }
    }

    @Override
    public void Render(Canvas _canvas) {
//        Paint paint = new Paint();
//        paint.setColor(Color.BLACK);
//        paint.setTextSize(50);
//        _canvas.drawText(scoreText, posSX, posSY, paint);

        EntityManager.Instance.Render(_canvas);

        if(health3) {
            _canvas.drawBitmap(ScaledBmp1, posBin1X - ScaledBmp1.getWidth() * 0.5f, posBinY - ScaledBmp1.getHeight() * 0.5f, null);
        }
        if(health2) {
            _canvas.drawBitmap(ScaledBmp2, posBin2X - ScaledBmp2.getWidth() * 0.5f, posBinY - ScaledBmp2.getHeight() * 0.5f, null);
        }
        if(health1) {
            _canvas.drawBitmap(ScaledBmp3, posBin3X - ScaledBmp3.getWidth() * 0.5f, posBinY - ScaledBmp3.getHeight() * 0.5f, null);
        }
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
