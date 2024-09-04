package com.abc.service;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {

	private static EmailService instance;

	private EmailService() {
	}

	public static EmailService getInstance() {
		if (instance == null) {
			synchronized (EmailService.class) {
				if (instance == null) {
					instance = new EmailService();
				}
			}
		}
		return instance;
	}

	public void sendEmail(String to, String subject, String content) throws MessagingException {
		String from = "lakshithadilan99@gmail.com";
		

		// SMTP server information
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		// Create session with authenticator
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		// Create email message
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);
		// message.setText(content); // Plain text
		message.setContent(content, "text/html");

		// Send the email
		Transport.send(message);
	}

	public static String getQueryResponseTemplate() {
		return """
				        <!DOCTYPE html>
				<html lang="en">
				<head>
				    <meta charset="UTF-8">
				    <meta name="viewport" content="width=device-width, initial-scale=1.0">
				    <title>Query Response</title>
				    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
				</head>
				<body style="background-color: #f8f9fa; font-family: Arial, sans-serif;">
				    <div style="max-width: 600px; margin: 20px auto; padding: 20px; background-color: #ffffff; border: 1px solid #dee2e6; border-radius: 8px;">
				        <div style="background-color: #000000; color: #ffffff; padding: 10px; border-radius: 8px 8px 0 0;">
				            <h1 style="font-size: 24px; margin: 0; text-align: center;">Query Response Notification</h1>
				        </div>
				        <div style="padding: 20px;">
				            <p style="font-size: 16px; color: #495057;">Dear {{username}},</p>
				            <p style="font-size: 16px; color: #495057;">Your recent query has been addressed by our support team. Please find the details below:</p>
				            <table style="width: 100%; border-collapse: collapse; margin-bottom: 20px;">
				                <thead style="background-color: #343a40; color: #ffffff;">
				                    <tr>
				                        <th style="padding: 10px; border: 1px solid #dee2e6;">Detail</th>
				                        <th style="padding: 10px; border: 1px solid #dee2e6;">Information</th>
				                    </tr>
				                </thead>
				                <tbody>
				                    <tr>
				                        <td style="padding: 10px; border: 1px solid #dee2e6;"><strong>Query ID</strong></td>
				                        <td style="padding: 10px; border: 1px solid #dee2e6;">{{queryId}}</td>
				                    </tr>
				                    <tr>
				                        <td style="padding: 10px; border: 1px solid #dee2e6;"><strong>Query</strong></td>
				                        <td style="padding: 10px; border: 1px solid #dee2e6;">{{queryText}}</td>
				                    </tr>
				                    <tr>
				                        <td style="padding: 10px; border: 1px solid #dee2e6;"><strong>Response</strong></td>
				                        <td style="padding: 10px; border: 1px solid #dee2e6;">{{responseText}}</td>
				                    </tr>
				                </tbody>
				            </table>
				            <p style="font-size: 16px; color: #495057;">We hope this resolves your concerns. If you have any further questions, feel free to reach out to us.</p>
				        </div>
				        <div style="text-align: center; margin-top: 30px; font-size: 14px; color: #6c757d;">
				            <p>Thank you for choosing ABC Restaurant. We are here to assist you!</p>
				        </div>
				    </div>
				</body>
				</html>

				 """;
	}
}
