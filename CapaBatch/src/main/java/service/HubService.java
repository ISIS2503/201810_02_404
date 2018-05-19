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
import logic.PropertyLogic;
import model.dto.model.HubDTO;
import model.dto.model.LockDTO;
import model.dto.model.PropertyDTO;

@Path("/hub")
@Produces(MediaType.APPLICATION_JSON)
public class HubService {

    private final ILogic hubLogic;

    public HubService() {
        this.hubLogic = new HubLogic();
    }

    @GET
    @Secured({Role.yale})
    public List<HubDTO> findAll() {
        return hubLogic.findAll();
    }

    @POST
    @Secured({Role.yale})
    public Response add(HubDTO dto) {
        PropertyLogic pl = new PropertyLogic();
        try {
            PropertyDTO pe = pl.find(dto.getIdProperty());
            hubLogic.add(dto);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Hub was added").build();
        } catch (Exception e) {
            Logger.getLogger(PropertyService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We dont found the PropertyId with the Id, Please use a existing Id.").build();
        }
    }

    @PUT
    @Secured({Role.yale})
    public HubDTO update(HubDTO dto) {
        return (HubDTO) hubLogic.update(dto);
    }

    @DELETE
    @Path("/{id}")
    @Secured({Role.yale})
    public Response delete(@PathParam("id") String id) {
        LockLogic ll = new LockLogic();
        List<LockDTO> lista = ll.findLockByHubId(id);
        try {
            for (LockDTO l : lista) {
                ll.delete(l.getId());
            }
            hubLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Hub was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(ResidentialUnitService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
