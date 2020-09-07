package org.bonn.se.carlook.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.services.util.ViewHelper;
import org.bonn.se.carlook.services.util.Views;

public class MainView extends VerticalLayout implements View {
    final VerticalLayout mainVLayout = new VerticalLayout();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        ViewHelper.isUserLoggedIn();

        mainVLayout.setMargin(true);

        UserDTO userDTO = ViewHelper.getLoggedInUserDTO();

        if(userDTO == null)
            return;

        VerticalLayout bodylayout = new VerticalLayout();
        Label welcomeLabel = new Label(
                String.format("Eingeloggter User: %s %s", userDTO.getForename(), userDTO.getSurname()));
        bodylayout.addComponent(welcomeLabel);

        this.addComponent(bodylayout);
        bodylayout.setWidthFull();

        if(ViewHelper.isUserSalesman())
            SalesmanView();
        else
            CustomerView();

        this.addComponent(mainVLayout);
    }

    private void SalesmanView(){
        Button insertCarButton = new Button("Neues Auto einstellen...");

        Button showInsertedCarsButton = new Button("Eingestellte Autos anzeigen...");

        mainVLayout.addComponent(insertCarButton);
        mainVLayout.addComponent(showInsertedCarsButton);

        insertCarButton.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(Views.INSERT_CAR);
        });

        showInsertedCarsButton.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(Views.SHOW_INSERTED_CARS);
        });
    }

    private void CustomerView(){
        Button insertCarButton = new Button("Autos durchsuchen...");

        Button showInsertedCarsButton = new Button("Reservierte Autos anzeigen...");

        mainVLayout.addComponent(insertCarButton);
        mainVLayout.addComponent(showInsertedCarsButton);

        insertCarButton.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(Views.SEARCHCAR);
        });

        showInsertedCarsButton.addClickListener((Button.ClickListener) clickEvent -> {
            UI.getCurrent().getNavigator().navigateTo(Views.SHOW_RESERVED_CARS);
        });
    }
}
