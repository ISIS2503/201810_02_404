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

import interfaces.IHUBConverter;
import model.dto.model.HUBDTO;
import model.entity.HUBEntity;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.dto.model.HUBDTO;
import model.entity.HUBEntity;

/**
 *
 * @author jd.correa
 */
public class HUBConverter implements IHUBConverter {

    public static IHUBConverter CONVERTER = new HUBConverter();

    public HUBConverter() {
    }

    @Override
    public HUBDTO entityToDto(HUBEntity entity) {
        HUBDTO dto = new HUBDTO();
        dto.setId(entity.getId());
        if (entity.getCerraduras() != null)
            dto.setCerraduras(CerraduraConverter.CONVERTER.listEntitiesToListDTOs(entity.getCerraduras()));
        return dto;
    }

    @Override
    public HUBEntity dtoToEntity(HUBDTO dto) {
        HUBEntity entity = new HUBEntity();
        entity.setId(dto.getId());
        if (dto.getCerraduras() != null)
            entity.setCerraduras(CerraduraConverter.CONVERTER.listDTOsToListEntities(dto.getCerraduras()));
        return entity;
    }

    @Override
    public List<HUBDTO> listEntitiesToListDTOs(List<HUBEntity> entities) {
        ArrayList<HUBDTO> dtos = new ArrayList<>();
        for (HUBEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<HUBEntity> listDTOsToListEntities(List<HUBDTO> dtos) {
        ArrayList<HUBEntity> entities = new ArrayList<>();
        for (HUBDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}

