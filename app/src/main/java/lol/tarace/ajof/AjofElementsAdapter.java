package lol.tarace.ajof;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by magicmicky on 16/02/16.
 */

public class AjofElementsAdapter extends RecyclerView.Adapter<AjofElementsAdapter.ViewHolder> {
    private List<String> mDataset;
    private int mSelected = -1;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.info_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AjofElementsAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AjofElementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ajof_element, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(this.mSelected == position)
            holder.mTextView.setTextColor(holder.mTextView.getResources().getColor(R.color.colorAccent));
        else {
            holder.mTextView.setTextColor(holder.mTextView.getResources().getColor(android.R.color.secondary_text_light));
        }
        holder.mTextView.setText(mDataset.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setSelected(int position) {
        int old = mSelected;
        this.mSelected = position;
        this.notifyItemChanged(old);
        this.notifyItemChanged(position);
    }
}