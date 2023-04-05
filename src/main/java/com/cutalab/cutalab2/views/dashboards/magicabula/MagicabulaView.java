package com.cutalab.cutalab2.views.dashboards.magicabula;

import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
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
        logo.addClassNames("dashboardLogo");
        Label label = new Label("Pagina in costruzione...");
        logo.setSrc("images/dog.png");
        add(title, logo, label);
        setHorizontalComponentAlignment(Alignment.CENTER, title, logo, label);
    }

}