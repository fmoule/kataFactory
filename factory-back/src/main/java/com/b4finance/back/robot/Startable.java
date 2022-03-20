package com.b4finance.back.robot;

public interface Startable {

    void start() throws Exception;

    void stop() throws Exception;

    boolean isStarted();
}
