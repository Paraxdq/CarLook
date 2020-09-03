package org.bonn.se.carlook.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.process.control.RegisterControl;
import org.bonn.se.carlook.process.control.exception.DatabaseConnectionError;
import org.bonn.se.carlook.process.control.exception.InvalidLoginData;
import org.bonn.se.carlook.process.control.exception.LoginControl;
import org.bonn.se.carlook.process.control.exception.UserAlreadyRegisteredException;
import org.bonn.se.carlook.services.util.*;

public class LoginRegisterView extends VerticalLayout implements View {
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        final VerticalLayout registerVLayout = new VerticalLayout();
        final VerticalLayout loginVLayout = new VerticalLayout();
        registerVLayout.setMargin(true);
        loginVLayout.setMargin(true);

        VerticalLayout bodylayout = new VerticalLayout();
        Label welcomeLabel = new Label("Herzlich Willkommen bei CarLook");
        bodylayout.addComponent(welcomeLabel);

        this.addComponent(bodylayout);
        bodylayout.setWidthFull();

        final HorizontalLayout mainHLayout = new HorizontalLayout();

        mainHLayout.addComponents(registerVLayout, loginVLayout);

        this.addComponent(mainHLayout);

        Label registerLabel = new Label("Gratis registrieren in weniger als 2 Minuten!");
        registerVLayout.addComponent(registerLabel);

        Label loginLabel = new Label("Bereits registriert? Hier einloggen!");
        loginVLayout.addComponent(loginLabel);

        FormLayout registerForm = new FormLayout();

        TextField tfRegisterForeName = new TextField("Vorname");
        tfRegisterForeName.setRequiredIndicatorVisible(true);
        registerForm.addComponent(tfRegisterForeName);
        TextField tfRegisterSurName = new TextField("Nachname");
        tfRegisterSurName.setRequiredIndicatorVisible(true);
        registerForm.addComponent(tfRegisterSurName);
        TextField tfRegisterEMail = new TextField("E-Mail");
        tfRegisterEMail.setRequiredIndicatorVisible(true);
        registerForm.addComponent(tfRegisterEMail);
        PasswordField tfRegisterPassword = new PasswordField("Passwort");
        tfRegisterPassword.setRequiredIndicatorVisible(true);
        registerForm.addComponent(tfRegisterPassword);
        PasswordField tfRegisterPasswordConfirm = new PasswordField("Passwort bestätigen");
        tfRegisterPasswordConfirm.setRequiredIndicatorVisible(true);
        registerForm.addComponent(tfRegisterPasswordConfirm);

        Button registerButton = new Button("Registrieren");

        registerForm.addComponent(registerButton);

        HorizontalLayout registerHLayout = new HorizontalLayout();
        registerHLayout.addComponentsAndExpand(registerForm);
        registerVLayout.addComponent(registerHLayout);

        registerButton.addClickListener((Button.ClickListener) clickEvent -> {

            if (!tfRegisterPasswordConfirm.getValue().equals(tfRegisterPassword.getValue())) {
                Notification.show("Die Passwörter muss identisch sein!", Notification.Type.ERROR_MESSAGE);
                tfRegisterPassword.clear();
                tfRegisterPasswordConfirm.clear();
                return;
            }

            if (tfRegisterForeName.isEmpty() || tfRegisterSurName.isEmpty() || tfRegisterEMail.isEmpty() || tfRegisterPassword.isEmpty() || tfRegisterPasswordConfirm.isEmpty() ){
                Notification.show("Bitte füllen Sie alle mit '*' markierten Felder aus!", Notification.Type.ERROR_MESSAGE);
                return;
            }

            RegisterControl reg = RegisterControl.getInstance();
            UserDTO userDTO = new UserDTO();

            userDTO.setForename(tfRegisterForeName.getValue());
            userDTO.setSurname(tfRegisterSurName.getValue());
            userDTO.setEMail(tfRegisterEMail.getValue());
            userDTO.setPassword(tfRegisterPassword.getValue());
            userDTO.setPassword(tfRegisterPasswordConfirm.getValue());

            boolean result = false;

            try{
                result = reg.registerUser(userDTO);
            } catch (UserAlreadyRegisteredException e) {
                Notification notification= new  Notification("Fehler",
                        "Ein Nutzer mit dieser E-Mail Adresse ist bereits registriert!",
                        Notification.Type.ERROR_MESSAGE);

                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());

                tfRegisterForeName.clear(); tfRegisterSurName.clear(); tfRegisterEMail.clear(); tfRegisterPassword.clear(); tfRegisterPasswordConfirm.clear();
                return;
            }

            if(result) {
                Notification notification = new Notification("Erfolg",
                        "Die Registration war erfolgreich!", Notification.Type.HUMANIZED_MESSAGE);

                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());

                //TODO Same for login
                UI.getCurrent().getSession().setAttribute(Globals.CURRENT_USER,
                        userDTO);

                UI.getCurrent().getSession().setAttribute(Globals.CURRENT_ROLE,
                        GlobalHelper.IsCompanyMember(userDTO.getEMail()) ? Role.Salesman : Role.Customer);

                UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
            }
            else{
                Notification notification = new Notification("Fehler",
                        "Ein unbekannter Fehler ist aufgetreten, bitte wenden Sie sich an einen Administrator!", Notification.Type.ERROR_MESSAGE);

                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());
            }
        });

        /*

        LOGIN

         */

        FormLayout loginForm = new FormLayout();

        TextField tfLoginEMail = new TextField("E-Mail");
        loginForm.addComponent(tfLoginEMail);
        PasswordField tfLoginPassword = new PasswordField("Passwort");
        loginForm.addComponent(tfLoginPassword);

        Button loginButton = new Button("Einloggen");

        loginForm.addComponent(loginButton);

        HorizontalLayout loginHLayout = new HorizontalLayout();
        loginHLayout.addComponentsAndExpand(loginForm);
        loginVLayout.addComponent(loginHLayout);

        loginButton.addClickListener((Button.ClickListener) clickEvent -> {

            if (tfLoginEMail.isEmpty() || tfLoginPassword.isEmpty() ){
                Notification.show("Bitte geben Sie Ihre E-Mail und Ihr Passwort an!", Notification.Type.ERROR_MESSAGE);
                return;
            }

            LoginControl login = LoginControl.getInstance();
            UserDTO userDTO = new UserDTO();

            userDTO.setEMail(tfLoginEMail.getValue());
            userDTO.setPassword(tfLoginPassword.getValue());

            try{
                userDTO = login.loginUser(userDTO);
            } catch (InvalidLoginData e) {
                Notification notification= new  Notification("Fehler",
                        "Diese Zugangsdaten sind leider nicht korrekt!",
                        Notification.Type.ERROR_MESSAGE);

                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());

                tfLoginEMail.clear(); tfLoginPassword.clear();
                return;
            } catch (DatabaseConnectionError e){
                Notification notification= new  Notification("Fehler",
                        "Es konnte keine Verbindung zur Datenbank hergestellt werden!",
                        Notification.Type.ERROR_MESSAGE);

                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());
                return;
            }

            //TODO Same for Register
            UI.getCurrent().getSession().setAttribute(Globals.CURRENT_USER,
                    userDTO);

            UI.getCurrent().getSession().setAttribute(Globals.CURRENT_ROLE,
                    GlobalHelper.IsCompanyMember(userDTO.getEMail()) ? Role.Salesman : Role.Customer);

            UI.getCurrent().getNavigator().navigateTo(Views.MAIN);

        });
    }
}
