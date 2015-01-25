/*
 * Copyright 2010 Grails Plugin Collective
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.7
//grails.project.war.file = "target/${appName}-${appVersion}.war"

def activemqVersion = '5.7.0'

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
  legacyResolve true
  // inherit Grails' default dependencies
  inherits("global") {
    // uncomment to disable ehcache
    // excludes 'ehcache'
  }
  log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
  repositories {
    mavenLocal()
    mavenCentral()
    grailsCentral()
    mavenRepo "http://repo.grails.org/grails/plugins"
    mavenRepo "http://download.java.net/maven/2/"
    mavenRepo "http://repository.jboss.org/nexus/content/groups/public-jboss/"
  }
  dependencies {
    compile 'org.apache.activemq:activemq-spring:5.10.1'
    compile 'org.springframework:spring-jms:4.0.7.RELEASE'
  }

  plugins {
      build(":release:3.0.1",
              ":rest-client-builder:1.0.3") {
          export = false
      }
  }
}


grails.release.scm.enabled = false
grails.project.repos.default = "grailsCentral"



