package org.techtowm.congressAndroidApp.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtowm.congressAndroidApp.data.PetResponse;
import org.techtowm.congressAndroidApp.databinding.PetitionRecyclerItemBinding;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> implements Filterable {
    private List<PetResponse> filteredList;
    private List<PetResponse> mData;
    private OnItemClickListener mListener;

    public PetAdapter(List<PetResponse> data, OnItemClickListener onItemClickListener) {
        this.mData = data;
        this.filteredList = data;
        this.mListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(TextView v,int position);
    }

    @NonNull
    @Override
    public PetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        PetitionRecyclerItemBinding binding = PetitionRecyclerItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final PetitionRecyclerItemBinding binding;
        public TextView textView;
        public TextView textView2;
        public TextView textView3;

        public ViewHolder(PetitionRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            textView = binding.titleTv;
            textView2 = binding.titleTv2;
            textView3 = binding.titleTv3;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if( pos != RecyclerView.NO_POSITION ) {
                        mListener.onItemClick(textView3, pos);
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PetAdapter.ViewHolder holder, int position) {
        PetResponse response = mData.get(position);
        holder.textView.setText(response.getBillName());
        holder.textView2.setText("소개의원 " + response.getApprover());
        holder.textView3.setText(response.getUrl());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();

                if( charSequence == null || charSequence.length() == 0 ) {
                    filterResults.values = filteredList;
                    filterResults.count = filteredList.size();
                }
                else {
                    String searchStr = charSequence.toString().toLowerCase();
                    List<PetResponse> list = new ArrayList<>();

                    for( int i = 0; i < filteredList.size(); i++ ) {
                        if(filteredList.get(i).getBillName().toLowerCase().contains(searchStr.toLowerCase())) {
                            list.add(filteredList.get(i));
                        }
                    }

                    filterResults.values = list;
                    filterResults.count = list.size();

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mData = (List<PetResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
