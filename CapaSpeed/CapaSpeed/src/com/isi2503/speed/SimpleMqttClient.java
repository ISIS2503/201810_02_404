package com.isi2503.speed;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.http.client.ClientProtocolException;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class SimpleMqttClient implements MqttCallback {

	MqttClient myClient;
	MqttConnectOptions connOpt;

	static final String BROKER_URL = "tcp://localhost:8083";
	int x = 500;
	int z = 500;
	boolean silenciar = false;

	static final Boolean subscriber = true;
	static final Boolean publisher = false;
	public void setX(int y) {
		x=y;
		z=y;
	}

	/**
	 * 
	 * connectionLost
	 * This callback is invoked upon losing the MQTT connection.
	 * 
	 */
	@Override
	public void connectionLost(Throwable t) {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        time.setTimeZone(TimeZone.getTimeZone("UTC"));
        String times = time.format(new Date());
		String envio = "{\"id\":\""+555+"\", \"type\": 5"+", \"time\":\""+times+"\"}";
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					new Speed(envio);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
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
		MqttTopic topic = myClient.getTopic(myTopic);

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
		if (publisher) {
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
		}

		// disconnect
		try {
			while (subscriber);
			myClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String payload = new String(message.getPayload());
		if(!payload.contains("silenciar") && !silenciar) {
		String[] s = payload.split("::");
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        time.setTimeZone(TimeZone.getTimeZone("UTC"));
        String times = time.format(new Date());
		String envio = "{\"id\":\""+s[2]+"\", \"type\":"+s[1]+", \"time\":\""+times+"\"}";
		String envio2 = "{\"id\":\""+s[2]+"\", \"type\": 4" +", \"time\":\""+times+"\"}";
		System.out.println("-------------------------------------------------");
		System.out.println("| Topic: " + topic);
		System.out.println("| Message: " + payload);
		System.out.println("-------------------------------------------------");
		// Recibe el mensaje y lo envía al mock de manera asincrona
		new Thread(new Runnable() {
			@Override
			public void run() {
				setX(z);
				try {
					new Speed(envio);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		}
		else if (payload.contains("silenciar")) silenciar=!silenciar;
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}
}