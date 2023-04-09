package com.example.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.AddNewTask;
import com.example.todolist.MainActivity;
import com.example.todolist.Model.todomodel;
import com.example.todolist.R;
import com.example.todolist.Utils.DatabaseHandler;

import java.security.PrivateKey;
import java.util.List;

public class todoadapter extends RecyclerView.Adapter<todoadapter.ViewHolder> {


    private List<todomodel> todolist;
    private MainActivity activity;
    private DatabaseHandler db;


    public todoadapter(DatabaseHandler db, MainActivity activity) {
        this.db = db;
        this.activity = activity;

    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent ,false);
        return  new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        db.openDatabase();
        todomodel item = todolist.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateStatus(item.getId(), 1);
                }
                else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    public int getItemCount(){
        return todolist.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setTask(List<todomodel> todolist){
        this.todolist = todolist;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void editItem(int position){
        todomodel item = todolist.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());
        AddNewTask fragment = new AddNewTask();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.chkbox);
        }
    }
}
