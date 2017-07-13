package com.example.khanh.myapplication1.fragments;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.khanh.myapplication1.R;
import com.example.khanh.myapplication1.adapters.MyFragmentPagerAdapter;
import com.example.khanh.myapplication1.adapters.NonSwipeableViewPager;
import com.example.khanh.myapplication1.model.Lesson;
import com.example.khanh.myapplication1.request.TaskQuestion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment2 extends FragmentBase {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    private ArrayList<Lesson> lessonList;
    FragmentPagerAdapter myFragmentPagerAdapter;
    @InjectView(R.id.vpPager)
    NonSwipeableViewPager vpPager;
    private ArrayList<Fragment> listFragment = new ArrayList<>();
    List<Lesson> listLesson;
    @InjectView(R.id.btnNext)
    Button btnNext;
    @InjectView(R.id.btnPre)
    Button btnPre;
    @InjectView(R.id.btnNumQue)
    Button btnNumQue;
    int[] myAnswer = new int[100];
    int ncorrect = 0, nskipped = 0, nmistakes = 0, dem = 0;
    boolean flagFinishTest = true;

    @Override
    protected void initDataDefault() {
        super.initDataDefault();
        loadData();

        Bundle bundle = getArguments();
        int index = bundle.getInt("INDEX");

        toolbar.setTitle("Test " + (index + 1));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.onBackPressed();
            }
        });

        toolbar.inflateMenu(R.menu.menu_main);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.mShare:
//                Intent i = new Intent(
//                        android.content.Intent.ACTION_SEND);
//                i.setType("text/plain");
//                i.putExtra(
//                        android.content.Intent.EXTRA_TEXT, "The string you want to share, which can include URLs");
//                startActivity(Intent.createChooser(i, "Title of your share dialog"));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void loadList() {
        for (int i = 0; i < listLesson.size(); i++) {
            listFragment.add(FragmentQuestion.newInstance(listLesson.get(i)));
            myAnswer[i] = -1;
        }

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), listFragment);

        vpPager.setAdapter(myFragmentPagerAdapter);

        vpPager.setOffscreenPageLimit(listLesson.size());
    }

    private void loadData() {
        TaskQuestion taskQuestion = new TaskQuestion(getContext());
        taskQuestion.request(new Response.Listener<ArrayList<Lesson>>() {
            @Override
            public void onResponse(ArrayList<Lesson> response) {
                listLesson = response;
                loadList();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_fragment2;
    }

    public static fragment2 newInstance(int index) {

        fragment2 fragmenT2 = new fragment2();
        Bundle bundle = new Bundle();

        bundle.putInt("INDEX", index);

        fragmenT2.setArguments(bundle);
        return fragmenT2;
    }

    public List<Lesson> getQuestionsList() {
        lessonList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getContext().getAssets().open("data-sample.json"), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = sb.toString();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Type type = new TypeToken<List<Lesson>>() {
        }.getType();

        return gson.fromJson(jsonString, type);
    }

    protected class yourDataTask extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {

            String str = "http://s3.yobimind.com/d/tmp/en-grammar.json";
            URLConnection urlConn = null;
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }

                return new JSONObject(stringBuffer.toString());
            } catch (Exception ex) {
                Log.e("App", "yourDataTask", ex);
                return null;
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            if (response != null) {
                try {
                    Log.e("App", "Success: " + response.getString("yourJsonElement"));
                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }

    @OnClick({R.id.btnNext, R.id.btnPre})
    public void onClick(View v) {
        final int k = vpPager.getCurrentItem();
        if (v.getId() == R.id.btnNext) {

            if (k < (listFragment.size() - 1)) {
                if (flagFinishTest == true) {
                    Toast.makeText(mainActivity.getApplication(), "skipped!", Toast.LENGTH_SHORT).show();
                }
                vpPager.setCurrentItem(k + 1);
                btnNumQue.setText("Question " + (k + 2));
            }

            if (k == listLesson.size() - 1) {
                flagFinishTest = false;
                for (int i = 0; i < listLesson.size(); i++) {
                    if (myAnswer[i] == -1) {
                        nskipped++;
                    } else if (myAnswer[i] == listLesson.get(i).getRightAnswer()) {
                        ncorrect++;
                    } else nmistakes++;
                }
                if (flagFinishTest == false) {
                    dem++;
                    if (dem == 1) {


                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(mainActivity, android.R.style.Theme_Material_Light_Dialog_Alert);

                        } else {
                            builder = new AlertDialog.Builder(mainActivity);
                        }
                        builder.setTitle("Are you sure you want to finish the test?")
                                .setMessage("You have skipped " + (nskipped) + " question.")
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        if ((nskipped) == listLesson.size()) {
                                            listFragment.add(FragmentResult.newInstance(ncorrect, 0, nskipped));
                                        } else {
                                            listFragment.add(FragmentResult.newInstance(ncorrect, nmistakes, nskipped));
                                        }
                                        myFragmentPagerAdapter.notifyDataSetChanged();
                                        vpPager.setCurrentItem(k + 1);
                                        btnNumQue.setText("Result");
                                    }
                                })
                                .show();


                    } else {
                        if ((nskipped) == listLesson.size()) {
                            listFragment.add(FragmentResult.newInstance(ncorrect, 0, nskipped));
                        } else {
                            listFragment.add(FragmentResult.newInstance(ncorrect, nmistakes, nskipped));
                        }
                        myFragmentPagerAdapter.notifyDataSetChanged();
                        vpPager.setCurrentItem(k + 1);
                        btnNumQue.setText("Result");
                    }

                }
            }
        }

        if (v.getId() == R.id.btnPre) {
            if (k > 0) {
                if (listFragment.size() > listLesson.size()) {
                    listFragment.remove(listFragment.size() - 1);
                    myFragmentPagerAdapter.notifyDataSetChanged();
                }
                vpPager.setCurrentItem(k - 1);
                btnNumQue.setText("Question " + (k));
                Fragment f = listFragment.get(k - 1);
                // kiem tra type cua fragment f co phai la FragmentQuestion
                if (f instanceof FragmentQuestion) {
                    ((FragmentQuestion) f).bbb();
                }
            }
        }
    }

    public void onSelect(int index) {
        int k = vpPager.getCurrentItem();
        myAnswer[k] = index;
        if (k < (listFragment.size() - 1)) {
            vpPager.setCurrentItem(k + 1);
            btnNumQue.setText("Question " + (k + 2));
        }
        if (k == listLesson.size() - 1) {
            flagFinishTest = false;
            for (int i = 0; i < listLesson.size(); i++) {
                if (myAnswer[i] == -1) {
                    nskipped++;
                } else if (myAnswer[i] == listLesson.get(i).getRightAnswer()) {
                    ncorrect++;
                } else nmistakes++;
            }
            listFragment.add(FragmentResult.newInstance(ncorrect, nmistakes, nskipped));
            myFragmentPagerAdapter.notifyDataSetChanged();
            vpPager.setCurrentItem(k + 1);
            btnNumQue.setText("Result");
        }
    }

}
