/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myfirstakkaactor.actor;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;

/**
 *
 * @author Etisalat
 */
public class Counter extends AbstractLoggingActor{

    public static Props props() {
       return Props.create(Counter.class);
    }
   public static class Message{}
    private int counter=0;
    
    {
        receive(ReceiveBuilder.match(Message.class, this::onMessage).build()
        );
    }
    
    private void onMessage(Message message)
    {
        counter++;
        log().info("Increased counter " + counter);
    }
    
}
