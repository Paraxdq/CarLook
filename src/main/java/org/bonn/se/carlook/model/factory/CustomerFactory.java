package org.bonn.se.carlook.model.factory;

import org.bonn.se.carlook.model.objects.dto.CustomerDTO;
import org.bonn.se.carlook.model.objects.entity.Customer;
import org.bonn.se.carlook.model.objects.entity.User;

public class CustomerFactory {
    public static Customer createEntity() {
        return new Customer();
    }

    public static CustomerDTO createDTO() {
        return new CustomerDTO();
    }

    public static Customer createEntityFromDTO(CustomerDTO dto) {
        return null;
    }

    public static CustomerDTO createDTOFromEntity(Customer entity) {
        return null;
    }

    public static Customer createEntityFromUserEntity(User userEntity) {
        Customer customer = createEntity();

        customer.setUserId(userEntity.getUserId());
        customer.setEMail(userEntity.getEMail());
        customer.setPassword(userEntity.getPassword());
        customer.setForename(userEntity.getForename());
        customer.setSurname(userEntity.getSurname());

        return customer;
    }
}
