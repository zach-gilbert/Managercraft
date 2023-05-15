package dev.gilbert.zacharia.managercraft;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;

import static org.junit.Assert.assertEquals;

public class TLSConnectionTests {

    /**
     * Tests the HttpClient with a keystore for SSL connection.
     *
     * @throws Exception if an error occurs during the execution of the test
     */
    @Test
    public void testHttpClientWithKeystore() throws Exception {
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");

        // load the keystore from the classpath using the correct password
        try (FileInputStream instream = new FileInputStream(this.getClass().getClassLoader().getResource("keystore.p12").getFile())) {
            keyStore.load(instream, "changeit".toCharArray());
        }

        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, "changeit".toCharArray())
                .loadTrustMaterial(keyStore, null)
                .build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);

        try (CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build()) {
            HttpGet request = new HttpGet("https://localhost:8443/api/minecraft/startServer");

            HttpResponse response = httpclient.execute(request);

            System.out.println("\n\nOutput:\n\n");
            System.out.println(response);

            System.out.println("\n"+ EntityUtils.toString(response.getEntity()) + "\n");

            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void testHttpClientWithKeystorePost() throws Exception {
        KeyStore keyStore  = KeyStore.getInstance("PKCS12");

        // load the keystore from the classpath using the correct password
        try (FileInputStream instream = new FileInputStream(this.getClass().getClassLoader().getResource("keystore.p12").getFile())) {
            keyStore.load(instream, "changeit".toCharArray());
        }

        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, "asdf1234ASDF!@#$".toCharArray())
                .loadTrustMaterial(keyStore, null)
                .build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext);

        try (CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build()) {
            HttpPost request = new HttpPost("https://localhost:8443/api/minecraft/startServerPost");

            HttpResponse response = httpclient.execute(request);

            System.out.println("\n\nOutput:\n\n");
            System.out.println(response);

            assertEquals(200, response.getStatusLine().getStatusCode());
        }
    }



}
