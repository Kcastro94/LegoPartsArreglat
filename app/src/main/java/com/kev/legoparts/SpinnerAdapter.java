package com.kev.legoparts;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by DAM on 26/1/17.
 */

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public SpinnerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        long id = position;
        return id;
    }

    public class ViewHolder {
        public TextView textSp;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View myView = convertView;
        if(myView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.list_spinner, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.textSp = (TextView) myView.findViewById(R.id.textSp);
            myView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) myView.getTag();

        String setId = list.get(position);

        holder.textSp.setText(setId);


        return myView;
    }
}
