package org.bonn.se.carlook.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.process.control.RegisterControl;
import org.bonn.se.carlook.process.control.exception.UserAlreadyRegisteredException;
import org.bonn.se.carlook.services.util.GlobalHelper;
import org.bonn.se.carlook.services.util.Globals;
import org.bonn.se.carlook.services.util.Role;
import org.bonn.se.carlook.services.util.Views;

public class InsertCarView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        final VerticalLayout vLayout = new VerticalLayout();
        vLayout.setMargin(true);

        VerticalLayout bodylayout = new VerticalLayout();
        Label welcomeLabel = new Label("Hier stellen Sie ihr Auto ein:");
        bodylayout.addComponent(welcomeLabel);

        vLayout.addComponent(bodylayout);

        this.addComponent(vLayout);

        //Label registerLabel = new Label("Gratis registrieren in weniger als 2 Minuten!");
        //vLayout.addComponent(registerLabel);

        FormLayout form = new FormLayout();

        TextField tfForeName = new TextField("Marke");
        tfForeName.setRequiredIndicatorVisible(true);
        form.addComponent(tfForeName);

        TextField tfSurName = new TextField("USW");
        tfSurName.setRequiredIndicatorVisible(true);
        form.addComponent(tfSurName);

        TextField tfEMail = new TextField("...");
        tfEMail.setRequiredIndicatorVisible(true);
        form.addComponent(tfEMail);

        PasswordField tfPassword = new PasswordField("Passwort");
        tfPassword.setRequiredIndicatorVisible(true);
        form.addComponent(tfPassword);

        Button registerButton = new Button("Registrieren");

        form.addComponent(registerButton);

        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.addComponentsAndExpand(form);
        vLayout.addComponent(hLayout);

    }
}