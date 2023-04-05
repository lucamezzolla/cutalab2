package com.cutalab.cutalab2.views.admin.laboratory;

import com.cutalab.cutalab2.backend.dto.LaboratoryAreaDTO;
import com.cutalab.cutalab2.backend.entity.LaboratoryAreaEntity;
import com.cutalab.cutalab2.backend.service.LaboratoryAreaService;
import com.cutalab.cutalab2.utils.ConfirmDialog;
import com.cutalab.cutalab2.utils.ConfirmDialogInterface;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin-area-laboratory", layout = MainLayout.class)
@PageTitle(Constants.MENU_ADMIN + " | " + Constants.APP_AUTHOR)
public class AdminAreaLaboratoryView extends VerticalLayout implements ComponentEventListener<ItemClickEvent<LaboratoryAreaDTO>> {

    private LaboratoryAreaService laboratoryAreaService;
    private TextField titleTextField;
    private Button addTitleButton;
    private Grid<LaboratoryAreaDTO> grid;
    private Dialog dialog;
    private VerticalLayout dialogLayout;

    public AdminAreaLaboratoryView(LaboratoryAreaService laboratoryAreaService) {
        this.laboratoryAreaService = laboratoryAreaService;
        H2 title = new H2(Constants.MENU_AREA_LABORATORY);
        titleTextField = new TextField();
        addTitleButton = new Button(new Icon(VaadinIcon.PLUS), this::onAddTitleButton);
        titleTextField.setPlaceholder("Area dei laboratori...");
        grid = new Grid<>(LaboratoryAreaDTO.class, false);
        grid.addItemClickListener(this);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setWidth("100%");
        grid.addColumn(LaboratoryAreaDTO::getName).setHeader(Constants.AREA_LABORATORY_GRID_HEADER_TITLE);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        dialog = new Dialog();
        dialog.setWidth("50%");
        dialog.setHeaderTitle(Constants.EDIT);
        dialogLayout = new VerticalLayout();
        dialogLayout.setMargin(false);
        dialogLayout.setPadding(false);
        dialog.add(dialogLayout);
        HorizontalLayout headerLayout = new HorizontalLayout(titleTextField, addTitleButton);
        headerLayout.setFlexGrow(1, titleTextField);
        headerLayout.setWidth("100%");
        add(title, headerLayout, grid, dialog);
        setFlexGrow(1, grid);
        setSizeFull();
        fillGrid();
    }

    private void reset() {
        titleTextField.setValue("");
    }

    private void addAreaLaboratory() {
        LaboratoryAreaEntity laboratoryAreaEntity = new LaboratoryAreaEntity();
        if(!titleTextField.getValue().isEmpty()) {
            laboratoryAreaEntity.setName(titleTextField.getValue());
            try {
                laboratoryAreaService.create(laboratoryAreaEntity);
                Constants.NOTIFICATION_DB_SUCCESS();
                reset();
                fillGrid();
            } catch (Exception e) {
                Constants.NOTIFICATION_DB_ERROR(e);
            }
        } else {
            Constants.NOTIFICATION_DB_VALIDATION_ERROR();
        }
    }

    private void fillGrid() {
        grid.setItems(laboratoryAreaService.findAll());
    }

    public void onAddTitleButton(ClickEvent<Button> buttonClickEvent) {
        Button eventButton = (Button) buttonClickEvent.getSource();
        if(eventButton.equals(addTitleButton)) {
            addAreaLaboratory();
        }
    }

    @Override
    public void onComponentEvent(ItemClickEvent<LaboratoryAreaDTO> laboratoryAreaDTOItemClickEvent) {
        LaboratoryAreaDTO laboratoryAreaDTO = laboratoryAreaDTOItemClickEvent.getItem();
        openDialog(laboratoryAreaDTO);
    }

    private void openDialog(LaboratoryAreaDTO laboratoryAreaDTO) {
        dialogLayout.removeAll();
        dialog.getFooter().removeAll();
        TextField editTextField = new TextField();
        editTextField.setWidth("100%");
        editTextField.setValue(laboratoryAreaDTO.getName());
        Button saveButton = new Button(Constants.SAVE, e -> {
            laboratoryAreaDTO.setName(editTextField.getValue());
            try {
                laboratoryAreaService.update(laboratoryAreaDTO);
                Constants.NOTIFICATION_DB_SUCCESS();
                fillGrid();
            } catch(Exception e1) {
                Constants.NOTIFICATION_DB_ERROR(e1);
            }
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button(Constants.CANCEL, e -> dialog.close());
        Button removeButton = new Button(new Icon(VaadinIcon.TRASH), e -> {
            ConfirmDialog confirmDialog = new ConfirmDialog();
            confirmDialog.setConfirmDialogListener(new ConfirmDialogInterface() {
                @Override
                public void confirmDialogListener() {
                    try {
                        laboratoryAreaService.remove(laboratoryAreaDTO);
                        Constants.NOTIFICATION_DB_SUCCESS();
                        fillGrid();
                    } catch(Exception e2) {
                        Constants.NOTIFICATION_DB_ERROR(e2);
                    }
                    confirmDialog.close();
                    dialog.close();
                }
            });
            confirmDialog.open();
        });
        removeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        HorizontalLayout hl = new HorizontalLayout(editTextField);
        dialogLayout.add(editTextField);
        HorizontalLayout hlFooter1 = new HorizontalLayout(removeButton);
        HorizontalLayout hlFooter2 = new HorizontalLayout(cancelButton, saveButton);
        HorizontalLayout hlFooter = new HorizontalLayout(hlFooter1, hlFooter2);
        hlFooter.setWidth("100%"); hlFooter1.setWidth("100%");
        hlFooter.setFlexGrow(1, hlFooter1);
        hlFooter.setMargin(false);
        dialog.getFooter().add(hlFooter);
        dialog.open();
    }

}