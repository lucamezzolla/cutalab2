package com.cutalab.cutalab2.views;

import com.cutalab.cutalab2.backend.entity.dashboards.disks.DiskEntity;
import com.cutalab.cutalab2.backend.security.SecurityService;
import com.cutalab.cutalab2.backend.service.*;
import com.cutalab.cutalab2.views.admin.AdminView;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.contacts.ContactsView;
import com.cutalab.cutalab2.views.dashboards.DashboardView;
import com.cutalab.cutalab2.views.laboratory.LaboratoryView;
import com.cutalab.cutalab2.views.links.LinksView;
import com.cutalab.cutalab2.views.login.LoginView;
import com.cutalab.cutalab2.views.moments.MomentsView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.HasMenuItems;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

@CssImport(value = "/css/styles.css")
@CssImport(themeFor = "vaadin-menu-bar", value = "/css/styles.css")
public class MainLayout extends AppLayout {

    private StatusService statusService;
    private MomentService momentService;
    private LinkService linkService;
    private AreaLinkService areaLinkService;
    private SecurityService securityService;
    private DiskService diskService;
    private UserService userService;
    private boolean isLogged = false;

    public MainLayout(
            StatusService statusService,
            UserService userService,
            MomentService momentService,
            LinkService linkService,
            AreaLinkService areaLinkService,
            SecurityService securityService,
            DiskService diskService) {
        this.statusService = statusService;
        this.userService = userService;
        this.momentService = momentService;
        this.linkService = linkService;
        this.areaLinkService = areaLinkService;
        this.securityService = securityService;
        this.diskService = diskService;
        if(this.securityService.getAuthenticatedUser() != null) isLogged = true;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1(Constants.APP_TITLE);
        logo.addClassNames("text-l", "m-m", "logo");
        Button login = new Button("Login", e -> UI.getCurrent().navigate(LoginView.class));
        Button logout = new Button("Logout", e -> securityService.logout());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        if(isLogged) {
            header.add(logout);
        } else {
            header.add(login);
        }
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setMargin(false);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m, header-layout");
        addToNavbar(header);
    }

    private void createDrawer() {
        MenuBar menuBar = new MenuBar();
        menuBar.getElement().setAttribute("theme", "menu-vertical");
        menuBar.setWidth("100%");

        if(isLogged) {
            MenuItem adminItem = menuBar.addItem(Constants.MENU_ADMIN, menuItemClickEvent -> {
                this.setContent(new AdminView(linkService, areaLinkService));
            });
            MenuItem dashboardsItem = menuBar.addItem(Constants.MENU_DASHBOARDS, menuItemClickEvent -> {
                this.setContent(new DashboardView(statusService, userService, diskService));
            });
        }

        MenuItem laboratoryItem = menuBar.addItem(Constants.MENU_LABORATORY, menuItemClickEvent -> {
            this.setContent(new LaboratoryView());
        });
        MenuItem momentsItem = menuBar.addItem(Constants.MENU_MOMENTS, menuItemClickEvent -> {
            this.setContent(new MomentsView(momentService));
        });
        MenuItem contactsItem = menuBar.addItem(Constants.MENU_CONTACTS, menuItemClickEvent -> {
            this.setContent(new ContactsView());
        });
        MenuItem linksItem = menuBar.addItem(Constants.MENU_LINKS, menuItemClickEvent -> {
            this.setContent(new LinksView(linkService, areaLinkService));
        });
        menuBar.setOpenOnHover(false);
        addToDrawer(new VerticalLayout(menuBar));
    }


    private MenuItem addMenuElement(HasMenuItems sub, Class<? extends VerticalLayout> navigationTarget, String name) {
        MenuItem mi;
        if(navigationTarget != null) {
            RouterLink rl = new RouterLink(name, navigationTarget);
            //rl.addClassName("router-link");
            mi = sub.addItem(rl, null);
        }
        else {
            mi = sub.addItem(name, click -> { });

        }
        return mi;
    }


}
