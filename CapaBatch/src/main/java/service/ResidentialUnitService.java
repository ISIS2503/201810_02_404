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
import logic.PropertyLogic;
import logic.ResidentialUnitLogic;
import model.dto.model.PropertyDTO;
import model.dto.model.ResidentialUnitDTO;
import model.entity.PropertyEntity;

@Path("/residentialUnit")
@Produces(MediaType.APPLICATION_JSON)
public class ResidentialUnitService {

    private final ILogic residentialUnitLogic;

    public ResidentialUnitService() {
        this.residentialUnitLogic = new ResidentialUnitLogic();
    }

    @POST
    @Secured({Role.admin})
    public ResidentialUnitDTO add(ResidentialUnitDTO dto) {
        return (ResidentialUnitDTO) residentialUnitLogic.add(dto);
    }

    @PUT
    @Secured({Role.admin})
    public ResidentialUnitDTO update(ResidentialUnitDTO dto) {
        return (ResidentialUnitDTO) residentialUnitLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    @Secured({Role.admin,Role.seguridad})
    public ResidentialUnitDTO find(@PathParam("id") String id) {
        return (ResidentialUnitDTO) residentialUnitLogic.find(id);
    }

    @GET
    @Secured({Role.admin,Role.seguridad})
    public List<ResidentialUnitDTO> findAll() {
        return residentialUnitLogic.findAll();
    }

    @DELETE
    @Path("/{id}")
    @Secured({Role.admin})
    public Response delete(@PathParam("id") String id) {
        PropertyLogic pl = new PropertyLogic();
        List<PropertyDTO> lista = pl.findPropertyByResidentialUnityId(id);
        try {
            for(PropertyDTO p : lista){
                pl.delete(p.getId());
            }
            residentialUnitLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Conjunto was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(ResidentialUnitService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
