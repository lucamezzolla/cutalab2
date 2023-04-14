package com.cutalab.cutalab2.views.admin.aba;

import com.cutalab.cutalab2.backend.dto.admin.aba.ABAPackageDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskDTO;
import com.cutalab.cutalab2.backend.service.admin.aba.ABAPackageService;
import com.cutalab.cutalab2.backend.service.admin.aba.ABASessionService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.text.SimpleDateFormat;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin-aba", layout = MainLayout.class)
//@CssImport(value = "/css/styles.css")
@PageTitle(Constants.ABA_TITLE + " | " + Constants.APP_AUTHOR)
public class AdminABAView extends VerticalLayout implements ComponentEventListener<ItemClickEvent<ABAPackageDTO>>  {

    private ABAPackageService abaPackageService;
    private ABASessionService abaSessionService;
    private Grid<ABAPackageDTO> grid;

    public AdminABAView(ABAPackageService abaPackageService, ABASessionService abaSessionService) {
        this.abaPackageService = abaPackageService;
        this.abaSessionService = abaSessionService;
        H2 title = new H2(Constants.ABA_TITLE);
        grid = new Grid<>(ABAPackageDTO.class, false);
        grid.addItemClickListener(this);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setWidth("100%");
        grid.addColumn(createCol1()).setHeader(Constants.ABA_GRID_COL_1);
        grid.addColumn(ABAPackageDTO::getHours).setHeader(Constants.ABA_GRID_COL_2);
        grid.addColumn(createCol3()).setHeader(Constants.ABA_GRID_COL_3);
        grid.addColumn(new ComponentRenderer<>(abaPackageDTO -> {
            Html html;
            if (abaPackageDTO.getOpen()) {
                html = new Html("<span style='background-color: #FFFDC0'>IN CORSO</span>");
            } else {
                html = new Html("<span style='background-color: #CEFFC0'>COMPLETATO</span>");
            }
            return html;
        })).setHeader(Constants.ABA_GRID_COL_4);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        add(title, grid);
        fillGrid();
    }

    private void fillGrid() {
        grid.setItems(abaPackageService.getAll());
    }

    private Renderer<ABAPackageDTO> createCol1() {
        return LitRenderer.<ABAPackageDTO> of("${item.paymentFormattedDate}")
            .withProperty("paymentFormattedDate", ABAPackageDTO::getPaymentFormattedDate);
    }

    private Renderer<ABAPackageDTO> createCol3() {
        return LitRenderer.<ABAPackageDTO> of("${item.periodFormatted}")
            .withProperty("periodFormatted", ABAPackageDTO::getPeriodFormatted);
    }

    @Override
    public void onComponentEvent(ItemClickEvent<ABAPackageDTO> abaPackageDTOItemClickEvent) {

    }

}