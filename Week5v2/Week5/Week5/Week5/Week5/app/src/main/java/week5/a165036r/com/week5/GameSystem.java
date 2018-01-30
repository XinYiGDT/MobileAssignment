package week5.a165036r.com.week5;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class GameSystem
{
    public final static GameSystem Instance = new GameSystem();
    public final static String SHARED_PREF_ID = "GameSaveFile";
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    float Timer = 0.0f;
    boolean pause  = false;
    boolean leaveGame = false;
    boolean missed  = false;
    int health =3;
    private GameSystem()
    {

    }

   public boolean getIsPaused()
    {
       return pause;
    }

    public void setIsPaused(boolean _pause)
    {
       pause = _pause;
    }

    public boolean getMissed()
    {
        return missed;
    }

    public void setIsMissed(boolean _missed)
    {
        missed = _missed;
    }

    public void init(SurfaceView _view)
    {

        sharedPref = gamepage.Instance.getSharedPreferences(SHARED_PREF_ID,0);

        StateManager.Instance.AddState(new IntroState());
        StateManager.Instance.AddState(new MainGameState());
    }

    public  void Update(float _deltaTime)
    {



        // EntityManager.Instance.Update(_deltaTime);
    }

    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);
    }



    public void SaveEditBegin()
    {
        //Prepare Editor
        if(editor!=null)
        {
            return;
        }

        //start editing
        editor = sharedPref.edit();
    }

    public void SaveEditEnd()
    {
        //Commit Changes
        if(editor == null)
        {
            //dont have editor. cannot commit
            return;
        }

        editor.commit();

        editor =null;
    }

    public void SetIntInSave(String _key, int _value)
    {
        if(editor == null)
            return;

        editor.putInt(_key, _value);
    }

    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key,0);
    }

}

