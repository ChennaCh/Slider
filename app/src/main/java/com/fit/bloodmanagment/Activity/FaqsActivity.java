package com.fit.bloodmanagment.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.fit.bloodmanagment.R;

public class FaqsActivity extends AppCompatActivity implements View.OnClickListener{
    TextView text1,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11,text12,text13,text14,text15;
    LinearLayout panel1,panel2,panel3,panel4,panel5,panel6,panel7,panel8,panel9,panel10,panel11,panel12,panel13,panel14,panel15;
    View openLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_faqs);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.Faqs));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        panel1 = (LinearLayout) findViewById(R.id.faqpanel1);
        panel2 = (LinearLayout) findViewById(R.id.faqpanel2);
        panel3 = (LinearLayout) findViewById(R.id.faqpanel3);
        panel4 = (LinearLayout) findViewById(R.id.faqpanel4);
        panel5 = (LinearLayout) findViewById(R.id.faqpanel5);
        panel6 = (LinearLayout) findViewById(R.id.faqpanel6);
        panel7 = (LinearLayout) findViewById(R.id.faqpanel7);
        panel8 = (LinearLayout) findViewById(R.id.faqpanel8);
        panel9 = (LinearLayout) findViewById(R.id.faqpanel9);
        panel10 = (LinearLayout) findViewById(R.id.faqpanel10);
        panel11= (LinearLayout) findViewById(R.id.faqpanel11);
        panel12= (LinearLayout) findViewById(R.id.faqpanel12);
        panel13= (LinearLayout) findViewById(R.id.faqpanel13);
        panel14= (LinearLayout) findViewById(R.id.faqpanel14);
        panel15= (LinearLayout) findViewById(R.id.faqpanel15);

        text1 = (TextView) findViewById(R.id.faqtext1);
        text2 = (TextView) findViewById(R.id.faqtext2);
        text3 = (TextView) findViewById(R.id.faqtext3);
        text4 = (TextView) findViewById(R.id.faqtext4);
        text5 = (TextView) findViewById(R.id.faqtext5);
        text6 = (TextView) findViewById(R.id.faqtext6);
        text7 = (TextView) findViewById(R.id.faqtext7);
        text8 = (TextView) findViewById(R.id.faqtext8);
        text9 = (TextView) findViewById(R.id.faqtext9);
        text10 = (TextView) findViewById(R.id.faqtext10);
        text11= (TextView) findViewById(R.id.faqtext11);
        text12= (TextView) findViewById(R.id.faqtext12);
        text13= (TextView) findViewById(R.id.faqtext13);
        text14= (TextView) findViewById(R.id.faqtext14);
        text15= (TextView) findViewById(R.id.faqtext15);




        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text4.setOnClickListener(this);
        text5.setOnClickListener(this);
        text6.setOnClickListener(this);
        text7.setOnClickListener(this);
        text8.setOnClickListener(this);
        text9.setOnClickListener(this);
        text10.setOnClickListener(this);
        text11.setOnClickListener(this);
        text12.setOnClickListener(this);
        text13.setOnClickListener(this);
        text14.setOnClickListener(this);
        text15.setOnClickListener(this);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view)
    {
        hideOthers(view);
    }
        private void hideThemAll () {
            if (openLayout == null) return;
            if (openLayout == panel1)
                panel1.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));
            if (openLayout == panel2)
                panel2.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel2, true));
            if (openLayout == panel3)
                panel3.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel3, true));
            if (openLayout == panel4)
                panel4.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel4, true));
            if (openLayout == panel5)
                panel5.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel5, true));
            if (openLayout == panel6)
                panel6.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel6, true));
            if (openLayout == panel7)
                panel7.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel7, true));
            if (openLayout == panel8)
                panel8.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel8, true));
            if (openLayout == panel9)
                panel9.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel9, true));
            if (openLayout == panel10)
                panel10.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel10, true));
            if (openLayout == panel11)
                panel11.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel11, true));
            if (openLayout == panel12)
                panel12.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel12, true));
            if (openLayout == panel13)
                panel13.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel13, true));
            if (openLayout == panel14)
                panel14.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel14, true));
            if (openLayout == panel15)
                panel15.startAnimation(new FaqsActivity.ScaleAnimToHide(1.0f, 1.0f, 1.0f, 0.0f, 500, panel15, true));

        }


    private void hideOthers(View layoutView) {
        {
            int v;
            if (layoutView.getId() == R.id.faqtext1) {
                v = panel1.getVisibility();
                if (v != View.VISIBLE) {
                    panel1.setVisibility(View.VISIBLE);
                    Log.v("CZ", "height..." + panel1.getHeight());
                }

                //panel1.setVisibility(View.GONE);
                //Log.v("CZ","again height..." + panel1.getHeight());
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel1.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel1, true));
                }
            } else if (layoutView.getId() == R.id.faqtext2) {
                v = panel2.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel2.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel2, true));
                }
            } else if (layoutView.getId() == R.id.faqtext3) {
                v = panel3.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel3.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel3, true));
                }
            } else if (layoutView.getId() == R.id.faqtext4) {
                v = panel4.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel4.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel4, true));
                }
            } else if (layoutView.getId() == R.id.faqtext5) {
                v = panel5.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel5.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel5, true));
                }
            } else if (layoutView.getId() == R.id.faqtext6) {
                v = panel6.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel6.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel6, true));
                }
            } else if (layoutView.getId() == R.id.faqtext7) {
                v = panel7.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel7.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel7, true));
                }
            } else if (layoutView.getId() == R.id.faqtext8) {
                v = panel8.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel8.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel8, true));
                }
            } else if (layoutView.getId() == R.id.faqtext9) {
                v = panel9.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel9.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel9, true));
                }
            } else if (layoutView.getId() == R.id.faqtext10) {
                v = panel10.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel10.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel10, true));
                }
            } else if (layoutView.getId() == R.id.faqtext11) {
                v = panel11.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel11.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel11, true));
                }
            } else if (layoutView.getId() == R.id.faqtext12) {
                v = panel12.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel12.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel12, true));
                }
            } else if (layoutView.getId() == R.id.faqtext13) {
                v = panel13.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel13.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel13, true));
                }
            } else if (layoutView.getId() == R.id.faqtext14) {
                v = panel14.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel14.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel14, true));
                }
            } else if (layoutView.getId() == R.id.faqtext15) {
                v = panel15.getVisibility();
                hideThemAll();
                if (v != View.VISIBLE) {
                    panel15.startAnimation(new FaqsActivity.ScaleAnimToShow(1.0f, 1.0f, 1.0f, 0.0f, 500, panel15, true));
                }
            }
        }
    }

    public class ScaleAnimToHide extends ScaleAnimation {

        private View mView;

        private LinearLayout.LayoutParams mLayoutParams;

        private int mMarginBottomFromY, mMarginBottomToY;

        private boolean mVanishAfter = false;

        public ScaleAnimToHide(float fromX, float toX, float fromY, float toY, int duration, View view, boolean vanishAfter) {
            super(fromX, toX, fromY, toY);
            setDuration(duration);
            openLayout = null;
            mView = view;
            mVanishAfter = vanishAfter;
            mLayoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            int height = mView.getHeight();
            mMarginBottomFromY = (int) (height * fromY) + mLayoutParams.bottomMargin - height;
            mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin)) - height;

            Log.v("CZ", "height..." + height + " , mMarginBottomFromY...." + mMarginBottomFromY + " , mMarginBottomToY.." + mMarginBottomToY);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                int newMarginBottom = mMarginBottomFromY + (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime);
                mLayoutParams.setMargins(mLayoutParams.leftMargin, mLayoutParams.topMargin, mLayoutParams.rightMargin, newMarginBottom);
                mView.getParent().requestLayout();
                //Log.v("CZ","newMarginBottom..." + newMarginBottom + " , mLayoutParams.topMargin..." + mLayoutParams.topMargin);
            } else if (mVanishAfter) {
                mView.setVisibility(View.GONE);
            }
        }
    }

    public class ScaleAnimToShow extends ScaleAnimation {

        private View mView;

        private LinearLayout.LayoutParams mLayoutParams;

        private int mMarginBottomFromY, mMarginBottomToY;

        private boolean mVanishAfter = false;

        public ScaleAnimToShow(float toX, float fromX, float toY, float fromY, int duration, View view, boolean vanishAfter) {
            super(fromX, toX, fromY, toY);
            openLayout = view;
            setDuration(duration);
            mView = view;
            mVanishAfter = vanishAfter;
            mLayoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            mView.setVisibility(View.VISIBLE);
            int height = mView.getHeight();
            //mMarginBottomFromY = (int) (height * fromY) + mLayoutParams.bottomMargin + height;
            //mMarginBottomToY = (int) (0 - ((height * toY) + mLayoutParams.bottomMargin)) + height;

            mMarginBottomFromY = 0;
            mMarginBottomToY = height;

            Log.v("CZ", ".................height..." + height + " , mMarginBottomFromY...." + mMarginBottomFromY + " , mMarginBottomToY.." + mMarginBottomToY);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                int newMarginBottom = (int) ((mMarginBottomToY - mMarginBottomFromY) * interpolatedTime) - mMarginBottomToY;
                mLayoutParams.setMargins(mLayoutParams.leftMargin, mLayoutParams.topMargin, mLayoutParams.rightMargin, newMarginBottom);
                mView.getParent().requestLayout();
                //Log.v("CZ","newMarginBottom..." + newMarginBottom + " , mLayoutParams.topMargin..." + mLayoutParams.topMargin);
            }
        }

    }

}

