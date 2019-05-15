package de.alpharogroup.csvtodb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import de.alpharogroup.csvtodb.entity.FriendsEntity;
import de.alpharogroup.migration.dto.FriendDto;

@Mapper(componentModel="spring")
public interface FriendsEntityMapper {
	@Mappings({ @Mapping(target = "id", source = "dto.id"), @Mapping(target = "firstname", source = "dto.firstname"),
			@Mapping(target = "lastname", source = "dto.lastname"), @Mapping(target = "city", source = "dto.city") })
	FriendsEntity mapToEntity(FriendDto dto);

}
