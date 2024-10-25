package project.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import project.demo.config.MapperConfig;
import project.demo.dto.user.UserRegistrationRequestDto;
import project.demo.dto.user.UserResponseDto;
import project.demo.dto.user.UserUpdateRequestDto;
import project.demo.model.User;

@Mapper(config = MapperConfig.class, uses = RoleMapper.class)
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRegistrationRequestDto userRegistrationRequestDto);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "fromRolesToRolesName")
    UserResponseDto toDto(User user);

    @Mappings({@Mapping(target = "id", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "username", ignore = true)})
    void updateUser(@MappingTarget User user, UserUpdateRequestDto userUpdateRequestDto);
}