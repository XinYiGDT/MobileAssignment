package week5.a165036r.com.week5;

import android.graphics.Canvas;
import android.view.SurfaceView;
import android.view.animation.PathInterpolator;

import java.util.HashMap;

public class StateManager
{
    public final static StateManager Instance = new StateManager();

    private SurfaceView view = null;
    private HashMap<String,StateBase> stateMap = new HashMap<String,StateBase>();
    private StateBase currState = null;
    private StateBase nextState = null;

    private StateManager()
    {

    }

    public void Init(SurfaceView _view)
    {
        view = _view;
    }

    void Update(float _dt)
    {
        if(nextState !=currState)
        {
            currState.OnExit();
            nextState.OnEnter(view);
            currState = nextState;
        }

        //Safety Check
        if(currState == null)
            return;

        //Update current State
        currState.Update(_dt);
    }

    void Render(Canvas _canvas)
    {
        currState.Render(_canvas);
    }

    void AddState(StateBase _newState)
    {
        //Add new state to map
        stateMap.put(_newState.GetName(),_newState);
    }

    void ChangeState(String _newState)
    {
        //Assign new state to next State
        nextState = stateMap.get(_newState);

        //Safety Check
        if(nextState == null)
            nextState = currState;

    }

    void Start(String _newCurrent)
    {
        //Ensure that this can only be called once
        if(currState !=null || nextState !=null)
            return;


        currState = stateMap.get(_newCurrent);
        if(currState != null)
        {
            currState.OnEnter(view);
            nextState = currState;
        }
    }
}
