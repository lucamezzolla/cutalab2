package com.cutalab.cutalab2.views.dashboards;

import com.cutalab.cutalab2.backend.service.DiskService;
import com.cutalab.cutalab2.backend.service.StatusService;
import com.cutalab.cutalab2.backend.service.UserService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.cutalab.cutalab2.views.dashboards.disks.DiskView;
import com.cutalab.cutalab2.views.dashboards.magicabula.MagicabulaView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value="dashboards", layout = MainLayout.class)
@PageTitle(Constants.MENU_DASHBOARDS + " | " + Constants.APP_AUTHOR)
public class DashboardView extends VerticalLayout {

    public DashboardView(StatusService statusService, UserService userService, DiskService diskService) {
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add(Constants.MENU_DASHBOARDS_DISKS, new DiskView(statusService, userService, diskService));
        tabSheet.add(Constants.MENU_DASHBOARDS_MAGICABULA, new MagicabulaView());
        add(tabSheet);
        setSizeFull();
    }

}