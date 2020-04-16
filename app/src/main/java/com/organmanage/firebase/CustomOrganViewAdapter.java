package com.organmanage.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomOrganViewAdapter extends BaseAdapter {

    ArrayList<donar> al_donarDetails;
    Context context;

    public CustomOrganViewAdapter(ArrayList<donar> al_donarDetails, Context context){

        this.al_donarDetails= al_donarDetails;
        this.context=context;
    }
    @Override
    public int getCount() {
        return al_donarDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return  al_donarDetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewAdapter.ViewHolder vholder;

        if(convertView==null)
        {
            vholder=new CustomViewAdapter.ViewHolder();
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=  inflater.inflate(R.layout.customview,parent,false);
            vholder.tvname=(TextView)convertView.findViewById(R.id.tv_name);
            vholder.tvdate=(TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(vholder);
        }else
        {
            vholder=(CustomViewAdapter.ViewHolder)convertView.getTag();
        }

        vholder.tvname.setText(al_donarDetails.get(position).organType);
        vholder.tvdate.setText(al_donarDetails.get(position).availableDateOn);
        return convertView;
    }

    public  static  class  ViewHolder
    {
        TextView tvname;
        TextView tvdate;
    }

}
