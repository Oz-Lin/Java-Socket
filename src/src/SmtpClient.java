import java.net.*;
import java.io.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * This program demonstrates a socket client program that talks to a SMTP server.
 *
 * @author www.codejava.net
 */
//public class SmtpClient {
//
//    public static void main(String[] args) {
//
//        String hostname = "smtp.gmail.com";
//        int port = 25;
//
//        try (Socket socket = new Socket(hostname, port)) {
//
//            InputStream input = socket.getInputStream();
//
//            OutputStream output = socket.getOutputStream();
//            PrintWriter writer = new PrintWriter(output, true);
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//
//            String line = reader.readLine();
//            System.out.println(line);
//
//            writer.println("helo " + hostname);
//
//            line = reader.readLine();
//            System.out.println(line);
//
//            writer.println("quit");
//            line = reader.readLine();
//            System.out.println(line);
//
//        } catch (UnknownHostException ex) {
//
//            System.out.println("Server not found: " + ex.getMessage());
//
//        } catch (IOException ex) {
//
//            System.out.println("I/O error: " + ex.getMessage());
//        }
//    }
//}




/**
 * This program demonstrates a socket client program that talks to a SMTP server.
 * It connects to Gmail's SMTP server using TLS encryption on port 587 and performs authentication.
 * Replace "your-email@gmail.com" and "your-password" with your actual Gmail account credentials.
 * Make sure to enable "Allow less secure apps" or generate an app password if you have 2-Step Verification enabled.
 * For more information, refer to Gmail's documentation on SMTP: https://developers.google.com/gmail/imap/smtp
 * Note: This code requires JavaMail API to be added to the classpath.
 * JavaMail API can be downloaded from: https://javaee.github.io/javamail/
 * Add the `javax.mail.jar` file to your project's dependencies or build path.
 *
 * @author www.codejava.net
 */
public class SmtpClient {

    public static void main(String[] args) {
        final String username = "your-email@gmail.com";
        final String password = "your-password";

        String hostname = "smtp.gmail.com";
        int port = 587;

        try (SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(hostname, port)) {

            InputStream input = socket.getInputStream();

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line = reader.readLine();
            System.out.println(line);

            writer.println("HELO " + hostname);
            line = reader.readLine();
            System.out.println(line);

            // Start TLS encryption
            writer.println("STARTTLS");
            line = reader.readLine();
            System.out.println(line);

            socket.startHandshake();

            // Perform authentication
            writer.println("EHLO " + hostname);
            line = reader.readLine();
            System.out.println(line);

            writer.println("AUTH LOGIN");
            line = reader.readLine();
            System.out.println(line);

            writer.println(base64Encode(username));
            line = reader.readLine();
            System.out.println(line);

            writer.println(base64Encode(password));
            line = reader.readLine();
            System.out.println(line);

            writer.println("QUIT");
            line = reader.readLine();
            System.out.println(line);

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    private static String base64Encode(String value) {
        return javax.xml.bind.DatatypeConverter.printBase64Binary(value.getBytes());
    }
}