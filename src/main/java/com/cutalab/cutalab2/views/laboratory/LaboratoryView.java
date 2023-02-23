package com.cutalab.cutalab2.views.laboratory;

import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value="", layout = MainLayout.class)
@AnonymousAllowed
@PageTitle(Constants.MENU_LABORATORY + " | " + Constants.APP_AUTHOR)
public class LaboratoryView extends VerticalLayout {

    public LaboratoryView() {
        H2 title = new H2(Constants.MENU_LABORATORY);
        Div body = new Div();
        body.add("Pagina in allestimento");
        body.setWidth("100%");
        body.addClassName("centered-text");
        add(title, body);
        setHorizontalComponentAlignment(Alignment.CENTER, title);
    }

}