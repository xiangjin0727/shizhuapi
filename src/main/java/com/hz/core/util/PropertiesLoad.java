package com.hz.core.util;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesLoad extends PropertyPlaceholderConfigurer{

  private static Properties properties ;

  @Override
  protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
    super.processProperties(beanFactory, props);
    properties = props;
  }

  public static String getProperty(String key) {
    return properties.getProperty(key);
  }
  
  public static Properties getPropertiesObject(){
    return properties;
  }
  
}
