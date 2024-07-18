import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This program demonstrates a client socket application that connects to
 * a web server and send a HTTP HEAD request.
 *
 * https://www.codejava.net/java-se/networking/java-socket-client-examples-tcp-ip
 */
public class HttpClient {

    public static void main(String[] args) {
        String inputStr;

        // add scanner if missing args
        if (args.length < 1) {
            System.out.print("Enter a URL: ");
            Scanner scanner = new Scanner(System.in);
            inputStr = scanner.nextLine();
        } else {
            inputStr = args[0];
        }

        URL url;

        try {
            url = new URL(inputStr);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return;
        }

        String hostname = url.getHost();
        int port = 80;

        try (Socket socket = new Socket(hostname, port)) {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            writer.println("HEAD " + url.getPath() + " HTTP/1.1");
            writer.println("Host: " + hostname);
            writer.println("User-Agent: Simple Http Client");
            writer.println("Accept: text/html");
            writer.println("Accept-Language: en-US");
            writer.println("Connection: close");
            writer.println();

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