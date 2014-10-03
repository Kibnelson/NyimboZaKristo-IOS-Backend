package com.nyimbozakristo.other;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public interface SessionFactoryListener {

  public void postProcessAnnotationConfiguration(Configuration config, LocalSessionFactoryBean bean)
      throws HibernateException;
}