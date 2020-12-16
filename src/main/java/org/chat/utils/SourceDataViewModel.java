package org.chat.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SourceDataViewModel {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty age = new SimpleStringProperty();

    private static final SourceDataViewModel viewModel = new SourceDataViewModel();

    private SourceDataViewModel() {
    }

    public static SourceDataViewModel getInstance() {
        return viewModel;
    }

    public void setTargetData(){
        TargetDataViewModel viewModel = TargetDataViewModel.getInstance();
        viewModel.setName(name.get());
        viewModel.setAge(age.get());
    }
}
