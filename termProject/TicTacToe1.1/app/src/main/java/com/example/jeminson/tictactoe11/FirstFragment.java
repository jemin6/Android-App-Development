package com.example.jeminson.tictactoe11;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * Created by jeminson on 2017. 7. 22..
 */

public class FirstFragment extends Fragment implements OnClickListener {

    private Button rpsPlayButton;

    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_fragment, container, false);

        rpsPlayButton = (Button) view.findViewById(R.id.rpsPlayButton);
        rpsPlayButton.setOnClickListener(this);

        return view;
    } // End onCreateView

    // Implement the interface
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rpsPlayButton) {
            Intent intent = new Intent(getActivity(), SecondActivity.class);
            startActivity(intent);
        } // End onClick
    }
}