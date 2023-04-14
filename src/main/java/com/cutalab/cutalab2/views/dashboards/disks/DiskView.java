package com.cutalab.cutalab2.views.dashboards.disks;

import com.cutalab.cutalab2.backend.dto.UserDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskGenreDTO;
import com.cutalab.cutalab2.backend.dto.dashboards.disks.DiskStyleDTO;
import com.cutalab.cutalab2.backend.service.DiskService;
import com.cutalab.cutalab2.backend.service.StatusService;
import com.cutalab.cutalab2.backend.service.UserService;
import com.cutalab.cutalab2.utils.ConfirmDialog;
import com.cutalab.cutalab2.utils.ConfirmDialogInterface;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.utils.PDFFileGenerator;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.BeanUtils;

import javax.annotation.security.PermitAll;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route(value="dashboards-disks", layout = MainLayout.class)
@CssImport(value = "/css/styles.css")
@PageTitle(Constants.MENU_DASHBOARDS_DISKS + " | " + Constants.APP_AUTHOR)
public class DiskView extends VerticalLayout implements ComponentEventListener<ItemClickEvent<DiskDTO>> {

    private final HorizontalLayout hlButtons;
    private final Button searchButton;
    private final Div pagesInfo;
    private StatusService statusService;
    private DiskService diskService;
    private UserService userService;

    private Dialog dialog;
    private ComboBox<DiskGenreDTO> genreCombo;
    private ComboBox<DiskStyleDTO> styleCombo;
    private ComboBox<UserDTO> collectionOfCombo;
    private TextField titleField;
    private TextField authorField;
    private Grid<DiskDTO> grid;
    private Label totalDiskLabel;
    private Label totalValueLabel;
    private Label partialValueLabel;
    private Integer offset = 0;
    private Integer page = 1;
    private Integer pages = 0;
    private boolean isSearchButton = true;

    public DiskView(StatusService statusService, UserService userService, DiskService diskService) {
        this.statusService = statusService;
        this.userService = userService;
        this.diskService = diskService;
        H2 title = new H2(Constants.MENU_DASHBOARDS_DISKS);
        Image logo = new Image();
        logo.setSrc("images/stereo.png");
        logo.addClassNames("dashboardLogo");
        dialog = new Dialog();
        collectionOfCombo = new ComboBox<>(); collectionOfCombo.setWidth("100%");
        titleField = new TextField(); titleField.setWidth("100%");
        authorField = new TextField(); authorField.setWidth("100%");
        genreCombo = new ComboBox<>(); genreCombo.setWidth("100%");
        styleCombo = new ComboBox<>(); styleCombo.setWidth("100%");
        collectionOfCombo.setClearButtonVisible(true);
        genreCombo.setClearButtonVisible(true);
        styleCombo.setClearButtonVisible(true);
        searchButton = new Button(Constants.SEARCH, clickEvent -> {
            isSearchButton = true;
            search(clickEvent);
        });
        searchButton.setWidth("100%");
        Button clearButton = new Button(Constants.CLEAR); clearButton.setWidth("100%");
        Button addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addClickListener(this::addDisk);
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clearButton.addClickListener(this::clear);
        collectionOfCombo.setPlaceholder(Constants.COLLECTION_OF_PLACEHOLDER);
        titleField.setPlaceholder(Constants.TITLE_SEARCH_PLACEHOLDER);
        authorField.setPlaceholder(Constants.AUTHOR_SEARCH_PLACEHOLDER);
        genreCombo.setPlaceholder(Constants.GENRE_PLACEHOLDER);
        styleCombo.setPlaceholder(Constants.STYLE_PLACEHOLDER);
        HorizontalLayout hl0 = new HorizontalLayout(clearButton, searchButton, addButton);
        HorizontalLayout hl1 = new HorizontalLayout(collectionOfCombo, genreCombo, styleCombo);
        HorizontalLayout hl2 = new HorizontalLayout(titleField, authorField, hl0);
        hl0.setWidth("100%"); hl1.setWidth("100%"); hl2.setWidth("100%");
        hl0.addClassName("horizontalLayoutSearchDisks");
        hl1.addClassName("horizontalLayoutSearchDisks");
        hl2.addClassName("horizontalLayoutSearchDisks");
        totalDiskLabel = new Label();
        totalValueLabel = new Label();
        partialValueLabel = new Label();
        grid = new Grid<>();
        grid.addItemClickListener(this);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setWidth("100%");
        grid.setAllRowsVisible(true);
        grid.addColumn(createAlbumRenderer());
        grid.setVisible(false);
        /************************************************************/
        Button infoCollection = new Button(Constants.COLLECTION_INFO_BUTTON);
        Button exportCollection = new Button(Constants.COLLECTION_EXPORT_BUTTON);
        Button backCollection = new Button(new Icon(VaadinIcon.ARROW_BACKWARD));
        Button forwardCollection = new Button(new Icon(VaadinIcon.ARROW_FORWARD));
        infoCollection.addClickListener(this::buildDialog); infoCollection.setId("infoCollection");
        exportCollection.addClickListener(this::buildDialog); exportCollection.setId("exportCollection");
        backCollection.addClickListener(this::buildDialog); backCollection.setId("backCollection");
        forwardCollection.addClickListener(this::buildDialog); forwardCollection.setId("forwardCollection");
        exportCollection.addClickListener(event -> {
            boolean isCheckPassed = true;
            if (!isCheckPassed) {
                Notification.show("Unfortunately you can not download this file");
            } else {
                Integer userId = collectionOfCombo.getValue().getId();
                if(userId != null && userId > 0) {
                    PDFFileGenerator pdfFileGenerator = new PDFFileGenerator();
                    File pdfFile = pdfFileGenerator.writeDisksToPdf(diskService.findAllDisks(userId));
                    final StreamResource resource = new StreamResource(pdfFile.getName(), () -> getStream(pdfFile));
                    final StreamRegistration registration = VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);
                    UI.getCurrent().getPage().open(registration.getResourceUri().getPath(), "_blank");
                }
            }
        });
        pagesInfo = new Div();
        pagesInfo.getStyle().set("margin-top", "10px");
        hlButtons = new HorizontalLayout(infoCollection, exportCollection,backCollection, pagesInfo, forwardCollection);
        hlButtons.setWidth("100%"); hlButtons.setVisible(false);
        /***********************************************************/
        add(title, logo, hl1, hl2, hlButtons, grid, dialog);
        setHorizontalComponentAlignment(Alignment.CENTER, title, logo);
        fillCombos();
        setSizeFull();
    }

    private InputStream getStream(File file) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return stream;
    }

    private void buildDialog(ClickEvent<Button> buttonClickEvent) {
        switch(buttonClickEvent.getSource().getId().get()) {
            case "infoCollection":
                Dialog dialog1 = new Dialog();
                dialog1.setHeaderTitle(Constants.COLLECTION_INFO_BUTTON);
                dialog1.setWidth("50%");
                VerticalLayout vl = new VerticalLayout(totalDiskLabel, partialValueLabel, totalValueLabel);
                vl.setWidth("100%");
                dialog1.add(vl);
                dialog1.open();
                break;
            case "backCollection":
                if(page > 1 && offset > 0) {
                    page -= 1;
                    offset -= 10;
                    isSearchButton = false;
                    search(null);
                }
                break;
            case "forwardCollection":
                if(page <= pages) {
                    page += 1;
                    offset += 10;
                    isSearchButton = false;
                    search(null);
                }
                break;
        }
    }

    private void fillCombos() {
        genreCombo.setItems(diskService.findAllGenres());
        styleCombo.setItems(diskService.findAllStyles());
        collectionOfCombo.setItems(userService.findAll());
    }

    private void search(ClickEvent<Button> buttonClickEvent) {
        if(collectionOfCombo.getValue() == null) {
            Constants.NOTIFICATION_DB_VALIDATION_INDEX_DISKS_ERROR();
        } else {
            if(isSearchButton) {
                offset = 0;
                page = 1;
            }
            String title = titleField.getValue();
            String author = authorField.getValue();
            DiskGenreDTO diskGenreDTO = genreCombo.getValue();
            DiskStyleDTO diskStyleDTO = styleCombo.getValue();
            UserDTO userDTO = collectionOfCombo.getValue();
            List<DiskDTO> disks = diskService.findDisks(offset, title, author, diskGenreDTO, diskStyleDTO, userDTO);
            double doublePages = (double)(Math.ceil((double)diskService.searchCount(title, author, diskGenreDTO, diskStyleDTO, userDTO) / 10));
            pages = (int) doublePages;
            Integer totalDisks = diskService.count(userDTO);
            BigDecimal totalValue = diskService.totalValue(userDTO);
            BigDecimal partialValue = BigDecimal.ZERO;
            if(disks.isEmpty()) {
                Constants.NOTIFICATION_NO_DISK();
            } else {
                grid.setVisible(true);
                for(DiskDTO d : disks) {
                    partialValue = partialValue.add(d.getPresumedValue());
                }
                grid.setItems(disks);
                totalDiskLabel.setText("Dischi in lista / totale dischi: "+disks.size()+" su "+totalDisks);
                partialValueLabel.setText("Valore dei dischi in lista: "+partialValue+" €");
                totalValueLabel.setText("Totale valore dell'intera collezione: "+totalValue+" €");
                pagesInfo.setText("pagina "+page+" di "+ pages);
                hlButtons.setVisible(true);
            }
        }
    }

    private void clear(ClickEvent<Button> buttonClickEvent) {
        titleField.setValue("");
        authorField.setValue("");
        collectionOfCombo.setValue(null);
        genreCombo.setValue(null);
        styleCombo.setValue(null);
        grid.setItems(new ArrayList<DiskDTO>());
        grid.setVisible(false);
        totalDiskLabel.setText("");
        totalValueLabel.setText("");
        partialValueLabel.setText("");
        totalDiskLabel.setText("");
        totalValueLabel.setText("");
        partialValueLabel.setText("");
        hlButtons.setVisible(false);
        offset = 0;
        page = 1;
        pages = 0;
    }

    private Renderer<DiskDTO> createAlbumRenderer() {
        return LitRenderer.<DiskDTO> of(
                "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                        + "<img src=\"${item.cover}\" style=\"width: 128px; height:128px; border-radius: 20px\" title=\"${item.title}\" />"
                        + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                        + "    <span style=\"font-size: x-large;\"> ${item.title} </span>"
                        + "    <span style=\"font-size: var(--lumo-font-size-l); color: var(--lumo-secondary-text-color);\">"
                        + "      ${item.author}" + "    </span>"
                        + "  </vaadin-vertical-layout>"
                        + "</vaadin-horizontal-layout>")
        .withProperty("cover", DiskDTO::getCover)
        .withProperty("title", DiskDTO::getTitle)
        .withProperty("author", DiskDTO::getAuthor);
    }

    private void addDisk(ClickEvent<Button> buttonClickEvent) {
        Dialog dialogChooseUser = new Dialog();
        dialogChooseUser.setHeaderTitle(Constants.DISK_CREATE_TITLE);
        dialogChooseUser.setWidth("50%");
        ComboBox<UserDTO> comboUsers = new ComboBox<>();
        comboUsers.setWidth("100%");
        comboUsers.setPlaceholder(Constants.COLLECTION_OF_PLACEHOLDER);
        comboUsers.setItems(userService.findAll());
        Button cancel = new Button(Constants.CANCEL, e -> { dialogChooseUser.close(); });
        Button confirm = new Button(Constants.CONTINUE, e -> {
            if(comboUsers.getValue() != null) {
                dialogChooseUser.close();
                dialog.removeAll();
                dialog.getFooter().removeAll();
                DiskDTO diskDTO = new DiskDTO();
                diskDTO.setUserId(comboUsers.getValue().getId());
                DiskCRUDView diskCRUDView = new DiskCRUDView(diskService, statusService, Constants.FORM_CREATE, diskDTO);
                dialog.add(diskCRUDView);
                dialog.setHeaderTitle(Constants.DISK_CREATE_TITLE);
                Button cancelButton = new Button(Constants.CANCEL, clickEvent -> { dialog.close(); });
                Button saveButton = new Button(Constants.SAVE);
                saveButton.addClickListener(clickEvent -> {
                    DiskDTO newDiskDTO = diskCRUDView.getDiskDTO();
                    if(validateDisk(newDiskDTO)) {
                        try {
                            diskService.insert(newDiskDTO);
                            dialog.close();
                            UserDTO userDTO = new UserDTO();
                            BeanUtils.copyProperties(userService.getById(diskDTO.getUserId()), userDTO);
                            collectionOfCombo.setValue(userDTO);
                            isSearchButton = true;
                            search(null);
                            Constants.NOTIFICATION_DB_SUCCESS();
                        } catch (Exception e2) {
                            Constants.NOTIFICATION_DB_ERROR(e2);
                        }
                    } else {
                        Constants.NOTIFICATION_DB_VALIDATION_DISK_ERROR();
                    }
                });
                saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                dialog.getFooter().add(cancelButton, saveButton);
                dialog.setWidth("70%");
                dialog.setMaxHeight("80%");
                dialog.open();
            } else {
                Constants.NOTIFICATION_DB_VALIDATION_INDEX_DISKS_ERROR();
            }
        });
        confirm.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialogChooseUser.add(comboUsers);
        dialogChooseUser.getFooter().add(cancel, confirm);
        dialogChooseUser.open();
    }

    private void editDisk(DiskDTO diskDTO) {
        DiskCRUDView diskCRUDView = new DiskCRUDView(diskService, statusService, Constants.FORM_UPDATE, diskDTO);
        dialog.removeAll();
        dialog.getFooter().removeAll();
        dialog.add(diskCRUDView);
        dialog.setHeaderTitle(Constants.EDIT);
        Button cancelButton = new Button(Constants.CANCEL, e -> { dialog.close(); });
        Button removeButton = new Button(new Icon(VaadinIcon.TRASH));
        removeButton.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                removeDisk(diskDTO);
            };
        });
        Button saveButton = new Button(Constants.SAVE);
        saveButton.addClickListener(clickEvent -> {
            DiskDTO newDiskDTO = diskCRUDView.getDiskDTO();
            if(validateDisk(newDiskDTO)) {
                try {
                    diskService.update(newDiskDTO);
                    dialog.close();
                    isSearchButton = true;
                    search(null);
                    Constants.NOTIFICATION_DB_SUCCESS();
                } catch (Exception e2) {
                    Constants.NOTIFICATION_DB_ERROR(e2);
                }
            } else {
                Constants.NOTIFICATION_DB_VALIDATION_DISK_ERROR();
            }

        });
        removeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout hlFooter1 = new HorizontalLayout(removeButton);
        HorizontalLayout hlFooter2 = new HorizontalLayout(cancelButton, saveButton);
        HorizontalLayout hlFooter = new HorizontalLayout(hlFooter1, hlFooter2);
        hlFooter.setWidth("100%"); hlFooter1.setWidth("100%");
        hlFooter.setFlexGrow(1, hlFooter1);
        hlFooter.setMargin(false);
        dialog.getFooter().add(hlFooter);
    }

    private void readDisk(DiskDTO diskDTO) {
        DiskCRUDView diskCRUDView = new DiskCRUDView(diskService, statusService, Constants.FORM_READ, diskDTO);
        dialog.removeAll();
        dialog.add(diskCRUDView);
        dialog.setHeaderTitle(Constants.DETAIL);
    }

    private void removeDisk(DiskDTO diskDTO) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setConfirmDialogListener(new ConfirmDialogInterface() {
            @Override
            public void confirmDialogListener() {
                try {
                    diskService.remove(diskDTO);
                    dialog.close();
                    isSearchButton = true;
                    search(null);
                } catch (Exception e) {
                    Constants.NOTIFICATION_DB_ERROR(e);
                }
                confirmDialog.close();
                dialog.close();
            }
        });
        confirmDialog.open();
    }

    @Override
    public void onComponentEvent(ItemClickEvent<DiskDTO> diskDTOItemClickEvent) {
        dialog.removeAll();
        dialog.getFooter().removeAll();
        DiskDTO diskDTO = diskService.findByID(diskDTOItemClickEvent.getItem().getId());
        dialog.setHeaderTitle(Constants.DETAIL);
        readDisk(diskDTO);
        Button editButton = new Button(Constants.EDIT, e -> {
            editDisk(diskDTOItemClickEvent.getItem());
            dialog.getFooter().remove(e.getSource());
        });
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button(Constants.CLOSE, e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(editButton);
        dialog.setWidth("70%");
        dialog.setMaxHeight("80%");
        dialog.open();
    }

    private boolean validateDisk(DiskDTO diskDTO) {
        if(diskDTO.getDiskStatus() == null || diskDTO.getAuthor().isEmpty() ||
                diskDTO.getTitle().isEmpty() || diskDTO.getCoverStatus() == null ||
                diskDTO.getDiskStyleList().size() == 0 || diskDTO.getDiskGenreList().size() == 0 ||
                diskDTO.getLabel().isEmpty() || diskDTO.getPresumedValue() == null ||
                diskDTO.getReprint() == null || diskDTO.getYear().isEmpty()) {
            return false;
        }
        return true;
    }

}