package com.dev5151.taskit.Interfaces;

public interface BottomNavBarControlInterface {
    void launchHome();

    void launchAccount();

    void launchPostTask();

    void launchFetchTask();

    void launchMessages();

    void goToTask(int i, String taskId);

    void launchBottomSheetEditDetails();
}
