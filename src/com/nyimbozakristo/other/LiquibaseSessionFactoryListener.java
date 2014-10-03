package com.nyimbozakristo.other;

import liquibase.spring.SpringLiquibase;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * @author nelson
 * Manages Liquibase used for managing table creation in the database
 */
public class LiquibaseSessionFactoryListener implements SessionFactoryListener, ResourceLoaderAware {

  private String changeLog;
  private ResourceLoader resourceLoader;
  private boolean executeUpdate;
  
  public void setExecuteUpdate(boolean executeUpdate) {
    this.executeUpdate = executeUpdate;
  }

  public void setChangeLog(String changeLog) {
    this.changeLog = changeLog;
  }

  public void setResourceLoader(ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;    
  }

  public void postProcessAnnotationConfiguration(Configuration config,
      LocalSessionFactoryBean bean) throws HibernateException {
    
    if (!executeUpdate)
      return;
    
    try {
      SpringLiquibase process = new SpringLiquibase();
      process.setResourceLoader(resourceLoader);
      process.setDataSource(bean.getDataSource());
      process.setChangeLog(changeLog);
      process.afterPropertiesSet();
    } catch (Exception e) {
      throw new HibernateException(e);
    }
  }

}