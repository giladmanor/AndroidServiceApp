package co.quizic.generic;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by user on 9/16/2015.
 */
public abstract class HTTPConnector {
    protected String serverUrl;
    protected HashMap<String, Object> header;
    protected boolean run = true;
    protected ResponseHandler handler;
    protected int pause;

    public HTTPConnector(HashMap<String, Object> header, ResponseHandler handler, int pulse, String serverUrl) {
        this.header = header;
        this.handler = handler;
        this.pause = pulse;
        this.serverUrl = serverUrl;
    }

    public interface ResponseHandler {
        void set(JSONObject response);
    }
}
