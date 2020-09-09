package org.bonn.se.carlook.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.bonn.se.carlook.model.objects.dto.CarDTO;
import org.bonn.se.carlook.process.control.CarControl;
import org.bonn.se.carlook.process.control.exception.DatabaseConnectionError;
import org.bonn.se.carlook.services.util.ViewHelper;

import javax.xml.crypto.Data;
import java.util.List;

public class ShowReservedCarsView extends VerticalLayout implements View {

    // Tablelle ausgeben, die Infos von DAO bekommt

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        ViewHelper.isUserLoggedIn();

        final VerticalLayout vLayout = new VerticalLayout();
        vLayout.setMargin(true);

        VerticalLayout bodylayout = new VerticalLayout();
        Label welcomeLabel = new Label("Hier sehen Sie Ihre reservierten Autos:");
        bodylayout.addComponent(welcomeLabel);

        vLayout.addComponent(bodylayout);

        this.addComponent(vLayout);

        HorizontalLayout dataGrid = new HorizontalLayout();
        {
            List<CarDTO> cars = null;

            try {
                cars = CarControl.getInstance().getReservedCarsFromUser(ViewHelper.getLoggedInUserDTO());
            } catch(DatabaseConnectionError ex){
                Notification notification= new  Notification("Fehler",
                        "Es konnte keine Verbindung zur Datenbank hergestellt werden!",
                        Notification.Type.ERROR_MESSAGE);

                notification.setDelayMsec(5000);
                notification.show(Page.getCurrent());
            }

            if(cars == null){
                Label noReservedCars = new Label("Sie haben keine Autos reserviert!");
                vLayout.addComponent(noReservedCars);
                return;
            }else{
                Grid<CarDTO> grid = new Grid<>(CarDTO.class);

                grid.setColumns("carBrand", "yearOfConstruction", "description");

                grid.getColumn("carBrand").setCaption("Marke");
                grid.getColumn("yearOfConstruction").setCaption("Baujahr");
                grid.getColumn("description").setCaption("Beschreibung");

                grid.setItems(cars);

                dataGrid.addComponent(grid);
            }
        }

        vLayout.addComponent(dataGrid);

    }

}
