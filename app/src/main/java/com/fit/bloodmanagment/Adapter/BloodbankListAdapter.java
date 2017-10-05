package com.fit.bloodmanagment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fit.bloodmanagment.Beans.BloodbankBean;
import com.fit.bloodmanagment.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 10/4/2017.
 */

public class BloodbankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
        List<BloodbankBean> data= Collections.emptyList();


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // View view= Inflater.inflate(R.layout.admin_view_feedback, parent,false);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bloodbank_view, parent, false);
        MyHolder holder=new MyHolder(itemView);
//        LayoutInflater mInflater = (LayoutInflater)
//                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//        view = mInflater.inflate(R.layout.view_faculty_details, null);

       // ViewHolder holder;
      //  holder = new ViewHolder();

//        holder.name = (TextView) itemView.findViewById(R.id.get_name);
//       // holder.branch = (TextView) view.findViewById(R.id.facu_branch);
//        holder.phone = (TextView) itemView.findViewById(R.id.get_phone);
//        holder.email = (TextView) itemView.findViewById(R.id.get_mail);
//        holder.feedback = (TextView) itemView.findViewById(R.id.get_feedback);
//        if (adapterItems.get(postion).getGname() != null){
//            holder.name.setText(adapterItems.get(postion).getGname());
//        }
//        if (adapterItems.get(postion).getGmobile() != null){
//            holder.mobile.setText(adapterItems.get(postion).getGmobile());
//        }
//        if (adapterItems.get(postion).getGbranch() != null){
//            holder.branch.setText(adapterItems.get(postion).getGbranch());
//        }
//        if (adapterItems.get(postion).getGqualification() != null){
//            holder.qualificaion.setText(adapterItems.get(postion).getGqualification());
//        }
//        if (adapterItems.get(postion).getGgender() != null){
//            String ch= adapterItems.get(postion).getGgender().toString();
//            if (ch.equals("MALE") || ch.equals("male")){
//                holder.getimage.setImageResource(R.drawable.men);
//            }
//            else {
//                holder.getimage.setImageResource(R.drawable.women);
//            }
//        }


        return holder;
    }

    public BloodbankListAdapter(Context context, List<BloodbankBean> data1){
        this.context=context;
        this.data=data1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        BloodbankBean current = data.get(position);
        myHolder.name.setText(current.getGname());
        //myHolder.department.setText(current.getBranch());
        myHolder.phone.setText(current.getGmobile());
        myHolder.email.setText(current.getGemail());
        myHolder.feedback.setText(current.getGabout());



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder{

        TextView email,phone,name,feedback;

        public MyHolder(View itemView) {
            super(itemView);
            email = (TextView) itemView.findViewById(R.id.get_mail);
            phone = (TextView) itemView.findViewById(R.id.get_phone);
            //department = (TextView) itemView.findViewById(R.id.get_department);
            name = (TextView) itemView.findViewById(R.id.get_name);
            feedback = (TextView) itemView.findViewById(R.id.get_feedback);
        }
    }
}
