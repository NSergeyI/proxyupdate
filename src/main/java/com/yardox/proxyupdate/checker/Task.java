package com.yardox.proxyupdate.checker;

import com.yardox.proxyupdate.persistence.model.MyProxy;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class Task implements Runnable {

    private final BlockingQueue<MyProxy> GOOD_PROXIES;

    private MyProxy myProxy;
    private LocalDateTime startTime;

    public Task(BlockingQueue<MyProxy> GOOD_PROXIES, MyProxy myProxy) {
        this.GOOD_PROXIES = GOOD_PROXIES;
        this.myProxy = myProxy;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public void run() {
        startTime = LocalDateTime.now();
        String responseIp = IpUtilities.getMyIp(myProxy);
        if (!responseIp.equals("37.232.31.55") && responseIp!="") {
            synchronized (GOOD_PROXIES) {
                GOOD_PROXIES.add(myProxy);
            }
            System.out.println(myProxy.getHost() + " = " + responseIp);
        } else {
            System.out.println(myProxy.getHost() + " / " + responseIp);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(myProxy, task.myProxy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myProxy);
    }
}
