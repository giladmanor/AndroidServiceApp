package co.quizic.generic;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONObject;

public class MyService extends Service {
    HTTPStreamer socket = null;
    private static boolean isOn = false;
    private static boolean shouldRun = false;


    public MyService() {

    }

    public void run(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                isOn = true;
                socket = new HTTPStreamer("http://10.0.0.29:4000", null, 0, new HTTPConnector.ResponseHandler() {
                    int i=0;
                    @Override
                    public void set(JSONObject response) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        socket.write("message","send "+ i++);
                    }
                });
                socket.start();
                socket.addEvents( new String[]{"message"});
                socket.write("message","send -0-");
            }
        }).start();
    }

    public class Binder extends android.os.Binder{
        public MyService getService(){
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        if(!isOn){
            run();
        }
        Log.d("MyService", "BIND");
        return new Binder();
    }

    public void stop(){
        shouldRun = false;
    }
}
