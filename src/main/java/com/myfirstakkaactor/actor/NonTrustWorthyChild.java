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
public class NonTrustWorthyChild extends AbstractLoggingActor{
    public static class Command{}
    private long messages = 0l;
    
    {
        receive(ReceiveBuilder
        .match(Command.class, this::onCommand)
        .build()
        );
    }
    private void onCommand(Command c)
    {
        messages++;
        if (messages % 4 == 0) {
            throw new RuntimeException("Oh no, I got four commands, can't handle more");
        }else{
            log().info("Got Command # ", messages);
        }
    }
    
    public static Props props(){
        return Props.create(NonTrustWorthyChild.class);
    }
}
