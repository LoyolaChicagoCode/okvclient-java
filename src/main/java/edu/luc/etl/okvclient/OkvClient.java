package edu.luc.etl.okvclient;

import com.ning.http.client.AsyncHttpClient;

import java.io.*;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by laufer on 10/22/13.
 */
public enum OkvClient implements Map<String, Object> {

    INSTANCE;

    @Override
    public int size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object get(final Object key) {
        if (!(key instanceof String)) {
            throw new ClassCastException("only String keys are supported");
        }
        try {
            final InputStream is = httpClient
                    .prepareGet(OKV_BASE_URL + URLEncoder.encode(key.toString(), URL_ENCODING))
                    .execute()
                    .get()
                    .getResponseBodyAsStream();
            final ObjectInputStream ois = new ObjectInputStream(is);
            final Object value = ois.readObject();
            return value;
        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Object put(final String key, final Object value) {
        final Object old = get(key);
        try {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            httpClient
                    .preparePost(OKV_BASE_URL)
                    .addParameter(key, URLEncoder.encode(bos.toString(), URL_ENCODING))
                    .execute()
                    .get();

        } catch (final Throwable ex) {
            throw new RuntimeException(ex);
        }
        return old;
    }

    @Override
    public Object remove(final Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<String> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Object> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        throw new UnsupportedOperationException();
    }

    private static final AsyncHttpClient httpClient = new AsyncHttpClient();

    private static final String OKV_BASE_URL = "http://api.openkeyval.org/";

    private static final String URL_ENCODING = "UTF-8";
}
