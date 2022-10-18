package com.zts.gateway.test;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhangtusheng
 * @Date 2022 10 16 23 21
 * @describe：
 **/
public class ApiTest {

    @Test
    public void test_rpc() {

        ApplicationConfig application = new ApplicationConfig();
        application.setName("api-gateway-test");
        application.setQosEnable(false);

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("zookeeper://127.0.0.1:2181");
        registry.setRegister(false);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("cn.bugstack.gateway.rpc.IActivityBooth");
        reference.setVersion("1.0.0");
        reference.setGeneric("true");

        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(application).registry(registry).reference(reference).start();

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        String[] parameterTypes = {"java.lang.String", "cn.bugstack.gateway.rpc.dto.XReq"};

        Map<String, Object> param01 = new HashMap<>();
        param01.put("str", "10001");

        Map<String, Object> param02 = new HashMap<>();
        param02.put("uid", "10001");
        param02.put("name", "zts");

        Object user = genericService.$invoke("test",
                new String[]{"java.lang.String", "cn.bugstack.gateway.rpc.dto.XReq"},
                new Object[]{param01.values().toArray(), param02});

        System.out.println(user);


    }
}
