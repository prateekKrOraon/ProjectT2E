package com.think2exam.projectt2e.modals;

public class EditProfileModel {

    int itemIcon,itemName;
    String itemField;

    public int getItemIcon() {
        return itemIcon;
    }

    public int getItemName() {
        return itemName;
    }

    public String getItemField() {
        return itemField;
    }

    public EditProfileModel(int itemIcon, int itemName, String itemField) {
        this.itemIcon = itemIcon;
        this.itemName = itemName;
        this.itemField = itemField;
    }
}
