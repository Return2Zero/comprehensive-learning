package com.wkw.comprehensive;

public class WheelTest {
    public static void main(String[] args) throws InterruptedException {
        RingBufferWheel<String> wheel = new RingBufferWheel<>(5);
        wheel.put("a", 3);
        wheel.put("e", 3);
        wheel.put("b", 1);
        wheel.put("d", 10);
        wheel.start();
        wheel.put("c", 8);
        Thread.sleep(9000);
        wheel.stop();
    }
}