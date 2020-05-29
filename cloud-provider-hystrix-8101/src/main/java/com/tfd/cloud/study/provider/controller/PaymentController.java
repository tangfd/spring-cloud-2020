package com.tfd.cloud.study.provider.controller;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tfd.cloud.study.common.api.Payment;
import com.tfd.cloud.study.common.utils.JsonResult;
import com.tfd.cloud.study.provider.feign.client.ProviderHystrixClient;
import com.tfd.cloud.study.provider.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @since TangFD 2020-04-18
 **/
@RestController
public class PaymentController implements ProviderHystrixClient {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port:-1}")
    private String port;

    @Override
    public JsonResult<String> save(@RequestBody Payment payment) {
        return new JsonResult<>(true, port, paymentService.save(payment));
    }

    /**
     * 服务端 降级 熔断，默认对服务超时和服务异常均做降级
     * {@link HystrixProperty}的属性配置再 {@link HystrixCommandProperties} 中
     */
    @Override
    @HystrixCommand(fallbackMethod = "getFall", commandProperties = {
            /**
             * 设置HystrixCommand.run()的隔离策略，有两种选项：
             *
             * THREAD —— 在固定大小线程池中，以单独线程执行，并发请求数受限于线程池大小。【默认】
             *
             * SEMAPHORE —— 在调用线程中执行，通过信号量来限制并发量
             */
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            /**
             * 设置HystrixCommand.run()的执行是否启用超时限制 默认为true
             */
            @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
            /**
             * 设置调用者等待命令执行的超时限制，超过此时间，HystrixCommand被标记为TIMEOUT，并执行回退逻辑。
             * 注意：超时会作用在HystrixCommand.queue()，即使调用者没有调用get()去获得Future对象。
             */
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),

            /********************断路器 设置*************************************/
            /**
             * 设置在一个滚动窗口中，打开断路器的最少请求数。
             */
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            /**
             * 设置统计的滚动窗口的时间段大小。该属性是线程池保持指标时间长短。
             */
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),

            /**
             * 设置断路器打开后（OPEN状态），等待下一次尝试执行请求的时间（HALF-OPEN状态），允许尝试执行一个请求
             * 如果请求失败，断路器将打开（OPEN）直到下一个睡眠窗口期间返回到该状态（HALF-OPEN）。如果请求成功，则断路器切换闭合状态（CLOSED）
             */
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            /**
             * 设置打开回路并启动回退逻辑的错误比率。
             */
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),
            /**
             *
             * 设置断路器是否起作用
             */
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true")
            /********************************************
             以上断路器的设置：在10秒内 是少有10个请求，并且有60%的请求失败，就会触发断路器断开（OPEN），
             此时接收到的请求直接通过fallbackMethod 返回
             再5秒窗口期后，切换为半开状态，尝试执行一个请求
             如果请求失败，断路器将再次打开（OPEN）直到下一个睡眠窗口期间切换为半开状态（HALF-OPEN）
             如果请求成功，则断路器切换闭合状态（CLOSED）
             重复以上过程
             ********************************************/
    })
    public JsonResult<Payment> get(@PathVariable("id") String id) {
        int i = new Random().nextInt(7);
        if (i == 2) {
            throw new RuntimeException();
        }
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new JsonResult<>(true, port, paymentService.get(id));
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFall")
    public JsonResult<Payment> getE(@PathVariable("id") String id) {
        System.out.println(Thread.currentThread().getName() + ": getE 开始执行.........");
        int a = 1 / 0;
        return new JsonResult<>();
    }

    /**
     * 对服务异常不做降级处理，交给服务自己处理
     */
    @Override
    @HystrixCommand(fallbackMethod = "getFall", ignoreExceptions = Exception.class)
    public JsonResult<Payment> getIgnE(@PathVariable("id") String id) {
        System.out.println(Thread.currentThread().getName() + ": getE 开始执行.........");
        if (id.equals("1")) {
            int a = 1 / 0;
        } else {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new JsonResult<>();
    }

    public JsonResult<Payment> getFall(String id) {
        return new JsonResult<>(false,
                Thread.currentThread().getName() + "：这是服务端的fallback方法");
    }
}
