/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myfirstakkaactor.actor;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 *
 * @author Etisalat
 */
public class Alarm extends AbstractLoggingActor {

    public static class Activity {
    }

    public static class Disable {

        private final String password;

        public Disable(String password) {
            this.password = password;
        }
    }

    public static class Enable {

        private final String password;

        public Enable(String password) {
            this.password = password;
        }
    }

    private final String password;
    private final PartialFunction<Object, BoxedUnit> enabled;
    private final PartialFunction<Object, BoxedUnit> disabled;

    public Alarm(String password) {
        this.password = password;
        enabled = ReceiveBuilder
                .match(Activity.class, this::onActivity)
                .match(Disable.class, this::onDisable)
                .build();
        disabled = ReceiveBuilder
                .match(Enable.class, this::onEnable)
                .build();

        receive(disabled);
    }

    public static Props props(String password) {
        return Props.create(Alarm.class, password);
    }

    private void onActivity(Activity ignored) {
        log().warning("wohohohohohohhohoh, ALARM ALARM!!!");
    }

    private void onDisable(Disable disable) {
        if (password.equals(disable.password)) {
            log().info("Alarm Started");
            getContext().become(disabled);
        } else {
            log().warning("Someone who is unauthorized tried to disable Alarm");
        }
    }

    private void onEnable(Enable enable) {
        if (password.equals(enable.password)) {
            log().info("Alarm Started");
            getContext().become(enabled);
        } else {
            log().info("Someone failed to enable the Alarm");
        }
    }

}
