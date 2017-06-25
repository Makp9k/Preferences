package de.appsfactory.mvp.preferences.processor.models;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Admin on 24.06.2017.
 */

public class PreferencesModel {

    private final String packageName;
    private final String originClassName;
    private final List<PreferenceEntryModel> entries = new LinkedList<>();

    public PreferencesModel(String packageName, String originClassName) {
        this.packageName = packageName;
        this.originClassName = originClassName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getOriginClassName() {
        return originClassName;
    }

    public List<PreferenceEntryModel> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "PreferencesModel{" +
                "packageName='" + packageName + '\'' +
                ", originClassName='" + originClassName + '\'' +
                ", entries=" + entries +
                '}';
    }
}
