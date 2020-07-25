package com.example.smart_things.Funtions;

import android.app.Notification;
import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import static com.example.smart_things.Funtions.Constantes.ISNOTCONECT;

public class MqttFuntions {

    public MqttValided mqttvalided;
    public MqttAndroidClient client;
    public IMqttToken token;

    String clientId = "";

    public void MQsetInterfaz(MqttValided MQinterfaz){
        this.mqttvalided = MQinterfaz;
    }

    public void connection(Context context, String ser, String use, String pass, final int Action ){
        clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(context,ser,clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(use);
        options.setPassword(pass.toCharArray());

        try {
            token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken){
                        mqttvalided.StatusMqttFuntions(true, Action);
                    }
                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        mqttvalided.StatusMqttFuntions(false, Action);
                    }
                }
            );
        }catch(MqttException e) {
            e.printStackTrace();
            mqttvalided.StatusMqttFuntions(false, Action);
        }
    }

    public boolean StatedConed(){
        boolean valided = false;
        if (client.isConnected()) {
            valided = true;
        }
        return valided;
    }

    public void publish (String topic,String men,int Action ){
        if(StatedConed()) {
            try {
                client.publish(topic, men.getBytes(), 0, false);
                mqttvalided.StatusMqttFuntions(true, Action);
            } catch (Exception e) {
                mqttvalided.StatusMqttFuntions(false, Action);
                e.printStackTrace();
            }
        }else {
            mqttvalided.StatusMqttFuntions(false, ISNOTCONECT);
        }
    }

}
