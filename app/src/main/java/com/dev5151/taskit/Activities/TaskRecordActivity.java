package com.dev5151.taskit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.dev5151.taskit.Adapters.ViewPagerAdapter;
import com.dev5151.taskit.Fragments.ChatFragment;
import com.dev5151.taskit.Fragments.CompletedTasksFragment;
import com.dev5151.taskit.Fragments.GivenTasksFragment;
import com.dev5151.taskit.Fragments.TasksRequestFragment;
import com.dev5151.taskit.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TaskRecordActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_record);
        initViews();

        fragmentList.add(new CompletedTasksFragment());
        fragmentList.add(new GivenTasksFragment());
        fragmentList.add(new TasksRequestFragment());

        titleList.add("Completed");
        titleList.add("Posted");
        titleList.add("Requests");

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void initViews() {
        fragmentManager = getSupportFragmentManager();
        tabLayout = findViewById(R.id.tab_layout);
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();
        viewPager = findViewById(R.id.view_pager);
    }

    private void initToolbar() {

    }
}
