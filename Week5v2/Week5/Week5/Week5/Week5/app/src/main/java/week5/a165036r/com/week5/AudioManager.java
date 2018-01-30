package week5.a165036r.com.week5;


import android.media.MediaPlayer;
import android.view.SurfaceView;
import java.util.HashMap;


//Created by XinYi
public class AudioManager
{
    // This is our singleton
    public final static AudioManager Instance = new AudioManager();

    private SurfaceView view = null;
    private HashMap<Integer, MediaPlayer> audioMap = new HashMap<Integer, MediaPlayer>();

    private AudioManager()
    {
    }

    public void Init(SurfaceView _view)
    {
        view = _view;
    }

    public void PlayAudio(int _id)
    {
        // Check if the audio is loaded or not
        if (audioMap.containsKey(_id))
        {
            // We got it!
            MediaPlayer curr = audioMap.get(_id);
            curr.reset();
            curr.start();
        }


        MediaPlayer newAudio = MediaPlayer.create(view.getContext(), _id);
        audioMap.put(_id, newAudio);

        newAudio.start();
    }

    public boolean IsPlaying()
    {
        return false;
    }
}
