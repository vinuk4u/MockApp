package com.iv.mockapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iv.mockapp.R;

import java.util.ArrayList;

/**
 * Created by vineeth on 09/09/16
 */
public class MAHeaderListItemsAdapter extends RecyclerView.Adapter<MAHeaderListItemsAdapter.ViewHolder> {
    private final ArrayList<String> mDataset;

    private final OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(String item);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MAHeaderListItemsAdapter(ArrayList<String> dataset, OnItemClickListener listener) {
        mDataset = dataset;
        mListener = listener;
    }

    public void add(int position, String item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MAHeaderListItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        viewHolder.titleTextView.setText(mDataset.get(viewHolder.getAdapterPosition()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mListener.onItemClick(mDataset.get(viewHolder.getAdapterPosition()));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // reference to the views for each data item
    // which will provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView titleTextView;

        public ViewHolder(View view) {
            super(view);
            titleTextView = (TextView) view.findViewById(R.id.title_textview);
        }
    }

}