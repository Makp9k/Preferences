package de.appsfactory.preferences.processor.models;

/**
 * Created by Admin on 24.06.2017.
 */

public class PreferenceEntryModel {

    private String name;
    private String type;
    private String preferenceTypeName;
    private Object defaultValue;
    private String customAdapter;

    public PreferenceEntryModel(String name, String type, String preferenceTypeName, Object defaultValue, String customAdapter) {
        this.name = name;
        this.type = type;
        this.preferenceTypeName = preferenceTypeName;
        this.defaultValue = defaultValue;
        this.customAdapter = customAdapter;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPreferenceTypeName() {
        return preferenceTypeName;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public String getCustomAdapter() {
        return customAdapter;
    }

    @Override
    public String toString() {
        return "PreferenceEntryModel{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", preferenceTypeName='" + preferenceTypeName + '\'' +
                ", defaultValue=" + defaultValue +
                ", customAdapter='" + customAdapter + '\'' +
                '}';
    }
}
