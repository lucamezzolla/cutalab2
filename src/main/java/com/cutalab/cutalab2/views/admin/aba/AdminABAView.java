package com.cutalab.cutalab2.views.admin.aba;

import com.cutalab.cutalab2.backend.dto.admin.aba.ABAPackageDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskDTO;
import com.cutalab.cutalab2.backend.entity.admin.aba.PeriodEnum;
import com.cutalab.cutalab2.backend.service.admin.aba.ABAPackageService;
import com.cutalab.cutalab2.backend.service.admin.aba.ABASessionService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.annotation.security.RolesAllowed;
import java.text.SimpleDateFormat;
import java.util.List;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin-aba", layout = MainLayout.class)
//@CssImport(value = "/css/styles.css")
@PageTitle(Constants.ABA_TITLE + " | " + Constants.APP_AUTHOR)
public class AdminABAView extends VerticalLayout implements ComponentEventListener<ItemClickEvent<ABAPackageDTO>>  {

    private ABAPackageService abaPackageService;
    private ABASessionService abaSessionService;
    private Grid<ABAPackageDTO> grid;
    private Button addPackageButton;

    public AdminABAView(ABAPackageService abaPackageService, ABASessionService abaSessionService) {
        this.abaPackageService = abaPackageService;
        this.abaSessionService = abaSessionService;
        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidth("100%");
        H2 title = new H2(Constants.ABA_TITLE);
        addPackageButton = new Button(new Icon(VaadinIcon.PLUS));
        addPackageButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        addPackageButton.addClickListener(e -> { addPackageAction(); });
        grid = new Grid<>(ABAPackageDTO.class, false);
        grid.addItemClickListener(this);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setSizeFull();
        grid.addColumn(createCol1()).setHeader(Constants.ABA_GRID_COL_1).setFlexGrow(1);;
        grid.addColumn(ABAPackageDTO::getHours).setHeader(Constants.ABA_GRID_COL_2).setFlexGrow(1);;
        grid.addColumn(createCol3()).setHeader(Constants.ABA_GRID_COL_3).setFlexGrow(1);;
        grid.addColumn(new ComponentRenderer<>(abaPackageDTO -> {
            Html html;
            if (abaPackageDTO.getOpen()) {
                html = new Html("<span style='background-color: #FFFDC0; width: 100%'>IN CORSO</span>");
            } else {
                html = new Html("<span style='background-color: #CEFFC0; width: 100%'>COMPLETATO</span>");
            }
            return html;
        })).setHeader(Constants.ABA_GRID_COL_4).setFlexGrow(1);
        hl.add(title, addPackageButton);
        hl.setFlexGrow(1, title);
        hl.setAlignSelf(Alignment.END, addPackageButton);
        add(hl, grid);
        setFlexGrow(1, grid);
        setSizeFull();
        fillGrid();
    }

    private void addPackageAction() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle(Constants.ABA_INSERT_PACKAGE);
        dialog.setWidth("50%");
        dialog.setMaxHeight("80%");
        Button paymentButton = new Button(Constants.ABA_CHOOSE_PAYMENT_PACKAGE);
        IntegerField hoursIntegerField = new IntegerField();
        hoursIntegerField.setLabel(Constants.ABA_HOURS_PACKAGE);
        hoursIntegerField.setStep(1);
        hoursIntegerField.setStepButtonsVisible(true);
        hoursIntegerField.setMin(0);
        hoursIntegerField.setMax(5000);
        hoursIntegerField.setValue(0);
        ComboBox<PeriodEnum> periodEnumComboBox = new ComboBox<>();
        periodEnumComboBox.setLabel(Constants.ABA_PERIOD_PACKAGE);
        paymentButton.setWidth("100%");
        hoursIntegerField.setWidth("100%");
        periodEnumComboBox.setWidth("100%");
        HorizontalLayout hl = new HorizontalLayout(hoursIntegerField, periodEnumComboBox);
        hl.setWidth("100%");
        dialog.add(hl, paymentButton);
        //FOOTER
        Button addButton = new Button(Constants.SAVE);
        Button cancelButton = new Button(Constants.CANCEL, e -> { dialog.close(); });
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialog.getFooter().add(cancelButton, addButton);
        //FILL
        fillPeriodEnumComboBox(periodEnumComboBox);
        //show
        dialog.open();
    }


    private void fillGrid() {
        List<ABAPackageDTO> packages = abaPackageService.getAll();
        grid.setItems(packages);
    }

    private void fillPeriodEnumComboBox(ComboBox<PeriodEnum> periodEnumComboBox) {
        periodEnumComboBox.setItems(PeriodEnum.ZERO.getList());
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