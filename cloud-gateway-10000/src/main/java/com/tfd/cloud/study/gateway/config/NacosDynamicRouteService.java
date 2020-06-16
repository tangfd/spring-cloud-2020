package com.tfd.cloud.study.gateway.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * spring cloud gateway 整合 nacos 实现动态路由，根据nacos动态刷新gateway的路由配置进行实现
 *
 * @author TangFD 2020-6-16
 */
@Component
public class NacosDynamicRouteService {

    private static final String DATA_ID = "nacos-gateway-dynamic-router";
    private static final String GROUP = "DEFAULT_GROUP";

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;
    @Value("${spring.cloud.nacos.config.namespace}")
    private String nameSpace;

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    private static final List<String> ROUTE_LIST = new ArrayList<>();

    @PostConstruct
    public void dynamicRouteByNacosListener() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, nameSpace);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String config = configService.getConfig(DATA_ID, GROUP, 5000);
        addAndPublishRoute(config);
        configService.addListener(DATA_ID, GROUP, new Listener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                clearRoute();
                addAndPublishRoute(configInfo);
                publish();
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });
    }

    private void clearRoute() {
        for (String id : ROUTE_LIST) {
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
        }
        ROUTE_LIST.clear();
    }

    private void addAndPublishRoute(String configInfo) {
        try {
            List<RouteDefinition> routeDefinitions = JSONObject.parseArray(configInfo, RouteDefinition.class);
            for (RouteDefinition definition : routeDefinitions) {
                routeDefinitionWriter.save(Mono.just(definition)).subscribe();
                ROUTE_LIST.add(definition.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void publish() {
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }
}