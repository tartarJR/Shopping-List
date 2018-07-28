package com.tatar.shoppinglist.ui;

public interface BaseView {
    void toggleProgressBar();

    void toggleNoDataTv(int size);

    void displayMessage(String message);
}
