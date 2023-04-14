package com.cutalab.cutalab2.views;

import com.cutalab.cutalab2.backend.service.SecurityService;
import com.cutalab.cutalab2.backend.service.*;
import com.cutalab.cutalab2.backend.service.admin.aba.ABAPackageService;
import com.cutalab.cutalab2.backend.service.admin.aba.ABASessionService;
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
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.RouterLink;

@Push
@CssImport(value = "/css/styles.css")
@CssImport(themeFor = "vaadin-menu-bar", value = "/css/styles.css")
public class MainLayout extends AppLayout implements AppShellConfigurator {

    private StatusService statusService;
    private MomentService momentService;
    private LinkService linkService;
    private AreaLinkService areaLinkService;
    private SecurityService securityService;
    private DiskService diskService;
    private UserService userService;
    private LaboratoryAreaService laboratoryAreaService;
    private LaboratoryService laboratoryService;
    private ABAPackageService abaPackageService;
    private ABASessionService abaSessionService;
    private boolean isLoggedUser = false;
    private boolean isLoggedAdmin = false;

    public MainLayout(
        StatusService statusService,
        UserService userService,
        MomentService momentService,
        LinkService linkService,
        AreaLinkService areaLinkService,
        SecurityService securityService,
        DiskService diskService,
        LaboratoryAreaService laboratoryAreaService,
        LaboratoryService laboratoryService,
        ABAPackageService abaPackageService,
        ABASessionService abaSessionService
    ) {
        this.statusService = statusService;
        this.userService = userService;
        this.momentService = momentService;
        this.linkService = linkService;
        this.areaLinkService = areaLinkService;
        this.securityService = securityService;
        this.diskService = diskService;
        this.laboratoryAreaService = laboratoryAreaService;
        this.laboratoryService = laboratoryService;
        this.abaPackageService = abaPackageService;
        this.abaSessionService = abaSessionService;
        this.isLoggedUser = this.securityService.isLogged();
        this.isLoggedAdmin = this.securityService.isAdmin();
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        //H1 logo = new H1(Constants.APP_TITLE);
        Image logo = new Image();
        logo.setSrc("images/title.png");
        logo.setHeight("80%");
        logo.addClassNames("logo");
        Button login = new Button("Login", e -> UI.getCurrent().navigate(LoginView.class));
        Button logout = new Button("Logout", e -> securityService.logout()); logout.setWidth("110px");
        HorizontalLayout hLogo = new HorizontalLayout(logo); hLogo.setMargin(false); hLogo.setPadding(false); hLogo.setHeight("95%"); hLogo.setWidth("100%");
        hLogo.setAlignSelf(FlexComponent.Alignment.CENTER, logo);
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), hLogo);
        if(isLoggedUser) {
            header.add(logout);
        } else {
            header.add(login);
        }
        header.addClassName("horizontalLayoutHeader");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setFlexGrow(1, hLogo);
        header.setMargin(false);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m, header-layout");
        addToNavbar(header);
    }

    private void createDrawer() {
        MenuBar menuBar = new MenuBar();
        menuBar.getElement().setAttribute("theme", "menu-vertical");
        menuBar.setWidth("100%");

        if (isLoggedAdmin) {
            MenuItem adminItem = menuBar.addItem(Constants.MENU_ADMIN, menuItemClickEvent -> {
                this.setContent(new AdminView(laboratoryAreaService, laboratoryService, linkService, areaLinkService, abaPackageService, abaSessionService));
            });
        }
        if(isLoggedUser) {
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
