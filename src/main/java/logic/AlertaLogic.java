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
import interfaces.IAlertaLogic;
import java.util.LinkedList;
import static model.dto.converter.AlertaConverter.CONVERTER;
import model.dto.model.AlertaDTO;
import persistence.AlertaPersistence;
import java.util.List;
import java.util.UUID;
import model.dto.converter.InmuebleConverter;
import model.dto.model.InmuebleDTO;
import model.entity.InmuebleEntity;

public class AlertaLogic implements IAlertaLogic {

    
    private final AlertaPersistence persistence;
    private final InmuebleLogic inmuebleLogic;

    public AlertaLogic() {
        this.persistence = new AlertaPersistence();
        this.inmuebleLogic = new InmuebleLogic();
    }

    @Override
    public AlertaDTO add(AlertaDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        AlertaDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        InmuebleDTO inmueble = dto.getInmueble();
        if (inmueble.getAlarmas() == null) {
            List<AlertaDTO> alertas = new LinkedList<AlertaDTO>();
            alertas.add(result);
            inmueble.setAlarmas(alertas);
            inmuebleLogic.update(inmueble);
        } else {
            inmueble.getAlarmas().add(result);
            inmuebleLogic.update(inmueble);
        }
        return result;
    }

    @Override
    public AlertaDTO update(AlertaDTO dto) {
        AlertaDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }
    
    @Override
    public List<AlertaDTO> findByInmuebleId(String id) {
        return CONVERTER.listEntitiesToListDTOs(persistence.findByInmuebleId(id));
    }

    @Override
    public AlertaDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }

    @Override
    public List<AlertaDTO> all() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }

    @Override
    public Boolean delete(String id) {
        return persistence.delete(id);
    }
}