package com.cutalab.cutalab2.views.admin.aba;

import com.cutalab.cutalab2.backend.dto.admin.aba.ABAPackageDTO;
import com.cutalab.cutalab2.backend.dto.admin.payments.PPaymentDTO;
import com.cutalab.cutalab2.backend.entity.admin.aba.PeriodEnum;
import com.cutalab.cutalab2.backend.service.admin.aba.ABAPackageService;
import com.cutalab.cutalab2.backend.service.admin.aba.ABASessionService;
import com.cutalab.cutalab2.backend.service.admin.payments.PaymentService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.H2;
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

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin-aba", layout = MainLayout.class)
//@CssImport(value = "/css/styles.css")
@PageTitle(Constants.ABA_TITLE + " | " + Constants.APP_AUTHOR)
public class AdminABAView extends VerticalLayout implements ComponentEventListener<ItemClickEvent<ABAPackageDTO>>, ClosePackageInterface  {

    private final ABAPackageService abaPackageService;
    private final ABASessionService abaSessionService;
    private final PaymentService paymentService;
    private final Grid<ABAPackageDTO> grid;
    private ComboBox<PPaymentDTO> paymentsCombo;
    private IntegerField hoursIntegerField;
    private ComboBox<PeriodEnum> periodEnumComboBox;
    private Checkbox isOpen;
    private Dialog addPackageDialog;

    public AdminABAView(ABAPackageService abaPackageService, ABASessionService abaSessionService, PaymentService paymentService) {
        this.abaPackageService = abaPackageService;
        this.abaSessionService = abaSessionService;
        this.paymentService = paymentService;
        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidth("100%");
        H2 title = new H2(Constants.ABA_TITLE);
        Button addPackageButton = new Button(new Icon(VaadinIcon.PLUS));
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
        addPackageDialog = new Dialog();
        addPackageDialog.setHeaderTitle(Constants.ABA_INSERT_PACKAGE);
        addPackageDialog.setWidth("50%");
        addPackageDialog.setMaxHeight("80%");
        isOpen = new Checkbox(Constants.ABA_IS_OPEN_PACKAGE);
        isOpen.setValue(true);
        paymentsCombo = new ComboBox<>();
        paymentsCombo.setLabel(Constants.ABA_CHOOSE_PAYMENT_PACKAGE);
        paymentsCombo.setItemLabelGenerator(PPaymentDTO::getDescriptionAndDate);
        hoursIntegerField = new IntegerField();
        hoursIntegerField.setLabel(Constants.ABA_HOURS_PACKAGE);
        hoursIntegerField.setStep(1);
        hoursIntegerField.setStepButtonsVisible(true);
        hoursIntegerField.setMin(0);
        hoursIntegerField.setMax(5000);
        hoursIntegerField.setValue(0);
        periodEnumComboBox = new ComboBox<>();
        periodEnumComboBox.setLabel(Constants.ABA_PERIOD_PACKAGE);
        isOpen.setWidth("100%");
        isOpen.getStyle().set("margin-top", "30px");
        paymentsCombo.setWidth("100%");
        hoursIntegerField.setWidth("100%");
        periodEnumComboBox.setWidth("100%");
        VerticalLayout vl = new VerticalLayout(hoursIntegerField, periodEnumComboBox, paymentsCombo, isOpen);
        vl.setWidth("100%");
        addPackageDialog.add(vl);
        //FOOTER
        Button addButton = new Button(Constants.SAVE, this::onAddPackage);
        Button cancelButton = new Button(Constants.CANCEL, e -> { addPackageDialog.close(); });
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addPackageDialog.getFooter().add(cancelButton, addButton);
        //FILL
        fillPeriodEnumComboBox(periodEnumComboBox);
        fillPaymentCombo(paymentsCombo);
        //show
        addPackageDialog.open();
    }

    private void onAddPackage(ClickEvent<Button> buttonClickEvent) {
        Integer hours = hoursIntegerField.getValue();
        PeriodEnum period = periodEnumComboBox.getValue();
        PPaymentDTO payment = paymentsCombo.getValue();
        Boolean isOpenPackage = isOpen.getValue();
        if(hours == null || period == null || payment == null) {
            Constants.NOTIFICATION_DB_VALIDATION_ERROR();
        } else {
            ABAPackageDTO abaPackageDTO = new ABAPackageDTO();
            abaPackageDTO.setHours(hours);
            abaPackageDTO.setPayment(payment);
            abaPackageDTO.setPeriod(period);
            abaPackageDTO.setOpen(isOpenPackage);
            try {
                abaPackageService.insert(abaPackageDTO);
                addPackageDialog.close();
                fillGrid();
            } catch (Exception e) {
                Constants.NOTIFICATION_DB_ERROR(e);
            }
        }
    }

    private void fillGrid() {
        List<ABAPackageDTO> packages = abaPackageService.getAll();
        grid.setItems(packages);
    }

    private void fillPeriodEnumComboBox(ComboBox<PeriodEnum> periodEnumComboBox) {
        periodEnumComboBox.setItems(PeriodEnum.ZERO.getList());
    }

    private void fillPaymentCombo(ComboBox<PPaymentDTO> paymentDTOComboBox) {
        List<PPaymentDTO> payments = paymentService.getAllProgressiPayments();
        paymentDTOComboBox.setItems(payments);
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
        ABAPackageDTO abaPackageDTO = abaPackageDTOItemClickEvent.getItem();
        ABASessionsView abaSessionsView = new ABASessionsView(abaPackageDTO, abaPackageService, abaSessionService);
        abaSessionsView.setHeaderTitle(Constants.ABA_PERIOD_PACKAGE_SESSIONS);
        abaSessionsView.setWidth("65%");
        abaSessionsView.setMaxHeight("65%");
        abaSessionsView.setListener(this);
        Button closeButton = new Button(new Icon("lumo", "cross"),
                (e) -> abaSessionsView.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        abaSessionsView.getHeader().add(closeButton);
        abaSessionsView.open();
    }

    @Override
    public void closePackageListener() {
        fillGrid();
    }
}