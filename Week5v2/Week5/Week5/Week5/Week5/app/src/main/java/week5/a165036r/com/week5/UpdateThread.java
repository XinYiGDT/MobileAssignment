package week5.a165036r.com.week5;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;

public class UpdateThread extends Thread
{
    static  final  long targetFPS = 60;

    private GameView view = null;
    private SurfaceHolder holder = null;
    private  boolean isRunning = false;

    public UpdateThread(GameView _view)
    {
        view = _view;
        holder = _view.getHolder();

        ResourceManager.Instance.Init(view);
        week5.a165036r.com.week5.AudioManager.Instance.Init(view);
        StateManager.Instance.Init(view);
        EntityManager.Instance.init(view);
        GameSystem.Instance.init(view);




    }

    public boolean isRunning()
    {
        return isRunning;
    }

    public void initialize()
    {
        isRunning = true;
    }

    public  void terminate()
    {
        isRunning = false;
       EntityManager.Instance.Clear();
    }

    @Override
    public void run()
    {
        //This is the game loop

        //Init here for variables needed
        long framepersecond = 1000/targetFPS; //1000 is miliSeconds -> 1 second
        long startTime = 0; //This is for getting Frame Rate Controller(FRC)

        long prevTime = System.nanoTime();// This is for deltaTime.

        StateManager.Instance.Start("Default");

        while (isRunning())
        {
            //Update
            startTime = System.currentTimeMillis();

            long currTime = System.nanoTime();
            float deltaTime = (float)((currTime - prevTime)/1000000000.0f);
            prevTime = currTime;
           // GameSystem.Instance.Update(deltaTime); //Gonna do this later
            StateManager.Instance.Update(deltaTime);

            //Render
            Canvas canvas  =  holder.lockCanvas();

            if(canvas!= null)
            {
                synchronized (holder) //lock the door
                {
                    canvas.drawColor(Color.BLACK);
                    StateManager.Instance.Render(canvas);

                    //TODO: our bulk render Function
                  //  GameSystem.Instance.Render(canvas);
            }
                holder.unlockCanvasAndPost(canvas);
            }
            //PostUpdate
            try
            {
                long sleepTime = framepersecond - (System.currentTimeMillis() - startTime);

                if(sleepTime >0)
                    sleep(sleepTime);
            }
            catch (InterruptedException e)
            {
                terminate();
            }

            if(GameSystem.Instance.leaveGame ==true)
            {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(view.getContext(),MainMenu.class);
                terminate();
                view.getContext().startActivity(intent);
            }
        }
    }
}
