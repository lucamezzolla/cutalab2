package com.cutalab.cutalab2.views.links;

import com.cutalab.cutalab2.backend.dto.AreaLinkDTO;
import com.cutalab.cutalab2.backend.dto.LinkDTO;
import com.cutalab.cutalab2.backend.entity.AreaLinkEntity;
import com.cutalab.cutalab2.backend.service.AreaLinkService;
import com.cutalab.cutalab2.backend.service.LinkService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;

@Route(value="links", layout = MainLayout.class)
@AnonymousAllowed
@CssImport(value = "/css/styles.css", themeFor = "vaadin-grid")
@PageTitle(Constants.MENU_LINKS + " | " + Constants.APP_AUTHOR)
public class LinksView extends VerticalLayout {

    private LinkService linkService;
    private AreaLinkService areaLinkService;

    public LinksView(LinkService linkService, AreaLinkService areaLinkService) {
        this.linkService = linkService;
        this.areaLinkService = areaLinkService;
        H2 title = new H2(Constants.MENU_LINKS);
        add(title);
        setHorizontalComponentAlignment(Alignment.CENTER, title);
        List<AreaLinkDTO> areas = areaLinkService.findAll();
        for (AreaLinkDTO a : areas) {
            AreaLinkEntity areaLinkEntity = areaLinkService.getEntityById(a.getId());
            List<LinkDTO> links = linkService.findAllByArea(areaLinkEntity);
            if (!links.isEmpty()) {
                H3 subtitle = new H3(a.getTitle());
                Grid<LinkDTO> grid = new Grid<>(LinkDTO.class, false);
                grid.setClassNameGenerator(item -> "vaadin-grid-links");
                grid.addColumn(LinkDTO::getTitle);
                grid.addItemClickListener(e -> {
                    LinkDTO linkDTO = e.getItem();
                    getUI().ifPresent(ui -> ui.getPage().open(linkDTO.getUrl()));
                });
                grid.setSelectionMode(Grid.SelectionMode.NONE);
                grid.setAllRowsVisible(true);
                grid.setWidth("100%");
                grid.setItems(links);
                add(subtitle, grid);
                setHorizontalComponentAlignment(Alignment.CENTER, subtitle);
            }
        }
    }


}