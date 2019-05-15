package de.alpharogroup.csvtodb.mapper;

import org.mapstruct.Mapper;

import de.alpharogroup.csvtodb.entity.BrosEntity;
import de.alpharogroup.migration.dto.BroDto;

@Mapper(componentModel="spring")
public interface BrosEntityMapper {

	BrosEntity mapToEntity(BroDto bro);

}
