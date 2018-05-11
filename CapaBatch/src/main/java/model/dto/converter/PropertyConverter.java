/*
 * The MIT License
 *
 * Copyright 2017 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
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
import model.dto.model.PropertyDTO;
import model.entity.PropertyEntity;
import java.util.ArrayList;
import java.util.List;


public class PropertyConverter implements IConverter<PropertyEntity, PropertyDTO>{

    public static PropertyConverter CONVERTER = new PropertyConverter();

    public PropertyConverter() {
    }

    @Override
    public PropertyDTO entityToDto(PropertyEntity entity) {
        PropertyDTO dto = new PropertyDTO();
        dto.setId(entity.getId());
        dto.setIdResidentialUnit(entity.getIdResidentialUnit());
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    public PropertyEntity dtoToEntity(PropertyDTO dto) {
        PropertyEntity entity = new PropertyEntity();
        entity.setId(dto.getId());
        entity.setIdResidentialUnit(dto.getIdResidentialUnit());
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    public List<PropertyDTO> listEntitiesToListDTOs(List<PropertyEntity> entities) {
        ArrayList<PropertyDTO> dtos = new ArrayList<>();
        for (PropertyEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    
    @Override
    public List<PropertyEntity> listDTOsToListEntities(List<PropertyDTO> dtos) {
        ArrayList<PropertyEntity> entities = new ArrayList<>();
        for (PropertyDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
