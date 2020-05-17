package com.dev5151.taskit.Interfaces;

import com.dev5151.taskit.models.TaskRequestModel;
import com.dev5151.taskit.models.Tasks;

public interface ItemClickListener {
    void onClickTask(Tasks task);

    void onClickApplicants(TaskRequestModel taskRequestModel);
}
