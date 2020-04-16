package com.organmanage.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomViewAdapter extends BaseAdapter {

    ArrayList<recipient> al_receptDetails;
    Context context;

    public CustomViewAdapter(ArrayList<recipient> recepient_detil, Context context){

        this.al_receptDetails= recepient_detil;
        this.context=context;
    }

    @Override
    public int getCount() {
        return al_receptDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return al_receptDetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vholder;

        if(convertView==null)
        {
            vholder=new ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=  inflater.inflate(R.layout.customview,parent,false);
            vholder.tvname=(TextView)convertView.findViewById(R.id.tv_name);
            vholder.tvdate=(TextView) convertView.findViewById(R.id.tv_date);

            convertView.setTag(vholder);
        }else
        {
            vholder=(ViewHolder)convertView.getTag();
        }

        vholder.tvname.setText(al_receptDetails.get(position).organType);
        vholder.tvdate.setText(al_receptDetails.get(position).requirdDateTill);
        return convertView;
    }

    public  static  class  ViewHolder
    {
        TextView tvname;
        TextView tvdate;
    }

}
