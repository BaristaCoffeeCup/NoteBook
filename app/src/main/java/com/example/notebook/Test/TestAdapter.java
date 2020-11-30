package com.example.notebook.Test;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notebook.R;
import com.example.notebook.mainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class TestAdapter extends ListAdapter<Record,TestAdapter.TestViewHolder> {

    private mainViewModel mainViewModel;
    public int positionNow = 0;
    public boolean timeIng = false;


    public TestAdapter(mainViewModel mainViewModel){

        super(new DiffUtil.ItemCallback<Record>() {
            @Override
            public boolean areItemsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Record oldItem, @NonNull Record newItem) {
                return ( oldItem.getTitle().equals(newItem.getTitle()) &&
                         oldItem.getTimeStart().equals(newItem.getTimeStart()) &&
                         oldItem.getTimeMax().equals(newItem.getTimeMax()) &&
                         oldItem.getTimeCount().equals(newItem.getTimeCount()) &&
                         oldItem.getTimeMax1() == newItem.getTimeMax1() &&
                         oldItem.getTimeUse1() == newItem.getTimeUse1()
                        );
            }
        });

        this.mainViewModel = mainViewModel;

    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_test_cell_layout,parent,false);

        final TestViewHolder myViewHolder = new TestViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TestViewHolder holder, final int position) {
        final Record record = getItem(position);
        holder.textViewTitle.setText(record.getTitle());
        holder.textViewTimeMax.setText(record.getTimeMax());
        holder.textViewTimeFinish.setText("");
        if(record.isFinished()){
            holder.imageButtonControl.setBackgroundResource(R.drawable.yiwancheng);
            holder.textViewTitle.setTextColor(Color.parseColor("#ff4500"));
            holder.imageView1.setImageResource(R.drawable.weibiaoti);
            holder.divider.setBackgroundColor(Color.parseColor("#ff4500"));
            holder.textViewTimeStart.setText(record.getTimeStart());
            holder.chronometer.setText(record.getTimeCount());
            positionNow+=1;
        }else{
            holder.textViewTimeStart.setText("未开始");
            holder.imageButtonControl.setBackgroundResource(R.drawable.kaishi);
            holder.textViewTitle.setTextColor(Color.parseColor("#3cb371"));
            holder.imageView1.setImageResource(R.drawable.weibiaoti2);
            holder.divider.setBackgroundColor(Color.parseColor("#3cb371"));
            holder.chronometer.setBase(SystemClock.elapsedRealtime());
        }

        holder.imageButtonControl.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                //当计时没有开始时
                if(positionNow == position && !timeIng){
                    //获取当前的时间并设置开始时间
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    String timeNow = hour + ":" + minute;
                    record.setTimeStart(timeNow);
                    mainViewModel.UpdateRecord(record);
                    holder.textViewTimeStart.setText(timeNow);

                    holder.imageButtonControl.setBackgroundResource(R.drawable.zanting);
                    timeIng = true;

                    //开始计时
                    holder.chronometer.setBase(SystemClock.elapsedRealtime());
                    holder.chronometer.start();

                }else if(positionNow == position && timeIng){   //已经完成某一项内容
                    holder.imageButtonControl.setBackgroundResource(R.drawable.yiwancheng);
                    positionNow+=1;
                    holder.textViewTitle.setTextColor(Color.parseColor("#ff4500"));
                    holder.imageView1.setImageResource(R.drawable.weibiaoti);
                    holder.divider.setBackgroundColor(Color.parseColor("#ff4500"));
                    record.setFinished(true);
                    mainViewModel.UpdateRecord(record);

                    timeIng = false;
                    //停止计时
                    holder.chronometer.stop();

                    int temp0 = Integer.parseInt(holder.chronometer.getText().toString().split(":")[0]);
                    int temp1 =Integer.parseInt(holder.chronometer.getText().toString().split(":")[1]);
                    //获取计时的一些数据
                    int timeUsed = temp0*60 + temp1;
                    record.setTimeCount(holder.chronometer.getText().toString().trim());
                    record.setTimeUse1(timeUsed);
                    record.setFinished(true);
                    mainViewModel.UpdateRecord(record);

                    //判断是否超时
                    int timeBetween =  record.getTimeUse1() - record.getTimeMax1()*60;
                    if(timeBetween > 0){
                        int minuteMore = timeBetween / 60;
                        int secondMore = timeBetween % 60;
                        String timeFinish = "超时:" + minuteMore + "分" + secondMore + "秒";
                        holder.textViewTimeFinish.setText(timeFinish);
                    }
                    else{
                        holder.textViewTimeFinish.setText("未超时");
                    }


                }
            }
        });
    }


    static class TestViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle,textViewTimeStart,textViewTimeCount,textViewTimeFinish,textViewTimeMax;
        ImageButton imageButtonControl,imageButtonReset;
        Chronometer chronometer;
        ImageView imageView1;
        View divider;
        TranslateAnimation showAnim,hideAnim;
        FloatingActionButton floatingActionButton;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitleRecord);
            textViewTimeStart = itemView.findViewById(R.id.textViewTimeStart);
            textViewTimeFinish = itemView.findViewById(R.id.textViewFinishTime);
            textViewTimeMax = itemView.findViewById(R.id.textViewMaxtime);

            chronometer = itemView.findViewById(R.id.textViewTimeCount);

            imageButtonControl = itemView.findViewById(R.id.imageButtonControl);

            imageView1 = itemView.findViewById(R.id.imageViewCircle);
            divider = itemView.findViewById(R.id.divider4);

        }
    }
}
