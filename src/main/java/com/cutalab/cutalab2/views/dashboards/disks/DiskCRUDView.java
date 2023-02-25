package com.cutalab.cutalab2.views.dashboards.disks;

import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskDTO;
import com.cutalab.cutalab2.utils.Constants;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;

import java.util.*;

public class DiskCRUDView extends VerticalLayout {

    private Integer formType;
    private DiskDTO diskDTO;
    private Grid<DiskDTO> grid;
    private TextField titleField, authorField;
    private Label genreLabel, styleLabel;


    public DiskCRUDView(Integer formType, DiskDTO diskDTO) {
        this.formType = formType;
        this.diskDTO = diskDTO;
        titleField = new TextField();
        authorField = new TextField();
        genreLabel = new Label();
        styleLabel = new Label();
        titleField.setPlaceholder(Constants.FORM_TITLE_PLACEHOLDER);
        authorField.setPlaceholder(Constants.FORM_AUTHOR_PLACEHOLDER);
        genreLabel.setText(Constants.FORM_GENRE_PLACEHOLDER);
        styleLabel.setText(Constants.FORM_STYLE_PLACEHOLDER);
        /*
        grid = new Grid<>(DiskDTO.class, false);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(createLabelRenderer()).setAutoWidth(true).setFlexGrow(0);;
        grid.addColumn(createValueRenderer()).setAutoWidth(true).setFlexGrow(0);;
        grid.setWidth("100%");
        grid.setAllRowsVisible(true);
        grid.setItems(diskDTO);
        add(grid);
        */
        List<HorizontalLayout> layouts = createLayouts();
        for(HorizontalLayout hl : layouts) {
            add(hl);
        }

    }

    private List<HorizontalLayout> createLayouts() {
        List<HorizontalLayout> retval = new ArrayList<>();
        Button coverButton = new Button("Ingrandisci copertina");
        coverButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        String note = "";
        if(diskDTO.getNote() == null || (diskDTO.getNote() != null && diskDTO.getNote().isEmpty()))  {
            note = "Nessuna";
        } else {
            note = diskDTO.getNote();
        }
        String genres = "";
        for (int i = 0; i < diskDTO.getDiskGenreList().size(); i++) {
            genres += diskDTO.getDiskGenreList().get(i).getName();
            if (i < diskDTO.getDiskGenreList().size() - 1) {
                genres += ", ";
            }
        }
        String styles = "";
        for (int i = 0; i < diskDTO.getDiskStyleList().size(); i++) {
            styles += diskDTO.getDiskStyleList().get(i).getName();
            if (i < diskDTO.getDiskStyleList().size() - 1) {
                styles += ", ";
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
        map.put(Constants.DISK_DETAIL_GENRE, new Text(genres));
        map.put(Constants.DISK_DETAIL_STYLE, new Text(styles));
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

}