package com.test.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;


public class MyBatisGeneratorRun {
    public static void main(String[] args) {
        //代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL);//指定数据库类型
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1:3306/tcg?useUnicode=true&characterEncoding=UTF-8");
        autoGenerator.setDataSource(dataSourceConfig);

        //全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOpen(false);
        //输出路径
        globalConfig.setOutputDir(System.getProperty("user.dir")+"/test/src/main/java");
        //设置作者名字
        globalConfig.setAuthor("zsy");
        // 是否覆盖同名文件，默认是false
        globalConfig.setFileOverride(false);
        // 不需要ActiveRecord特性的请改为false
//        globalConfig.setActiveRecord(true);
        // XML 二级缓存
//        globalConfig.setEnableCache(false);
        // XML ResultMap
        globalConfig.setBaseResultMap(true);
        // XML columList
        globalConfig.setBaseColumnList(true);
        //去掉service的I前缀,一般只需要设置service就行
        /** 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        globalConfig.setMapperName("%sMapper");
        globalConfig.setXmlName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceDiy");
        globalConfig.setControllerName("%sController");
        globalConfig.setServiceImplName("%sServiceImpl");
        autoGenerator.setGlobalConfig(globalConfig);

        //包配置
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.test");//自定义包的路径
        packageConfig.setEntity("entity");
        packageConfig.setMapper("mapper");
        packageConfig.setXml("mapper");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        autoGenerator.setPackageInfo(packageConfig);

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        //包，列的命名规则，使用驼峰规则
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        //是否使用Lombok
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);

        //自动填充字段,在项目开发过程中,例如创建时间，修改时间,每次，都需要我们来指定，太麻烦了,设置为自动填充规则，就不需要我们赋值咯
        List<TableFill> list = new ArrayList<TableFill>();
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        list.add(createTime);
        list.add(updateTime);

        strategyConfig.setTableFillList(list);
        autoGenerator.setStrategy(strategyConfig);

        //执行
        autoGenerator.execute();

    }
}
