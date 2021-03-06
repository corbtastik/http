/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Baked with love by corbett
 * Project: http
 * Package: net.cworks.http
 * Class: HttpPatchBuilder
 * Created: 6/24/14 10:09 PM
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package net.cworks.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpPatchBuilder extends HttpPostBuilder {

    protected HttpPatchBuilder(final String url) {
        super(url);
    }

    protected HttpPatchBuilder(final String url, final HttpClientBuilder builder) {
        super(url, builder);
    }

    @Override
    protected HttpUriRequest createRequest() throws IOException {

        final HttpPatch request;
        try {
            String url = urlWithParameters();
            request = new HttpPatch(url);
            HttpEntity httpEntity = createEntity();
            request.setEntity(httpEntity);
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

        return request;
    }
}
