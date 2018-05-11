package com.isi2503.speed;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class EmailSenderService {
	private final Properties properties = new Properties();
	
	private String password;
 
	private Session session;
 
	private void init() {
 
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port","587");
		properties.put("mail.smtp.mail.sender","xuaxy9@gmail.com");
		properties.put("mail.smtp.user", "Yale");
		properties.put("mail.smtp.auth", "true");
 
		session = Session.getDefaultInstance(properties);
	}
	public static void main(String[] args) {
		EmailSenderService a = new EmailSenderService();
		a.sendEmail("Prueba");
	}
	public void sendEmail(String x){
 
		init();
		try{
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
			System.out.println("entra");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress("andsnake22@gmail.com"));
			message.setSubject("Prueba");
			message.setText(x);
			Transport t = session.getTransport("smtp");
			System.out.println("entra2");
			t.connect((String)properties.get("mail.smtp.host"),"xuaxy9@gmail.com", "CambiarAClaveEnPrivado");
			System.out.println("conecta");
			t.sendMessage(message, message.getAllRecipients());
			t.close();
			System.out.println("enviado");
		}catch (MessagingException me){
                        System.out.println("Error al enviar,"+me.getMessage()+me.getClass().getName());
			return;
		}
		
	}
 
}