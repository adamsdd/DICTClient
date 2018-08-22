/**
 * @author Dawid Adamczyk #adamsdd
 */

package pl.adamsdd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class Connection
{
    private Socket sock;
    private BufferedReader br;
    private PrintWriter pw;
    private final int TIMEOUT = 5000;
    
    public void connectTo(String server, int port)
    {
        System.out.println("Trwa nawiazywanie polaczenia...");
        
        try{
            System.out.println("Lacze...");
            
            sock = new Socket(server, port);
            sock.setSoTimeout(TIMEOUT);
            System.out.println("Nawiazano polczenie z : " + server);
            
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            br.readLine();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void close()
    {
        try{
            br.close();
            pw.close();
            if(!sock.isClosed())
                sock.close();
            
            System.out.println("Polaczenie zakonczone");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String search(String query)
    {
        String response = "";
        String def = "Definicje : \n";
        
        try{            
            String request = "DEFINE * " + query;
            pw.println(request);
            pw.flush();
            
            while(response != null && !response.startsWith("250"))
            {
                response = br.readLine();
                def += response + "\n";
                
                if(response.startsWith("552") || response.startsWith("501"))
                    break;
            }
            
        }catch(IOException ioe){
            System.out.println("Error - Blad podczas odczytywania");
            ioe.printStackTrace();
        }

        return def;
    }
    
}