package com.taximagicroshambo.roshambo;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.taximagicroshambo.roshambo.services.ThrowToWinAsyncTask;

import java.net.URL;


public class RoshamboFragment extends Fragment implements View.OnClickListener{


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RoshamboFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RoshamboFragment newInstance(String param1, String param2) {
        RoshamboFragment fragment = new RoshamboFragment();
        return fragment;
    }
    public RoshamboFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roshambo, container, false);

        view.findViewById(R.id.rock).setOnClickListener(this);
        view.findViewById(R.id.paper).setOnClickListener(this);
        view.findViewById(R.id.scissors).setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        String choice = "";

        switch(v.getId()){
            case R.id.rock:
                choice = "rock";
                break;
            case R.id.paper:
                choice = "paper";
                break;
            case R.id.scissors:
                choice = "scissors";
                break;
        }

        Uri.Builder builder = Uri.parse(getString(R.string.server_address)).buildUpon();
        builder.appendPath(choice);

        // application context because insertion is independent of the activity
        new ThrowToWinAsyncTask(getActivity().getApplicationContext()).execute(builder.build());

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
