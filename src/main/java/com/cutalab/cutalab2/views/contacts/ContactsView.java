package com.cutalab.cutalab2.views.contacts;

import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value="contacts", layout = MainLayout.class)
@AnonymousAllowed
@PageTitle(Constants.MENU_CONTACTS + " | " + Constants.APP_AUTHOR)
public class ContactsView extends VerticalLayout {

    public ContactsView() {
        H2 title = new H2(Constants.MENU_CONTACTS);
        Image image1 = new Image();
        Image image2 = new Image();
        image1.addClassNames("contacts-image");
        image1.setSrc("images/luca.jpg");
        image2.setSrc("images/email.png");
        image1.addClassNames("contactsImages");
        image2.addClassNames("contactsImages");
        add(title, image1, image2);
        setHorizontalComponentAlignment(Alignment.CENTER, title, image1, image2);
    }
}
