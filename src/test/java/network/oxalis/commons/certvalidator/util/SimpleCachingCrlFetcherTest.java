package network.oxalis.commons.certvalidator.util;

import network.oxalis.commons.certvalidator.api.CertificateValidationException;
import network.oxalis.commons.certvalidator.api.CrlCache;
import network.oxalis.commons.certvalidator.api.CrlFetcher;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.ServerSocket;
import java.security.cert.X509CRL;
import java.util.Date;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleCachingCrlFetcherTest {

    @Test
    public void ldapNotSupported() throws Exception {
        CrlFetcher crlFetcher = new SimpleCachingCrlFetcher(new SimpleCrlCache());

        Assert.assertNull(crlFetcher.get("ldap://something..."));
    }

    @Test
    public void returnSameIfNoNextUpdate() throws Exception {
        CrlCache crlCache = new SimpleCrlCache();
        CrlFetcher crlFetcher = new SimpleCachingCrlFetcher(crlCache);

        X509CRL x509CRL = Mockito.mock(X509CRL.class);
        Mockito.doReturn(null).when(x509CRL).getNextUpdate();

        crlCache.set("url", x509CRL);

        Assert.assertEquals(crlFetcher.get("url"), x509CRL);
    }

    @Test
    public void returnNullIfNotValidAndProtocolNotSupported() throws Exception {
        CrlCache crlCache = new SimpleCrlCache();
        CrlFetcher crlFetcher = new SimpleCachingCrlFetcher(crlCache);

        X509CRL x509CRL = Mockito.mock(X509CRL.class);
        Mockito.doReturn(new Date()).when(x509CRL).getNextUpdate();

        Thread.sleep(25);

        crlCache.set("url", x509CRL);

        Assert.assertNull(crlFetcher.get("url"));
    }

    @Test(enabled = false, expectedExceptions = CertificateValidationException.class)
    public void triggerExceptionWithoutMessage() throws Exception {
        CrlCache crlCache = Mockito.mock(CrlCache.class);
        CrlFetcher crlFetcher = new SimpleCachingCrlFetcher(crlCache);

        crlFetcher.get(null);
    }

    @Test(enabled = false, expectedExceptions = CertificateValidationException.class)
    public void testNonAccessibleHttpCert() throws Exception {
        try {
            NoResponseHttpServer.start();
            CrlFetcher crlFetcher = new SimpleCachingCrlFetcher(new SimpleCrlCache());
            crlFetcher.get("http://127.0.0.1:" + NoResponseHttpServer.getPort() + "/idontexist");
        } finally {
            NoResponseHttpServer.stop();
        }
    }

    static class NoResponseHttpServer {

        private static HttpServer server;

        public static void start() throws IOException {
            int port = getRandomAvailablePort();
            server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/", new NoResponseHandler());
            server.start();
        }

        public static void stop() {
            if (server != null) {
                server.stop(0);
            }
        }

        public static int getPort() {
            if (server == null) {
                return -1;
            }
            return server.getAddress().getPort();
        }

        private static int getRandomAvailablePort() {
            try (ServerSocket socket = new ServerSocket(0)) {
                socket.setReuseAddress(true);
                return socket.getLocalPort();
            } catch (IOException e) {
                throw new RuntimeException("Failed to find a random available port", e);
            }
        }

        static class NoResponseHandler implements HttpHandler {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Do nothing, effectively not sending a response
            }
        }
    }

}
