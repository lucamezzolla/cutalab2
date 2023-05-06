package com.cutalab.cutalab2.utils;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;

public class ConfirmDialog extends Dialog {

    private ConfirmDialogInterface confirmDialogListener;

    public ConfirmDialog() {
        Label label = new Label(Constants.DISK_REMOVE_CONFIRMATION);
        add(label);
        getFooter().add(new Button(Constants.CANCEL, e -> close()));
        Button removeButton = new Button(Constants.REMOVE);
        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        removeButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> confirmDialogListener.confirmDialogListener());
        getFooter().add(removeButton);
    }

    public void setConfirmDialogListener(ConfirmDialogInterface confirmDialogListener) {
        this.confirmDialogListener = confirmDialogListener;
    }
}