package com.cutalab.cutalab2.views.admin.aba;

import com.cutalab.cutalab2.backend.dto.admin.aba.ABAPackageDTO;
import com.cutalab.cutalab2.backend.dto.admin.aba.ABASessionDTO;
import com.cutalab.cutalab2.backend.service.admin.aba.ABASessionService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin-aba-sessions", layout = MainLayout.class)
//@CssImport(value = "/css/styles.css")
@PageTitle(Constants.ABA_TITLE + " | " + Constants.APP_AUTHOR)
public class ABASessionsView extends Dialog {

    private final ABAPackageDTO abaPackageDTO;
    private final ABASessionService abaSessionService;
    private final Grid<ABASessionDTO> grid;
    private IntegerField hours;
    private DateTimePicker day;
    private Checkbox checkbox;
    private Dialog addSessiondialog;

    public ABASessionsView(ABAPackageDTO abaPackageDTO, ABASessionService abaSessionService) {
        this.abaPackageDTO = abaPackageDTO;
        this.abaSessionService = abaSessionService;
        setWidth("80%");
        setMaxHeight("80%");
        H2 title = new H2(Constants.ABA_PERIOD_PACKAGE_SESSIONS);
        Button addSessionButton = new Button(new Icon(VaadinIcon.PLUS));
        addSessionButton.addClickListener(this::onAddSessionView);
        addSessionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        grid = new Grid<>();
        grid.setSizeFull();
        grid.addColumn(ABASessionDTO::getFormattedDay).setHeader(Constants.ABA_PACKAGE_SESSION_GRID_1);
        grid.addColumn(ABASessionDTO::getHours).setHeader(Constants.ABA_PACKAGE_SESSION_GRID_2);
        grid.addColumn(ABASessionDTO::getFormattedIsOpen).setHeader(Constants.ABA_PACKAGE_SESSION_GRID_3);
        HorizontalLayout hl = new HorizontalLayout(title, addSessionButton);
        hl.add(title, addSessionButton);
        hl.setFlexGrow(1, title);
        hl.setAlignSelf(FlexComponent.Alignment.END, addSessionButton);
        add(hl, grid);
        setSizeFull();
        fillGrid();
    }

    private void fillGrid() {
        grid.setItems(abaSessionService.getAll(abaPackageDTO));
    }

    private void onAddSessionView(ClickEvent<Button> buttonClickEvent) {
        addSessiondialog = new Dialog();
        addSessiondialog.setWidth("60%");
        addSessiondialog.setMaxHeight("60%");
        hours = new IntegerField();
        hours.setLabel(Constants.ABA_HOURS_PACKAGE);
        hours.setStep(1);
        hours.setStepButtonsVisible(true);
        hours.setMin(0);
        hours.setMax(5000);
        hours.setValue(0);
        hours.setWidth("100%");
        day = new DateTimePicker(Constants.ABA_DAY_SESSION);
        day.setValue(LocalDateTime.now());
        day.setWidth("100%");
        checkbox = new Checkbox(Constants.ABA_IS_OPEN_PACKAGE);
        checkbox.setValue(Boolean.TRUE);
        checkbox.setWidth("100%");
        VerticalLayout vl = new VerticalLayout(day, hours, checkbox);
        vl.setWidth("100%");
        addSessiondialog.add(vl);
        //FOOTER
        Button addButton = new Button(Constants.SAVE, this::onAddSession);
        Button cancelButton = new Button(Constants.CANCEL, e -> { addSessiondialog.close(); });
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addSessiondialog.getFooter().add(cancelButton, addButton);
        //show
        addSessiondialog.open();
    }

    private void onAddSession(ClickEvent<Button> buttonClickEvent) {
        Integer sessionHours = hours.getValue();
        LocalDateTime sessionDay = day.getValue();
        Boolean isOpenSession = checkbox.getValue();
        if(sessionHours == null || sessionDay == null) {
            Constants.NOTIFICATION_DB_VALIDATION_ERROR();
        } else {
            ABASessionDTO sessionDTO = new ABASessionDTO();
            sessionDTO.setABAPackage(abaPackageDTO);
            sessionDTO.setDay(sessionDay);
            sessionDTO.setHours(sessionHours);
            sessionDTO.setOpen(isOpenSession);
            try {
                abaSessionService.insert(sessionDTO);
                addSessiondialog.close();
                fillGrid();
            } catch (Exception e) {
                Constants.NOTIFICATION_DB_ERROR(e);
            }
        }
    }

}