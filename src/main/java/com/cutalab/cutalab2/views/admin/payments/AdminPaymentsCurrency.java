package com.cutalab.cutalab2.views.admin.payments;

import com.cutalab.cutalab2.backend.dto.admin.payments.PCurrencyDTO;
import com.cutalab.cutalab2.backend.service.admin.payments.PaymentService;
import com.cutalab.cutalab2.utils.ConfirmDialog;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.cutalab.cutalab2.views.mycomponents.MyTextField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.util.LinkedList;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin-payments-currencies", layout = MainLayout.class)
//@CssImport(value = "/css/styles.css")
@PageTitle(Constants.PAYMENTS + " | " + Constants.APP_AUTHOR)
public class AdminPaymentsCurrency extends VerticalLayout implements ComponentEventListener<ItemClickEvent<PCurrencyDTO>> {

    private final PaymentService paymentService;
    private final Grid<PCurrencyDTO> grid;

    public AdminPaymentsCurrency(PaymentService paymentService) {
        this.paymentService = paymentService;
        Button addCurrencyButton = new Button(new Icon(VaadinIcon.PLUS));
        addCurrencyButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        addCurrencyButton.addClickListener(this::addCurrencyAction);
        HorizontalLayout hl = new HorizontalLayout();
        hl.setWidth("100%");
        H2 title = new H2(Constants.PAYMENTS_CURRENCIES);
        grid = new Grid<>(PCurrencyDTO.class, false);
        grid.addItemClickListener(this);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setSizeFull();
        grid.addColumn(PCurrencyDTO::getName).setHeader(Constants.PAYMENTS_CURRENCY_COLUMN_1).setFlexGrow(1);
        hl.add(title, addCurrencyButton);
        hl.setFlexGrow(1, title);
        hl.setAlignSelf(Alignment.END, addCurrencyButton);
        add(hl, grid);
        setFlexGrow(1, grid);
        setSizeFull();
        fillGrid();
    }

    private void fillGrid() {
        grid.setItems(paymentService.getAllCurrencies());
    }

    private void addCurrencyAction(ClickEvent<Button> buttonClickEvent) {
        Dialog dialog = new Dialog();
        dialog.setWidth("60%");
        dialog.setMaxHeight("70%");
        dialog.setCloseOnEsc(true);
        dialog.setHeaderTitle(Constants.PAYMENTS_NEW_CURRENCY);
        PCurrencyDTO pCurrencyDTO = new PCurrencyDTO();
        LinkedList<MyTextField> fields1 = pCurrencyDTO.getFields();
        VerticalLayout vl = new VerticalLayout();
        vl.setWidth("100%");
        vl.setMargin(false);
        vl.setPadding(false);
        for(MyTextField myTextField : fields1) {
            vl.add(myTextField);
        }
        dialog.add(vl);
        Button saveButton = new Button(Constants.SAVE);
        saveButton.addClickListener(e -> {
            LinkedList<MyTextField> fields2 = new LinkedList<>();
            for(int i = 0; i < vl.getComponentCount(); i++) {
                if(vl.getComponentAt(i) instanceof MyTextField) {
                    MyTextField myTextField = (MyTextField) vl.getComponentAt(i);
                    fields2.add(myTextField);
                }
            }
            pCurrencyDTO.setFields(fields2);
            try {
                paymentService.createCurrency(pCurrencyDTO);
                dialog.close();
                fillGrid();
            } catch (Exception ex) {
                Constants.NOTIFICATION_DB_VALIDATION_ERROR();
            }
        });
        dialog.getFooter().add(saveButton);
        dialog.open();
    }

    @Override
    public void onComponentEvent(ItemClickEvent<PCurrencyDTO> pCurrencyDTOItemClickEvent) {
        PCurrencyDTO pCurrencyDTO = pCurrencyDTOItemClickEvent.getItem();
        Dialog dialog = new Dialog();
        dialog.setWidth("60%");
        dialog.setMaxHeight("70%");
        dialog.setCloseOnEsc(true);
        dialog.setHeaderTitle(Constants.PAYMENTS_EDIT_CURRENCY);
        LinkedList<MyTextField> fields1 = pCurrencyDTO.getFields();
        VerticalLayout vl = new VerticalLayout();
        vl.setWidth("100%");
        vl.setMargin(false);
        vl.setPadding(false);
        for(MyTextField myTextField : fields1) {
            vl.add(myTextField);
        }
        dialog.add(vl);
        Button saveButton = new Button(Constants.SAVE);
        Button removeButton = new Button(Constants.REMOVE, e -> {
            ConfirmDialog confirmDialog = new ConfirmDialog();
            confirmDialog.setLabel(Constants.PAYMENTS_REMOVE_CURRENCY);
            confirmDialog.setConfirmDialogListener(() -> {
                try {
                    paymentService.removeCurrency(pCurrencyDTO);
                    Constants.NOTIFICATION_DB_SUCCESS();
                    fillGrid();
                } catch(Exception e2) {
                    Constants.NOTIFICATION_DB_ERROR(e2);
                }
                confirmDialog.close();
                dialog.close();
            });
            confirmDialog.open();
        });
        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        saveButton.addClickListener(e -> {
            LinkedList<MyTextField> fields2 = new LinkedList<>();
            for(int i = 0; i < vl.getComponentCount(); i++) {
                if(vl.getComponentAt(i) instanceof MyTextField) {
                    MyTextField myTextField = (MyTextField) vl.getComponentAt(i);
                    fields2.add(myTextField);
                }
            }
            pCurrencyDTO.setFields(fields2);
            try {
                paymentService.updateCurrency(pCurrencyDTO);
                dialog.close();
                fillGrid();
            } catch (Exception ex) {
                Constants.NOTIFICATION_DB_VALIDATION_ERROR();
            }
        });
        dialog.getFooter().add(removeButton, saveButton);
        dialog.open();
    }
}

