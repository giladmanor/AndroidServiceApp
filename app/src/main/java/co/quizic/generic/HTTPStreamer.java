package co.quizic.generic;

// Using socket.io packages to connect to nodeJS socket.io
// details available at: http://socket.io/blog/native-socket-io-and-android/

import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;


/**
 * Created by Gilad Manor on 9/16/2015.
 */
public class HTTPStreamer extends HTTPConnector {
    private static final String TAG = "HTTPStreamer";
    private Socket socket;

    public HTTPStreamer(String serverUrl, HashMap<String, Object> header, int pulse, ResponseHandler handler) {
        super(header, handler, pulse, serverUrl);

        try {
            socket = IO.socket(serverUrl);
        } catch (URISyntaxException e) {
            Log.e(TAG,"Exception in IO.socket creation",e);
            e.printStackTrace();
        }

    }

    public void write(String event,String message){
        socket.emit(event, message);
    }

    public void addEvents(String[] events){
        for(String event :events){
            socket.on(event, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    try {
                        if (handler!=null) handler.set((JSONObject)args[0]);
                    } catch (Exception e) {
                        Log.e(TAG,"Exception in handler.set(...)",e);
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public boolean start() {
        socket.connect();
        return this.run;
    }

    public boolean stop() {
        socket.close();
        this.run = false;
        return true;
    }


}
