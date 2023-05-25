package com.cutalab.cutalab2.views.admin.payments;

import com.cutalab.cutalab2.backend.service.admin.payments.PaymentService;
import com.cutalab.cutalab2.utils.Constants;
import com.cutalab.cutalab2.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@RolesAllowed("ROLE_ADMIN")
@Route(value="admin-payments", layout = MainLayout.class)
//@CssImport(value = "/css/styles.css")
@PageTitle(Constants.PAYMENTS + " | " + Constants.APP_AUTHOR)
public class AdminPayments extends VerticalLayout  {

    private final PaymentService paymentService;

    public AdminPayments(PaymentService paymentService) {
        this.paymentService = paymentService;
        H2 title = new H2(Constants.PAYMENTS);
        TabSheet tabSheet = new TabSheet();
        tabSheet.setSizeFull();
        tabSheet.add(Constants.PAYMENTS, new VerticalLayout());
        tabSheet.add(Constants.PAYMENTS_LOANS, new VerticalLayout());
        tabSheet.add(Constants.PAYMENTS_CURRENCIES, new AdminPaymentsCurrency(paymentService));
        tabSheet.add(Constants.PAYMENTS_REGITRIES, new AdminPaymentsRegistry(paymentService));
        tabSheet.add(Constants.PAYMENTS_SERVICES, new AdminPaymentsService(paymentService));
        add(title, tabSheet);
        setSizeFull();
    }
}
