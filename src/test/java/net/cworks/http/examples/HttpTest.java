/**
 * Created with love by corbett.
 * User: corbett
 * Date: 5/23/14
 * Time: 9:35 PM
 */
package net.cworks.http.examples;

import net.cworks.http.Http;
import net.cworks.http.RequestMixin;
import net.cworks.http2.Http2;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;

public class HttpTest {

    /**
     * Apache http-client instance
     */
    private static HttpClient httpClient = null;

    /**
     * Default HTTP connection timeout
     */
    public static final int CONNECTION_TIMEOUT = 60000;

    /**
     * Default timeout to use for requests to Cs2
     */
    public static final int READ_TIMEOUT = 60000;

    @BeforeClass
    public static void createHttpClient() {
        HttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        httpClient = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig())
            .setConnectionManager(connectionManager)
            .build();
    }

    private static RequestConfig requestConfig() {
        RequestConfig config = RequestConfig.custom()
            .setConnectTimeout(CONNECTION_TIMEOUT)
            .setConnectionRequestTimeout(READ_TIMEOUT)
            .build();
        return config;
    }

    @Test
    public void createHttp() {

        try {
            String response = Http.get("http://api.icndb.com/jokes/random")
                .use(httpClient).asString();

            // caller uses baked in HttpClient
            response = Http.get("http://api.icndb.com/jokes/random").asString();

            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void simpleGET() {

        try {
            File responseFile = new File("simpleClient.txt");
            String response = Http.get("http://api.icndb.com/jokes/random").asString();
            System.out.println(response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void simplePOST() {

        try {
            String response = Http.post("http://requestb.in/10m67h71")
                .data("dataA", "valueA")
                .param("paramA", "valueA")
                .asString();

            System.out.println(response);

        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void simpleUpload() {

        try {
            String response = Http.multipart("http://requestb.in/10m67h71")
                .upload(new File("src/test/resources/data/nacho_libre.pdf"),
                    ContentType.create("application/pdf"))
                .upload(new File("src/test/resources/data/nacho_eggs.png"),
                    ContentType.create("image/png"))
                .data("dataA", "valueA")
                .param("paramA", "valueA")
                .asString();

            System.out.println(response);
        } catch(IOException ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void simplePUT() {

        try {
            String response = Http.put("http://requestb.in/10m67h71")
                .data("dataD", "valueD")
                .param("paramD", "valueD")
                .asString();
            System.out.println("put> " + response);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void simpleExample() {
        try {
            String response = Http.get("http://www.google.com").asString();
            System.out.println(response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void simpleMixin() {
        try {
            String response = Http.get("http://zip.getziptastic.com/v2/US/76102")
                .mixin(new RequestMixin() {
                    @Override
                    public void mixin(HttpUriRequest request) {
                        request.setHeader("cray", "cray");
                    }
            }).asString();
            System.out.println(response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void basicAuth() {
        try {
            String response = Http
                .get("http://httpbin.org/basic-auth/bucky/nacho")
                .port(80)
                .mixin(new RequestMixin() {
                    @Override
                    public void mixin(HttpUriRequest request) {
                        BASE64Encoder encoder = new BASE64Encoder();
                        request.setHeader("Authorization",
                            "Basic " + encoder.encode("bucky:nacho".getBytes()));
                    }
                })
                .asString();

            System.out.println(response);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testHttp2Use() {

        try {
            String response = Http2.get("http://httpbin.org/basic-auth/bucky/nacho")
                .username("bucky")
                .password("nacho").asString();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
