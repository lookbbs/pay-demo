package com.ydf.pay.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 设置从connect Manager获取Connection 超时时间，单位毫秒
     */
    private static final int CONNECTION_REQUEST_TIMEOUT = 5000;

    /**
     * 连接超时时间，单位毫秒
     */
    private static final int CONNECTION_TIMEOUT = 30000;

    /**
     * 请求获取数据的超时时间，单位毫秒
     */
    private static final int SOCKET_TIMEOUT = 60000;

    private HttpClientUtil() {

    }

    /**
     * @param url
     * @param requestBody
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, String requestBody) throws Exception {

        LOGGER.info("[URL]={}", url);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);

        httpClient = acceptsUntrustedCertsHttpClient();
        httpPost.setEntity(new StringEntity(requestBody, "utf-8"));

        CloseableHttpResponse closeableHttpResponse = null;
        HttpEntity httpEntity = null;
        String responseBody = "";
        try {
            closeableHttpResponse = httpClient.execute(httpPost);
        } catch (Exception e) {
            LOGGER.info("###########http [Exception]={}", e);
            throw e;
        }
        if (HttpStatus.SC_OK == closeableHttpResponse.getStatusLine().getStatusCode()) {
            httpEntity = closeableHttpResponse.getEntity();
            responseBody = EntityUtils.toString(httpEntity, "utf-8");
        }
        LOGGER.info("http [responseBody]={}", responseBody);
        return responseBody;

    }

    /**
     * 跳过ssl证书
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    private static CloseableHttpClient acceptsUntrustedCertsHttpClient() throws Exception {
        HttpClientBuilder b = HttpClientBuilder.create();

        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();
        b.setSslcontext(sslContext);

        // don't check Hostnames, either.
        // -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't
        // want to weaken
        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

        // here's the special part:
        // -- need to create an SSL Socket Factory, to use our weakened "trust
        // strategy";
        // -- and create a Registry, to register it.
        //
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
                .build();

        // now, we create connection-manager using our Registry.
        // -- allows multi-threaded use
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        b.setConnectionManager(connMgr);

        // finally, build the HttpClient;
        // -- done!
        CloseableHttpClient client = b.build();
        return client;
    }

    public static String sendPost(String url, String requestBody) throws Exception {

        LOGGER.info("[URL]={}", url);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        RequestConfig config = RequestConfig.custom()//
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)//
                .setConnectTimeout(CONNECTION_TIMEOUT)//
                .setSocketTimeout(SOCKET_TIMEOUT)//
                .build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);

        httpClient = acceptsUntrustedCertsHttpClient();
        httpPost.setEntity(new StringEntity(requestBody, "utf-8"));

        CloseableHttpResponse closeableHttpResponse = null;
        HttpEntity httpEntity = null;
        String responseBody = "";
        try {
            closeableHttpResponse = httpClient.execute(httpPost);
        } catch (Exception e) {
            LOGGER.info("###########http [Exception]={}", e);
            throw e;
        }
        httpEntity = closeableHttpResponse.getEntity();
        responseBody = EntityUtils.toString(httpEntity, "utf-8");
        LOGGER.info("http [responseBody]={}", responseBody);
        return responseBody;

    }

}
