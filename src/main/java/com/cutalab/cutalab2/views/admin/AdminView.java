package com.cutalab.cutalab2.views.admin;

import com.cutalab.cutalab2.backend.service.AreaLinkService;
import com.cutalab.cutalab2.backend.service.LaboratoryAreaService;
import com.cutalab.cutalab2.backend.service.LaboratoryService;
import com.cutalab.cutalab2.backend.service.LinkService;
import com.cutalab.cutalab2.backend.service.admin.aba.ABAPackageService;
import com.cutalab.cutalab2.backend.service.admin.aba.ABASessionService;
import com.cutalab.cutalab2.backend.service.admin.payments.PaymentService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.cutalab.cutalab2.views.admin.aba.AdminABAView;
import com.cutalab.cutalab2.views.admin.laboratory.AdminLaboratoryView;
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

    public AdminView(LaboratoryAreaService laboratoryAreaService, LaboratoryService laboratoryService, LinkService linkService, AreaLinkService areaLinkService, ABAPackageService abaPackageService, ABASessionService abaSessionService, PaymentService paymentService) {
        PaymentService paymentService1 = new PaymentService();
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add(Constants.ABA_TITLE, new AdminABAView(abaPackageService, abaSessionService, paymentService));
        tabSheet.add(Constants.MENU_LABORATORY, new AdminLaboratoryView(laboratoryService, laboratoryAreaService));
        tabSheet.add(Constants.MENU_LINKS, new AdminLinksView(areaLinkService, linkService));
        add(tabSheet);
        setSizeFull();
    }
}
