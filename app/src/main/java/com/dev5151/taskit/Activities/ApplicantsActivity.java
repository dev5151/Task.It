package com.dev5151.taskit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.dev5151.taskit.Fragments.ApplicantsDetailsFragment;
import com.dev5151.taskit.Fragments.ApplicantsListFragment;
import com.dev5151.taskit.Interfaces.ApplicantsInterface;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.TaskRequestModel;

public class ApplicantsActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    private static ApplicantsInterface applicantsInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);

        initViews();
        launchFragment(new ApplicantsListFragment());

        applicantsInterface = new ApplicantsInterface() {
            @Override
            public void launchApplicantsList() {
                launchFragment(new ApplicantsListFragment());
            }

            @Override
            public void launchApplicationDetails(TaskRequestModel taskRequestModel) {
                Bundle bundle = new Bundle();
                bundle.putString("taskId", taskRequestModel.getTaskId());
                bundle.putString("applicantUid", taskRequestModel.getApplicantUid());
                ApplicantsDetailsFragment applicantsDetailsFragment = new ApplicantsDetailsFragment();
                applicantsDetailsFragment.setArguments(bundle);
                launchFragment(applicantsDetailsFragment);

            }
        };
    }

    private void initViews() {
        fragmentManager = getSupportFragmentManager();
    }

    private void launchFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment, "").commit();
    }

    public static ApplicantsInterface getApplicantsInterface() {
        return applicantsInterface;
    }

}
