package com.fit.bloodmanagment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fit.bloodmanagment.Activity.BloodBanksActivity;
import com.fit.bloodmanagment.Beans.BloodbankBean;
import com.fit.bloodmanagment.Map.BloodbanksMapActivity;
import com.fit.bloodmanagment.R;

import java.util.Collections;
import java.util.List;

import static android.R.id.message;

/**
 * Created by admin on 10/4/2017.
 */

public class BloodbankListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    BloodBanksActivity bbactivity=new BloodBanksActivity();
        List<BloodbankBean> data= Collections.emptyList();
  ImageView mapgifimage,mailgif,callgif;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // View view= Inflater.inflate(R.layout.admin_view_feedback, parent,false);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bloodbank_view, parent, false);
        mapgifimage=(ImageView)itemView.findViewById(R.id.gifmappin);
        mailgif=(ImageView)itemView.findViewById(R.id.gifmail);
        callgif=(ImageView)itemView.findViewById(R.id.gifcall);
        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        final MyHolder holder=new MyHolder(itemView);


        mapgifimage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent=new Intent(context, BloodbanksMapActivity.class);
                context.startActivity(intent);

                return true;
            }
        });
        mailgif.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setType("message/rfc822");
                i.setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    context.startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        callgif.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return true;

            }
        });

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

    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = context.getPackageManager()
                            .getLaunchIntentForPackage(
                                    context.getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }
}
