package com.chat.common.utils.dochelp;

import lombok.extern.slf4j.Slf4j;
import me.doudan.doc.AnnotationHelper;
import me.doudan.doc.annotation.ControllerLayer;
import me.doudan.doc.annotation.DataLayer;
import me.doudan.doc.annotation.ManagementLayer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ServiceLoader;
@Configuration
@Slf4j
public class DocHelper {
    @Bean
    public Map<Class<?>,String> annotationTypeMap(){
        Map<Class<?>,String> map = new LinkedHashMap<>();
        map.put(ControllerLayer.class,"ControllerLayer");
        map.put(ManagementLayer.class,"ManagementLayer");
        map.put(ServiceLoader.class,"ServiceLoader");
        map.put(DataLayer.class,"DataLayer");
        return map;
    }

    @Bean
    public AnnotationHelper annotationHandler(Map annotationTypeMap) {
        log.info("创建 AnnotationHelper Bean");
        return new AnnotationHelper(annotationTypeMap);
    }
}
