package com.tao.utils;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;

import java.util.HashMap;
import java.util.Map;

public class CodeGenerator {
    /**
     * 项目路径
     */
    private static final String PARENT_DIR = System.getProperty("user.dir");
    /**
     * xml路径
     */
    private static final String XML_PATH = PARENT_DIR + "/dao/src/main/resources/mappers";
    /**
     * entity路径
     */
    private static final String ENTITY_PATH = PARENT_DIR + "/dao/src/main/java/com/tao/pojo";
    /**
     * mapper路径
     */
    private static final String MAPPER_PATH = PARENT_DIR + "/dao/src/main/java/com/tao/mapper";
    /**
     * service路径
     */
    private static final String SERVICE_PATH = PARENT_DIR + "/service/src/main/java/com/tao/service";
    /**
     * serviceImpl路径
     */
    private static final String SERVICE_IMPL_PATH = PARENT_DIR + "/service/src/main/java/com/tao/servicelmpl/";
    /**
     * controller路径
     */
    private static final String CONTROLLER_PATH = PARENT_DIR + "/web/src/main/java/com/tao/web/controller";
    /**
     * 数据库url
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/canting?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8";
    /**
     * 数据库用户名
     */
    private static final String USERNAME = "root";
    /**
     * 数据库密码
     */
    private static final String PASSWORD = "88888888";

    public static void main(String[] args) {
        // 要构建代码的表名
        String[] tableNames = {"activity","category","dish","dish_flavor","flavor","order_detail","orders","review","role","setmeal","setmeal_dish","user"};
        FastAutoGenerator.create(DB_URL, USERNAME, PASSWORD)
                // 全局配置
                .globalConfig(builder -> builder
                        .author("tao")
                        .enableSwagger()
                )
                // 包配置
                .packageConfig(builder -> builder
                        .parent("")
                        .xml("mappers")
                        .entity("com.tao.pojo")
                        .mapper("com.tao.dao")
                        .service("com.tao.service")
                        .serviceImpl("com.tao.servicelmpl")
                        .controller("com.tao.web.controller")
                        .pathInfo(getPathInfo())
                )
                // 策略配置
                .strategyConfig(builder -> builder
                        .addInclude(tableNames)
                        // entity
                        .entityBuilder()
                        .fileOverride()
                        .enableChainModel()
                        .fileOverride()
                        .enableLombok()
                        .enableRemoveIsPrefix()
                        .logicDeleteColumnName("is_delete")
                        .idType(IdType.AUTO)
                        .addTableFills(new Column("create_time", FieldFill.INSERT))
                        .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                        // controller
                        .controllerBuilder()
                        .fileOverride()
                        .enableRestStyle()
                        .formatFileName("%sController")
                        // service
                        .serviceBuilder()
                        .fileOverride()
                        .superServiceClass(IService.class)
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImp")
                        // mapper
                        .mapperBuilder()
                        .fileOverride()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        .superClass(BaseMapper.class)
                        .formatMapperFileName("%sDao")
                        .formatXmlFileName("%sXml")
                        .enableMapperAnnotation()
                )
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 获取路径信息map
     *
     * @author MK
     */
    private static Map<OutputFile, String> getPathInfo() {
        Map<OutputFile, String> pathInfo = new HashMap<>(5);
        pathInfo.put(OutputFile.entity, ENTITY_PATH);
        pathInfo.put(OutputFile.mapper, MAPPER_PATH);
        pathInfo.put(OutputFile.service, SERVICE_PATH);
        pathInfo.put(OutputFile.serviceImpl, SERVICE_IMPL_PATH);
        pathInfo.put(OutputFile.controller, CONTROLLER_PATH);
        pathInfo.put(OutputFile.xml, XML_PATH);
        return pathInfo;
    }
}