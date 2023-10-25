package it.fi.meucci;
import java.io.*;
import java.net.*;

public class Client {

    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket miSocket;
    BufferedReader tastiera;
    String strRicevutaDalServer;
    DataOutputStream outVersoServer;
    BufferedReader inDaServer;

    String n1;
    String op;
    String n2;
    

    public Socket connetti(){
        System.out.println(" CLIENT partito in esecuzione...");
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            miSocket = new Socket(nomeServer,portaServer);

            outVersoServer = new DataOutputStream(miSocket.getOutputStream());
            inDaServer = new BufferedReader(new InputStreamReader(miSocket.getInputStream()));

        } catch (UnknownHostException e) {
            // TODO: handle exception
            System.err.println("host sconosciuto");
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("errore durante la connesione ");
            System.exit(1);
        }
        return miSocket;
    }

    public void comunica(){

        try {
            System.out.println(" ...  inserisci il primo numero al server:"+'\n');
            n1 = tastiera.readLine();
            System.out.println(" ...  inserisci il operatore al server:"+'\n');
            op = tastiera.readLine();
            System.out.println(" ...  inserisci il secondo numero al server:"+'\n');
            n2 = tastiera.readLine();
            

            System.out.println(" invio il calcolo e attendo...");
            outVersoServer.writeBytes(n1+'\n');
            outVersoServer.writeBytes(op+'\n');
            outVersoServer.writeBytes(n2+'\n');

            strRicevutaDalServer = inDaServer.readLine();
            System.out.println(" la risposta del server :"+'\n'+strRicevutaDalServer);

            if(strRicevutaDalServer.contains("risultato")||strRicevutaDalServer.contains("ERRORE")){
            System.out.println(" Client:termina elaborazione e chiude la connessione");
            miSocket.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
            System.out.println("errore durante la connesione con il server!");
            System.exit(1);
        }
    }
    
}

