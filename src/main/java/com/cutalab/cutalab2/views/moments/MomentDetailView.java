package com.cutalab.cutalab2.views.moments;

import com.cutalab.cutalab2.backend.dto.MomentDTO;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value="moments-detail", layout = MainLayout.class)
@AnonymousAllowed
@CssImport(value = "/css/styles.css", themeFor = "album")
@PageTitle(Constants.MENU_MOMENTS + " | " + Constants.APP_AUTHOR)

public class MomentDetailView extends VerticalLayout {

    public MomentDetailView(MomentDTO momentDTO) {
        H2 title = new H2(momentDTO.getTitle());
        H3 subtitle = new H3(momentDTO.getSubtitle());
        Div body = new Div();
        body.add(momentDTO.getBody());
        body.setWidth("100%");
        body.addClassName("centered-text");
        IFrame album = new IFrame();
        album.setSrc("https://drive.google.com/embeddedfolderview?id="+momentDTO.getGoogledriveid()+"#grid");
        album.setSizeFull();
        album.addClassNames("album-photos");
        add(title, subtitle, body, album);
        setHorizontalComponentAlignment(Alignment.CENTER, title, subtitle, body);
        setSizeFull();
        /*
        <iframe th:src="'https://drive.google.com/embeddedfolderview?id='+${googledriveid}+'#grid'" style=" width: 100%; height: 100vh; border:none; margin:0; padding:0;z-index:999999;">
        </iframe>
        */

    }

}
