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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
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
import logic.AlertLogic;
import logic.LockLogic;
import logic.PropertyLogic;
import logic.ResidentialUnitLogic;
import model.dto.model.AlertDTO;
import model.dto.model.LockDTO;
import model.dto.model.PropertyDTO;
import model.dto.model.ResidentialUnitDTO;

@Path("/alert")
@Produces(MediaType.APPLICATION_JSON)
public class AlertService {

    private final AlertLogic alertLogic;

    public AlertService() {
        this.alertLogic = new AlertLogic();
    }

    @GET
    @Secured({Role.yale})
    public List<AlertDTO> findAll() {
        return alertLogic.findAll();
    }

    @POST
    @Secured({Role.yale})
    public Response add(AlertDTO dto) {

        PropertyLogic pl = new PropertyLogic();
        ResidentialUnitLogic rl = new ResidentialUnitLogic();
        LockLogic ll = new LockLogic();
        try {
            PropertyDTO pe = pl.find(dto.getIdProperty());
            ResidentialUnitDTO re = rl.find(dto.getIdResidentialUnity());
            LockDTO le = ll.find(dto.getIdLock());

            alertLogic.add(dto);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Alert was added").build();
        } catch (Exception e) {
            Logger.getLogger(PropertyService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We dont found the Ids, Please use a existing Id.").build();
        }
    }

    @PUT
    @Secured({Role.yale})
    public AlertDTO update(AlertDTO dto) {
        return (AlertDTO) alertLogic.update(dto);
    }

    @DELETE
    @Path("/{id}")
    @Secured({Role.yale})
    public Response delete(@PathParam("id") String id) {
        try {
            alertLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Cerradura was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(AlertService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }

    @GET
    @Path("/neigh/{id}")
    @Secured({Role.yale})
    public List<AlertDTO> findByNeighboorHood(@PathParam("id") String id) {

        ResidentialUnitLogic re = new ResidentialUnitLogic();
        List<ResidentialUnitDTO> listaR = re.findByneighborhoodId(id);
        List<AlertDTO> lista1 = alertLogic.findByIdResidentialUnit(id);
        List<AlertDTO> lista2 = new ArrayList<>();
        Date hoy = new Date();
        for (AlertDTO a : lista1) {
            if (a.getDate().getMonth() == hoy.getMonth()) {
                lista2.add(a);
            }
        }
        return lista2;
    }

    @GET
    @Path("/prop/{id}")
    @Secured({Role.yale, Role.seguridad, Role.admin, Role.client})
    public List<AlertDTO> findByProperty(@PathParam("id") String id) {
        List<AlertDTO> lista1 = alertLogic.findByIdProperty(id);
        List<AlertDTO> lista2 = new ArrayList<>();
        Date hoy = new Date();
        for (AlertDTO a : lista1) {
            if (a.getDate().getMonth() == hoy.getMonth()) {
                lista2.add(a);
            }
        }
        return lista2;
    }

    @GET
    @Path("/resi/{id}")
    @Secured({Role.yale, Role.seguridad, Role.admin})
    public List<AlertDTO> findByResidentialUnit(@PathParam("id") String id) {
        List<AlertDTO> lista1 = alertLogic.findByIdResidentialUnit(id);
        List<AlertDTO> lista2 = new ArrayList<>();
        Date hoy = new Date();
        for (AlertDTO a : lista1) {
            if (a.getDate().getMonth() == hoy.getMonth()) {
                lista2.add(a);
            }
        }
        return lista2;
    }
}
