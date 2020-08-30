package org.bonn.se.carlook.model.factory;

import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.model.objects.entity.User;

public class UserFactory{

    private UserFactory(){}

    public static User createEntity() {
        return new User();
    }

    public static UserDTO createDTO() {
        return new UserDTO();
    }

    public static User createEntityFromDTO(UserDTO dto) {
        User userEntity = new User();

        userEntity.setForename(dto.getForename());
        userEntity.setSurname(dto.getSurname());
        userEntity.setEMail(dto.getEMail());
        userEntity.setPassword(dto.getPassword());

        return userEntity;
    }

    public static UserDTO createDTOFromEntity(User entity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setForename(entity.getForename());
        userDTO.setSurname(entity.getSurname());
        userDTO.setEMail(entity.getEMail());
        userDTO.setPassword(entity.getPassword());

        return userDTO;
    }
}