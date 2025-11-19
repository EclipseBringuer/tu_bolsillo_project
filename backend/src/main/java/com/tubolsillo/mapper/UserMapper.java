package com.tubolsillo.mapper;

import com.tubolsillo.dto.UserDTO;
import com.tubolsillo.entity.Role;
import com.tubolsillo.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

/**
 * Conversor de la entidad User
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Convierte la entidad User en UserDTO
     *
     * @param user El usuario a transformar
     * @return El usuario ya transformado
     */
    UserDTO toUserDTO(User user);

    /**
     * Convierte un conjunto de roles en una lista en formato string
     *
     * @param roles El conjunto de roles a transformar
     * @return La lista de nombres de roles
     */
    default List<String> mapRolesToStringList(Set<Role> roles) {
        if (roles == null) return null;

        return roles.stream().map(Role::getName).toList();
    }
}
