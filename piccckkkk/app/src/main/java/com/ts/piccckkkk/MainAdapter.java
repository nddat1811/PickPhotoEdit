package com.ts.piccckkkk;

import android.app.Activity;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Activity activity;
    ArrayList<String> arrayList;
    TextView tvEmpty;
    MainViewModel mainViewModel;
    boolean isEnable = false;
    boolean isSelectAll = false;
    ArrayList<String> selectList = new ArrayList<>();

    public MainAdapter(Activity activity, ArrayList<String> arrayList, TextView tvEmpty) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.tvEmpty = tvEmpty;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        //Init viewmodel

        mainViewModel = ViewModelProviders.of((FragmentActivity) activity).get(MainViewModel.class);
        
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(arrayList.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(!isEnable){
                    ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                            MenuInflater menuInflater = actionMode.getMenuInflater() ;


                            menuInflater.inflate(R.menu.menu, menu);

                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                            //When action mode is prepare
                            //Set isEnable true
                            isEnable = true;
                            //Create method
                            ClickItem(holder);

                            //Set observer on get text method
                            mainViewModel.getText().observe((LifecycleOwner) activity, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    //When text change
                                    //Set text on action mode tittle
                                    actionMode.setTitle(String.format("%s Selected", s));
                                }
                            });
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                            //When click on action mode item
                            //Get item id
                            int id =menuItem.getItemId();
                            //Use switch condition
                            switch (id){
                                case R.id.menu_delete:
                                    //When click on delete
                                    //Use for loop
                                    for(String s : selectList){
                                        //Remove selected item from array list
                                        arrayList.remove(s);
                                    }
                                    //Check condition
                                    if(arrayList.size() == 0){
                                        //When array list is empty
                                        //Visible text view
                                        tvEmpty.setVisibility(View.VISIBLE);
                                    }
                                    //Finish action mode
                                    break;
                                case R.id.menu_select_all:
                                    //When click on select all
                                    //Check condition
                                    if(selectList.size() == arrayList.size()){
                                        //When all item selected
                                        //Set isSelectAll false
                                        isSelectAll = false;
                                        //Clear select array list
                                        selectList.clear();
                                    }else {
                                        //When all item unselected
                                        //Set isSelectAll true
                                        isSelectAll = true;
                                        //Clear select array list
                                        selectList.clear();
                                        //Add all value in select arraylist
                                        selectList.addAll(arrayList);
                                    }
                                    //Set text on view model
                                    mainViewModel.setText(String.valueOf(selectList.size()));
                                    //Notify adapter
                                    notifyDataSetChanged();
                                    break;
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode actionMode) {
                            //When action mode is destroy
                            //Set isEnable false
                            isEnable = false;
                            //Set isSelectAll = false;
                            isSelectAll = false;
                            //Clear select array list
                            selectList.clear();
                            //notify adapter
                            notifyDataSetChanged();

                        }
                    };
                    //Start action mode
                    ((AppCompatActivity) view.getContext()).startActionMode(callback);

                }else{
                    //When action mode is already enable
                    //Call method
                    ClickItem(holder);
                }
                return true;
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Check condition
                if(isEnable){
                    //When action mode is enable
                    //Call method
                    ClickItem(holder);
                }else {
                    //When action mode is not enable
                    //Display toast
                    Toast.makeText(activity, "u clicked" + arrayList.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
                return  true;
            }
        });


        //Check condition
        if(isSelectAll){
            //When all value selected
            //Visible all check box image
            holder.ivCheckbox.setVisibility(View.VISIBLE);
            //Set background color
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }else{
            //When item selected
            //Hide check box image
            holder.ivCheckbox.setVisibility(View.GONE);
            //Set BG color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);

        }

    }

    private void ClickItem(ViewHolder holder) {
        //Get Selected item value
        String s = arrayList.get(holder.getAdapterPosition());
        //Check condition
        if(holder.ivCheckbox.getVisibility() == View.GONE){
            //When item not selected
            //Visible check box image
            holder.ivCheckbox.setVisibility(View.VISIBLE);
            //Set BG color
            holder.itemView.setBackgroundColor(Color.LTGRAY);
            //Add value in select array list
            selectList.add(s);

        }else{
            //When item selected
            //Hide check box image
            holder.ivCheckbox.setVisibility(View.GONE);
            //Set BG color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            //Add value in select array list
            selectList.remove(s);

        }

        //Set text on view model
        mainViewModel.setText(String.valueOf(selectList.size()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView ivCheckbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_view);
            ivCheckbox = itemView.findViewById(R.id.iv_check_box);

        }
    }
}
