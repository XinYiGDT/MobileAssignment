package week5.a165036r.com.week5;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public  class GameView extends SurfaceView
{

    private SurfaceHolder holder = null;
    // TODO : UpdateThread
    private UpdateThread updateThread = new UpdateThread(this);
    public GameView(Context _context)
    {
        super(_context);
        holder = getHolder();

        if(holder != null)
        {
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder)
                {

                    if(!updateThread.isRunning())
                    updateThread.initialize();

                    if(!updateThread.isAlive())
                    updateThread.start();
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                    updateThread.terminate();
                }
            });
        }
    }

}

