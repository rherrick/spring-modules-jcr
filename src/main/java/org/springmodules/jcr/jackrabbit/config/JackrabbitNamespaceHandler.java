/*
 * Copyright 2002-2006 the original author or authors.
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
package org.springmodules.jcr.jackrabbit.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springmodules.jcr.jackrabbit.LocalTransactionManager;
import org.springmodules.jcr.jackrabbit.RepositoryFactoryBean;
import org.w3c.dom.Element;

/**
 * Jackrabbit-specifc namespace handler.
 * 
 * @author Costin Leau
 * @author Willie Wheeler
 */
public class JackrabbitNamespaceHandler extends NamespaceHandlerSupport {
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.xml.NamespaceHandler#init()
	 */
	@Override
	public void init() {
		registerBeanDefinitionParser("repository",
				new JackrabbitRepositoryBeanDefinitionParser());
		registerBeanDefinitionParser("transaction-manager",
				new JackrabbitLocalTransactionManagerBeanDefinitionParser());
	}

	/**
	 * Parses the &lt;jackrabbit:repository&gt; configuration element.
	 */
	private static class JackrabbitRepositoryBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {
		
		/* (non-Javadoc)
		 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass
		 * (org.w3c.dom.Element)
		 */
		@Override
		protected Class<?> getBeanClass(Element element) {
			return RepositoryFactoryBean.class;
		}
	}
	
	/**
	 * Parses the &lt;jackrabbit:transaction-manager&gt; configuration element.
	 */
	private static class JackrabbitLocalTransactionManagerBeanDefinitionParser
			extends AbstractSingleBeanDefinitionParser {
		
		/* (non-Javadoc)
		 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#getBeanClass
		 * (org.w3c.dom.Element)
		 */
		@Override
		protected Class<?> getBeanClass(Element element) {
			return LocalTransactionManager.class;
		}
		
		/* (non-Javadoc)
		 * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(org.w3c.dom.Element,
		 * org.springframework.beans.factory.support.BeanDefinitionBuilder)
		 */
		@Override
		protected void doParse(Element elem, BeanDefinitionBuilder builder) {
			builder.addPropertyReference("sessionFactory", elem.getAttribute("sessionFactory"));
		}
	}

}
