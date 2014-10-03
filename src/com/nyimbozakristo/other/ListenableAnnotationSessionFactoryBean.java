package com.nyimbozakristo.other;
import org.hibernate.HibernateException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

/**
 * @author nelson
 * Manages Liquibase annotation used for managing table creation in the database
 */
public class ListenableAnnotationSessionFactoryBean extends AnnotationSessionFactoryBean {

  private SessionFactoryListener configurationListener;

  public void setConfigurationListener(SessionFactoryListener configurationListener) {
    this.configurationListener = configurationListener;
  }

  @Override
  protected void postProcessAnnotationConfiguration(AnnotationConfiguration config)
    throws HibernateException {
      if (configurationListener != null)
        configurationListener.postProcessAnnotationConfiguration(config, this);
      }
}