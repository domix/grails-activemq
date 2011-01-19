/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.grails.plugins.activemq;

import grails.util.Environment;
import groovy.lang.GroovyClassLoader;
import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;
import org.codehaus.groovy.grails.commons.ApplicationHolder;

/**
 * Helper methods.
 */
public final class ActiveMQUtils {

    private static ConfigObject config;

    private ActiveMQUtils() {
        // static only
    }

    /**
     * Parse and load the activemq configuration.
     *
     * @return the configuration
     */
    public static synchronized ConfigObject getConfig() {
        if (config == null) {
            reloadConfig();
        }

        return config;
    }

    /**
     * Reset the config for testing.
     */
    public static synchronized void resetConfig() {
        config = null;
    }

    /**
     * Allow a secondary plugin to add config attributes.
     *
     * @param className the name of the config class.
     */
    public static synchronized void loadSecondaryConfig(final String className) {
        mergeConfig(getConfig(), className);
    }

    /**
     * Force a reload of the activemq configuration.
     */
    public static void reloadConfig() {
        mergeConfig(ReflectionUtils.getConfig(), "DefaultActiveMQConfig");
    }


    /**
     * Merge in a secondary config (provided by a plugin as defaults) into the main config.
     *
     * @param currentConfig the current configuration
     * @param className     the name of the config class to load
     */
    private static void mergeConfig(final ConfigObject currentConfig, final String className) {
        GroovyClassLoader classLoader = new GroovyClassLoader(ActiveMQUtils.class.getClassLoader());
        ConfigSlurper slurper = new ConfigSlurper(Environment.getCurrent().getName());
        ConfigObject secondaryConfig;
        try {
            secondaryConfig = slurper.parse(classLoader.loadClass(className));
        }
        catch (ClassNotFoundException e) {
            // TODO fix this
            throw new RuntimeException(e);
        }

        config = mergeConfig(currentConfig, (ConfigObject) secondaryConfig.getProperty("activemq"));
        ReflectionUtils.setConfig(config);
    }

    /**
     * Merge two configs together. The order is important; if <code>secondary</code> is not null then
     * start with that and merge the main config on top of that. This lets the <code>secondary</code>
     * config act as default values but let user-supplied values in the main config override them.
     *
     * @param currentConfig the main config, starting from Config.groovy
     * @param secondary     new default values
     * @return the merged configs
     */
    @SuppressWarnings("unchecked")
    private static ConfigObject mergeConfig(final ConfigObject currentConfig, final ConfigObject secondary) {
        ConfigObject config = new ConfigObject();
        if (secondary == null) {
            config.putAll(currentConfig);
        } else {
            config.putAll(secondary.merge(currentConfig));
        }
        return config;
    }


    @SuppressWarnings("unchecked")
    private static <T> T getBean(final String name) {
        return (T) ApplicationHolder.getApplication().getMainContext().getBean(name);
    }
}
