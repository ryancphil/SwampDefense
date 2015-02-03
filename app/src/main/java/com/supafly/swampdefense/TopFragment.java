package com.supafly.swampdefense;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ryan on 11/15/2014.
 */
public class TopFragment extends Fragment {
    Button hint;
    Button pause;
    CanvasView canvasView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_top, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        canvasView = (CanvasView) getView().findViewById(R.id.canvasView);

        //HINT BUTTON
        hint = (Button) getView().findViewById(R.id.hint);
        hint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Display hint dialog
                DialogFragment hint = new Hint();
                hint.show(getActivity().getFragmentManager(), "hint");
                Log.e("Hint Dialog", "hint");
                if(!((MainActivity)getActivity()).isPaused) {
                    ((MainActivity) getActivity()).isPaused = !((MainActivity) getActivity()).isPaused;
                }
            }
        });

        //PAUSE BUTTON
        pause = (Button) getView().findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).isPaused = !((MainActivity)getActivity()).isPaused;
                final TextView tapstart = (TextView) getActivity().findViewById(R.id.tapStart);
                Log.e("TAPSTART: ",tapstart.toString());
                if(((MainActivity)getActivity()).isPaused) {
                    (getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tapstart.setText("PAUSED");
                        }
                    });
                    ((MainActivity) getActivity()).song.pause();

                }else{
                    (getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tapstart.setText("");
                        }
                    });
                    ((MainActivity) getActivity()).song.start();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static class Hint extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            // Single text dialogue with one button that closes the box
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.hint)
                    .setNegativeButton("Got it!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // dismiss dialog
                            ((MainActivity)getActivity()).isPaused = !((MainActivity)getActivity()).isPaused;
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
