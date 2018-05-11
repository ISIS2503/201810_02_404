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
import model.dto.model.PassDTO;
import model.entity.PassEntity;

/**
 *
 * @author Dexcrash
 */
public class PassConverter implements IConverter<PassEntity, PassDTO>{
    
    
    public static PassConverter CONVERTER = new PassConverter();

    public PassConverter() {
    }

    @Override
    public PassDTO entityToDto(PassEntity entity) {
        PassDTO dto = new PassDTO();
        dto.setId(entity.getId());
        dto.setIdLock(entity.getIdLock());
        dto.setIdUser(entity.getIdUser());
        dto.setPassNumber(entity.getPassNumber());
        return dto;
    }

    @Override
    public PassEntity dtoToEntity(PassDTO dto) {
        PassEntity entity = new PassEntity();
        entity.setId(dto.getId());
        entity.setIdLock(dto.getIdLock());
        entity.setIdUser(dto.getIdUser());
        entity.setPassNumber(dto.getPassNumber());
        return entity;
    }

    @Override
    public List<PassDTO> listEntitiesToListDTOs(List<PassEntity> entities) {
        ArrayList<PassDTO> dtos = new ArrayList<>();
        for (PassEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    
    @Override
    public List<PassEntity> listDTOsToListEntities(List<PassDTO> dtos) {
        ArrayList<PassEntity> entities = new ArrayList<>();
        for (PassDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
