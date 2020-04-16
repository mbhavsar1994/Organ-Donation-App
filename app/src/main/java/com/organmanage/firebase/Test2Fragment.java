package com.organmanage.firebase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Test2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Test2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Test2Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseAuth auth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<donar>  ls_donardetails;
    private ListView mListView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Test2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Test2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Test2Fragment newInstance(String param1, String param2) {
        Test2Fragment fragment = new Test2Fragment();
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

        View rootView = inflater.inflate(R.layout.fragment_test2, container, false);
        auth = FirebaseAuth.getInstance();
        mListView = (ListView) rootView.findViewById(R.id.list_view);

        TextView textView = new TextView(getActivity());
        textView.setText("      Organ donated      Available date ");
        textView.setTextSize(18);
        textView.setFreezesText(true);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        mListView.addHeaderView(textView);


        databaseReference = FirebaseDatabase.getInstance().getReference("Donars").child(auth.getCurrentUser().getUid());
        //  Log.e("Get Data", post.<YourMethod>());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ls_donardetails= new ArrayList<donar>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String  name= postSnapshot.child("name").getValue().toString();
                    String organtype= postSnapshot.child("organType").getValue().toString();
                    String availDate=postSnapshot.child("availableDateOn").getValue().toString();
                    String city=postSnapshot.child("city").getValue().toString();
                    String bldgrp= postSnapshot.child("bloodGroup").getValue().toString();
                    donar dd= new donar(name,organtype,availDate,false,city,bldgrp);
                    ls_donardetails.add(dd);
                    CustomOrganViewAdapter adapter=new CustomOrganViewAdapter(ls_donardetails , getActivity());
                    mListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
