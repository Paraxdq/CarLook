package org.bonn.se.carlook.gui.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.bonn.se.carlook.gui.view.*;
import org.bonn.se.carlook.services.util.Views;

import javax.servlet.annotation.WebServlet;

// TODO: Jamma

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality!
 */
@Theme("mytheme")
@Title("Colla@HBRS")
public class MyUI extends UI {

    Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        navigator = new Navigator( this,this );

        navigator.addView(Views.MAIN, (Class<? extends View>) MainView.class);
        navigator.addView(Views.LOGIN, (Class<? extends View>) LoginRegisterView.class);
        navigator.addView(Views.INSERT_CAR, (Class<? extends View>) InsertCarView.class);
        navigator.addView(Views.SHOW_INSERTED_CARS, (Class<? extends View>) ShowInsertedCarsView.class);

        //"LoginView" als Default, nach erfolgreichem Login redirect erfolgt zur "MainView"
        UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }
}
