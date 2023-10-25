package it.yang;

import java.io.*;
import java.net.*;

public class Server {
    public  void avvioServer(){
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(6789);
            System.out.println("Server in attesa di connessioni...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientHandlerThread = new Thread(new ClientHandler(clientSocket));
                clientHandlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    public float Calcolo(String operatore,float n1,float n2){
        if(operatore.equals("+"))return n1+n2;
        if(operatore.equals("-"))return n1-n2;
        if(operatore.equals("*"))return n1*n2;
        if(operatore.equals("/"))return n1/n2;
        return 0;
    }

    @Override
    public void run(){

        try (
            BufferedReader inDaClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outVersoClient = new DataOutputStream(clientSocket.getOutputStream());
        ) {

            String operatore;
            float n1;
            float n2;
            String n;
            
            
                try {
                    n = inDaClient.readLine();
                    n1 = Float.parseFloat(n);

                    operatore = inDaClient.readLine();
                    if(operatore.length()>1)outVersoClient.writeBytes(" ERRORE: Inserisci un operatore valido"+"\n");
                    
                    n = inDaClient.readLine();
                    n2 = Float.parseFloat(n);

                    float risultato = Calcolo(operatore, n1, n2);

                    if(operatore.equals("/") && n2==0) outVersoClient.writeBytes(" ERRORE: inserisci un operatore valido"+"\n");
                        
                    else if(risultato == 0) outVersoClient.writeBytes(" ERRORE:Inserisci un operatore valido"+"\n");
                            
                    else{
                        outVersoClient.writeBytes(" risultato: "+risultato+"\n");
                        System.out.println("il client ha svolto l'operazione :"+n1+operatore+n2);
                        System.out.println("risultato: "+risultato+"\n"); 
                    }

                } catch (Exception e) {
                    outVersoClient.writeBytes("Inserisci un numero valido"+"\n");
                }
                
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}