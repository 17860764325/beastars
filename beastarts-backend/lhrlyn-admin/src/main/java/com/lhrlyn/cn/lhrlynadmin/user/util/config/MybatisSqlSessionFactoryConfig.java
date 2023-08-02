package com.lhrlyn.cn.lhrlynadmin.user.util.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;

/**
 * @author lihaoran
 * @date 7/31/23 11:12 PM
 */
public class MybatisSqlSessionFactoryConfig {
    @Primary
    @Bean("db1SqlSessionFactory")
    public SqlSessionFactory db1SqlSessionFactory(DataSource dataSource) throws Exception {
        /**
         * 使用 mybatis plus 配置
         */
        MybatisSqlSessionFactoryBean b1 = new MybatisSqlSessionFactoryBean();
        System.out.println("dataSourceLyz" + dataSource.toString());
        b1.setDataSource(dataSource);
        b1.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return b1.getObject();
    }
}
