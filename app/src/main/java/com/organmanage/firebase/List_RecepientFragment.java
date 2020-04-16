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
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link List_RecepientFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link List_RecepientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class List_RecepientFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth auth;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    ArrayList<recipient> list_recepint;
    private ListView mListView;

    private OnFragmentInteractionListener mListener;

    public List_RecepientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment List_RecepientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static List_RecepientFragment newInstance(String param1, String param2) {
        List_RecepientFragment fragment = new List_RecepientFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_list__recepient, container, false);
        auth = FirebaseAuth.getInstance();
        mListView = (ListView) rootView.findViewById(R.id.list_view);

        TextView textView = new TextView(getActivity());
        textView.setText("      Organ Requested       Required By");
        textView.setTextSize(18);
        textView.setFreezesText(true);
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        mListView.addHeaderView(textView);

        databaseReference = FirebaseDatabase.getInstance().getReference("Recepient").child(auth.getCurrentUser().getUid());
        //  Log.e("Get Data", post.<YourMethod>());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list_recepint= new ArrayList<recipient>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                      String  name= postSnapshot.child("name").getValue().toString();
                      String organtype= postSnapshot.child("organType").getValue().toString();
                      String ReqDate=postSnapshot.child("requirdDateTill").getValue().toString();
                      String bldgrp= postSnapshot.child("bloodGroup").getValue().toString();
                     // recipient re= new recipient( , postSnapshot.child("").getValue(), postSnapshot.child("").getValue());
                      recipient re= new recipient(name,organtype,ReqDate,false,bldgrp);
                      list_recepint.add(re);
                     CustomViewAdapter adapter=new CustomViewAdapter(list_recepint , getActivity());
                     mListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("The read failed: " ,databaseError.getMessage());
            }
        });


       // CustomViewAdapter adapter=new CustomViewAdapter( , getActivity());
       // mListView.setAdapter(adapter);
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
