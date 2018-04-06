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
package logic;
import interfaces.IConjuntoLogic;
import java.util.LinkedList;
import static model.dto.converter.ConjuntoConverter.CONVERTER;
import model.dto.model.ConjuntoDTO;
import persistence.ConjuntoPersistence;
import java.util.List;
import java.util.UUID;
import model.dto.converter.InmuebleConverter;
import model.dto.model.InmuebleDTO;
import model.entity.ConjuntoEntity;
import model.entity.InmuebleEntity;

public class ConjuntoLogic implements IConjuntoLogic {

    private final ConjuntoPersistence persistence;
    private final InmuebleLogic inmuebleLogic;

    public ConjuntoLogic() {
        this.persistence = new ConjuntoPersistence();
        this.inmuebleLogic = new InmuebleLogic();
    }

    @Override
    public ConjuntoDTO add(ConjuntoDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        if (dto.getInmuebles() != null) {
            List<InmuebleDTO> inmueblesDTO = dto.getInmuebles();
            dto.setInmuebles(null);
            ConjuntoEntity entity = persistence.add(CONVERTER.dtoToEntity(dto));
            List<InmuebleEntity> inmuebles = new LinkedList<>();
            for (InmuebleDTO inmueble : inmueblesDTO) {
                inmuebles.add(InmuebleConverter.CONVERTER.dtoToEntity(inmuebleLogic.add(inmueble)));
            }
            entity.setInmuebles(inmuebles);
            return update(CONVERTER.entityToDto(entity));
        }
        return CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
    }

    @Override
    public ConjuntoDTO update(ConjuntoDTO dto) {
        ConjuntoDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public ConjuntoDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }

    @Override
    public List<ConjuntoDTO> all() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public Boolean delete(String id) {
        return persistence.delete(id);
    }
}