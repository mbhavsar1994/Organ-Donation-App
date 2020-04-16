package com.organmanage.firebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoctorDonarAdapter extends BaseAdapter
{
    private FirebaseAuth auth;

    private DatabaseReference UpdateReference;
    private ArrayList<donar> list_arrDonar;
    ArrayList<String> recept_Guids;
    private Context context;

    public   DoctorDonarAdapter(ArrayList<donar> list_arrDonar, ArrayList<String> recept_Guids, Context context){

        this.list_arrDonar=list_arrDonar;
        this.recept_Guids=recept_Guids;
        this.context= context;
    }
    @Override
    public int getCount() {
        return list_arrDonar.size();
    }

    @Override
    public Object getItem(int i) {
        return  list_arrDonar.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        DoctorDonarAdapter.ViewHolder vholder;

        if(convertView==null)
        {
            vholder=new DoctorDonarAdapter.ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=  inflater.inflate(R.layout.custom_recepient_view,parent,false);
            vholder.tvname=(TextView)convertView.findViewById(R.id.tv_name);
            vholder.tvdate=(TextView) convertView.findViewById(R.id.tv_organType);
            vholder.btn_assigned=(Button)convertView.findViewById(R.id.btn_assiged);
            convertView.setTag(vholder);
        }else
        {
            vholder=(DoctorDonarAdapter.ViewHolder)convertView.getTag();
        }

        vholder.tvname.setText(list_arrDonar.get(position).name);
        vholder.tvdate.setText(list_arrDonar.get(position).city);
        vholder.btn_assigned.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                UpdateReference = FirebaseDatabase.getInstance().getReference("Donars").child(list_arrDonar.get(position).userGuid).child(list_arrDonar.get(position).id).child("isDonated");

                //update  User  to firebase
                UpdateReference.setValue("True");

                UpdateReference = FirebaseDatabase.getInstance().getReference("Recepient").child(recept_Guids.get(0).toString()).child(recept_Guids.get(1).toString()).child("isAssigned");

                UpdateReference.setValue("True");

                Toast.makeText(view.getContext(), "Organ is successfully assigned to recepient!", Toast.LENGTH_LONG).show();

            }
        });


        return convertView;
    }
    public  static  class  ViewHolder
    {
        TextView tvname;
        TextView tvdate;
        Button btn_assigned;
    }
}
