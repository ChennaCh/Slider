package com.fit.bloodmanagment.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fit.bloodmanagment.Activity.BloodBanksActivity;
import com.fit.bloodmanagment.Activity.DonarActivity;
import com.fit.bloodmanagment.Beans.BloodbankBean;
import com.fit.bloodmanagment.Beans.DonorBean;
import com.fit.bloodmanagment.Map.BloodbanksMapActivity;
import com.fit.bloodmanagment.Map.DonorlistMapActivity;
import com.fit.bloodmanagment.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DonorListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    DonarActivity donarActivity=new DonarActivity();
    List<DonorBean> data= Collections.emptyList();
    ImageView mapgifimage,mailgif,callgif;
    DonorListAdapter.MyHolder myHolder;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // View view= Inflater.inflate(R.layout.admin_view_feedback, parent,false);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_donor_list_adapter, parent, false);
        mapgifimage=(ImageView)itemView.findViewById(R.id.donorgifmap);
        mailgif=(ImageView)itemView.findViewById(R.id.donorgifmail);
        callgif=(ImageView)itemView.findViewById(R.id.donorgifcall);
        final DonorListAdapter.MyHolder holder=new DonorListAdapter.MyHolder(itemView);




        return holder;
    }
    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }
    public DonorListAdapter(Context context, List<DonorBean> data1){
        this.context=context;
        this.data=data1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        myHolder= (DonorListAdapter.MyHolder) holder;
        DonorBean current = data.get(position);
        myHolder.name.setText(current.getDfullname());
        //myHolder.department.setText(current.getBranch());
        myHolder.mobile.setText(current.getDmobile());
        myHolder.email.setText(current.getDemail());
        myHolder.address.setText(current.getDaddress());
        myHolder.bloodgroup.setText(current.getDbloodgroup());
        myHolder.city.setText(current.getDcity());
        myHolder.age.setText(current.getDage());

        mailgif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String mail =  data.get(position).getDemail();
                    //  Toast.makeText(context, "mail."+mail, Toast.LENGTH_SHORT).show();


                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setType("message/rfc822");
                    i.setData(Uri.parse("mailto:"+ mail));
                    // i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Urgent Requirement of Blood");
                    i.putExtra(Intent.EXTRA_TEXT   , "hi, Please send the complete details about blood avaible status");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        callgif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPermissionGranted()){
                    String mobile =  data.get(position).getDmobile();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + mobile));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Intent chooser  = Intent.createChooser(intent, "Complete Action using..");
                    context.startActivity(chooser);
                }
            }
        });

        mapgifimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BloodbanksMapActivity.class);
                String s=data.get(position).getDaddress();
                intent.putExtra("addr",s);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class MyHolder extends RecyclerView.ViewHolder{

        TextView name,mobile,email,address,city,bloodgroup,age;

        public MyHolder(View itemView) {
            super(itemView);
            email = (TextView) itemView.findViewById(R.id.donor_mail);
            mobile = (TextView) itemView.findViewById(R.id.donor_phone);
            bloodgroup = (TextView) itemView.findViewById(R.id.donor_bloodgroup);
            name = (TextView) itemView.findViewById(R.id.donor_name);
            address = (TextView) itemView.findViewById(R.id.donor_address);
            city=(TextView)itemView.findViewById(R.id.donor_city);
            age=(TextView)itemView.findViewById(R.id.donor_age);
        }
    }


    public void setFilter(ArrayList<DonorBean> newList){
            ArrayList arraylist=new ArrayList<>();
        arraylist.addAll(newList);
        notifyDataSetChanged();

    }
}
