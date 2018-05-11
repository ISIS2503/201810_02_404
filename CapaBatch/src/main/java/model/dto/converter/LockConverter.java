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
import model.dto.model.LockDTO;
import model.entity.LockEntity;
import java.util.ArrayList;
import java.util.List;


public class LockConverter implements IConverter<LockEntity, LockDTO>{

    public static LockConverter CONVERTER = new LockConverter();

    public LockConverter() {
    }

    @Override
    public LockDTO entityToDto(LockEntity entity) {
       LockDTO dto = new LockDTO();
        dto.setId(entity.getId());
        dto.setIdHub(entity.getIdHub());
        return dto;
    }

    @Override
    public LockEntity dtoToEntity(LockDTO dto) {
        LockEntity entity = new LockEntity();        
        entity.setId(dto.getId());
        entity.setIdHub(dto.getIdHub());
        return entity;
    }

    @Override
    public List<LockDTO> listEntitiesToListDTOs(List<LockEntity> entities) {
        ArrayList<LockDTO> dtos = new ArrayList<>();
        for (LockEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<LockEntity> listDTOsToListEntities(List<LockDTO> dtos) {
        ArrayList<LockEntity> entities = new ArrayList<>();
        for (LockDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
