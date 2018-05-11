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
import interfaces.ILogic;
import java.util.List;
import java.util.UUID;
import static model.dto.converter.AlertConverter.CONVERTER;
import model.dto.model.AlertDTO;
import persistence.AlertPersistence;

public class AlertLogic implements ILogic<AlertDTO> {

    private final AlertPersistence persistence;

    public AlertLogic() {
        this.persistence = new AlertPersistence();
    }

    @Override
    public AlertDTO add(AlertDTO dto) {
        if (dto.getId() == null) {
            dto.setId(UUID.randomUUID().toString());
        }
        AlertDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }

    @Override
    public AlertDTO update(AlertDTO dto) {
        AlertDTO result = CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }
    
    @Override
    public AlertDTO find(String id) {
        return CONVERTER.entityToDto(persistence.find(id));
    }

    @Override
    public Boolean delete(String id) {
        return persistence.delete(id);
    }
    
    public List<AlertDTO> findAll() {
        return CONVERTER.listEntitiesToListDTOs(persistence.all());
    }
    
    public List<AlertDTO> allFromInmueble(String id) {
        return CONVERTER.listEntitiesToListDTOs(persistence.findByInmuebleId(id));
    }
    
        
    public List<AlertDTO> findByInmuebleId(String id) {
        return CONVERTER.listEntitiesToListDTOs(persistence.findByInmuebleId(id));
    }

}