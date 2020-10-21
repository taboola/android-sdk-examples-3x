package com.e.sdk_example_app_30.tabs;

public interface TabsContract {

    interface TabsView {
        void setCurrentPage(int currentPage);

    }

    interface TabsPresenter extends BasePresenter<TabsContract.TabsView> {
        void setCurrentPage(int position);
    }

}
