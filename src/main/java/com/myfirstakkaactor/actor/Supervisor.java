/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myfirstakkaactor.actor;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import static akka.actor.SupervisorStrategy.stop;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.Duration;

/**
 *
 * @author Etisalat
 */
public class Supervisor extends AbstractLoggingActor {

    final ActorRef child = getContext().actorOf(NonTrustWorthyChild.props(), "child");

    {
        {
            receive(ReceiveBuilder
                    .matchAny(command -> child.forward(command, getContext()))
                    .build()
            );
        }
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return new OneForOneStrategy(10,
                Duration.create(10, TimeUnit.SECONDS),
                DeciderBuilder
                        .match(RuntimeException.class, ex -> stop())
                        .build()
        );
    }

    public static Props props() {
        return Props.create(Supervisor.class);
    }
}
