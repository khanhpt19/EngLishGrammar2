package com.example.khanh.myapplication1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.khanh.myapplication1.R;
import com.example.khanh.myapplication1.model.Lesson;

import java.util.List;

/**
 * Created by khanh on 7/6/2017.
 */

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.MyViewHolder> {

    private List<Lesson>lessonList;
    CallBack callBack;

    public LessonAdapter(List<Lesson> lessonList, CallBack callBack) {
        this.lessonList = lessonList;
        this.callBack = callBack;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public Button txtname;
        public MyViewHolder(View view){
            super(view);
            txtname= (Button) view.findViewById(R.id.txtname);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Lesson lesson=lessonList.get(position);

        holder.txtname.setText(lesson.getQid()+"");
        holder.txtname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.OnClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessonList.size();
    }

    public interface CallBack{
        void OnClick(int index);
    }
}
