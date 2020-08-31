package org.bonn.se.carlook.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.process.control.RegisterControl;
import org.bonn.se.carlook.process.control.exception.UserAlreadyRegisteredException;
import org.bonn.se.carlook.services.util.Globals;
import org.bonn.se.carlook.services.util.RegistrationResult;
import org.bonn.se.carlook.services.util.Views;

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
            UserDTO user = new UserDTO();

            user.setForename(tfForeName.getValue());
            user.setSurname(tfSurName.getValue());
            user.setEMail(tfEMail.getValue());
            user.setPassword(tfPassword.getValue());
            user.setPassword(tfPasswordConfirm.getValue());

            RegistrationResult<UserDTO> result = null;

            try{
                result = reg.registerUser(user);
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

            if(result.getResult()) {
                Notification notification = new Notification("Erfolg",
                        "Die Registration war erfolgreich!", Notification.Type.HUMANIZED_MESSAGE);

                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());

                UI.getCurrent().getSession().setAttribute(Globals.CURRENT_USER,
                        result.getRegisteredUserDTO());

                UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
            }
            else{
                List<RegistrationResult.FailureType> failList = result.getReasons();
                for (RegistrationResult.FailureType item : failList) {

                    Notification notification = new Notification("Fehler",
                            "Ein unbekannter Fehler ist aufgetreten, bitte wenden Sie sich an einen Administrator!", Notification.Type.ERROR_MESSAGE);

                    notification.setDelayMsec(5000);
                    notification.show(Page.getCurrent());
                }
            }
        });
    }
}
