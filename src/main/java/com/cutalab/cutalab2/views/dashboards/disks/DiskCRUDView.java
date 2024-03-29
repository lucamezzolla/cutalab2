package com.cutalab.cutalab2.views.dashboards.disks;

import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskGenreDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskStyleDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.StatusDTO;
import com.cutalab.cutalab2.backend.service.DiskService;
import com.cutalab.cutalab2.backend.service.StatusService;
import com.cutalab.cutalab2.utils.Constants;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

public class DiskCRUDView extends VerticalLayout {

    private final StatusService statusService;
    private final DiskService diskService;

    private final DiskDTO diskDTO;
    private List<HorizontalLayout> layouts;

    public DiskCRUDView(DiskService diskService, StatusService statusService, Integer formType, DiskDTO diskDTO) {
        this.diskService = diskService;
        this.statusService = statusService;
        this.diskDTO = diskDTO;
        if(formType.equals(Constants.FORM_CREATE)) {
            layouts = createLayouts();
        } else if(formType.equals(Constants.FORM_UPDATE)) {
            layouts = updateLayouts();
        } else if(formType.equals(Constants.FORM_READ)) {
            layouts = readLayouts();
        }
        for(HorizontalLayout hl : layouts) {
            add(hl);
        }
    }

    private List<HorizontalLayout> createLayouts() {
        List<HorizontalLayout> retval = new ArrayList<>();
        Checkbox openable = new Checkbox(true);
        TextField titleField = new TextField(); titleField.setWidth("100%");
        TextField authorField = new TextField(); authorField.setWidth("100%");
        TextField labelField = new TextField(); labelField.setWidth("100%");
        ComboBox<String> reprintField = new ComboBox<>(); reprintField.setWidth("100%");
        reprintField.setItems("si", "no");
        NumberField valueField = new NumberField(); valueField.setStep(2); valueField.setWidth("100%");
        valueField.setValue(0.00);
        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        valueField.setSuffixComponent(euroSuffix);
        TextField yearField = new TextField(); yearField.setWidth("100%"); yearField.setMaxLength(4);
        ComboBox<StatusDTO> diskStatus = new ComboBox<>();
        ComboBox<StatusDTO> coverStatus = new ComboBox<>();
        diskStatus.setItems(statusService.findAll());
        coverStatus.setItems(statusService.findAll());
        diskStatus.setWidth("100%");
        coverStatus.setWidth("100%");
        TextArea notesArea = new TextArea(); notesArea.setWidth("100%");
        Button genreListButton = new Button(Constants.DISK_ADD_GENRE, e-> {
            Dialog dialog = new Dialog();
            dialog.open();
            dialog.setWidth("50%");
            dialog.setMaxHeight("80%");
            Grid<DiskGenreDTO> grid= new Grid<>(DiskGenreDTO.class, false);
            grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
            grid.setSelectionMode(Grid.SelectionMode.MULTI);
            grid.addColumn(DiskGenreDTO::getName).setHeader(Constants.DISK_DETAIL_GENRE);
            grid.setItems(diskService.findAllGenres());
            dialog.add(grid);
            Button closeButton = new Button(Constants.CLOSE, clickEvent -> {
                diskDTO.getDiskGenreList().clear();
                for (DiskGenreDTO diskGenreDTO : grid.getSelectedItems()) {
                    diskDTO.getDiskGenreList().add(diskGenreDTO);
                }
                dialog.close();
            });
            for (DiskGenreDTO diskGenreDTO : diskDTO.getDiskGenreList()) {
                grid.select(diskGenreDTO);
            }
            closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            dialog.getFooter().add(closeButton);
        });
        Button styleListButton = new Button(Constants.DISK_ADD_STYLE, e-> {
            Dialog dialog = new Dialog();
            dialog.open();
            dialog.setWidth("50%");
            dialog.setMaxHeight("80%");
            Grid<DiskStyleDTO> grid= new Grid<>(DiskStyleDTO.class, false);
            grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
            grid.setSelectionMode(Grid.SelectionMode.MULTI);
            Grid.Column<DiskStyleDTO> styleColumn = grid.addColumn(DiskStyleDTO::getName).setHeader(Constants.DISK_DETAIL_STYLE);
            GridListDataView<DiskStyleDTO> dataView = grid.setItems(diskService.findAllStyles());
            StyleFilter styleFilter = new StyleFilter(dataView);
            grid.getHeaderRows().clear();
            HeaderRow headerRow = grid.appendHeaderRow();
            headerRow.getCell(styleColumn).setComponent(createFilterHeader(styleFilter::setName));
            dialog.add(grid);
            Button closeButton = new Button(Constants.CLOSE, clickEvent -> {
                diskDTO.getDiskStyleList().clear();
                for (DiskStyleDTO diskStyleDTO : grid.getSelectedItems()) {
                    diskDTO.getDiskStyleList().add(diskStyleDTO);
                }
                dialog.close();
            });
            for (DiskStyleDTO diskStyleDTO : diskDTO.getDiskStyleList()) {
                grid.select(diskStyleDTO);
            }
            closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            dialog.getFooter().add(closeButton);
        });
        genreListButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        styleListButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        HorizontalLayout genreStyleLayout = new HorizontalLayout(genreListButton, styleListButton);
        LinkedHashMap<String, Component> map = new LinkedHashMap<>();
        map.put(Constants.DISK_DETAIL_OPENABLE, openable);
        map.put(Constants.DISK_DETAIL_TITLE, titleField);
        map.put(Constants.DISK_DETAIL_AUTHOR, authorField);
        map.put(Constants.DISK_DETAIL_LABEL, labelField);
        map.put(Constants.DISK_DETAIL_REPRINT, reprintField);
        map.put(Constants.DISK_DETAIL_VALUE, valueField);
        map.put(Constants.DISK_DETAIL_YEAR, yearField);
        map.put(Constants.DISK_DETAIL_DISK_STATUS, diskStatus);
        map.put(Constants.DISK_DETAIL_COVER_STATUS, coverStatus);
        map.put(Constants.DISK_DETAIL_NOTE, notesArea);
        map.put("", genreStyleLayout);
        for (String key : map.keySet()) {
            Html keyText = new Html("<b>"+key+"</b>");
            keyText.getElement().getStyle().set("width", "15%");
            Component component = map.get(key);
            component.getElement().getStyle().set("width", "85%");
            HorizontalLayout hl = new HorizontalLayout(keyText, component);
            hl.setWidth("100%");
            retval.add(hl);
        }
        return retval;
    }

    private List<HorizontalLayout> readLayouts() {
        List<HorizontalLayout> retval = new ArrayList<>();
        Button coverButton = new Button("Ingrandisci copertina", e -> {
            Dialog dialog = new Dialog();
            dialog.open();
            dialog.setMaxWidth("80%");
            dialog.setMaxHeight("80%");
            Image coverImage = new Image();
            coverImage.setHeight("100%");
            if(diskDTO.getCover() != null) {
                coverImage.setSrc(diskDTO.getCover());
            }
            dialog.add(coverImage);
            Button cancelButton = new Button(Constants.CLOSE, clickEvent -> dialog.close());
            dialog.getFooter().add(cancelButton);
        });
        coverButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        coverButton.setEnabled(diskDTO.getCover() != null);
        String note;
        if(diskDTO.getNote() == null || (diskDTO.getNote() != null && diskDTO.getNote().isEmpty()))  {
            note = "Nessuna";
        } else {
            note = diskDTO.getNote();
        }
        StringBuilder genres = new StringBuilder();
        for (int i = 0; i < diskDTO.getDiskGenreList().size(); i++) {
            genres.append(diskDTO.getDiskGenreList().get(i).getName());
            if (i < diskDTO.getDiskGenreList().size() - 1) {
                genres.append(", ");
            }
        }
        StringBuilder styles = new StringBuilder();
        for (int i = 0; i < diskDTO.getDiskStyleList().size(); i++) {
            styles.append(diskDTO.getDiskStyleList().get(i).getName());
            if (i < diskDTO.getDiskStyleList().size() - 1) {
                styles.append(", ");
            }
        }
        LinkedHashMap<String, Component> map = new LinkedHashMap<>();
        map.put(Constants.DISK_DETAIL_OPENABLE, new Text(diskDTO.isOpenable() ? "Sì" : "No"));
        map.put(Constants.DISK_DETAIL_COVER, coverButton);
        map.put(Constants.DISK_DETAIL_TITLE, new Text(diskDTO.getTitle()));
        map.put(Constants.DISK_DETAIL_AUTHOR, new Text(diskDTO.getAuthor()));
        map.put(Constants.DISK_DETAIL_LABEL, new Text(diskDTO.getLabel()));
        map.put(Constants.DISK_DETAIL_REPRINT, new Text(diskDTO.getReprint()));
        map.put(Constants.DISK_DETAIL_VALUE, new Text(diskDTO.getPresumedValue() + " €"));
        map.put(Constants.DISK_DETAIL_YEAR, new Text(diskDTO.getYear()));
        map.put(Constants.DISK_DETAIL_DISK_STATUS, new Text(diskDTO.getDiskStatus().getName()));
        map.put(Constants.DISK_DETAIL_COVER_STATUS, new Text(diskDTO.getCoverStatus().getName()));
        map.put(Constants.DISK_DETAIL_NOTE, new Text(note));
        map.put(Constants.DISK_DETAIL_GENRE, new Text(genres.toString()));
        map.put(Constants.DISK_DETAIL_STYLE, new Text(styles.toString()));
        for (String key : map.keySet()) {
            Html keyText = new Html("<b>"+key+"</b>");
            keyText.getElement().getStyle().set("width", "180px");
            Component component = map.get(key);
            HorizontalLayout hl = new HorizontalLayout(keyText, component);
            hl.setWidth("100%");
            retval.add(hl);
        }
        return retval;
    }

    private List<HorizontalLayout> updateLayouts() {
        List<HorizontalLayout> retval = new ArrayList<>();
        Checkbox openable = new Checkbox(); openable.setValue(diskDTO.isOpenable());
        TextField titleField = new TextField(); titleField.setWidth("100%"); titleField.setValue(diskDTO.getTitle());
        TextField authorField = new TextField(); authorField.setWidth("100%"); authorField.setValue(diskDTO.getAuthor());
        TextField labelField = new TextField(); labelField.setWidth("100%"); labelField.setValue(diskDTO.getLabel());
        ComboBox<String> reprintField = new ComboBox<>(); reprintField.setWidth("100%");
        reprintField.setItems("si", "no"); reprintField.setValue(diskDTO.getReprint());
        NumberField valueField = new NumberField(); valueField.setStep(2); valueField.setWidth("100%");
        valueField.setValue(diskDTO.getPresumedValue().doubleValue());
        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        valueField.setSuffixComponent(euroSuffix);
        TextField yearField = new TextField(); yearField.setWidth("100%"); yearField.setValue(diskDTO.getYear()); yearField.setMaxLength(4);
        ComboBox<StatusDTO> diskStatus = new ComboBox<>();
        ComboBox<StatusDTO> coverStatus = new ComboBox<>();
        diskStatus.setItems(statusService.findAll());
        coverStatus.setItems(statusService.findAll());
        diskStatus.setWidth("100%"); diskStatus.setValue(diskDTO.getDiskStatus());
        coverStatus.setWidth("100%"); coverStatus.setValue(diskDTO.getCoverStatus());
        TextArea notesArea = new TextArea(); notesArea.setWidth("100%"); notesArea.setValue(diskDTO.getNote());
        Button genreListButton = new Button(Constants.DISK_EDIT_GENRE, e-> {
            Dialog dialog = new Dialog();
            dialog.open();
            dialog.setWidth("50%");
            dialog.setMaxHeight("80%");
            Grid<DiskGenreDTO> grid= new Grid<>(DiskGenreDTO.class, false);
            grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
            grid.setSelectionMode(Grid.SelectionMode.MULTI);
            grid.addColumn(DiskGenreDTO::getName).setHeader(Constants.DISK_DETAIL_GENRE);
            grid.setItems(diskService.findAllGenres());
            for(DiskGenreDTO genreDTO : diskDTO.getDiskGenreList()) {
                grid.getSelectionModel().select(genreDTO);
            }
            dialog.add(grid);
            Button cancelButton = new Button(Constants.CANCEL, clickEvent -> dialog.close());
            Button saveButton = new Button(Constants.SAVE, clickEvent -> {
                diskDTO.getDiskGenreList().clear();
                List<DiskGenreDTO> selectedItems = new ArrayList<>(grid.getSelectedItems());
                diskDTO.setDiskGenreList(selectedItems);
                dialog.close();
            });
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            dialog.getFooter().add(cancelButton, saveButton);
        });
        Button styleListButton = new Button(Constants.DISK_EDIT_STYLE, e-> {
            Dialog dialog = new Dialog();
            dialog.open();
            dialog.setWidth("50%");
            dialog.setMaxHeight("80%");
            Grid<DiskStyleDTO> grid= new Grid<>(DiskStyleDTO.class, false);
            grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
            grid.setSelectionMode(Grid.SelectionMode.MULTI);
            Grid.Column<DiskStyleDTO> styleColumn = grid.addColumn(DiskStyleDTO::getName).setHeader(Constants.DISK_DETAIL_STYLE);
            GridListDataView<DiskStyleDTO> dataView = grid.setItems(diskService.findAllStyles());
            StyleFilter styleFilter = new StyleFilter(dataView);
            grid.getHeaderRows().clear();
            HeaderRow headerRow = grid.appendHeaderRow();
            headerRow.getCell(styleColumn).setComponent(createFilterHeader(styleFilter::setName));
            for (DiskStyleDTO styleDTO : diskDTO.getDiskStyleList()) {
                grid.getSelectionModel().select(styleDTO);
            }
            dialog.add(grid);
            Button cancelButton = new Button(Constants.CANCEL, clickEvent -> dialog.close());
            Button saveButton = new Button(Constants.SAVE, clickEvent -> {
                diskDTO.getDiskStyleList().clear();
                List<DiskStyleDTO> selectedItems = new ArrayList<>(grid.getSelectedItems());
                diskDTO.setDiskStyleList(selectedItems);
                dialog.close();
            });
            saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            dialog.getFooter().add(cancelButton, saveButton);
        });
        genreListButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        styleListButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        HorizontalLayout genreStyleLayout = new HorizontalLayout(genreListButton, styleListButton);
        LinkedHashMap<String, Component> map = new LinkedHashMap<>();
        map.put(Constants.DISK_DETAIL_OPENABLE, openable);
        map.put(Constants.DISK_DETAIL_TITLE, titleField);
        map.put(Constants.DISK_DETAIL_AUTHOR, authorField);
        map.put(Constants.DISK_DETAIL_LABEL, labelField);
        map.put(Constants.DISK_DETAIL_REPRINT, reprintField);
        map.put(Constants.DISK_DETAIL_VALUE, valueField);
        map.put(Constants.DISK_DETAIL_YEAR, yearField);
        map.put(Constants.DISK_DETAIL_DISK_STATUS, diskStatus);
        map.put(Constants.DISK_DETAIL_COVER_STATUS, coverStatus);
        map.put(Constants.DISK_DETAIL_NOTE, notesArea);
        map.put("", genreStyleLayout);
        for (String key : map.keySet()) {
            Html keyText = new Html("<b>"+key+"</b>");
            keyText.getElement().getStyle().set("width", "15%");
            Component component = map.get(key);
            component.getElement().getStyle().set("width", "85%");
            HorizontalLayout hl = new HorizontalLayout(keyText, component);
            hl.setWidth("100%");
            retval.add(hl);
        }
        return retval;
    }

    public DiskDTO getDiskDTO() {
        DiskDTO newDiskDTO = new DiskDTO();
        BeanUtils.copyProperties(diskDTO, newDiskDTO);
        newDiskDTO.setOpenable(((Checkbox) layouts.get(0).getComponentAt(1)).getValue());
        newDiskDTO.setTitle(((TextField) layouts.get(1).getComponentAt(1)).getValue());
        newDiskDTO.setAuthor(((TextField) layouts.get(2).getComponentAt(1)).getValue());
        newDiskDTO.setLabel(((TextField) layouts.get(3).getComponentAt(1)).getValue());
        newDiskDTO.setReprint(((ComboBox<String>) layouts.get(4).getComponentAt(1)).getValue());
        newDiskDTO.setPresumedValue(BigDecimal.valueOf((((NumberField) layouts.get(5).getComponentAt(1)).getValue())));
        newDiskDTO.setYear(((TextField) layouts.get(6).getComponentAt(1)).getValue());
        newDiskDTO.setDiskStatus(((ComboBox<StatusDTO>) layouts.get(7).getComponentAt(1)).getValue());
        newDiskDTO.setCoverStatus(((ComboBox<StatusDTO>) layouts.get(8).getComponentAt(1)).getValue());
        newDiskDTO.setNote(((TextArea) layouts.get(9).getComponentAt(1)).getValue());
        newDiskDTO.setDiskStyleList(diskDTO.getDiskStyleList());
        newDiskDTO.setDiskGenreList(diskDTO.getDiskGenreList());
        return newDiskDTO;
    }

    private Component createFilterHeader(Consumer<String> filterChangeConsumer) {
        TextField textField = new TextField();
        textField.setPlaceholder("Comincia a scrivere lo stile...");
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");
        return layout;
    }

}

class StyleFilter {

    private final GridListDataView<DiskStyleDTO> dataView;

    private Integer id;

    private String name;

    public StyleFilter(GridListDataView<DiskStyleDTO> dataView) {
        this.dataView = dataView;
        this.dataView.addFilter(this::test);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.dataView.refreshAll();
    }

    public boolean test(DiskStyleDTO diskStyleDTO) {
        return matches(diskStyleDTO.getName(), name);
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }

}