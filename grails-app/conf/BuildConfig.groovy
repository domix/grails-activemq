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
grails.project.target.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

def activemqVersion = '5.6.0'

grails.project.dependency.resolution = {
  // inherit Grails' default dependencies
  inherits("global") {
    // uncomment to disable ehcache
    // excludes 'ehcache'
  }
  log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
  repositories {
    mavenLocal()
    mavenCentral()
    grailsPlugins()
    grailsHome()
    grailsCentral()
    grailsRepo "http://grails.org/plugins"
    mavenRepo "http://repo.grails.org/grails/plugins"
    mavenRepo "http://download.java.net/maven/2/"
    mavenRepo "http://repository.jboss.org/nexus/content/groups/public-jboss/"
  }
  dependencies {
    compile("org.apache.activemq:activemq-core:$activemqVersion") {
      transitive = false
    }
    compile("org.apache.activemq:kahadb:$activemqVersion") {
      transitive = false
    }
    compile("org.apache.activemq.protobuf:activemq-protobuf:1.1") {
      transitive = false
    }
    compile("org.apache.activemq:activeio-core:3.1.4") {
      transitive = false
    }
    compile "org.apache.geronimo.specs:geronimo-j2ee-management_1.1_spec:1.0.1"
    compile "org.apache.geronimo.specs:geronimo-jms_1.1_spec:1.1.1"
  }

  plugins {
    /*
    This validation is for prevent load the following plugins in previous Grails versions.
    I some Grails versions from 1.3.* the 'export = false' does not work. For Grails 2.* works properly
    */
    if (grailsVersion.startsWith('2')) {
      build(":release:2.0.3") { export = false }
      build(":rest-client-builder:1.0.2") { export = false }
      build(":tomcat:$grailsVersion") { export = false }
    }
  }
}


grails.release.scm.enabled = false
grails.project.repos.default = "grailsCentral"



