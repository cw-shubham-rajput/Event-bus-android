package com.example.shubham.eventbus_demo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Userfragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Userfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Userfragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Userfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Userfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Userfragment newInstance(String param1, String param2) {
        Userfragment fragment = new Userfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        GlobalBus.getBus().register(this);
        return inflater.inflate(R.layout.fragment_userfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListener(view);
    }

    public void setClickListener(final View view) {
        Button btnSubmit = (Button) view.findViewById(R.id.submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etMessage = (EditText) view.findViewById(R.id.editText);

                // We are broadcasting the message here to listen to the subscriber.
                Events.FragmentActivityMessage fragmentActivityMessageEvent =
                        new Events.FragmentActivityMessage(
                                String.valueOf(etMessage.getText()));
                GlobalBus.getBus().post(fragmentActivityMessageEvent);
            }
        });
    }

    @Subscribe
    public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {
        TextView messageView = (TextView) getView().findViewById(R.id.message);
        TextView title = (TextView) getView().findViewById(R.id.title);
        title.setText("Message from Activity");
        messageView.setText(getString(R.string.message_received) + " " + activityFragmentMessage.getMessage());

        Toast.makeText(getActivity(),
                getString(R.string.message_fragment) +
                        " " + activityFragmentMessage.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);
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
        void onFragmentInteraction(Uri uri);
    }
}
