package com.example.khanh.myapplication1.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.khanh.myapplication1.R;
import com.example.khanh.myapplication1.model.Lesson;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by khanh on 7/7/2017.
 */

public class FragmentQuestion extends FragmentBase{
    @InjectView(R.id.txtAns1)
    TextView txtAns1;
    @InjectView(R.id.txtAns2)
    TextView txtAns2;
    @InjectView(R.id.txtAns3)
    TextView txtAns3;
    @InjectView(R.id.txtAns4)
    TextView txtAns4;
    @InjectView(R.id.txtQue)
    TextView txtQue;
    @InjectView(R.id.txtCheck1)
    TextView txtCheck1;
    @InjectView(R.id.txtCheck2)
    TextView txtCheck2;
    @InjectView(R.id.txtCheck3)
    TextView txtCheck3;
    @InjectView(R.id.txtCheck4)
    TextView txtCheck4;
    @Override
    protected void initDataDefault() {
        super.initDataDefault();
        Bundle bundle= getArguments();

        int qid=bundle.getInt("QID");
        String imgque=bundle.getString("IMGQUE");
        String txtque=bundle.getString("TXTQUE");
        String audioque=bundle.getString("AUDIOQUE");
        ArrayList<String> answer=bundle.getStringArrayList("ANSWER");
        int cat= bundle.getInt("CAT");
        int rightans=bundle.getInt("RIGHTANS");

        txtQue.setText(txtque);
        txtAns1.setText(answer.get(0));
        txtAns2.setText(answer.get(1));
        txtAns3.setText(answer.get(2));
        txtAns4.setText(answer.get(3));

//        Log.e("Test",txtque);

    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.question_layout2;
    }

    public  static FragmentQuestion newInstance(Lesson lesson){

        FragmentQuestion fragmenT2= new FragmentQuestion();
        Bundle bundle= new Bundle();

        bundle.putInt("QID",lesson.getQid());
        bundle.putString("IMGQUE",lesson.getImgQuestion());
        bundle.putString("TXTQUE",lesson.getTxtQuestion());
        bundle.putString("AUDIOQUE",lesson.getAudioQuestion());
        bundle.putStringArrayList("ANSWER",lesson.getAnswers());
        bundle.putInt("CAT",lesson.getCat());
        bundle.putInt("RIGHTANS",lesson.getRightAnswer());

        fragmenT2.setArguments(bundle);
        return fragmenT2;

    }

    @OnClick({R.id.txtAns1,R.id.txtAns2,R.id.txtAns3,R.id.txtAns4})
    public void onClick(View v){
        if(v.getId()==R.id.txtAns1){
            txtAns1.setBackgroundColor(getResources().getColor(R.color.chooseGray));
            txtAns2.setBackgroundColor(getResources().getColor(R.color.noChoose));
            txtAns3.setBackgroundColor(getResources().getColor(R.color.noChoose));
            txtAns4.setBackgroundColor(getResources().getColor(R.color.noChoose));
            ((fragment2)getParentFragment()).onSelect(0);
        }
        if(v.getId()==R.id.txtAns2){
            txtAns2.setBackgroundColor(getResources().getColor(R.color.chooseGray));
            txtAns1.setBackgroundColor(getResources().getColor(R.color.noChoose));
            txtAns3.setBackgroundColor(getResources().getColor(R.color.noChoose));
            txtAns4.setBackgroundColor(getResources().getColor(R.color.noChoose));
            ((fragment2)getParentFragment()).onSelect(1);
        }
        if(v.getId()==R.id.txtAns3){
            txtAns3.setBackgroundColor(getResources().getColor(R.color.chooseGray));
            txtAns2.setBackgroundColor(getResources().getColor(R.color.noChoose));
            txtAns1.setBackgroundColor(getResources().getColor(R.color.noChoose));
            txtAns4.setBackgroundColor(getResources().getColor(R.color.noChoose));
            ((fragment2)getParentFragment()).onSelect(2);
        }
        if(v.getId()==R.id.txtAns4){
            txtAns4.setBackgroundColor(getResources().getColor(R.color.chooseGray));
            txtAns2.setBackgroundColor(getResources().getColor(R.color.noChoose));
            txtAns3.setBackgroundColor(getResources().getColor(R.color.noChoose));
            txtAns1.setBackgroundColor(getResources().getColor(R.color.noChoose));
            ((fragment2)getParentFragment()).onSelect(3);
        }
    }

    public void bbb() {
        boolean flagFT = ((fragment2)getParentFragment()).flagFinishTest;
        if (flagFT == false) {
            int id = ((fragment2)getParentFragment()).vpPager.getCurrentItem();
            int sl = ((fragment2)getParentFragment()).myAnswer[id];
            int ra = ((fragment2)getParentFragment()).listLesson.get(id).getRightAnswer();

            setResult(sl, ra);
        }
    }

    public void setResult(int select,int rightanswer){
        setRightAnswer(rightanswer,select);
        setSelectAnswer(select,rightanswer);

    }

    private void setSelectAnswer(int select, int rightanswer) {
        if(select==0 ){
            if(select==rightanswer){
            }
            else{
                txtCheck1.setBackground(getResources().getDrawable(R.drawable.ic_clear));
//                txtAns1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }
        if(select==1){
            if(select==rightanswer){
            }
            else{
                txtCheck2.setBackground(getResources().getDrawable(R.drawable.ic_clear));
//                txtAns2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }
        if(select==2){
            if(select==rightanswer){
            }else{
                txtCheck3.setBackground(getResources().getDrawable(R.drawable.ic_clear));
//                txtAns3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }
        if(select==3){
            if(select==rightanswer){
            }
            else{
                txtCheck4.setBackground(getResources().getDrawable(R.drawable.ic_clear));
//                txtAns4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }

    private void setRightAnswer(int rightanswer,int select) {
        if(rightanswer==0) {
            txtCheck1.setBackground(getResources().getDrawable(R.drawable.ic_done));
                txtAns1.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns2.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns3.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns4.setBackgroundColor(getResources().getColor(R.color.noChoose));
        }
        if(rightanswer==1){
            txtCheck2.setBackground(getResources().getDrawable(R.drawable.ic_done));
                txtAns2.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns1.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns3.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns4.setBackgroundColor(getResources().getColor(R.color.noChoose));
        }
        if(rightanswer==2){
            txtCheck3.setBackground(getResources().getDrawable(R.drawable.ic_done));
                txtAns3.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns2.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns1.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns4.setBackgroundColor(getResources().getColor(R.color.noChoose));
        }
        if(rightanswer==3){
            txtCheck4.setBackground(getResources().getDrawable(R.drawable.ic_done));
            txtAns4.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns2.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns3.setBackgroundColor(getResources().getColor(R.color.noChoose));
                txtAns1.setBackgroundColor(getResources().getColor(R.color.noChoose));
        }
    }

}
