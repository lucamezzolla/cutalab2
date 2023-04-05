package com.cutalab.cutalab2.views.admin.links;

import com.cutalab.cutalab2.backend.dto.AreaLinkDTO;
import com.cutalab.cutalab2.backend.dto.LinkDTO;
import com.cutalab.cutalab2.backend.service.AreaLinkService;
import com.cutalab.cutalab2.backend.service.LinkService;
import com.cutalab.cutalab2.utils.ConfirmDialog;
import com.cutalab.cutalab2.utils.ConfirmDialogInterface;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin-link", layout = MainLayout.class)
@PageTitle(Constants.MENU_ADMIN + " | " + Constants.APP_AUTHOR)
public class AdminLinksView extends VerticalLayout implements ComponentEventListener<ItemClickEvent<LinkDTO>> {

    private AreaLinkService areaLinkService;
    private LinkService linkService;
    private TextField titleTextField;
    private TextField urlTextField;
    private ComboBox<AreaLinkDTO> areaLinkCombo;
    private Button addLinkButton;
    private Grid<LinkDTO> grid;
    private Dialog dialog;
    private VerticalLayout dialogLayout;

    public AdminLinksView(AreaLinkService areaLinkService, LinkService linkService) {
        this.areaLinkService = areaLinkService;
        this.linkService = linkService;
        H2 title = new H2(Constants.MENU_LINKS);
        titleTextField = new TextField();
        urlTextField = new TextField();
        areaLinkCombo = new ComboBox<>();
        areaLinkCombo.addFocusListener(e -> { fillAreaLinkCombo(); });
        areaLinkCombo.setPlaceholder("Area...");
        addLinkButton = new Button(new Icon(VaadinIcon.PLUS), this::onAddLinkButton);
        titleTextField.setPlaceholder("Titolo...");
        urlTextField.setPlaceholder("URL...");
        grid = new Grid<>(LinkDTO.class, false);
        grid.addItemClickListener(this);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setWidth("100%");
        grid.addColumn(LinkDTO::getTitle).setHeader(Constants.LINK_GRID_HEADER_TITLE).setAutoWidth(true).setFlexGrow(0);
        grid.addColumn("areaLinkDTO.title").setHeader(Constants.MENU_AREA_LINK).setFlexGrow(1);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        dialog = new Dialog();
        dialog.setWidth("50%");
        dialog.setHeaderTitle(Constants.EDIT);
        dialogLayout = new VerticalLayout();
        dialogLayout.setMargin(false);
        dialogLayout.setPadding(false);
        dialog.add(dialogLayout);
        HorizontalLayout headerLayout = new HorizontalLayout(titleTextField, urlTextField, areaLinkCombo, addLinkButton);
        headerLayout.setWidth("100%");
        add(title, headerLayout, grid, dialog);
        setFlexGrow(1, grid);
        setSizeFull();
        fillAreaLinkCombo();
        fillGrid();
    }

    private void fillAreaLinkCombo() {
        areaLinkCombo.setItems(areaLinkService.findAll());
    }

    private void fillGrid() {
        grid.setItems(linkService.findAll());
    }

    private void reset() {
        titleTextField.setValue("");
        urlTextField.setValue("");
        areaLinkCombo.setValue(null);
    }

    private void addLink() {
        String title = titleTextField.getValue();
        String url = urlTextField.getValue();
        AreaLinkDTO areaLinkDTO = areaLinkCombo.getValue();
        if(title.isEmpty() || url.isEmpty() || areaLinkDTO == null) {
            Constants.NOTIFICATION_DB_VALIDATION_ERROR();
        } else {
            LinkDTO linkDTO = new LinkDTO();
            Binder<LinkDTO> binder = new Binder<>(LinkDTO.class);
            binder.forField(titleTextField).bind(LinkDTO::getTitle, LinkDTO::setTitle);
            binder.forField(urlTextField).bind(LinkDTO::getUrl, LinkDTO::setUrl);
            binder.forField(areaLinkCombo).bind(LinkDTO::getAreaLinkDTO, LinkDTO::setAreaLinkDTO);
            try {
                binder.writeBean(linkDTO);

            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
            linkService.create(linkDTO);
            reset();
            fillGrid();
        }
    }

    private void onAddLinkButton(ClickEvent<Button> buttonClickEvent) {
        Button eventButton = (Button) buttonClickEvent.getSource();
        if(eventButton.equals(addLinkButton)) {
            addLink();
        }
    }

    @Override
    public void onComponentEvent(ItemClickEvent<LinkDTO> linkDTOItemClickEvent) {
        LinkDTO linkDTO = linkDTOItemClickEvent.getItem();
        openDialog(linkDTO);
    }

    private void openDialog(LinkDTO linkDTO) {
        dialogLayout.removeAll();
        dialog.getFooter().removeAll();
        TextField editTextField = new TextField();
        TextField urlTextField = new TextField();
        ComboBox<AreaLinkDTO> areaLinkDTOField = new ComboBox<>();
        editTextField.setWidth("100%");
        urlTextField.setWidth("100%");
        areaLinkDTOField.setWidth("100%");
        Binder<LinkDTO> binder = new Binder<>(LinkDTO.class);
        binder.forField(editTextField).bind(LinkDTO::getTitle, LinkDTO::setTitle);
        binder.forField(urlTextField).bind(LinkDTO::getUrl, LinkDTO::setUrl);
        binder.forField(areaLinkDTOField).bind(LinkDTO::getAreaLinkDTO, LinkDTO::setAreaLinkDTO);
        Button saveButton = new Button(Constants.SAVE, e -> {
            if(editTextField.getValue().isEmpty() || urlTextField.getValue().isEmpty() || areaLinkDTOField.getValue() == null) {
                Constants.NOTIFICATION_DB_VALIDATION_ERROR();
            } else {
                try {
                    binder.writeBean(linkDTO);
                    linkService.update(linkDTO);
                    Constants.NOTIFICATION_DB_SUCCESS();
                    fillGrid();
                } catch (Exception e1) {
                    Constants.NOTIFICATION_DB_ERROR(e1);
                }
                dialog.close();
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button(Constants.CANCEL, e -> dialog.close());
        Button removeButton = new Button(new Icon(VaadinIcon.TRASH), e -> {
            removeLink(linkDTO);
        });
        removeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        HorizontalLayout hl = new HorizontalLayout(editTextField, areaLinkDTOField);
        hl.setWidth("100%");
        hl.setMargin(false);
        hl.setPadding(false);
        hl.setFlexGrow(1, editTextField);
        hl.setFlexGrow(1, urlTextField);
        hl.setFlexGrow(1, areaLinkDTOField);
        dialogLayout.add(hl, urlTextField);
        HorizontalLayout hlFooter1 = new HorizontalLayout(removeButton);
        HorizontalLayout hlFooter2 = new HorizontalLayout(cancelButton, saveButton);
        HorizontalLayout hlFooter = new HorizontalLayout(hlFooter1, hlFooter2);
        hlFooter.setWidth("100%"); hlFooter1.setWidth("100%");
        hlFooter.setFlexGrow(1, hlFooter1);
        hlFooter.setMargin(false);
        dialog.getFooter().add(hlFooter);
        dialog.open();
        areaLinkDTOField.setItems(areaLinkService.findAll());
        areaLinkDTOField.setValue(linkDTO.getAreaLinkDTO());
        binder.readBean(linkDTO);
    }

    private void removeLink(LinkDTO linkDTO) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setConfirmDialogListener(new ConfirmDialogInterface() {
            @Override
            public void confirmDialogListener() {
                try {
                    linkService.remove(linkDTO);
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
    }

}
