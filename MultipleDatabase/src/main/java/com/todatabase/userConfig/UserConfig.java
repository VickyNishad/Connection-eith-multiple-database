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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryRef" , basePackages = {
		"com.todatabase.userRepo"}, transactionManagerRef="transactionManager")
public class UserConfig {

	@Primary
	@Bean(name = "datasource")
	@ConfigurationProperties(prefix = "spring.user.datasource")
	public DataSource dataSource()
	{
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			EntityManagerFactoryBuilder builder,@Qualifier("datasource") DataSource dataSource )
	{
		Map<String, Object> propertiesMap  = new HashMap<String, Object>();
		propertiesMap.put("hibernate.hbm2ddl.auto", "update");
		propertiesMap.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
		
		return builder.dataSource(dataSource).properties(propertiesMap).packages("com.todatabase.userModel")
				.persistenceUnit("User").build();
	}
	
	
	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory)
	{
		return new JpaTransactionManager(entityManagerFactory);
	}
	
}
