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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import model.dto.model.PassDTO;
import mqtt.publisher.MqttPublisher;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 *
 * @author jd.correa
 */
@Path("/password")
@Consumes(MediaType.APPLICATION_JSON)
@Secured({Role.client})
public class PasswordService {
    
    private final static String ADD_COMMAND = "ADD_PASSWORD";
    private final static String CHANGE_COMMAND = "CHANGE_PASSWORD";
    private final static String DELETE_COMMAND = "DELETE_PASSWORD";
    private final static String DELETE_ALL_COMMAND = "DELETE_ALL_PASSWORDS";    
    private final static String SEPARATOR = ";";
    private final static String END = "-";
    private final static String TOPIC = "clave.apto1";
    
    public PasswordService(){
    }
    
    @POST
    public String addPassword(PassDTO pass) throws MqttException {
        String message = ADD_COMMAND
                + SEPARATOR 
                + pass.getIndex()
                + SEPARATOR 
                + pass.getNuevaPass() 
                + END;
        new MqttPublisher("apto01")
                .sendMessage(TOPIC, message)
                .disconnect();
        return pass.getNuevaPass();
    }
    
    @PUT
    public String changePassword(PassDTO pass) throws MqttException {
        String message = CHANGE_COMMAND
                + SEPARATOR 
                + pass.getIndex()
                + SEPARATOR 
                + pass.getNuevaPass() 
                + END;
        new MqttPublisher("apto01")
                .sendMessage(TOPIC, message)
                .disconnect();
        return pass.getNuevaPass();
    }
    
    @DELETE
    public String deletePassword(PassDTO pass) throws MqttException {
        String message = DELETE_COMMAND
                + SEPARATOR 
                + pass.getIndex()
                + END;
        new MqttPublisher("apto01")
                .sendMessage(TOPIC, message)
                .disconnect();
        return "200";
    }
    
    @DELETE
    @Path("/all")
    public String deleteAllPassword() throws MqttException {
        String message = DELETE_ALL_COMMAND
                + END;
        new MqttPublisher("apto01")
                .sendMessage(TOPIC, message)
                .disconnect();
        return "200";
    }
}
