package com.test.alldemo.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.test.alldemo.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {

    @Autowired
    SeckillService seckillService;
    /**
     * Guava令牌桶：每秒放行10个请求
     */
    RateLimiter rateLimiter = RateLimiter.create(33);

    /**
     * 下单接口：导致超卖的错误示范
     *
     * @param sid
     * @return
     */
    @PostMapping("/createWrongOrder")
    public String createWrongOrder(@RequestParam("sid") int sid) {
        int id = 0;
        try {
            id = seckillService.createWrongOrder(sid);
            log.info("创建订单id: [{}]", id);
        } catch (Exception e) {
            log.error("Exception", e);
        }
        return String.valueOf(id);
    }

    /**
     * 乐观锁更新库存 + 令牌桶限流
     * @param sid
     * @return
     */
    @PostMapping("/createOptimisticOrder")
    public String createOptimisticOrder(@RequestParam("sid") int sid) {
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            log.warn("你被限流了，真不幸，直接返回失败");
            return "购买失败，库存不足";
        }
        int surplusOrder;
        try {
            surplusOrder = seckillService.createOptimisticOrder(sid);
            log.info("购买成功，剩余库存为: [{}]", surplusOrder);
        } catch (Exception e) {
            log.error("购买失败：[{}]", e.getMessage());
            return "购买失败，库存不足";
        }
        return String.format("购买成功，剩余库存为：%d", surplusOrder);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}