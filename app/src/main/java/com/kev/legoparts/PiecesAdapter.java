package com.kev.legoparts;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by DAM on 26/1/17.
 */

public class PiecesAdapter extends BaseAdapter {

    private Context context;
    private List<LegoPiece> legoSet;

    public PiecesAdapter(Context context, List<LegoPiece> legoSet) {
        this.context = context;
        this.legoSet = legoSet;
    }

    @Override
    public int getCount() {
        return legoSet.size();
    }

    @Override
    public Object getItem(int position) {
        return legoSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return legoSet.get(position).getId();
    }

    public class ViewHolder {
        public ImageView ivImage;
        public TextView tvName;
        public TextView tvQuantity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View myView = convertView;
        if(myView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.list_pieces, parent, false);
            ViewHolder holder = new ViewHolder();
            holder.ivImage = (ImageView) myView.findViewById(R.id.image);
            holder.tvName = (TextView) myView.findViewById(R.id.name);
            holder.tvQuantity = (TextView) myView.findViewById(R.id.quantity);
            myView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) myView.getTag();

        LegoPiece legoPiece = legoSet.get(position);
        Bitmap image = legoPiece.getImage();
        holder.ivImage.setImageBitmap(image);
        String name = legoPiece.getName();
        holder.tvName.setText(name);
        String quantity = String.valueOf(legoPiece.getQuantity());
        holder.tvQuantity.setText(quantity);

        return myView;
    }
}
