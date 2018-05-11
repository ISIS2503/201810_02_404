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
package mqtt.publisher;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author miller
 */
public class MqttPublisher implements MqttCallback {
    
    private static final String BROKER_URL = "tcp://172.24.42.107:8083";
    
    private MqttClient client;
    private MqttConnectOptions connOpt;

    public MqttPublisher(String clientId) {
        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);
        // Connect to Broker
        try {
            client = new MqttClient(BROKER_URL, clientId);
            client.setCallback(this);
            client.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Connected to " + BROKER_URL);
    }
    
    public MqttPublisher sendMessage(String topic, String message) throws MqttException {
        client.publish(topic, new MqttMessage(message.getBytes()));
        return this;
    }
    
    public void disconnect() throws MqttException {
        client.disconnect();
    }
    
    @Override
    public void connectionLost(Throwable thrwbl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messageArrived(String string, MqttMessage mm) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("Mensaje enviado!");
    }
}
