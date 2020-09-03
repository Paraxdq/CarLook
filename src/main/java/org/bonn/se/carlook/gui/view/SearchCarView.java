package org.bonn.se.carlook.gui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;
import org.bonn.se.carlook.services.util.ViewHelper;

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

    }
}
