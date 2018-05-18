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
import model.dto.model.AlertDTO;
import model.entity.AlertEntity;
import java.util.ArrayList;
import java.util.List;

public class AlertConverter implements IConverter<AlertEntity, AlertDTO>{

    public static AlertConverter CONVERTER = new AlertConverter();

    public AlertConverter() {
    }

    @Override    
    public AlertDTO entityToDto(AlertEntity entity) {
        AlertDTO dto = new AlertDTO();
        dto.setId(entity.getId());
        dto.setIdResidentialUnity(entity.getIdResidentialUnity());
        dto.setIdLock(entity.getIdLock());
        dto.setIdProperty(entity.getIdProperty());
        dto.setDate(entity.getDate());
        dto.setType(entity.getType());
        dto.setLatitud(entity.getLatitud());
        dto.setLongitud(entity.getLongitud());
        return dto;
    }

    @Override    
    public AlertEntity dtoToEntity(AlertDTO dto) {
        AlertEntity entity = new AlertEntity();
        entity.setId(dto.getId());
        entity.setIdResidentialUnity(dto.getIdResidentialUnity());
        entity.setIdLock(dto.getIdLock());
        entity.setIdProperty(dto.getIdProperty());
        entity.setDate(dto.getDate());
        entity.setType(dto.getType());
        entity.setLatitud(dto.getLatitud());
        entity.setLongitud(dto.getLongitud());
        return entity;
    }

    @Override    
    public List<AlertDTO> listEntitiesToListDTOs(List<AlertEntity> entities) {
        ArrayList<AlertDTO> dtos = new ArrayList<>();
        for (AlertEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override    
    public List<AlertEntity> listDTOsToListEntities(List<AlertDTO> dtos) {
        ArrayList<AlertEntity> entities = new ArrayList<>();
        for (AlertDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
