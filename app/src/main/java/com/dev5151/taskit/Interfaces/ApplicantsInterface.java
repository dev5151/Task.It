package com.dev5151.taskit.Interfaces;

import com.dev5151.taskit.models.TaskRequestModel;
import com.google.android.gms.tasks.Task;

public interface ApplicantsInterface {
    void launchApplicantsList();

    void launchApplicationDetails(TaskRequestModel taskRequestModel);
}
