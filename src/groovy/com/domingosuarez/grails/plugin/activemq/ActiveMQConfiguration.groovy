package com.domingosuarez.grails.plugin.activemq

import org.apache.activemq.ActiveMQConnectionFactory
import org.apache.activemq.broker.TransportConnector
import org.apache.activemq.usage.StoreUsage
import org.apache.activemq.usage.SystemUsage
import org.apache.activemq.usage.TempUsage
import org.apache.activemq.xbean.XBeanBrokerService
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.cfg.GrailsConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.connection.CachingConnectionFactory
import org.springframework.jms.core.JmsTemplate

import javax.jms.ConnectionFactory

/**
 * Created by domix on 1/24/15.
 */
@Configuration
class ActiveMQConfiguration {
  @Autowired
  GrailsApplication grailsApplication

  Map config

  @Bean
  TempUsage jmsTempUsage() {
    def config = getConfig()

    new TempUsage(
      limit: config.tempUsageLimit
    )
  }

  @Bean
  StoreUsage jmsStoreUsage() {
    def config = getConfig()

    new StoreUsage(
      limit: config.storeUsageLimit
    )
  }

  @Bean
  @Autowired
  SystemUsage jmsSystemUsage(TempUsage jmsTempUsage, StoreUsage jmsStoreUsage) {
    new SystemUsage(
      tempUsage: jmsTempUsage,
      storeUsage: jmsStoreUsage
    )
  }

  @Bean
  @Autowired
  XBeanBrokerService brokerFactoryBean(SystemUsage systemUsage) {
    def config = getConfig()

    def message = "\nLoading ActiveMQ"
    println message
    println "=" * message.length()

    new XBeanBrokerService(
      useJmx: config.useJmx,
      start: config.startBroker,
      brokerId: config.brokerId,
      brokerName: config.brokerName,
      persistent: config.persistent,
      systemUsage: systemUsage,
      transportConnectors: [
        new TransportConnector(uri: new URI("tcp://localhost:${config.port}"))
      ]
    )
  }

  @Bean
  ActiveMQConnectionFactory amqConnectionFactory() {
    new ActiveMQConnectionFactory(brokerURL: 'vm://localhost')
  }

  @Bean
  @Autowired
  ConnectionFactory connectionFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
    new CachingConnectionFactory(
      targetConnectionFactory: activeMQConnectionFactory,
      sessionCacheSize: 10,
      cacheProducers: false
    )
  }

  Map getConfig() {
    if (!config) {
      GrailsConfig grailsConfig = new GrailsConfig(grailsApplication)
      def baseConfig = 'grails.activemq.'

      def result = [:]

      result.active = grailsConfig.get(baseConfig + 'active', Boolean.TRUE, [true, false])
      result.useJmx = grailsConfig.get(baseConfig + 'useJmx', Boolean.FALSE, [true, false])
      result.startBroker = grailsConfig.get(baseConfig + 'startBroker', Boolean.TRUE, [true, false])
      result.brokerId = grailsConfig.get(baseConfig + 'brokerId', 'brokerId')
      result.brokerName = grailsConfig.get(baseConfig + 'brokerName', 'localhost')
      result.persistent = grailsConfig.get(baseConfig + 'persistent', Boolean.FALSE, [true, false])
      result.port = grailsConfig.get(baseConfig + 'port', 61616)

      result.tempUsageLimit = grailsConfig.get(baseConfig + 'tempUsage.limit', 64 * 1024 * 1024)
      result.storeUsageLimit = grailsConfig.get(baseConfig + 'storeUsage.limit', 64 * 1024 * 1024)

      config = result
    }
    config
  }

  @Bean
  @Autowired
  JmsTemplate defaultJmsTemplate(ConnectionFactory connectionFactory) {
    new JmsTemplate(
      connectionFactory: connectionFactory
    )
  }
}
