package org.techtowm.congressAndroidApp.adpater;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtowm.congressAndroidApp.data.LegisResponse;
import org.techtowm.congressAndroidApp.databinding.LegisRecyclerItemBinding;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LegisAdapter extends RecyclerView.Adapter<LegisAdapter.ViewHolder> implements Filterable {
    private List<LegisResponse> mData;
    private List<LegisResponse> filteredList;
    private OnItemClickListener mListener;

    public LegisAdapter(List<LegisResponse> data, OnItemClickListener onItemClickListener) {
        this.mData = data;
        this.filteredList = data;
        mListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
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
                    List<LegisResponse> list = new ArrayList<>();

                    for( int i = 0; i < filteredList.size(); i++ ) {
                        if( filteredList.get(i).getBillName().toLowerCase().contains(searchStr.toLowerCase())) {
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
                mData = (List<LegisResponse>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    @NonNull
    @Override
    public LegisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LegisRecyclerItemBinding binding = LegisRecyclerItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final LegisRecyclerItemBinding binding;
        public TextView textView;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;
        public TextView textView5;

        public ViewHolder(LegisRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            textView = binding.tv1;
            textView2 = binding.tv2;
            textView3 = binding.tv3;
            textView4 = binding.tv4;
            textView5 = binding.tv5;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if( pos != RecyclerView.NO_POSITION ) {
                        mListener.onItemClick(textView2, pos);
                    }
                }
            });

        }
    }

    @Override
    public void onBindViewHolder(@NonNull LegisAdapter.ViewHolder holder, int position) {
        LegisResponse responseBody = mData.get(position);
        holder.textView.setText(responseBody.getBillName());
        holder.textView2.setText(responseBody.getLegConRe());

        if( whetherNoticeEndOrProceeding(responseBody.getRegisPeriod()) ) {
            Log.d("Sang" ,"onBindViewHolder whetherNoticeEndOrProceeding result : true" );
            holder.textView3.setText("예고 진행중");
        }
        else {
            Log.d("Sang" ,"onBindViewHolder whetherNoticeEndOrProceeding result : false" );
            holder.textView3.setText("예고 종료");
        }

        holder.textView4.setText(responseBody.getAge() + "대");
        holder.textView5.setText(responseBody.getCurrCommitee());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public boolean whetherNoticeEndOrProceeding(String s) {
        boolean flag = false;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate now = LocalDate.now();
            String legislationNoticePeriod = s;
            String legislationMonth = legislationNoticePeriod.substring(18, 20);
            String legislationDay = legislationNoticePeriod.substring(21, 23);

            if( Integer.valueOf(now.getMonthValue()) <= Integer.parseInt(legislationMonth) ) {
                if( Integer.valueOf(now.getDayOfMonth()) <= Integer.parseInt(legislationDay)) {
                    Log.d("Sang", "Legislation proceeding ");
                    flag = true;
                }
                else {
                    Log.d("Sang", "Legislation passed " + legislationDay);
                    flag = false;
                }
            }
            else {
                Log.d("Sang", "Legislation passed " + legislationMonth);
                return false;
            }
        }
        return flag;
    }
}
/*
public void setArrayList(String i) {
        mData.add(i);
    }
 */
