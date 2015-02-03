package com.supafly.swampdefense;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.TimerTask;

/**
 * Created by Ryan on 11/15/2014.
 */
public class PlayFragment extends Fragment {

    CanvasView canvasView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_play, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        canvasView = (CanvasView) getView().findViewById(R.id.canvasView);
        canvasView.invalidate();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static class GameOverDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            // Single text dialogue with one button that closes the box
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(((MainActivity)getActivity()).scoreString)
                    .setNegativeButton("Play again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Start the game again.
                            if(!((MainActivity) getActivity()).canvasView.gameController.hasStarted) {
                                Log.e("hasStarted", "STARTING NEW GAME!");
                                ((MainActivity) getActivity()).canvasView.gameController.hasStarted = true;
                                ((MainActivity) getActivity()).canvasView.gameController.startClock();
                            }
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
