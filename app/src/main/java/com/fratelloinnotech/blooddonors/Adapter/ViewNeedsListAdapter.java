package com.fratelloinnotech.blooddonors.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.fratelloinnotech.blooddonors.Beans.ViewNeedsBean;
import com.fratelloinnotech.blooddonors.Map.BloodbanksMapActivity;
import com.fratelloinnotech.blooddonors.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ViewNeedsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    //DonarActivity donarActivity=new DonarActivity();
    List<ViewNeedsBean> data= Collections.emptyList();
    ViewNeedsListAdapter.MyHolder myHolder;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // View view= Inflater.inflate(R.layout.admin_view_feedback, parent,false);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_view_needs_list_adapter, parent, false);
        final ViewNeedsListAdapter.MyHolder holder=new ViewNeedsListAdapter.MyHolder(itemView);
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
    public ViewNeedsListAdapter(Context context, List<ViewNeedsBean> data1){
        this.context=context;
        this.data=data1;
    }
public ViewNeedsListAdapter()
{}

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        myHolder= (ViewNeedsListAdapter.MyHolder) holder;
        ViewNeedsBean current = data.get(position);
        myHolder.name.setText(current.getNname());
        //myHolder.department.setText(current.getBranch());
        myHolder.mobile.setText(current.getNmobile());
        myHolder.email.setText(current.getNemail());
        myHolder.vpurpose.setText(current.getNpurpose());
        myHolder.address.setText(current.getNaddress());
        myHolder.bloodgroup.setText(current.getNbloodgroup());
        myHolder.city.setText(current.getNcity());
        myHolder.vduedate.setText(current.getNduedate());


        myHolder.mailgif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String mail =  data.get(position).getNemail();
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

        myHolder.callgif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPermissionGranted()){
                    String mobile =  data.get(position).getNmobile();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + mobile));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Intent chooser  = Intent.createChooser(intent, "Complete Action using..");
                    context.startActivity(chooser);
                }
            }
        });

        myHolder.mapgifimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BloodbanksMapActivity.class);
                String s=data.get(position).getNaddress();
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
    TextView name,mobile,email,address,vpurpose,city,bloodgroup,vduedate;
        ImageView mapgifimage,mailgif,callgif;

        public MyHolder(View itemView) {
            super(itemView);
            email = (TextView) itemView.findViewById(R.id.vneed_mail);
            mobile = (TextView) itemView.findViewById(R.id.vneed_phone);
            bloodgroup = (TextView) itemView.findViewById(R.id.vneed_bloodgroup);
            name = (TextView) itemView.findViewById(R.id.vneed_name);
            address = (TextView) itemView.findViewById(R.id.vneed_address);
            city=(TextView)itemView.findViewById(R.id.vneed_city);
            vpurpose=(TextView)itemView.findViewById(R.id.vneed_purpose);
            vduedate=(TextView)itemView.findViewById(R.id.vneed_duedate);
            mapgifimage=(ImageView)itemView.findViewById(R.id.vneedgifmap);
            mailgif=(ImageView)itemView.findViewById(R.id.vneedgifmail);
            callgif=(ImageView)itemView.findViewById(R.id.vneedgifcall);
        }
    }

    public void setFilter(ArrayList<ViewNeedsBean> newbbList){
        ArrayList arraylist=new ArrayList<>();
        arraylist.addAll(newbbList);
        notifyDataSetChanged();

    }
}
