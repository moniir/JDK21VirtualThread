package com.monir.executorServices;

import com.monir.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.Executors;

public class AutoClosableExecService {

    private static final Logger log = LoggerFactory.getLogger(AutoClosableExecService.class);

    //ExecutorService is now extends the autoClosable
    public static void main(String[] args) {
  /*      var executorService = Executors.newSingleThreadExecutor();
        executorService.execute(AutoClosableExecService::task);
        log.info("submitted");
        // task
        executorService.shutdown(); // it will wait until task complete & then shutdown. Will not let other task to execute once shutdown call
      //  executorService.shutdownNow(); // this method will immediately shutdown task & do not wait for any others task to complete
*/
       try( var executorService = Executors.newSingleThreadExecutor()) {
           executorService.execute(AutoClosableExecService::task);
           executorService.execute(AutoClosableExecService::task);
           executorService.execute(AutoClosableExecService::task);
           log.info("submitted");
          /* Are we supposed to use ExecutorService with try-with resources?
               Ans: It depends. If we are already used shutdown then we can use try-with() ->resources
           Spring web/ server applications etc. ExecutorService will be used throughout the application. We do not shutdown
           */
       }
    }

    private static void task(){
        CommonUtils.sleep(Duration.ofSeconds(2));
        log.info("task executed");
    }
}
