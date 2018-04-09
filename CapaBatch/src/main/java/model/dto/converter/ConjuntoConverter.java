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

import interfaces.IConjuntoConverter;
import model.dto.model.ConjuntoDTO;
import model.entity.ConjuntoEntity;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import model.dto.model.InmuebleDTO;
import model.entity.InmuebleEntity;

/**
 *
 * @author ca.mendoza968
 */
public class ConjuntoConverter implements IConjuntoConverter {

    public static IConjuntoConverter CONVERTER = new ConjuntoConverter();

    public ConjuntoConverter() {
    }

    @Override
    public ConjuntoDTO entityToDto(ConjuntoEntity entity) {
        ConjuntoDTO dto = new ConjuntoDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        if (entity.getInmuebles() != null)
            dto.setInmuebles(InmuebleConverter.CONVERTER.listEntitiesToListDTOs(entity.getInmuebles()));
        return dto;
    }

    @Override
    public ConjuntoEntity dtoToEntity(ConjuntoDTO dto) {
        ConjuntoEntity entity = new ConjuntoEntity();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        if (dto.getInmuebles() != null)
            entity.setInmuebles(InmuebleConverter.CONVERTER.listDTOsToListEntities(dto.getInmuebles()));
        return entity;
    }

    @Override
    public List<ConjuntoDTO> listEntitiesToListDTOs(List<ConjuntoEntity> entities) {
        ArrayList<ConjuntoDTO> dtos = new ArrayList<>();
        for (ConjuntoEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    @Override
    public List<ConjuntoEntity> listDTOsToListEntities(List<ConjuntoDTO> dtos) {
        ArrayList<ConjuntoEntity> entities = new ArrayList<>();
        for (ConjuntoDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }
}
