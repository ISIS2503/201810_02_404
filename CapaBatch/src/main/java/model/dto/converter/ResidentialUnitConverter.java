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
import java.util.ArrayList;
import java.util.List;
import model.dto.model.ResidentialUnitDTO;
import model.entity.ResidentialUnitEntity;

/**
 *
 * @author Dexcrash
 */
public class ResidentialUnitConverter implements IConverter<ResidentialUnitEntity, ResidentialUnitDTO>{

    public static ResidentialUnitConverter CONVERTER = new ResidentialUnitConverter();

    public ResidentialUnitConverter() {
    }
    
    @Override
    public ResidentialUnitDTO entityToDto(ResidentialUnitEntity entity) {
        ResidentialUnitDTO dto = new ResidentialUnitDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCity(entity.getCity());
        dto.setNeighborhood(entity.getNeighborhood());
        return dto;
    }

    @Override
    public ResidentialUnitEntity dtoToEntity(ResidentialUnitDTO dto) {
        ResidentialUnitEntity entity = new ResidentialUnitEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCity(dto.getCity());
        entity.setNeighborhood(dto.getNeighborhood());
        return entity;
    }

    @Override
    public List<ResidentialUnitDTO> listEntitiesToListDTOs(List<ResidentialUnitEntity> entities) {
        ArrayList<ResidentialUnitDTO> dtos = new ArrayList<>();
        for (ResidentialUnitEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<ResidentialUnitEntity> listDTOsToListEntities(List<ResidentialUnitDTO> dtos) {
        ArrayList<ResidentialUnitEntity> entities = new ArrayList<>();
        for (ResidentialUnitDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
