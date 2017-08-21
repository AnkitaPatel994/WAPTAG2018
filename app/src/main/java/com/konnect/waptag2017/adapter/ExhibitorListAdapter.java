package com.konnect.waptag2017.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.konnect.waptag2017.R;
import com.konnect.waptag2017.model.ExhibitorModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qlooit-9 on 6/4/16.
 */
public class ExhibitorListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ExhibitorModel> exhibitor_list = new ArrayList<>();


    public ExhibitorListAdapter(Context applicationContext,
                                ArrayList<ExhibitorModel> exhibitorModels) {
        inflater = LayoutInflater.from(applicationContext);
        exhibitor_list.addAll(exhibitorModels);

    }

    @Override
    public int getCount() {
        return exhibitor_list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.exhibitor_list, parent, false);
            holder.txtCompany = (TextView) convertView.findViewById(R.id.txtCompany);
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtMobile = (TextView) convertView.findViewById(R.id.txtMobile);
            holder.txtMail = (TextView) convertView.findViewById(R.id.txtMail);
            holder.txtStall = (TextView) convertView.findViewById(R.id.txtStall);
            holder.txtAdd = (TextView) convertView.findViewById(R.id.txtAdd);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtCompany.setText(exhibitor_list.get(position).getExhibitor_company());
        if (exhibitor_list.get(position).getExhibitor_name().isEmpty()) {
            holder.txtName.setVisibility(View.GONE);
        } else {
            holder.txtName.setVisibility(View.VISIBLE);
            holder.txtName.setText(exhibitor_list.get(position).getExhibitor_name());
        }
        if (exhibitor_list.get(position).getExhibitor_mobile().isEmpty()) {
            holder.txtMobile.setVisibility(View.GONE);
        } else {
            holder.txtMobile.setVisibility(View.VISIBLE);
            holder.txtMobile.setText(exhibitor_list.get(position).getExhibitor_mobile());
        }
        holder.txtStall.setText("STALL NO : " + exhibitor_list.get(position).getExhibitor_stall());
        if (exhibitor_list.get(position).getExhibitor_mail().isEmpty()) {
            holder.txtMail.setVisibility(View.GONE);
        } else {
            holder.txtMail.setVisibility(View.VISIBLE);
            holder.txtMail.setText(exhibitor_list.get(position).getExhibitor_mail());
        }
        if (exhibitor_list.get(position).getExhibitor_add().isEmpty()) {
            holder.txtAdd.setVisibility(View.GONE);
        } else {
            holder.txtAdd.setVisibility(View.VISIBLE);
            holder.txtAdd.setText(exhibitor_list.get(position).getExhibitor_add());
        }

        return convertView;
    }

    class ViewHolder {

        private TextView txtCompany, txtMobile;
        private TextView txtName, txtMail, txtStall, txtAdd;
    }
}
