package org.bonn.se.carlook.model.factory;

import org.bonn.se.carlook.model.objects.dto.SalesmanDTO;
import org.bonn.se.carlook.model.objects.entity.Salesman;
import org.bonn.se.carlook.model.objects.entity.User;

public class SalesmanFactory{

    public static Salesman createEntity() {
        return new Salesman();
    }

    public static SalesmanDTO createDTO() {
        return new SalesmanDTO();
    }

    public static Salesman createEntityFromDTO(SalesmanDTO dto) {
        return null;
    }

    public static SalesmanDTO createDTOFromEntity(Salesman entity) {
        return null;
    }

    public static Salesman createEntityFromUserEntity(User userEntity) {
        Salesman salesman = createEntity();

        salesman.setUserId(userEntity.getUserId());
        salesman.setEMail(userEntity.getEMail());
        salesman.setPassword(userEntity.getPassword());
        salesman.setForename(userEntity.getForename());
        salesman.setSurname(userEntity.getSurname());

        return salesman;
    }
}
