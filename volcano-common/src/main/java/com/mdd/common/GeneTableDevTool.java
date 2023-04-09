//package com.mdd.common;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//
///**
// * @author liushan
// * @data 2023/2/15 18:10
// */
//public class GeneTableDevTool {
//    public static void main(String[] args) {
//
//        AutoGenerator mpg = new AutoGenerator();
//        // 选择 freemarker 引擎，默认 Velocity
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//
//        PackageConfig packageConfig = new PackageConfig();
//        packageConfig.setParent("com.mdd.common");
//        packageConfig.setMapper("dao.mapper");
//        packageConfig.setEntity("entity.system");
//        packageConfig.setXml("dao");
//        mpg.setPackageInfo(packageConfig);
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        gc.setAuthor("liushan");
//        //生成文件的路径，根据自己电脑路径更改
//        gc.setOutputDir("D:/javaa/volcanoTbl");
//        gc.setFileOverride(true);// 是否覆盖同名文件，默认是false
//        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
//        gc.setEnableCache(false);// XML 二级缓存
//        gc.setBaseResultMap(true);// XML ResultMap
//        gc.setBaseColumnList(true);// XML columList
//        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
//        gc.setMapperName("%sMapper");
//        gc.setXmlName("%sMapper");
//        // gc.setServiceName("MP%sService");
//        // gc.setServiceImplName("%sServiceDiy");
//        // gc.setControllerName("%sAction");
//        mpg.setGlobalConfig(gc);
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setDbType(DbType.MYSQL);
//        dsc.setTypeConvert(new MySqlTypeConvert());
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("root");
//        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/volcano?useUnicode=true&characterEncoding=utf8");
//        mpg.setDataSource(dsc);
//
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        // strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
//        strategy.setInclude("va_user_auth"); // 需要生成的表
//        strategy.setEntityLombokModel(true);
//        mpg.setStrategy(strategy);
//        mpg.execute();
//        System.out.println("success");
//    }
//}
