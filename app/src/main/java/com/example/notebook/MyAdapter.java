package com.example.notebook;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends ListAdapter<item,MyAdapter.MyViewHolder> {

    private mainViewModel mainViewModel;
    private int positionClick = -1;
    private WebView webView;

    public MyAdapter(mainViewModel mainViewModel,WebView webView){
        super(new DiffUtil.ItemCallback<item>() {
            @Override
            public boolean areItemsTheSame(@NonNull item oldItem, @NonNull item newItem) {
                return oldItem.getId() ==  newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull item oldItem, @NonNull item newItem) {
                return (oldItem.getTitle().equals(newItem.getTitle()) &&
                        oldItem.getContent().equals(newItem.getContent()) &&
                        oldItem.getDateDay() == newItem.getDateDay() &&
                        oldItem.getDateMonth() == newItem.getDateMonth() &&
                        oldItem.getItemType() == newItem.getItemType());
            }
        });
        this.mainViewModel = mainViewModel;
        this.webView = webView;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_cell_layout,parent,false);

        final MyViewHolder myViewHolder = new MyViewHolder(itemView);
        myViewHolder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item itemTemp = (item) myViewHolder.itemView.getTag(R.id.id_for_getWord);
                if(isChecked){
                    itemTemp.setIfRemember(true);
                    mainViewModel.updateItems(itemTemp);
                }else{
                    itemTemp.setIfRemember(false);
                    mainViewModel.updateItems(itemTemp);
                }
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final item item = getItem(position);
        holder.itemView.setTag(R.id.id_for_getWord,item);
        int month = item.getDateMonth();
        int day = item.getDateDay();
        String itemDate = month + "." + day;
        holder.textViewDate.setText(itemDate);
        holder.textViewTitle.setText(item.getTitle());
        holder.textViewContent.setText(item.getContent());
        holder.textViewAllContent.setText(item.getContent());

        if(item.isIfRemember()){
            holder.aSwitch.setChecked(true);
        }else{
            holder.aSwitch.setChecked(false);
        }

        if(positionClick == position){
            holder.constraintLayout.setVisibility(View.VISIBLE);
            holder.textViewContent.setVisibility(View.GONE);
        }else{
            holder.constraintLayout.setVisibility(View.GONE);
            holder.textViewContent.setVisibility(View.VISIBLE);
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

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.baidu.com/s?wd=" + item.getTitle());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle,textViewContent,textViewDate,textViewAllContent;
        Switch aSwitch;
        ConstraintLayout constraintLayout;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTitle = itemView.findViewById(R.id.textViewTitleRecord);
            textViewContent = itemView.findViewById(R.id.textViewContent);
            aSwitch = itemView.findViewById(R.id.switchRember);
            constraintLayout = itemView.findViewById(R.id.ConstrainLayoutChild);
            textViewAllContent = itemView.findViewById(R.id.textViewAllContent);
            constraintLayout.setVisibility(View.GONE);

            imageView = itemView.findViewById(R.id.imageViewIcon);

        }
    }

}
