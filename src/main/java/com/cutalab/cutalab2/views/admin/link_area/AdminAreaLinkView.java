package com.cutalab.cutalab2.views.admin.link_area;

import com.cutalab.cutalab2.backend.dto.AreaLinkDTO;
import com.cutalab.cutalab2.backend.entity.AreaLinkEntity;
import com.cutalab.cutalab2.backend.service.AreaLinkService;
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

import javax.annotation.security.PermitAll;


@PermitAll
@Route(value="admin-area-link", layout = MainLayout.class)
@PageTitle(Constants.MENU_ADMIN + " | " + Constants.APP_AUTHOR)
public class AdminAreaLinkView extends VerticalLayout implements ComponentEventListener<ItemClickEvent<AreaLinkDTO>> {

    private AreaLinkService areaLinkService;
    private TextField titleTextField;
    private Button addTitleButton;
    private Grid<AreaLinkDTO> grid;
    private Dialog dialog;
    private VerticalLayout dialogLayout;

    public AdminAreaLinkView(AreaLinkService areaLinkService) {
        this.areaLinkService = areaLinkService;
        H2 title = new H2(Constants.MENU_AREA_LINK);
        titleTextField = new TextField();
        addTitleButton = new Button(new Icon(VaadinIcon.PLUS), this::onAddTitleButton);
        titleTextField.setPlaceholder("Area dei links...");
        grid = new Grid<>(AreaLinkDTO.class, false);
        grid.addItemClickListener(this);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setWidth("100%");
        grid.addColumn(AreaLinkDTO::getTitle).setHeader(Constants.AREA_LINK_GRID_HEADER_TITLE);
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

    private void addAreaLink() {
        AreaLinkEntity areaLinkEntity = new AreaLinkEntity();
        if(!titleTextField.getValue().isEmpty()) {
            areaLinkEntity.setTitle(titleTextField.getValue());
            try {
                areaLinkService.create(areaLinkEntity);
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
        grid.setItems(areaLinkService.findAll());
    }

    public void onAddTitleButton(ClickEvent<Button> buttonClickEvent) {
        Button eventButton = (Button) buttonClickEvent.getSource();
        if(eventButton.equals(addTitleButton)) {
            addAreaLink();
        }
    }

    @Override
    public void onComponentEvent(ItemClickEvent<AreaLinkDTO> areaLinkDTOItemClickEvent) {
        AreaLinkDTO areaLinkDTO = areaLinkDTOItemClickEvent.getItem();
        openDialog(areaLinkDTO);
    }

    private void openDialog(AreaLinkDTO areaLinkDTO) {
        dialogLayout.removeAll();
        dialog.getFooter().removeAll();
        TextField editTextField = new TextField();
        editTextField.setWidth("100%");
        editTextField.setValue(areaLinkDTO.getTitle());
        Button saveButton = new Button(Constants.SAVE, e -> {
            areaLinkDTO.setTitle(editTextField.getValue());
            try {
                areaLinkService.update(areaLinkDTO);
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
                        areaLinkService.remove(areaLinkDTO);
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
        removeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        HorizontalLayout hl = new HorizontalLayout(editTextField, removeButton);
        hl.setWidth("100%");
        hl.setMargin(false);
        hl.setPadding(false);
        hl.setFlexGrow(1, editTextField);
        dialogLayout.add(hl);
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);
        dialog.open();
    }

}