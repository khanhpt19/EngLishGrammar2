package com.example.khanh.myapplication1.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.khanh.myapplication1.R;
import com.example.khanh.myapplication1.adapters.LessonAdapter;
import com.example.khanh.myapplication1.model.Lesson;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragment1 extends FragmentBase implements LessonAdapter.CallBack {
    @InjectView(R.id.recycler_view)
    RecyclerView recycler_view;
    @InjectView(R.id.toolbar1)
    Toolbar toolbar1;

    List<Lesson> lessonList = new ArrayList<>();
    private LessonAdapter mAdapter;

    @Override
    protected void initDataDefault() {
        super.initDataDefault();

        lessonList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            lessonList.add(new Lesson(i));
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recycler_view.setLayoutManager(layoutManager);

        mAdapter = new LessonAdapter(lessonList, this);

        recycler_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        toolbar1.inflateMenu(R.menu.menu_main);
        mainActivity.setUpToolbar(toolbar1);
    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_fragment1;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mnMore) {
            Toast.makeText(mainActivity, "More Great App", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.mnProgress) {
            Toast.makeText(mainActivity, "Progress", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnClick(int index) {
        if (isConnected() == true) {
            //Khởi tạo dialog
            mainActivity.onOpenFragment(fragment2.newInstance(index), true);
        } else {
            ViewDialog viewDialog = new ViewDialog();
            viewDialog.showDialog(mainActivity, "Thiết bị của bạn không được kết nối Internet. Hãy kết nối Internet và restart app",2);
        }
    }
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
