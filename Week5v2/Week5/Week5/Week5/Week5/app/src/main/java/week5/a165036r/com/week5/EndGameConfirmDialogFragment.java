package week5.a165036r.com.week5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by lim on 1/29/2018.
 */

public class EndGameConfirmDialogFragment extends DialogFragment
{
    public static boolean IsShown = false;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        IsShown = true;
        GameSystem.Instance.setIsPaused(true);
        //TODO
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("All your life are gone! Return to Main Menu?").setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                GameSystem.Instance.leaveGame= true;
                StateManager.Instance.ChangeState("Default");
                getActivity().finish();
                Intent intent = new Intent(builder.getContext(),MainMenu.class);
                builder.getContext().startActivity(intent);
                IsShown = false;
            }
        });
        return builder.create();
    }
    @Override
    public void onCancel(DialogInterface dialog)
    {
        IsShown = false;
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        IsShown = false;
    }

}
