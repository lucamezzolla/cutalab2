package com.cutalab.cutalab2.views.admin;

import com.cutalab.cutalab2.backend.service.AreaLinkService;
import com.cutalab.cutalab2.backend.service.LaboratoryAreaService;
import com.cutalab.cutalab2.backend.service.LaboratoryService;
import com.cutalab.cutalab2.backend.service.LinkService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.cutalab.cutalab2.views.admin.laboratory.AdminAreaLaboratoryView;
import com.cutalab.cutalab2.views.admin.laboratory.AdminLaboratoryView;
import com.cutalab.cutalab2.views.admin.links.AdminAreaLinkView;
import com.cutalab.cutalab2.views.admin.links.AdminLinksView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin", layout = MainLayout.class)
@PageTitle(Constants.MENU_ADMIN + " | " + Constants.APP_AUTHOR)
public class AdminView extends VerticalLayout {

    private LaboratoryAreaService laboratoryAreaService;
    private LaboratoryService laboratoryService;
    private LinkService linkService;
    private AreaLinkService areaLinkService;

    public AdminView(LaboratoryAreaService laboratoryAreaService, LaboratoryService laboratoryService, LinkService linkService, AreaLinkService areaLinkService) {
        this.laboratoryAreaService = laboratoryAreaService;
        this.laboratoryService = laboratoryService;
        this.linkService = linkService;
        this.areaLinkService = areaLinkService;
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add(Constants.MENU_AREA_LABORATORY, new AdminAreaLaboratoryView(laboratoryAreaService));
        tabSheet.add(Constants.MENU_LABORATORY, new AdminLaboratoryView(laboratoryService, laboratoryAreaService));
        tabSheet.add(Constants.MENU_AREA_LINK, new AdminAreaLinkView(areaLinkService));
        tabSheet.add(Constants.MENU_LINKS, new AdminLinksView(areaLinkService, linkService));
        add(tabSheet);
        setSizeFull();
    }
}
