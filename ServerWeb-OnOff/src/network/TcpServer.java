/*
Implementazione di un server web utilizzando la comunicazione tramite socket.
Lettura dati multi riga provenienti dal client
cd Es03/ServerWeb-OnOff/src/network
java TcpServer.java
*/
 
package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
	public static void main(String[] args) throws Exception {
		
		final int SERVER_PORT=8765;
		String clientMsg = "";
		String serverMsg = "";
		boolean flag;
		// URL url = new URL("https://www.favicon.cc/");
		// favicon.ico serverMsg += url;

		try {			 
			// Creazione del socket sul server e ascolto sulla porta
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("Server: in ascolto sulla porta " + SERVER_PORT);

			flag=false;
			while(!flag) {
				// Attesa della connessione con il client
				System.out.println("\nAttesa ricezione dati dal client ....................... \n");
				Socket clientSocket = serverSocket.accept();
				
				// Create output stream to write data and input stream to read data from socket
				DataOutputStream outStream = new DataOutputStream(clientSocket.getOutputStream());	
				BufferedReader inStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
                //Lettura dati dal client un righa alla volta   
                clientMsg=inStream.readLine();
				System.out.println(clientMsg);	
		 
                // Elaborare qui i dati ricevuti dal client 

                clientMsg.trim();	//tolgo gli spazi all'inizio e alla fine della stringa
				String arrayCliMsg[]=clientMsg.split("\\s+");

				//Invio dei dati su stream di rete al client
				serverMsg = "HTTP/1.1 200 OK\r\n";
				//serverMsg += "Connection: close\r\n";
				serverMsg += "Content-Type: text/html\r\n"; 
                serverMsg += "\r\n";
				
                switch(arrayCliMsg[1]) {

				    case "/":
						serverMsg += "<b><h2>Saluti da Marco Zambelan!!</h2></b>";
						serverMsg += "<b><h3>Digita 'info' per vedere i comandi disponibili.";
                        break;

					case "/info":
						serverMsg += "<b><h2>Comandi disponibili:</h2></b>";
						serverMsg += "<b><h3>'on' : accende le luci;</h3></b>";
						serverMsg += "<b><h3>'off' : spegne le luci;</h3></b>";
						serverMsg += "<b><h3>'quit' : esce dal server;</h3></b>";
						break;

                    case "/on":
						serverMsg += "<b><h2>Luci accese</h2></b>";
                        break;

                    case "/off":
						serverMsg += "<b><h2>Luci spente</h2></b>";
                        break;

					case "/quit":
						serverMsg += "<b><h2>Uscita dal server...</h2></b>";
						flag = true;
                        break;

                    default : serverMsg += "<b><h2>Errore</h2></b>";
                }
            	System.out.print(serverMsg + "\n");		
                outStream.write(serverMsg.getBytes());
				outStream.flush();
				
				// Close resources
				clientSocket.close();
				inStream.close();
				outStream.close();
			}
			System.out.println("\n....................... Fine ricezione dati\n");
			// Close resources
			serverSocket.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
