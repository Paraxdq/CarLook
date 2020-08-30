package org.bonn.se.carlook.model.factory;

import org.bonn.se.carlook.model.objects.dto.AbstractDTO;
import org.bonn.se.carlook.model.objects.entity.AbstractEntity;

public interface AbstractFactory<E extends AbstractEntity, D extends AbstractDTO> {

    public E createEntity();

    public D createDTO();

    public E createEntityFromDTO(D dto);

    public D createDTOFromEntity(E entity);
}
