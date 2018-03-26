package lambda;
import java.io.IOException;
import java.util.logging.Level;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SimpleMqttClient implements MqttCallback {

	MqttClient myClient;
	MqttConnectOptions connOpt;

	static final String BROKER_URL = "tcp://localhost:8083";

	// the following two flags control whether this example is a publisher, a subscriber or both
	static final Boolean subscriber = true;
	static final Boolean publisher = false;

	/**
	 * 
	 * connectionLost
	 * This callback is invoked upon losing the MQTT connection.
	 * 
	 */
	@Override
	public void connectionLost(Throwable t) {
		System.out.println("Connection lost!");
	}

	/**
	 * 
	 * MAIN
	 * 
	 */
	public static void main(String[] args) {
		SimpleMqttClient smc = new SimpleMqttClient();
		smc.runClient();
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

	/**
	 * 
	 * runClient
	 * The main functionality of this simple example.
	 * Create a MQTT client, connect to broker, pub/sub, disconnect.
	 * 
	 */
	public void runClient() {
		// setup MQTT Client
		String clientID = "apto01";
		connOpt = new MqttConnectOptions();

		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);

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
		String myTopic = "alerta.apto1";

		// subscribe to topic if subscriber
		if (subscriber) {
			try {
				int subQoS = 0;
				myClient.subscribe(myTopic, subQoS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// publish messages if publisher
		/*if (publisher) {
			for (int i=1; i<=10; i++) {
				String pubMsg = "{\"pubmsg\":" + i + "}";
				int pubQoS = 0;
				MqttMessage message = new MqttMessage(pubMsg.getBytes());
				message.setQos(pubQoS);
				message.setRetained(false);

				// Publish the message
				System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
				MqttDeliveryToken token = null;
				try {
					// publish message to broker
					token = topic.publish(message);
					// Wait until the message has been delivered to the broker
					token.waitForCompletion();
					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}*/

		// disconnect
		try {
			// wait to ensure subscribed messages are delivered
			while (subscriber);
			myClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String payload = new String(message.getPayload());
		System.out.println("-------------------------------------------------");
		System.out.println("| Topic:" + topic);
		System.out.println("| Message: " + payload);
		System.out.println("-------------------------------------------------");
		// Recibe el mensaje y lo envía al mock de manera asincrona
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new Speed(payload);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		// Persiste la información, ojalá de manera asincrona
		new Thread(new Runnable() {
			@Override
			public void run() {
				//new Batch(payload);
			}
		});
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {

	}
}