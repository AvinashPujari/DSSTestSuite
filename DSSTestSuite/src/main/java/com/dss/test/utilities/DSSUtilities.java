/**
 * 
 */
package com.dss.test.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.reporters.jq.TestNgXmlPanel;

import com.dss.test.properties.DSSProperties;
import com.thoughtworks.selenium.condition.JUnitConditionRunner;

/**
 * ------- DSSUtilities ------- Author: QA-DART Created on: May 23, 2016 History
 * of Changes:
 */
public class DSSUtilities {

	public void sendEmailReport(String frmAddress, String toAddress, String subject, String message)
			throws AddressException, MessagingException {

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "webmail.bitwiseglobal.com");
		Session session = Session.getDefaultInstance(properties);

		// creates a new e-mail message
		try {
			Message msg = new MimeMessage(session);

			msg.setFrom(new InternetAddress(frmAddress));
			InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
			msg.setRecipients(Message.RecipientType.TO, toAddresses);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			// msg.setText(message);

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(message);

			// Create a multipar message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = DSSProperties.DESTINATION_FOLDER_NAME;
			DataSource source = new FileDataSource(filename);

			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("DSS_REGRESSION_REPORT.zip");
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			msg.setContent(multipart);

			// sends the e-mail
			Transport.send(msg);
			System.out.println("Email sent successfully...");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public void zipFolder(String srcFolder, String destZipFile) throws Exception {
		
		
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;

		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);
		
		addFolderToZip(DSSProperties.SOURCE_PATH, srcFolder, zip);
		zip.flush();
		zip.close();
	}

	public void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {

		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
		}
	}

	public void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
		
		
		File folder = new File(srcFolder);
		
		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
			}
		}
	}
	
	
	public String captureScrenshot(WebDriver driver, String screenshotName){
		
		
		try{
			
			TakesScreenshot ts = (TakesScreenshot)driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			
			String dest = "C:\\Screenshots\\"+screenshotName+".png";
			
			File destination = new File(dest);
			FileUtils.copyFile(source, destination);
			
			System.out.println("Screenshot Taken");
			
			return dest;
			
		} catch (Exception e){
			
			System.out.println("Exception while taking screenshot"+e.getMessage());
			return e.getMessage();
			
		}
		
		
		
	}
	
	public static String generateEmailid(String Market){
		
        String emailId;
        Random random = new Random();
        int randomnum = random.nextInt();

        DateFormat timeFormat = new SimpleDateFormat("ddMMMMyy");
        Date date = new Date();
        timeFormat.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        String currentdate = timeFormat.format(date);

        emailId = Market + "_" + currentdate + "_" + randomnum + "@test.com";

        return emailId;

	}
	
	
	 /*public static void main(String arg[]) throws Exception { 
		 
		 
		 DSSUtilities util = new DSSUtilities();
		 
		 util.zipFolder(DSSProperties.SOURCE_FOLDER, DSSProperties.DESTINATION_FOLDER_NAME);
		 util.sendEmailReport(DSSProperties.FromAddress, DSSProperties.ToAddress, DSSProperties.SubjectLine, DSSProperties.BodyMessage);
		 
		 
	 }*/
	
	
	
	

}
