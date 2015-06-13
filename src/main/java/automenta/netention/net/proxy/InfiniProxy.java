package automenta.netention.net.proxy;

import automenta.netention.Core;
import com.syncleus.spangraph.InfiniPeer;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.util.MimeMappings;
import org.infinispan.Cache;

import java.io.File;

import static automenta.netention.web.SpacetimeWebServer.send;
import static io.undertow.Handlers.resource;

public class InfiniProxy extends CachingProxyServer {

    public final Cache<String, Object> index;


    public void add(URLSensor r) {
        index.put(r.id, r);
    }

    public InfiniProxy(String proxyIndex, InfiniPeer peer, int port, String cachePath) {
        super(port, cachePath);

        this.index = peer.the(proxyIndex);

        addPrefixPath("/index", new HttpHandler() {
            @Override public void handleRequest(HttpServerExchange ex) throws Exception {


                try {
                    send(Core.jsonFields.writeValueAsString(index), ex);
                }
                catch (Exception e) {
                    e.printStackTrace();;
                    send(e.toString(), ex);
                }

            }
        });
        addPrefixPath("/cache", resource(
                new FileResourceManager(new File(cachePath), 100))
                .setDirectoryListingEnabled(true).setWelcomeFiles("README").setMimeMappings(MimeMappings.DEFAULT));

        /*
        addPrefixPath("/cache", new HttpHandler() {
            @Override public void handleRequest(HttpServerExchange ex) throws Exception {

                List<Object> files = new ArrayList();

                Path dir = Paths.get(cachePath);
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {

                    for (Path file: stream) {
                        files.add(Files.getFileAttributeView(file, BasicFileAttributeView.class)
                                .readAttributes());
                    }
                } catch (IOException | DirectoryIteratorException x) {
                    // IOException can never be thrown by the iteration.
                    // In this snippet, it can only be thrown by newDirectoryStream.
                    System.err.println(x);
                }

                send(Core.jsonFields.writeValueAsString(files), ex);
            }
        });
        */
    }

}