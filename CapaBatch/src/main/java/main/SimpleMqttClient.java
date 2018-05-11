/*
 * The MIT License
 *
 * Copyright 2016 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
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
package main;
 
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import logic.AlertLogic;
import model.dto.model.AlertDTO;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttPublish;
 
/**
 *
 * @author Luis Felipe Mendivelso Osorio <lf.mendivelso10@uniandes.edu.co>
 */
public class SimpleMqttClient implements MqttCallback {
 
    AlertLogic alertaLogic;
    MqttClient myClient;
    MqttConnectOptions connOpt;
 
    static final String BROKER_URL = "tcp://localhost:8083";
 
    // the following two flags control whether this example is a publisher, a subscriber or both
    static final Boolean subscriber = true;
    static final Boolean publisher = false;
 
    private final static String infoInoTopic = "info_ino.apto1";
    private final static String infoHubTopic = "info_hub.apto1";
    private final static int maxLostHealthChecks = 3;
    private final static long healthCheckFrequency = 100;
    private static HealthCheckCounter lostHealthChecks = new HealthCheckCounter();
 
    /**
     *
     * runClient The main functionality of this simple example. Create a MQTT
     * client, connect to broker, pub/sub, disconnect.
     *
     */
    @SuppressWarnings("empty-statement")
    public void runClient() {
        // setup MQTT Client
        String clientID = "apto01";
        connOpt = new MqttConnectOptions();
 
        connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);
 
        alertaLogic = new AlertLogic();
 
        // Connect to Broker
        try {
            myClient = new MqttClient(BROKER_URL, clientID);
            myClient.setCallback(this);
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }
 
        System.out.println("Connected to " + BROKER_URL);
 
        // setup topic
        // topics on m2m.io are in the form <domain>/<stuff>/<thing>
        // subscribe to topic if subscriber
        if (subscriber) {
            try {
                int subQoS = 0;
                myClient.subscribe(infoInoTopic, subQoS);
                // Start timer
                timer.start();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        // disconnect
        try {
            // wait to ensure subscribed messages are delivered
            while (subscriber);
            myClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
     
    static Thread timer = new Thread() {
        @Override
        public void run() {
            while (subscriber) {                
                try {
                    Thread.sleep(healthCheckFrequency);
                    lostHealthChecks.add();
               } catch (InterruptedException ex) {
                    Logger.getLogger(SimpleMqttClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };
 
    static Thread t1 = new Thread() {
        @Override
        public void run() {
            SimpleMqttClient smc = new SimpleMqttClient();
            smc.runClient();
        }
    };
 
    static Thread t2 = new Thread() {
        @Override
        public void run() {
            try {
                String webappDirLocation = "src/main/webapp/";
                String webPort = System.getenv("PORT");
                if (webPort == null || webPort.isEmpty()) {
                    webPort = "8080";
                }
                Server server = new Server(Integer.valueOf(webPort));
                WebAppContext root = new WebAppContext();
                root.setContextPath("/");
                root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
                root.setResourceBase(webappDirLocation);
                root.setParentLoaderPriority(true);
                server.setHandler(root);
                server.start();
                server.join();
            } catch (InterruptedException ex) {
                //
            } catch (Exception ex) {
                //
            }
        }
    };
 
    public static void main(String agrs[]) {
        t1.start();
        t2.start();
    }
 
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        final String payload = new String(message.getPayload());
        String[] data = payload.split("::");
        if (data.length == 2) {
            Date now = new Date();
            // TODO: Comprobar que está ingresando en los horarios dados
            myClient.publish(infoHubTopic, new MqttMessage(true ? "*".getBytes() : "&".getBytes()));
        } else {
            switch (data[1]) {
                case "-1":
                    lostHealthChecks.setCount(0);
                    break;
                default:
                    alertaLogic.add(                            
                            new AlertDTO(data[2], Integer.parseInt(data[1]), new Date(), "", "", "")                                                                               
                    );
                    break;
            }
        }
 
    }
 
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
 
    /**
     *
     * connectionLost This callback is invoked upon losing the MQTT connection.
     *
     * @param t
     */
    @Override
    public void connectionLost(Throwable t) {
        System.out.println("Connection lost!");
    }
}