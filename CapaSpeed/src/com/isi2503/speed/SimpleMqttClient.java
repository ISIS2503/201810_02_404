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

	static final String BROKER_URL = "tcp://172.24.42.107:8083";
	boolean silenciar = false;
	EmailSenderService email = new EmailSenderService();

	static final Boolean subscriber = true;
	static final Boolean publisher = false;
	static final String M2MIO_USERNAME = "isis2503";
	static final String M2MIO_PASSWORD_MD5 = "1234";

	/**
	 * 
	 * connectionLost
	 * This callback is invoked upon losing the MQTT connection.
	 * 
	 */
	@Override
	public void connectionLost(Throwable t) {
		email.sendEmail("En la cerradura de id:555 se ha detectado una alarma de tipo HUB fuera de línea.");
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
		connOpt.setKeepAliveInterval(20);
		connOpt.setUserName(M2MIO_USERNAME);
		connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());

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
		String myTopic = "info_ino.apto1";
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
		String[] s = payload.split("::");
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        time.setTimeZone(TimeZone.getTimeZone("UTC"));
        String times = time.format(new Date());
		System.out.println("-------------------------------------------------");
		System.out.println("| Topic: " + topic);
		System.out.println("| Message: " + payload);
		System.out.println("-------------------------------------------------");
		// Recibe el mensaje y lo envÃ­a al mock de manera asincrona
		if(!payload.contains("silenciar") && !silenciar && !s[1].contains("-1")) {
		if(s[1].contains("0")) {
			email.sendEmail("En la cerradura de id:"+s[2]+" se ha detectado una alarma de tipo Tiempo Máximo de Apertura Sobrepasado. Tiempo de alarma:"+times);
		}
		else if(s[1].contains("1")) {
			email.sendEmail("En la cerradura de id:"+s[2]+" se ha detectado una alarma de tipo Intentos de apertura sobrepasados.Tiempo de alarma:"+times);
		}
		else if(s[1].contains("2")) {
			email.sendEmail("En la cerradura de id:"+s[2]+" se ha detectado una alarma de tipo Sensor de Presencia Activado.Tiempo de alarma:"+times);
		}
		else if(s[1].contains("3")) {
			email.sendEmail("En la cerradura de id:"+s[2]+" se ha detectado una alarma de tipo Batería Agotada.Tiempo de alarma:"+times);
		}
		else if(s[1].contains("4")) {
			email.sendEmail("En la cerradura de id:"+s[2]+" se ha detectado una alarma de tipo Horario no Autorizado.Tiempo de alarma:"+times);
		}
		}
		else if (payload.contains("silenciar")) silenciar=!silenciar;
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub

	}
}