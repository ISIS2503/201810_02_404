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
package service;

import auth.AuthorizationFilter.Role;
import logic.PropertyLogic;
import model.dto.model.PropertyDTO;
import com.sun.istack.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import auth.Secured;
import interfaces.ILogic;
import logic.HubLogic;
import logic.LockLogic;
import logic.PassLogic;
import logic.ResidentialUnitLogic;
import model.dto.model.HubDTO;
import model.dto.model.LockDTO;
import model.dto.model.ResidentialUnitDTO;
import model.entity.ResidentialUnitEntity;

@Path("/property")
@Produces(MediaType.APPLICATION_JSON)
public class PropertyService {

    private final ILogic propertyLogic;

    public PropertyService() {
        this.propertyLogic = new PropertyLogic();
    }

    @POST
    @Secured({Role.yale})
    public Response add(PropertyDTO dto) {          
        ResidentialUnitLogic rul = new ResidentialUnitLogic();       
        try {
            ResidentialUnitDTO rue = rul.find(dto.getIdResidentialUnit());
            propertyLogic.add(dto);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Property was added").build();                
        } catch (Exception e) {
            Logger.getLogger(PropertyService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We dont found the ResidentialUnit with the Id, Please use a existing Id.").build();
        }     
    }

    @PUT
    @Secured({Role.yale})
    public PropertyDTO update(PropertyDTO dto) {
        return (PropertyDTO) propertyLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    @Secured({Role.yale})
    public PropertyDTO find(@PathParam("id") String id) {
        return (PropertyDTO) propertyLogic.find(id);
    }

    @GET
    @Secured({Role.yale})
    public List<PropertyDTO> findAll() {
        return propertyLogic.findAll();
    }

    @DELETE
    @Path("/{id}")
    @Secured({Role.yale})
    public Response delete(@PathParam("id") String id) {
        HubLogic hl = new HubLogic();
        LockLogic ll = new LockLogic();      
        List<HubDTO> listaH = hl.findHubByPropertyId(id);
        
        try {           
                for(HubDTO h:listaH){
                    List<LockDTO> listaL = ll.findLockByHubId(h.getId());
                    for(LockDTO l:listaL){
                        
                        ll.delete(l.getId());
                    }
                    hl.delete(h.getId());
                }                
            
            propertyLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Property was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(ResidentialUnitService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
