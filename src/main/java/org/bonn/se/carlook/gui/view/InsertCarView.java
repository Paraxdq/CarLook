package org.bonn.se.carlook.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.bonn.se.carlook.model.factory.CarFactory;
import org.bonn.se.carlook.model.objects.dto.CarDTO;
import org.bonn.se.carlook.process.control.CarControl;
import org.bonn.se.carlook.services.util.*;

public class InsertCarView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        ViewHelper.isUserLoggedIn();

        final VerticalLayout vLayout = new VerticalLayout();
        vLayout.setMargin(true);

        VerticalLayout bodylayout = new VerticalLayout();
        Label welcomeLabel = new Label("Hier stellen Sie ihr Auto ein:");
        bodylayout.addComponent(welcomeLabel);

        vLayout.addComponent(bodylayout);

        this.addComponent(vLayout);

        FormLayout form = new FormLayout();

        TextField tfCarBrand = new TextField("Marke:");
        tfCarBrand.setRequiredIndicatorVisible(true);
        form.addComponent(tfCarBrand);

        TextField tfCarConstruction = new TextField("Baujahr:");
        tfCarConstruction.setRequiredIndicatorVisible(true);
        form.addComponent(tfCarConstruction);

        TextField tfCarDescription = new TextField("Beschreibung:");
        tfCarDescription.setRequiredIndicatorVisible(true);
        form.addComponent(tfCarDescription);

        Button insertButton = new Button("Einstellen");

        form.addComponent(insertButton);

        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.addComponentsAndExpand(form);
        vLayout.addComponent(hLayout);

        insertButton.addClickListener((Button.ClickListener) clickEvent -> {
            if(tfCarBrand.isEmpty() || tfCarConstruction.isEmpty() || tfCarDescription.isEmpty()){
                Notification.show("Bitte füllen Sie alle mit '*' markierten Felder aus!", Notification.Type.ERROR_MESSAGE);
                return;
            }

            try {
                Integer.parseInt(tfCarConstruction.getValue());
            }
            catch (NumberFormatException e) {
                Notification.show("Das Baujahr muss ein gültiges Jahr (z.B. 2000) sein!", Notification.Type.ERROR_MESSAGE);
                return;
            }

            CarDTO carDTO = CarFactory.createDTO();

            carDTO.setCarBrand(tfCarBrand.getValue());
            carDTO.setYearOfConstruction(Integer.parseInt(tfCarConstruction.getValue()));
            carDTO.setDescription(tfCarDescription.getValue());

            boolean result = CarControl.getInstance().AddNewCar(carDTO);

            if(result){
                Notification notification = new Notification("Erfolg",
                        "Das Auto wurde erfolgreich eingestellt!", Notification.Type.HUMANIZED_MESSAGE);

                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());

                UI.getCurrent().getNavigator().navigateTo(Views.MAIN);
            }
        });
    }
}