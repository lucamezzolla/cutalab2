package com.cutalab.cutalab2.views.dashboards.magicabula;

import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value="dashboards-magicabula", layout = MainLayout.class)
@PageTitle(Constants.MENU_DASHBOARDS_MAGICABULA + " | " + Constants.APP_AUTHOR)
public class MagicabulaView extends VerticalLayout {

    public MagicabulaView() {
        H2 title = new H2(Constants.MENU_DASHBOARDS_MAGICABULA);
        Image logo = new Image();
        logo.setSrc("images/dog.png");
        logo.setWidth("13%");
        add(title, logo);
        setHorizontalComponentAlignment(Alignment.CENTER, title, logo);
    }

}