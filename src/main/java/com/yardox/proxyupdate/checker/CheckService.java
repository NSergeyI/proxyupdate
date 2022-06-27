package com.yardox.proxyupdate.checker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yardox.proxyupdate.DiskService;
import com.yardox.proxyupdate.parser.dto.foxtools.MainClassResponse;
import com.yardox.proxyupdate.persistence.model.MyProxy;
import com.yardox.proxyupdate.persistence.model.ProxyType;
import com.yardox.proxyupdate.persistence.service.IMyProxyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
public class CheckService {

    private static final int THREAD_NUMBER = 10;
    private static final long KEEP_ALIVE_TIME = 60;
    private static final long THREAD_SLEEP = 5000;
    private static final int COMPANIES_QUEUE_FLUSH = 100;
    private static final LinkedBlockingQueue QUEUE = new LinkedBlockingQueue();
    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(THREAD_NUMBER,
            THREAD_NUMBER, KEEP_ALIVE_TIME, TimeUnit.SECONDS, QUEUE);

    private static Logger LOGGER = LogManager.getLogger(CheckService.class);
    private final BlockingQueue<MyProxy> GOOD_PROXIES = new LinkedBlockingDeque<>(300);
    ;

    Map<Task, Future> futures = new HashMap<>();
    private ArrayList<MyProxy> proxies;

    @Autowired
    private IMyProxyService myProxyService;
    @Autowired
    private DiskService diskService;

    public CheckService() {
    }

    public CheckService(ArrayList<MyProxy> proxies) {
        this.proxies = proxies;
    }

    public ArrayList<MyProxy> checkMyProxies() {
        for (MyProxy myProxy : proxies) {
            Task task = new Task(GOOD_PROXIES, myProxy);
            futures.put(task, EXECUTOR.submit(task));
        }
        processedResultTaskAndReloadTask(3);
        return proxies;
    }

    private void processedResultTaskAndReloadTask(int theNumberOfThreadsRemainingInTheQueue) {
        int count = 0;
        do {
            List<Task> forReload = processedBlockingQueueAndGetTaskForReload();
            count = count + forReload.size();

            for (Task task : forReload) {
                futures.put(task, EXECUTOR.submit(task));
            }

            try {
                Thread.sleep(THREAD_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info(
                    "Checked {} proxies, wait save, completed: {}, all: {}, count: {}, active count: {} ",
                    GOOD_PROXIES.size(),
                    EXECUTOR.getCompletedTaskCount(), EXECUTOR.getTaskCount(), count,
                    EXECUTOR.getActiveCount());
            flush(false);
        } while (EXECUTOR.getActiveCount() > theNumberOfThreadsRemainingInTheQueue);
        flush(true);
        LOGGER.info("END");
    }

    private void flush(boolean force) {
        if (force || GOOD_PROXIES.size() >= COMPANIES_QUEUE_FLUSH) {
            synchronized (GOOD_PROXIES) {
//                diskService.saveFile(new ArrayList(GOOD_PROXIES), "C:\\workspace\\proxyupdate\\good.txt");
                LOGGER.info("Save {} companies", GOOD_PROXIES.size());
                GOOD_PROXIES.clear();
            }
        }
    }

    public void shutdownPool() {
        processedResultTaskAndReloadTask(0);
        flush(true);
        EXECUTOR.shutdown();
    }

    private List<Task> processedBlockingQueueAndGetTaskForReload() {
        List<Task> forReload = new ArrayList<>();
        List<Task> forRemove = new ArrayList<>();

        for (Map.Entry<Task, Future> entry : futures.entrySet()) {
            Task currentTask = entry.getKey();
            Future currentFuture = entry.getValue();
            LocalDateTime start = currentTask.getStartTime();
            if (currentFuture.isDone()) {
                forRemove.add(currentTask);
            } else {
                if (start != null && start.plusMinutes(5).isBefore(LocalDateTime.now())) {
                    entry.getValue().cancel(true);
                    LOGGER.debug("stopped: {}", entry.getKey());
                    currentTask.setStartTime(null);
                    forReload.add(currentTask);
                    forRemove.add(currentTask);
                }
            }
        }
        for (Task task : forRemove) {
            futures.remove(task);
        }
        return forReload;
    }

    public void getProxiesFromBD() {
//        proxies = (ArrayList<MyProxy>) myProxyService.findAll();
        proxies = diskService.read();
    }
}
