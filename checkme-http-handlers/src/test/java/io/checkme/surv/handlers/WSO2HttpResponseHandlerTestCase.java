package io.checkme.surv.handlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.checkme.surv.handler.HttpResponseHandler;

@RunWith(MockitoJUnitRunner.class)

public class WSO2HttpResponseHandlerTestCase {

    private static final String APIM_TITLE = "<title>Welcome to APIM</title>";

    private static final String NOT_APIM_TITLE = "<title>Welcome to not APIM</title>";

    @Mock
    private HttpURLConnection mockedHttpUrlConnection;

    @Test
    public void shouldReturnTrue() throws IOException {
        //GIVEN
        InputStream apimInputStream = new ByteArrayInputStream(APIM_TITLE.getBytes());
        when(mockedHttpUrlConnection.getInputStream()).thenReturn(apimInputStream);
        HttpResponseHandler handler = (HttpResponseHandler) new WSO2HttpResponseHandler();
        //WHEN
        boolean result = handler.handle(mockedHttpUrlConnection);
        //THEN
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseBecauseOfContent() throws IOException {
        //GIVEN
        InputStream apimWrongInputStream = new ByteArrayInputStream(NOT_APIM_TITLE.getBytes());
        when(mockedHttpUrlConnection.getInputStream()).thenReturn(apimWrongInputStream);
        HttpResponseHandler handler = (HttpResponseHandler) new WSO2HttpResponseHandler();
        //WHEN
        boolean result = handler.handle(mockedHttpUrlConnection);
        //THEN
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnFalseBecauseOfStreamError() throws IOException {
        //GIVEN
        when(mockedHttpUrlConnection.getInputStream()).thenThrow(new IOException());
        HttpResponseHandler handler = (HttpResponseHandler) new WSO2HttpResponseHandler();
        //WHEN
        boolean result = handler.handle(mockedHttpUrlConnection);
        //THEN
        assertThat(result).isFalse();
    }
}
