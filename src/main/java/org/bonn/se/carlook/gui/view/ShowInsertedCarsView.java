package org.bonn.se.carlook.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import org.bonn.se.carlook.services.util.ViewHelper;

public class ShowInsertedCarsView extends VerticalLayout implements View {

    // Tablelle mit eingestellen Fahrzeugen von eingeloggtem Vertriebler ausgeben

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        ViewHelper.isUserLoggedIn();

    }

}
