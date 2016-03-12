package com.iv.mockapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iv.mockapp.models.response.MAResponseModel;

import java.util.List;

/**
 * Created by vineeth on 10/09/16
 */
public class MASpinnerAdapter extends ArrayAdapter<MAResponseModel> {

    private final Context mContext;

    private final int mSpinnerItemLayoutResourceId;
    private final int mSpinnerDropdownItemLayoutResourceId;

    private final LayoutInflater mInflater;

    public MASpinnerAdapter(Context context, int spinnerItemLayoutResourceId, int spinnerDropdownItemLayoutResourceId,
                            List<MAResponseModel> datas) {
        super(context, spinnerItemLayoutResourceId, datas);

        mContext = context;
        mSpinnerItemLayoutResourceId = spinnerItemLayoutResourceId;
        mSpinnerDropdownItemLayoutResourceId = spinnerDropdownItemLayoutResourceId;

        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent, mSpinnerDropdownItemLayoutResourceId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent, mSpinnerItemLayoutResourceId);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent, int textViewResourceId) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(textViewResourceId, parent, false);

            ViewHolder viewHolder = new ViewHolder(rowView);

            rowView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder) rowView.getTag();

        TextView labelView = (TextView) viewHolder.label;
        labelView.setText(getItem(position).getName());

        return rowView;
    }

    static class ViewHolder {
        private final View label;

        ViewHolder(View rowView) {
            label = rowView.findViewById(android.R.id.text1);
        }
    }
}