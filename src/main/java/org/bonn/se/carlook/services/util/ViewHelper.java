package org.bonn.se.carlook.services.util;

import com.vaadin.ui.UI;
import org.bonn.se.carlook.model.objects.dto.UserDTO;

public class ViewHelper {
    public static void isUserLoggedIn() {
        if(UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER) == null)
            UI.getCurrent().getNavigator().navigateTo(Views.LOGIN);
    }

    public static boolean isUserSalesman(){
        return (UI.getCurrent().getSession().getAttribute(Globals.CURRENT_ROLE) == Role.Salesman);
    }

    public static boolean isUserCustomer(){
        return (UI.getCurrent().getSession().getAttribute(Globals.CURRENT_ROLE) == Role.Customer);
    }

    public static UserDTO getLoggedInUserDTO(){
        return (UserDTO) (UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER));
    }
}
