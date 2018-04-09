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

import interfaces.IAlertaConverter;
import model.dto.model.AlertaDTO;
import model.entity.AlertaEntity;
import java.util.ArrayList;
import java.util.List;

public class AlertaConverter implements IAlertaConverter {

    public static IAlertaConverter CONVERTER = new AlertaConverter();

    public AlertaConverter() {
    }

    @Override
    public AlertaDTO entityToDto(AlertaEntity entity) {
        AlertaDTO dto = new AlertaDTO();
        dto.setId(entity.getId());
        dto.setDeviceId(entity.getDeviceId());
        dto.setTime(entity.getTime());
        dto.setType(entity.getType());
        return dto;
    }

    @Override
    public AlertaEntity dtoToEntity(AlertaDTO dto) {
        AlertaEntity entity = new AlertaEntity();
        entity.setId(dto.getId());
        entity.setDeviceId(dto.getDeviceId());
        entity.setTime(dto.getTime());
        entity.setType(dto.getType());
        return entity;
    }

    @Override
    public List<AlertaDTO> listEntitiesToListDTOs(List<AlertaEntity> entities) {
        ArrayList<AlertaDTO> dtos = new ArrayList<>();
        for (AlertaEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<AlertaEntity> listDTOsToListEntities(List<AlertaDTO> dtos) {
        ArrayList<AlertaEntity> entities = new ArrayList<>();
        for (AlertaDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
