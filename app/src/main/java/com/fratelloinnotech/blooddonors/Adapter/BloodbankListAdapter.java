package com.fratelloinnotech.blooddonors.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fratelloinnotech.blooddonors.Activity.BloodBanksActivity;
import com.fratelloinnotech.blooddonors.Beans.BloodbankBean;
import com.fratelloinnotech.blooddonors.Map.BloodbanksMapActivity;
import com.fratelloinnotech.blooddonors.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 10/4/2017.
 */

public class BloodbankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    BloodBanksActivity bbactivity=new BloodBanksActivity();
    List<BloodbankBean> data= Collections.emptyList();
    //ImageView mapgifimage,mailgif,callgif;
    SharedPreferences shre;
    MyHolder myHolder;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // View view= Inflater.inflate(R.layout.admin_view_feedback, parent,false);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bloodbank_view, parent, false);
//        mapgifimage=(ImageView)itemView.findViewById(R.id.gifmappin);
//        mailgif=(ImageView)itemView.findViewById(R.id.gifmail);
//        callgif=(ImageView)itemView.findViewById(R.id.gifcall);
        final MyHolder holder=new MyHolder(itemView);
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
    public BloodbankListAdapter(Context context, List<BloodbankBean> data1){
        this.context=context;
        this.data=data1;
    }
    public BloodbankListAdapter(){

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        myHolder= (MyHolder) holder;
        BloodbankBean current = data.get(position);
        myHolder.name.setText(current.getBname());
        //myHolder.department.setText(current.getBranch());
        myHolder.mobile.setText(current.getBmobile());
        myHolder.email.setText(current.getBemail());
        myHolder.address.setText(current.getBaddress());
        myHolder.landline.setText(current.getBlandline());
        myHolder.city.setText(current.getBcity());
        myHolder.mailgif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String mail =  data.get(position).getBemail();
                   // Toast.makeText(context, mail, Toast.LENGTH_SHORT).show();
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

        myHolder.callgif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPermissionGranted()){
                    String mobile =  data.get(position).getBmobile();
                    Toast.makeText(context, mobile, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Intent.ACTION_CALL);
//                    intent.setData(Uri.parse("tel:" + mobile));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Intent chooser  = Intent.createChooser(intent, "Complete Action using..");
//                    context.startActivity(chooser);
                }
            }
        });

        myHolder.mapgifimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BloodbanksMapActivity.class);
                String s=data.get(position).getBaddress();
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

        TextView name,mobile,landline,email,address,city;
        ImageView mapgifimage,mailgif,callgif;

        public MyHolder(View itemView) {
            super(itemView);
            email = (TextView) itemView.findViewById(R.id.get_mail);
            mobile = (TextView) itemView.findViewById(R.id.get_phone);
            landline = (TextView) itemView.findViewById(R.id.get_landline);
            name = (TextView) itemView.findViewById(R.id.get_name);
            address = (TextView) itemView.findViewById(R.id.get_address);
            city=(TextView)itemView.findViewById(R.id.get_city);
            mapgifimage=(ImageView)itemView.findViewById(R.id.gifmappin);
            mailgif=(ImageView)itemView.findViewById(R.id.gifmail);
            callgif=(ImageView)itemView.findViewById(R.id.gifcall);
        }
    }
    public void setFilter(ArrayList<BloodbankBean> newbbList){
        ArrayList arraylist=new ArrayList<>();
        arraylist.addAll(newbbList);
        notifyDataSetChanged();

    }
}
