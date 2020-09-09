package org.bonn.se.carlook.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.bonn.se.carlook.model.objects.dto.CarDTO;
import org.bonn.se.carlook.process.control.CarControl;
import org.bonn.se.carlook.process.control.exception.CarAlreadyReservedException;
import org.bonn.se.carlook.process.control.exception.DatabaseConnectionError;
import org.bonn.se.carlook.services.util.GlobalHelper;
import org.bonn.se.carlook.services.util.ViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchCarView extends VerticalLayout implements View {
    /*
    Für eine erste Anzeige von Suchtreffern sollten zumindest die Angaben
    „Marke“ (z.B. BMW), „Baujahr“ (z.B. 2002), „Beschreibung“ (kleiner Text wie „Gepflegtes
    Auto, unfallfrei mit vielen Extras“) ausgegeben werden. Eine Suche nach
    dem Prinzip „On-The-Fly“ wäre sehr schön, es reicht aber auch für einen ersten
    Prototyp eine konventionelle Suche mit einem Such-Button.“
     */

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        ViewHelper.isUserLoggedIn();

        final VerticalLayout vLayout = new VerticalLayout();
        vLayout.setMargin(true);

        VerticalLayout bodylayout = new VerticalLayout();
        Label welcomeLabel = new Label("Suchen Sie hier nach Autos:");
        bodylayout.addComponent(welcomeLabel);

        vLayout.addComponent(bodylayout);

        this.addComponent(vLayout);

        FormLayout searchForm = new FormLayout();

        TextField tfSearchCarBrand = new TextField("Marke");
        //tfSearchCarBrand.setRequiredIndicatorVisible(true);
        searchForm.addComponent(tfSearchCarBrand);
        TextField tfSearchCarConstruction = new TextField("Baujahr");
        //tfSearchCarConstruction.setRequiredIndicatorVisible(true);
        searchForm.addComponent(tfSearchCarConstruction);
        TextField tfSearchCarDescription = new TextField("Beschreibung");
        //tfSearchCarDescription.setRequiredIndicatorVisible(true);
        searchForm.addComponent(tfSearchCarDescription);

        Button searchButton = new Button("Suchen");

        searchForm.addComponent(searchButton);

        vLayout.addComponent(searchForm);

        Grid<CarDTO> grid = new Grid<>(CarDTO.class);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        HorizontalLayout dataGrid = new HorizontalLayout();
        vLayout.addComponent(dataGrid);

        searchButton.addClickListener((Button.ClickListener) clickEvent -> {

                String carBrand, description;
                int yearOfConstruction = 0;

                if (GlobalHelper.StringIsEmptyOrNull(tfSearchCarBrand.getValue()) &&
                        GlobalHelper.StringIsEmptyOrNull(tfSearchCarConstruction.getValue()) &&
                        GlobalHelper.StringIsEmptyOrNull(tfSearchCarDescription.getValue())){

                    Notification.show("Bitte tragen Sie Werte ein!", Notification.Type.ERROR_MESSAGE);
                    return;
                }

                try {
                    if(!GlobalHelper.StringIsEmptyOrNull(tfSearchCarConstruction.getValue()))
                        yearOfConstruction = Integer.parseInt(tfSearchCarConstruction.getValue());
                }
                catch (NumberFormatException e) {
                    Notification.show("Das Baujahr muss ein gültiges Jahr (z.B. 2000) sein!", Notification.Type.ERROR_MESSAGE);
                    return;
                }

                carBrand = tfSearchCarBrand.getValue();
                description = tfSearchCarDescription.getValue();

                List<CarDTO> cars = null;

                try {
                    cars = CarControl.getInstance().getFilteredCars(carBrand, yearOfConstruction, description);
                } catch (DatabaseConnectionError ex){
                    Notification notification= new  Notification("Fehler",
                            "Es konnte keine Verbindung zur Datenbank hergestellt werden!",
                            Notification.Type.ERROR_MESSAGE);

                    notification.setDelayMsec(5000);
                    notification.show(Page.getCurrent());
                }

                if(cars == null){
                    Label noReservedCars = new Label("Es wurden keine passenden Autos gefunden!");
                    vLayout.addComponent(noReservedCars);
                } else {

                    grid.setColumns("carBrand", "yearOfConstruction", "description");

                    grid.getColumn("carBrand").setCaption("Marke");
                    grid.getColumn("yearOfConstruction").setCaption("Baujahr");
                    grid.getColumn("description").setCaption("Beschreibung");

                    grid.setItems(cars);

                    dataGrid.addComponent(grid);
                }
        });

        Button reserveButton = new Button("Reservieren");

        reserveButton.addClickListener((Button.ClickListener) clickEvent2 -> {
            Set<CarDTO> selectedCar = grid.getSelectedItems();

            List<CarDTO> selected = new ArrayList<>(selectedCar);
            CarDTO carDTO = selected.get(0);

            try {
                CarControl.getInstance().ReserveCar(carDTO.getCarId());

                Notification notification = new Notification("Erfolg",
                        "Die Reservierung war erfolgreich!", Notification.Type.HUMANIZED_MESSAGE);

                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());
            } catch (CarAlreadyReservedException ex){
                Notification.show("Das ausgewählte Auto wurde bereits von Ihnen reserviert!", Notification.Type.ERROR_MESSAGE);
            } catch (DatabaseConnectionError ex){
                Notification notification= new  Notification("Fehler",
                        "Es konnte keine Verbindung zur Datenbank hergestellt werden!",
                        Notification.Type.ERROR_MESSAGE);

                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());
            }

        });

        vLayout.addComponent(reserveButton);
    }
}

