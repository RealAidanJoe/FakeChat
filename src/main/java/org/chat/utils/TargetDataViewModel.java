package org.chat.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TargetDataViewModel {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty age = new SimpleStringProperty();

    private static final TargetDataViewModel viewModel = new TargetDataViewModel();

    private TargetDataViewModel() {
    }

    public static TargetDataViewModel getInstance() {
        return viewModel;
    }

    public void setName(String s) {
        name.set(s);
    }

    public void setAge(String s) {
        age.set(s);
    }
}
