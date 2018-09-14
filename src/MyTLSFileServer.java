import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import javax.naming.ldap.*;
import javax.net.*;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class MyTLSFileServer {

    private static int port;

    public static void main(String args[]){
        try {
            SSLContext ctx = SSLContext.getInstance("TLS"); // get an SSL context for TLS version
            KeyStore ks = KeyStore.getInstance("JKS"); // init the context with server's private key in jks
            char[] passphrase = "<whatever>".toCharArray(); // store passphrase to unlock jks file
            ks.load(new FileInputStream("server.jks"),passphrase); //load the keystore file

            //use KeyManager class to manage the key
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks,passphrase);

            // initialise the SSL context with keys
            ctx.init(kmf.getKeyManagers(),null,null);

            ServerSocketFactory ssf = ctx.getServerSocketFactory();

            port = Integer.parseInt(args[0]);

            SSLServerSocket ss =(SSLServerSocket)
            ssf.createServerSocket(port);

            String EnabledProtocols[] = {"TLSv1.2", "TLSv1.1"};
            ss.setEnabledProtocols(EnabledProtocols);

            SSLSocket s = (SSLSocket)ss.accept();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
