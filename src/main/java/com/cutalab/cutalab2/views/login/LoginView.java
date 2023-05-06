package com.cutalab.cutalab2.views.login;

import com.cutalab.cutalab2.utils.Constants;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle(Constants.MENU_LOGIN)
@AnonymousAllowed
public class LoginView extends Div implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView() {
        addClassName("login-rich-content");
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.getElement().getThemeList().add("dark");
        loginForm.setAction("login");
        add(loginForm);
        loginForm.getElement().setAttribute("no-autofocus", "");
    }

    @Override
    public void onAttach(AttachEvent event){
        // view is loaded at this point. You can for example execute javascript which relies on certain elements being in the page
        UI.getCurrent().getPage().executeJs("document.getElementsByTagName('H5').innerHTML = 'Nome utente o password errati.'");
        UI.getCurrent().getPage().executeJs("document.querySelectorAll('p[value=\"Check that you have entered the correct username and password and try again.\"]').innerHTML = 'Verifica di aver inserito il nome utente e la password corretti e riprova.'");
        UI.getCurrent().getPage().executeJs("document.querySelectorAll('span[value=\"Log in\"]').innerHTML = 'Entra'");
        UI.getCurrent().getPage().executeJs("document.getElementById('label-vaadin-text-field-0').innerHTML = 'Nome utente'");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }

}