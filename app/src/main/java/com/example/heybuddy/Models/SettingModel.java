package com.example.heybuddy.Models;

public class SettingModel {
    private String icon;
    private String settingName;
    private String settingDescription;

    public SettingModel(String icon, String settingName, String settingDescription) {
        this.icon = icon;
        this.settingName = settingName;
        this.settingDescription = settingDescription;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getSettingDescription() {
        return settingDescription;
    }

    public void setSettingDescription(String settingDescription) {
        this.settingDescription = settingDescription;
    }

}
