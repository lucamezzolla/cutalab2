package com.cutalab.cutalab2.views.mycomponents;

import com.vaadin.flow.component.textfield.TextField;

public class MyTextField extends TextField {

    public MyTextField(String label, String placeholder, String id, boolean focus) {
        super(label, placeholder);
        this.setId(id);
        this.setWidth("100%");
        this.setAutofocus(focus);
    }

}