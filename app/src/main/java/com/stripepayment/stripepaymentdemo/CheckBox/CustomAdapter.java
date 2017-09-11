package com.stripepayment.stripepaymentdemo.CheckBox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.stripepayment.stripepaymentdemo.R;

import java.util.List;

/**
 * Created by fazal on 9/11/2017.
 */

public class CustomAdapter extends ArrayAdapter<Item> {
    List<Item> list;
    private ViewHolder holder;

    public CustomAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            Context context = parent.getContext();
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.checked_item,null,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Item item = getItem(position);
        ListView listView = (ListView)parent;
        holder.text.setText(item.getText());
        holder.layout.setChecked(listView.isItemChecked(position));

        return convertView;

    }

    class ViewHolder {
        TextView text;
        CheckBox checkBox;
        CheckableRelativeLayout layout;

        public ViewHolder(View itemView) {
            text = itemView.findViewById(R.id.text);
            checkBox = itemView.findViewById(R.id.checkbox);
            layout = (CheckableRelativeLayout)itemView.findViewById(R.id.layout);
        }
    }
}
