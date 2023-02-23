package com.cutalab.cutalab2.views.admin.laboratory;

import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value="admin-laboratory", layout = MainLayout.class)
@PageTitle(Constants.MENU_ADMIN + " | " + Constants.APP_AUTHOR)
public class AdminLaboratoryView extends VerticalLayout {

    public AdminLaboratoryView() {
        H2 title = new H2(Constants.MENU_LABORATORY);
        add(title);
    }
}