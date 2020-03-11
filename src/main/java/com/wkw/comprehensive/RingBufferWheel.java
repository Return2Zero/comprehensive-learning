package com.wkw.comprehensive;

import java.util.Date;

public class RingBufferWheel<T> {

    private Entity<T>[] wheel;

    private int wheelSize;

    private int taskNum;

    private int index;

    private boolean stop;

    public RingBufferWheel(int wheelSize) {
        wheel = new Entity[wheelSize];
        this.wheelSize = wheelSize;
        taskNum = 0;
        index = 0;
        stop = true;
    }

    public void put(T task, int delay) {
        int sumIndex = delay + index;
        int wheelNum = sumIndex / wheelSize;
        int idx = sumIndex%wheelSize;
        Entity entity = new Entity(wheelNum, task, wheel[idx]);
        wheel[idx] = entity;
        taskNum ++;
    }

    public void start() {
        stop = false;
        new Thread(() -> {
            System.out.println(new Date() + ":" + "wheel start now");
            while (!stop) {
                Entity task = wheel[index];
                Entity pre = null;
                while (null != task) {
                    if (task.wheelNum == 0) {
                        System.out.println(new Date() + ":" + task.value);
                        if (pre == null) {
                            wheel[index] = task.next;
                            pre = null;
                        } else {
                            pre.next = task.next;
                        }
                        taskNum --;
                    } else {
                        task.wheelNum --;
                        pre = task;
                    }
                    task = task.next;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                index++;
                if (index == wheelSize) {
                    index = 0;
                }
            }
        }).start();
    }

    public void stop() {
        stop = true;
    }

    class Entity<T> {

        private int wheelNum;

        private T value;

        private Entity next;

        public Entity(int wheelNum, T value, Entity next) {
            this.wheelNum = wheelNum;
            this.value = value;
            this.next = next;
        }
    }
}
