package org.techtowm.congressAndroidApp.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtowm.congressAndroidApp.data.ArticleResponse;
import org.techtowm.congressAndroidApp.databinding.ArticleRecyclerItemBinding;

import java.util.ArrayList;
import java.util.List;

public class ArtAdapter extends RecyclerView.Adapter<ArtAdapter.ViewHolder> implements Filterable {
    private List<ArticleResponse> mData;
    private List<ArticleResponse> filteredList;
    private OnItemClickListener mListener;

    public ArtAdapter(List<ArticleResponse> data, OnItemClickListener onItemClickListener) {
        this.mData = data;
        this.filteredList = data;
        this.mListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(TextView v, int position);
    }

    @NonNull
    @Override
    public ArtAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArticleRecyclerItemBinding binding = ArticleRecyclerItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ArticleRecyclerItemBinding binding;
        public TextView textView;
        public TextView textView2;
        public TextView textView3;

        public ViewHolder(ArticleRecyclerItemBinding binding) {
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
    public void onBindViewHolder(@NonNull ArtAdapter.ViewHolder holder, int position) {
         ArticleResponse response = mData.get(position);
         holder.textView.setText(response.getTitle());
         holder.textView2.setText("최종 수정일 " + response.getFixDT());
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
                    List<ArticleResponse> list = new ArrayList<>();

                    for( int i = 0; i < filteredList.size(); i++ ) {
                        if(filteredList.get(i).getTitle().toLowerCase().contains(searchStr.toLowerCase())) {
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
                mData = (List<ArticleResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
