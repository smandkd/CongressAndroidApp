package org.techtowm.congressAndroidApp.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtowm.congressAndroidApp.data.DiplomacyResponse;
import org.techtowm.congressAndroidApp.databinding.DiplomacyRecyclerItemBinding;

import java.util.ArrayList;
import java.util.List;

public class DipAdapter extends RecyclerView.Adapter<DipAdapter.ViewHolder> implements Filterable {
    private List<DiplomacyResponse> mData;
    private List<DiplomacyResponse> filteredList;
    private OnItemClickListener mListener;

    public DipAdapter(List<DiplomacyResponse> mData, OnItemClickListener onItemClickListener) {
        this.mData = mData;
        this.filteredList = mData;
        this.mListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(TextView v, int position);
    }

    @NonNull
    @Override
    public DipAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DiplomacyRecyclerItemBinding binding = DiplomacyRecyclerItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final DiplomacyRecyclerItemBinding binding;
        public TextView textView;
        public TextView textView2;
        public TextView textView3;

        public ViewHolder(DiplomacyRecyclerItemBinding binding) {
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
    public void onBindViewHolder(@NonNull DipAdapter.ViewHolder holder, int position) {
        DiplomacyResponse response = mData.get(position);
        String date = response.getUpdateDT();
        String date_2 = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8);
        holder.textView.setText(response.getArticleTitle());
        holder.textView2.setText("작성일 " + date_2);
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
                    List<DiplomacyResponse> list = new ArrayList<>();

                    for( int i = 0; i < filteredList.size(); i++ ) {
                        if(filteredList.get(i).getArticleTitle().toLowerCase().contains(searchStr.toLowerCase())) {
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
                mData = (List<DiplomacyResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
