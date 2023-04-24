package com.cutalab.cutalab2.views.admin.laboratory;

import com.cutalab.cutalab2.backend.dto.LaboratoryAreaDTO;
import com.cutalab.cutalab2.backend.dto.LaboratoryDTO;
import com.cutalab.cutalab2.backend.service.LaboratoryAreaService;
import com.cutalab.cutalab2.backend.service.LaboratoryService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.cutalab.cutalab2.views.admin.links.AdminAreaLinkView;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

import java.time.LocalDateTime;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin-laboratory", layout = MainLayout.class)
@PageTitle(Constants.MENU_ADMIN + " | " + Constants.APP_AUTHOR)
public class AdminLaboratoryView extends VerticalLayout implements ComponentEventListener<ItemClickEvent<LaboratoryDTO>> {

    private TextArea editor;
    private final TextField titleTextField;
    private final Button addTitleButton;
    private final ComboBox<LaboratoryAreaDTO> laboratoryAreaDTOComboBox;
    private final Grid<LaboratoryDTO> grid;
    private final Dialog dialog;

    private final LaboratoryService laboratoryService;
    private final LaboratoryAreaService laboratoryAreaService;

    public AdminLaboratoryView(LaboratoryService laboratoryService, LaboratoryAreaService laboratoryAreaService) {
        this.laboratoryService = laboratoryService;
        this.laboratoryAreaService = laboratoryAreaService;
        H2 title = new H2(Constants.MENU_LABORATORY);
        titleTextField = new TextField();
        addTitleButton = new Button(new Icon(VaadinIcon.PLUS), this::onAddTitleButton);
        Button areaLaboratoryButton = new Button(Constants.MENU_AREA_LABORATORY, this::onAreaLaboratory);
        areaLaboratoryButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        titleTextField.setPlaceholder("Titolo del laboratorio...");
        laboratoryAreaDTOComboBox = new ComboBox<>();
        grid = new Grid<>(LaboratoryDTO.class, false);
        grid.addItemClickListener(this);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setWidth("100%");
        grid.addColumn(LaboratoryDTO::getTitle).setHeader(Constants.AREA_LINK_GRID_HEADER_TITLE);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        dialog = new Dialog();
        dialog.setWidth("90%");
        dialog.setHeight("90%");
        laboratoryAreaDTOComboBox.setItems(laboratoryAreaService.findAll());
        HorizontalLayout headerLayout = new HorizontalLayout(titleTextField, laboratoryAreaDTOComboBox, addTitleButton, areaLaboratoryButton);
        headerLayout.setFlexGrow(1, titleTextField);
        headerLayout.setFlexGrow(1, laboratoryAreaDTOComboBox);
        headerLayout.setWidth("100%");
        add(title, headerLayout, grid, dialog);
        setFlexGrow(1, grid);
        setSizeFull();
        fillGrid();
    }

    private void addAreaLaboratory() {
        dialog.removeAll();
        dialog.setHeaderTitle(Constants.CREATE+" | "+titleTextField.getValue());
        editor = new TextArea();
        editor.setSizeFull();
        Button saveButton = new Button(Constants.SAVE, e-> {
            if(titleTextField.getValue().isEmpty() || laboratoryAreaDTOComboBox.getValue() == null || editor.getValue().isEmpty()) {
                Constants.NOTIFICATION_DB_VALIDATION_ERROR();
            } else {
                LaboratoryDTO laboratoryDTO = new LaboratoryDTO();
                laboratoryDTO.setTitle(titleTextField.getValue());
                laboratoryDTO.setCreatedAt(LocalDateTime.now());
                laboratoryDTO.setUpdatedAt(LocalDateTime.now());
                laboratoryDTO.setBody(editor.getValue());
                laboratoryDTO.setLaboratoryAreaDTO(laboratoryAreaDTOComboBox.getValue());
                try {
                    laboratoryService.create(laboratoryDTO);
                    fillGrid();
                } catch (Exception e1) {
                    Constants.NOTIFICATION_DB_ERROR(e1);
                }
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.setWidth("100%");
        VerticalLayout vl = new VerticalLayout(editor, saveButton);
        vl.setSizeFull();
        vl.setFlexGrow(1, editor);
        dialog.add(vl);
        dialog.open();

    }

    public void onAddTitleButton(ClickEvent<Button> buttonClickEvent) {
        if(titleTextField.getValue().isEmpty() || laboratoryAreaDTOComboBox.getValue() == null) {
            Constants.NOTIFICATION_DB_VALIDATION_ERROR();
        } else {
            Button eventButton = (Button) buttonClickEvent.getSource();
            if (eventButton.equals(addTitleButton)) {
                addAreaLaboratory();
            }
        }
    }

    private void onAreaLaboratory(ClickEvent<Button> buttonClickEvent) {
        Dialog dialog = new Dialog();
        dialog.setWidth("90%");
        dialog.setMaxHeight("90%");
        dialog.setHeaderTitle(Constants.MENU_AREA_LABORATORY);
        dialog.add(new AdminAreaLaboratoryView(laboratoryAreaService));
        dialog.open();
    }

    private void fillGrid() {
        grid.setItems(laboratoryService.findAll());
    }

    @Override
    public void onComponentEvent(ItemClickEvent<LaboratoryDTO> laboratoryDTOItemClickEvent) {
        dialog.removeAll();
        dialog.setHeaderTitle(Constants.PREVIEW + " | " + laboratoryDTOItemClickEvent.getItem().getTitle());
        StyledText text = new StyledText(laboratoryDTOItemClickEvent.getItem().getBody());
        VerticalLayout vl = new VerticalLayout(text);
        vl.setSizeFull();
        dialog.add(vl);
        dialog.open();
    }
}

class StyledText extends Composite<Span> implements HasText {

    private final Span content = new Span();
    private String text;

    public StyledText(String htmlText) {
        setText(htmlText);
    }

    @Override
    protected Span initContent() {
        return content;
    }

    @Override
    public void setText(String htmlText) {
        if(htmlText == null) {
            htmlText = "";
        }
        if(htmlText.equals(text)) {
            return;
        }
        text = htmlText;
        content.removeAll();
        content.add(new Html("<span>" + htmlText + "</span>"));
    }

    @Override
    public String getText() {
        return text;
    }
}