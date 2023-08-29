package com.cutalab.cutalab2.views.moments;

import com.cutalab.cutalab2.backend.dto.MomentDTO;
import com.cutalab.cutalab2.backend.service.MomentService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(value="moments", layout = MainLayout.class)
@AnonymousAllowed
@PageTitle(Constants.MENU_MOMENTS + " | " + Constants.APP_AUTHOR)
public class MomentsView extends VerticalLayout {

    private static final String TOOLTIP_PHOTO_GALLERY = "Galleria fotografica";
    private static final String TOOLTIP_COMPUTER = "Imprese con il personal computer";

    public MomentsView(MomentService momentService) {

        H2 title = new H2(Constants.MENU_MOMENTS);

        Image img1 = new Image("images/photo_gallery.png", TOOLTIP_PHOTO_GALLERY);
        Image img2 = new Image("images/computer.png", TOOLTIP_COMPUTER);
        img1.setClassName("icon-box");
        img2.setClassName("icon-box");

        Tooltip tooltip1 = Tooltip.forComponent(img1).withText(TOOLTIP_PHOTO_GALLERY).withPosition(Tooltip.TooltipPosition.BOTTOM);
        Tooltip tooltip2 = Tooltip.forComponent(img2).withText(TOOLTIP_COMPUTER).withPosition(Tooltip.TooltipPosition.BOTTOM);

        HorizontalLayout hl1 = new HorizontalLayout(img1, img2);

        hl1.setWidth("100%");

        //preparo photo-gallery
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

        //*****************************************************************************************

        Button indexButton = new Button("Torna all'indice", e-> {
            removeAll();
            add(title, hl1);
        });
        indexButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);

        img1.addClickListener(e -> {
            removeAll();
            add(title, indexButton, grid);
        });

        add(title, hl1);

        //*****************************************************************************************

        setHorizontalComponentAlignment(Alignment.CENTER, title, indexButton);
        setSizeFull();

    }


}

