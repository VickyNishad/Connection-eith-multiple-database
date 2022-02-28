package com.todatabase.userConfig;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "productEntityManagerFactoryRef" , basePackages = {
		"com.todatabase.productRepo" } , transactionManagerRef = "productTransactionManager")
public class ProductConfig {
	
	
	@Bean(name = "productDatasource")
	@ConfigurationProperties(prefix = "spring.product.datasource")
	public DataSource dataSource()
	{
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "productEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			EntityManagerFactoryBuilder builder , @Qualifier("productDatasource") DataSource dataSource )
	{
		Map<String, Object> propertiesMap  = new HashMap<String, Object>();
		propertiesMap.put("hibernate.hbm2ddl.auto", "update");
		propertiesMap.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
		
		return builder.dataSource(dataSource).properties(propertiesMap).packages("com.todatabase.product.Model")
				.persistenceUnit("Product").build();
	}
	
	

	@Bean(name = "productTransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("productEntityManagerFactory") EntityManagerFactory entityManagerFactory)
	{
		return new JpaTransactionManager(entityManagerFactory);
	}

}
