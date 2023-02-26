package com.cutalab.cutalab2.views.dashboards.disks;

import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.StatusDTO;
import com.cutalab.cutalab2.backend.service.StatusService;
import com.cutalab.cutalab2.utils.Constants;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.*;

public class DiskCRUDView extends VerticalLayout {

    private StatusService statusService;

    private Integer formType;
    private DiskDTO diskDTO;
    private List<HorizontalLayout> layouts;

    public DiskCRUDView(StatusService statusService, Integer formType, DiskDTO diskDTO) {
        this.statusService = statusService;
        this.formType = formType;
        this.diskDTO = diskDTO;
        if(formType.equals(Constants.FORM_CREATE)) {

        } else if(formType.equals(Constants.FORM_UPDATE)) {
            layouts = updateLayouts();
        } else if(formType.equals(Constants.FORM_READ)) {
            layouts = createLayouts();
        }
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

    private List<HorizontalLayout> updateLayouts() {
        List<HorizontalLayout> retval = new ArrayList<>();
        Checkbox openable = new Checkbox(); openable.setValue(diskDTO.isOpenable());
        TextField titleField = new TextField(); titleField.setWidth("100%"); titleField.setValue(diskDTO.getTitle());
        TextField authorField = new TextField(); authorField.setWidth("100%"); authorField.setValue(diskDTO.getAuthor());
        TextField labelField = new TextField(); labelField.setWidth("100%"); labelField.setValue(diskDTO.getLabel());
        TextField reprintField = new TextField(); reprintField.setWidth("100%"); reprintField.setValue(diskDTO.getReprint());
        TextField valueField = new TextField(); valueField.setWidth("100%"); valueField.setValue(String.valueOf(diskDTO.getPresumedValue()));
        TextField yearField = new TextField(); yearField.setWidth("100%"); yearField.setValue(diskDTO.getYear());
        ComboBox<StatusDTO> diskStatus = new ComboBox<>();
        ComboBox<StatusDTO> coverStatus = new ComboBox<>();
        diskStatus.setItems(statusService.findAll());
        coverStatus.setItems(statusService.findAll());
        diskStatus.setWidth("100%"); diskStatus.setValue(diskDTO.getDiskStatus());
        coverStatus.setWidth("100%"); coverStatus.setValue(diskDTO.getCoverStatus());
        TextArea notesArea = new TextArea(); notesArea.setWidth("100%"); notesArea.setValue(diskDTO.getNote());
        Button genreListButton = new Button(Constants.DISK_EDIT_GENRE);
        Button styleListButton = new Button(Constants.DISK_EDIT_STYLE);
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
        newDiskDTO.setReprint(((TextField) layouts.get(4).getComponentAt(1)).getValue());
        newDiskDTO.setPresumedValue(BigDecimal.valueOf(Double.parseDouble(((TextField) layouts.get(5).getComponentAt(1)).getValue())));
        newDiskDTO.setYear(((TextField) layouts.get(6).getComponentAt(1)).getValue());
        newDiskDTO.setDiskStatus(((ComboBox<StatusDTO>) layouts.get(7).getComponentAt(1)).getValue());
        newDiskDTO.setCoverStatus(((ComboBox<StatusDTO>) layouts.get(8).getComponentAt(1)).getValue());
        newDiskDTO.setNote(((TextArea) layouts.get(9).getComponentAt(1)).getValue());
        return newDiskDTO;
    }

}