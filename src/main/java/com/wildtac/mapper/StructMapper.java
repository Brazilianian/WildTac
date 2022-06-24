package com.wildtac.mapper;

import java.util.List;

public interface StructMapper<D, O> {
    List<O> fromDtoListToObjectList(List<D> dtoList);
    List<D> fromObjectListToDtoList(List<O> objectList);
    O fromDtoToObject(D dto);
    D fromObjectToDto(O object);
}
