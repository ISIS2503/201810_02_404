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
import model.dto.model.ScheduleDTO;
import model.entity.ScheduleEntity;

/**
 *
 * @author Dexcrash
 */
public class ScheduleConverter implements IConverter<ScheduleEntity, ScheduleDTO>{
 
        
     public static ScheduleConverter CONVERTER = new ScheduleConverter();

    public ScheduleConverter() {
    }

    @Override
    public ScheduleDTO entityToDto(ScheduleEntity entity) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(entity.getId());
        dto.setIdLock(entity.getIdLock());
        dto.setIdUser(entity.getIdUser());
        dto.setMinHour(entity.getMinHour());
        dto.setMaxHour(entity.getMaxHour());        
        return dto;
    }

    @Override
    public ScheduleEntity dtoToEntity(ScheduleDTO dto) {
        ScheduleEntity entity = new ScheduleEntity();
        entity.setId(dto.getId());
        entity.setIdLock(dto.getIdLock());
        entity.setIdUser(dto.getIdUser());
        entity.setMinHour(dto.getMinHour());
        entity.setMaxHour(dto.getMaxHour());
        return entity;
    }

    @Override
    public List<ScheduleDTO> listEntitiesToListDTOs(List<ScheduleEntity> entities) {
        ArrayList<ScheduleDTO> dtos = new ArrayList<>();
        for (ScheduleEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }
    
    @Override
    public List<ScheduleEntity> listDTOsToListEntities(List<ScheduleDTO> dtos) {
        ArrayList<ScheduleEntity> entities = new ArrayList<>();
        for (ScheduleDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
    
}
