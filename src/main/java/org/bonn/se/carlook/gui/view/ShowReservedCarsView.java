package org.bonn.se.carlook.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import org.bonn.se.carlook.services.util.ViewHelper;

public class ShowReservedCarsView extends VerticalLayout implements View {

    // Tablelle ausgeben, die Infos von DAO bekommt

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        ViewHelper.isUserLoggedIn();

    }

}
