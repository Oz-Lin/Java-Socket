import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This program demonstrates a client socket application that connects
 * to a Whois server to get information about a domain name.
 * https://www.codejava.net/java-se/networking/java-socket-client-examples-tcp-ip
 */
public class WhoisClient {

    public static void main(String[] args) {
        String domainName;

        if (args.length < 1) {
            System.out.print("Enter a domain name: ");
            Scanner scanner = new Scanner(System.in);
            domainName = scanner.nextLine();
        } else {
            domainName = args[0];
        }

        String hostname = "whois.internic.net";
        int port = 43;

        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(domainName);

            InputStream input = socket.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}