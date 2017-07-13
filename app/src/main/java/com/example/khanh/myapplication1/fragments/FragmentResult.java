package com.example.khanh.myapplication1.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.khanh.myapplication1.R;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by khanh on 7/8/2017.
 */

public class FragmentResult extends FragmentBase {

    @InjectView(R.id.txtCorrect)
    TextView txtCorrect;
    @InjectView(R.id.txtMistakes)
    TextView txtMistakes;
    @InjectView(R.id.txtSkipped)
    TextView txtSkipped;
    @InjectView(R.id.progress_bar_result_test)
    ProgressBar progressBar;
    Handler mHandler= new Handler();
    int mProgressStatus=0;
    @InjectView(R.id.txt_percent_result)
    TextView txt_percent_result;
    List<Integer>myResult;
    @Override
    protected void initDataDefault() {
        super.initDataDefault();
        Bundle bundle=getArguments();

        int correct=bundle.getInt("correct");
        int mistakes=bundle.getInt("mistakes");
        int skipped=bundle.getInt("skipped");

        dosomething((100*correct)/(correct+mistakes+skipped));

        txtCorrect.setText(correct+"");
        txtMistakes.setText(mistakes+"");
        txtSkipped.setText(skipped+"");

    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.result_layout;
    }
    public  static FragmentResult newInstance(int c, int m, int s){

        FragmentResult fragmentResult= new FragmentResult();
        Bundle bundle= new Bundle();

        bundle.putInt("correct",c);
        bundle.putInt("mistakes",m);
        bundle.putInt("skipped",s);

        fragmentResult.setArguments(bundle);
        return fragmentResult;
    }

    public void dosomething(final int percent) {
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus <= percent) {
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(mProgressStatus);
                            txt_percent_result.setText(mProgressStatus+"%");
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mProgressStatus++;
                }
            }
        }).start();
    }
}
