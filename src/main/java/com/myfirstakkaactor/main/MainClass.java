/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myfirstakkaactor.main;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.myfirstakkaactor.actor.Alarm;
import com.myfirstakkaactor.actor.Counter;
import com.myfirstakkaactor.actor.NonTrustWorthyChild;
import com.myfirstakkaactor.actor.Supervisor;

/**
 *
 * @author Etisalat
 */
public class MainClass {
    
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sample1");
        ActorRef counter = system.actorOf(Counter.props(), "counter");
        counter.tell(new Counter.Message(), ActorRef.noSender());
        
        /////////////////////////////////////////Alarm///////////////////////////////
        system = ActorSystem.create();
        ActorRef alarm = system.actorOf(Alarm.props("cat"), "alarm");
        
        alarm.tell(new Alarm.Activity(), ActorRef.noSender());
        alarm.tell(new Alarm.Enable("dogs"), ActorRef.noSender());
        alarm.tell(new Alarm.Enable("cat"), ActorRef.noSender());
        alarm.tell(new Alarm.Activity(), ActorRef.noSender());
        alarm.tell(new Alarm.Disable("dogs"), ActorRef.noSender());
        alarm.tell(new Alarm.Disable("cat"), ActorRef.noSender());
        alarm.tell(new Alarm.Activity(), ActorRef.noSender());
        ///////////////////////////////ESCALATE////////////////////////////////////////////////
        system = ActorSystem.create();
        final ActorRef supervisor = system.actorOf(Supervisor.props(),"supervisor");
        for (int i = 0; i < 10; i++) {
            supervisor.tell(new NonTrustWorthyChild.Command(), ActorRef.noSender());
        }
        
        
        
    }
    
}
