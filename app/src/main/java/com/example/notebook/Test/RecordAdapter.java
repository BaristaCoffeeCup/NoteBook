package com.example.notebook.Test;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebook.MyAdapter;
import com.example.notebook.R;
import com.example.notebook.mainViewModel;

import java.util.List;

public class RecordAdapter extends ListAdapter<TestItem, RecordAdapter.RecordViewHolder> {

    private mainViewModel mainViewModel;
    private int positionClick = -1;
    private boolean alreadyAdd = false;


    public RecordAdapter(mainViewModel mainViewModel){
        super(new DiffUtil.ItemCallback<TestItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull TestItem oldItem, @NonNull TestItem newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull TestItem oldItem, @NonNull TestItem newItem) {
                return false;
            }
        });

        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_record_cell,parent,false);

        return new RecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecordAdapter.RecordViewHolder holder, int position) {

        final TestItem item = getItem(position);
        holder.textViewTitle.setText(item.getTestTitle());
        holder.textViewDate.setText(item.getDate());

        String[] allTitle = item.getTitle().split("&");
        String[] allFinishTime = item.getFinishtime().split("&");
        String[] allMaxTime = item.getMaxtime().split("&");

        holder.tableLayout.removeAllViewsInLayout();
        for(int i=0;i<allTitle.length;i++){
            TableRow tableRow = new TableRow(holder.context);

            TextView textViewTitle = new TextView(holder.context);
            textViewTitle.setText("    " + allTitle[i] + "    ");
            textViewTitle.setTextSize(14);
            textViewTitle.setBackgroundResource(R.drawable.background2);

            TextView textViewMax = new TextView(holder.context);
            textViewMax.setText("    " + allMaxTime[i] + "    ");
            textViewMax.setTextSize(14);
            textViewMax.setBackgroundResource(R.drawable.background2);

            TextView textViewFinish = new TextView(holder.context);
            int timeUse = Integer.parseInt(allFinishTime[i]);
            textViewFinish.setText("    用时:" + timeUse/60 + "分" + timeUse%60 +"秒    ");
            textViewFinish.setTextSize(14);
            textViewFinish.setBackgroundResource(R.drawable.background2);

            tableRow.addView(textViewTitle);
            tableRow.addView(textViewMax);
            tableRow.addView(textViewFinish);

            holder.tableLayout.addView(tableRow);
        }

        if(positionClick == position){
            holder.tableLayout.setVisibility(View.VISIBLE);
        }else{
            holder.tableLayout.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(positionClick == holder.getBindingAdapterPosition()){
                    positionClick = -1;
                    notifyItemChanged(holder.getBindingAdapterPosition());
                }else{
                    int oldPosition = positionClick;
                    positionClick = holder.getBindingAdapterPosition();
                    notifyItemChanged(oldPosition);
                    notifyItemChanged(positionClick);
                }
            }
        });

    }


    static class RecordViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle,textViewDate;
        TableLayout tableLayout;
        Context context;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = itemView.findViewById(R.id.textViewDateFinal);
            textViewTitle = itemView.findViewById(R.id.textViewTitleFinal);
            tableLayout = itemView.findViewById(R.id.MyTableLayout);
            context = itemView.getContext();

        }
    }
}


