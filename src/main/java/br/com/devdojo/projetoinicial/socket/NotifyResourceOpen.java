package br.com.devdojo.projetoinicial.socket;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.impl.JSONEncoder;

/**
 * @author Andre Ribeiro on 4/25/2017.
 */
@PushEndpoint("/notifyClose")
public class NotifyResourceOpen {
    @OnMessage(encoders = {JSONEncoder.class})
    public String onMessage(String message) {
        return message;
    }
}
