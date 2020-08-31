package org.bonn.se.carlook.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.process.control.RegisterControl;
import org.bonn.se.carlook.process.control.exception.UserAlreadyRegisteredException;
import org.bonn.se.carlook.services.util.*;

import java.util.List;

public class MainView extends VerticalLayout implements View {
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        final VerticalLayout vLayout = new VerticalLayout();
        vLayout.setMargin(true);

        VerticalLayout bodylayout = new VerticalLayout();
        Label welcomeLabel = new Label("Herzlich Willkommen bei CarLook");
        bodylayout.addComponent(welcomeLabel);

        vLayout.addComponent(bodylayout);

        this.addComponent(vLayout);

        Label registerLabel = new Label("Gratis registrieren in weniger als 2 Minuten!");
        vLayout.addComponent(registerLabel);

        FormLayout form = new FormLayout();

        TextField tfForeName = new TextField("Vorname");
        tfForeName.setRequiredIndicatorVisible(true);
        form.addComponent(tfForeName);
        TextField tfSurName = new TextField("Nachname");
        tfSurName.setRequiredIndicatorVisible(true);
        form.addComponent(tfSurName);
        TextField tfEMail = new TextField("E-Mail");
        tfEMail.setRequiredIndicatorVisible(true);
        form.addComponent(tfEMail);
        PasswordField tfPassword = new PasswordField("Passwort");
        tfPassword.setRequiredIndicatorVisible(true);
        form.addComponent(tfPassword);
        PasswordField tfPasswordConfirm = new PasswordField("Passwort bestätigen");
        tfPasswordConfirm.setRequiredIndicatorVisible(true);
        form.addComponent(tfPasswordConfirm);

        Button registerButton = new Button("Registrieren");

        form.addComponent(registerButton);

        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.addComponentsAndExpand(form);
        vLayout.addComponent(hLayout);


        registerButton.addClickListener((Button.ClickListener) clickEvent -> {

            if (!tfPasswordConfirm.getValue().equals(tfPassword.getValue())) {
                Notification.show("Die Passwörter muss identisch sein!", Notification.Type.ERROR_MESSAGE);
                tfPassword.clear();
                tfPasswordConfirm.clear();
                return;
            }

            if (tfForeName.isEmpty() || tfSurName.isEmpty() || tfEMail.isEmpty() || tfPassword.isEmpty() || tfPasswordConfirm.isEmpty() ){
                Notification.show("Bitte füllen Sie alle mit '*' markierten Felder aus!", Notification.Type.ERROR_MESSAGE);
                return;
            }

            RegisterControl reg = RegisterControl.getInstance();
            UserDTO userDTO = new UserDTO();

            userDTO.setForename(tfForeName.getValue());
            userDTO.setSurname(tfSurName.getValue());
            userDTO.setEMail(tfEMail.getValue());
            userDTO.setPassword(tfPassword.getValue());
            userDTO.setPassword(tfPasswordConfirm.getValue());

            boolean result = false;

            try{
                result = reg.registerUser(userDTO);
            } catch (UserAlreadyRegisteredException e) {
                Notification notification= new  Notification("Fehler",
                        "Ein Nutzer mit dieser E-Mail Adresse ist bereits registriert!",
                        Notification.Type.ERROR_MESSAGE);

                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());

                //TODO TEST
                form.forEach(component -> components.clear());
                //tfForeName.clear(); tfSurName.clear(); tfEMail.clear(); tfPassword.clear(); tfPasswordConfirm.clear();

                return;
            }

            if(!result) {
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
    }
}
