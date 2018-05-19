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

import auth.AuthorizationFilter.Role;
import auth.Secured;
import com.sun.istack.logging.Logger;
import interfaces.ILogic;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import logic.LockLogic;
import logic.PassLogic;
import logic.ScheduleLogic;
import model.dto.model.LockDTO;
import model.dto.model.PassDTO;
import model.dto.model.PropertyDTO;
import mqtt.publisher.MqttPublisher;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author jd.correa
 */
@Path("/passwords")
@Consumes(MediaType.APPLICATION_JSON)
public class PassService {
    
    private final static String ADD_COMMAND = "ADD_PASSWORD";
    private final static String CHANGE_COMMAND = "CHANGE_PASSWORD";
    private final static String DELETE_COMMAND = "DELETE_PASSWORD";
    private final static String DELETE_ALL_COMMAND = "DELETE_ALL_PASSWORDS";    
    private final static String SEPARATOR = ";";    
    private final static String TOPIC = "info_hub.apto1";
    
    private final ILogic passLogic;

    public PassService(){
        this.passLogic = new PassLogic();
    }
    
    @POST
    @Secured({Role.client, Role.yale})
    public Response addPass(PassDTO pass) throws MqttException {
        
        LockLogic ll = new LockLogic();       
        Boolean r;
        try {
            LockDTO le = ll.find(pass.getIdLock());
            passLogic.add(pass);            
            r=true;
        } catch (Exception e) {
            Logger.getLogger(PropertyService.class).log(Level.WARNING, e.getMessage());
            r =false;
        }        
        
        String message = ADD_COMMAND
                + SEPARATOR 
                + pass.getId()
                + SEPARATOR 
                + pass.getPassNumber();
        new MqttPublisher("apto01")
                .sendMessage(TOPIC, message)
                .disconnect();
        
        if(r)return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Pass was added").build();                
        else return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We dont found the Lock with the Id, Please use a existing Id.").build();
    }
    
    @PUT
    @Secured({Role.client, Role.yale})
    public PassDTO changePass(PassDTO pass) throws MqttException {
        String message = CHANGE_COMMAND
                + SEPARATOR 
                + pass.getId()
                + SEPARATOR 
                + pass.getPassNumber();
        new MqttPublisher("apto01")
                .sendMessage(TOPIC, message)
                .disconnect();        
        
        return (PassDTO) passLogic.update(pass);
    }
    
    @DELETE
    @Secured({Role.client, Role.yale})
    public Response deletePass(PassDTO pass) throws MqttException {
        String message = DELETE_COMMAND
                + SEPARATOR 
                + pass.getId();
        new MqttPublisher("apto01")
                .sendMessage(TOPIC, message)
                .disconnect();
        try {
            passLogic.delete(pass.getId());
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Property was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(ResidentialUnitService.class).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }        
    }
    
    @DELETE
    @Path("/all")
    @Secured({Role.client, Role.yale})
    public String deleteAllPass() throws MqttException {
        String message = DELETE_ALL_COMMAND;
        new MqttPublisher("apto01")
                .sendMessage(TOPIC, message)
                .disconnect();
        return "200";
    }
 
    @GET
    @Path("/{id}")
    @Secured({Role.client, Role.yale})
    public PropertyDTO find(@PathParam("id") String id) {
        return (PropertyDTO) passLogic.find(id);
    }

    @GET
    @Secured({Role.client, Role.yale})
    public List<PropertyDTO> findAll() {
        return passLogic.findAll();
    }
}
