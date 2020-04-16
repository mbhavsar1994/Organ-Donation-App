package com.organmanage.firebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DoctorRecepientAdapter extends BaseAdapter {


    private ArrayList<recipient> list_arrRecepient;
    private  Context context;

    public  DoctorRecepientAdapter(ArrayList<recipient> list_arrRecepient, Context context){

        this.list_arrRecepient=list_arrRecepient;
        this.context= context;
    }


    @Override
    public int getCount() {
        return list_arrRecepient.size();
    }

    @Override
    public Object getItem(int i) {
        return list_arrRecepient.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent ) {
          DoctorRecepientAdapter.ViewHolder vholder;

        if(convertView==null)
        {
            vholder=new DoctorRecepientAdapter.ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=  inflater.inflate(R.layout.custom_recepient_view,parent,false);
            vholder.tvname=(TextView)convertView.findViewById(R.id.tv_name);
            vholder.tvdate=(TextView) convertView.findViewById(R.id.tv_organType);
            vholder.btn_assigned=(Button)convertView.findViewById(R.id.btn_assiged);

            convertView.setTag(vholder);
        }else
        {
            vholder=(DoctorRecepientAdapter.ViewHolder)convertView.getTag();
        }

        vholder.tvname.setText(list_arrRecepient.get(position).name);
        vholder.tvdate.setText(list_arrRecepient.get(position).organType);
         vholder.btn_assigned.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View view) {
                  String guid= getGuid(position);
                 Intent intent = new Intent(context, AssignedDonar.class);
                 intent.putExtra("name",list_arrRecepient.get(position).name);
                 intent.putExtra("bloodGrp",list_arrRecepient.get(position).bloodGroup);
                 intent.putExtra("usrGuid",list_arrRecepient.get(position).userGuid);
                 intent.putExtra("Guid",guid);
                 intent.putExtra("OrganType",list_arrRecepient.get(position).organType);
                 context.startActivity(intent);
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


    private  String getGuid(int position){

       return list_arrRecepient.get(position).getId();
    }
}
