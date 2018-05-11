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
package service;

import auth.AuthorizationFilter;
import auth.AuthorizationFilter.Role;
import auth.Secured;
import com.sun.istack.logging.Logger;
import interfaces.ILogic;
import java.util.List;
import java.util.concurrent.locks.Lock;
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
import logic.HubLogic;
import logic.LockLogic;
import logic.PassLogic;
import logic.ResidentialUnitLogic;
import model.dto.model.HubDTO;
import model.dto.model.LockDTO;
import model.dto.model.PassDTO;

@Path("/lock")
@Secured({Role.admin,Role.client})
@Produces(MediaType.APPLICATION_JSON)
public class LockService {

    private final ILogic lockLogic;

    public LockService() {
        this.lockLogic = new LockLogic();
    }
    
    @GET
    @Secured({Role.admin,Role.client})
    public List<LockDTO> all() {
        return lockLogic.findAll();
    }
    @POST
    @Secured({Role.admin})
    public Response add(LockDTO dto) {
        HubLogic hl = new HubLogic();       
        try {
            HubDTO he = hl.find(dto.getIdHub());
            lockLogic.add(dto);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Lock was added").build();                
        } catch (Exception e) {
            Logger.getLogger(PropertyService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We dont found the Hub with the Id, Please use a existing Id.").build();
        }        
    }

    @PUT
    @Secured({Role.admin})
    public LockDTO update(LockDTO dto) {
        return (LockDTO) lockLogic.update(dto);
    }
     @DELETE
    @Path("/{id}")
    @Secured({Role.admin})
    public Response delete(@PathParam("id") String id) {
        PassLogic pl = new PassLogic();
        List<PassDTO> lista = pl.findPassByLockId(id);
        try {
            for(PassDTO l : lista){
                pl.delete(l.getId());
            }
            lockLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Lock was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(ResidentialUnitService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}