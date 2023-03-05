package com.cutalab.cutalab2.views.moments;

import com.cutalab.cutalab2.backend.dto.MomentDTO;
import com.cutalab.cutalab2.backend.service.MomentService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value="moments", layout = MainLayout.class)
@AnonymousAllowed
@PageTitle(Constants.MENU_MOMENTS + " | " + Constants.APP_AUTHOR)
public class MomentsView extends VerticalLayout {

    private MomentService momentService;

    public MomentsView(MomentService momentService) {
        this.momentService = momentService;
        H2 title = new H2(Constants.MENU_MOMENTS);
        Grid<MomentDTO> grid = new Grid<>(MomentDTO.class, false);
        grid.setClassNameGenerator(item -> "vaadin-grid-links");
        grid.addColumn(MomentDTO::getTitle);
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.setAllRowsVisible(true);
        grid.setWidth("100%");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.addItemClickListener(e -> {
            MomentDTO momentDTO = e.getItem();
            removeAll();
            add(new MomentDetailView(momentDTO));
        });
        grid.setItems(momentService.findAll());
        add(title, grid);
        setHorizontalComponentAlignment(Alignment.CENTER, title);
        setSizeFull();
    }
}
