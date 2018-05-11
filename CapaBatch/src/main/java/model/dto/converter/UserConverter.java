/*
 * The MIT License
 *
 * Copyright 2018 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package model.dto.converter;

import interfaces.IConverter;
import java.util.ArrayList;
import java.util.List;
import model.dto.model.UserDTO;
import model.entity.UserEntity;

/**
 *
 * @author Dexcrash
 */
public class UserConverter implements IConverter<UserEntity, UserDTO>{
    
     public static UserConverter CONVERTER = new UserConverter();

    public UserConverter() {
    }

    @Override
    public UserDTO entityToDto(UserEntity entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public UserEntity dtoToEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    public List<UserDTO> listEntitiesToListDTOs(List<UserEntity> entities) {
        ArrayList<UserDTO> dtos = new ArrayList<>();
        for (UserEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    
    @Override
    public List<UserEntity> listDTOsToListEntities(List<UserDTO> dtos) {
        ArrayList<UserEntity> entities = new ArrayList<>();
        for (UserDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
