package com.funnic.mvc.blueprint.aries.impl;

import com.funnic.mvc.core.api.Controller;
import com.funnic.mvc.core.api.view.ViewRepositoryFactory;
import org.apache.aries.blueprint.ComponentDefinitionRegistry;
import org.apache.aries.blueprint.NamespaceHandler;
import org.apache.aries.blueprint.ParserContext;
import org.apache.aries.blueprint.mutable.*;
import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Bundle;
import org.osgi.service.blueprint.reflect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Per
 */
public class NSHandler implements NamespaceHandler {
	private static final String NS_URI = "http://www.github.com/perandersson/osgi-mvc/schemas/mvc";
	private static final String CONTROLLER_NAME = "controller";
	private static final String VIEW_NAME = "view";
	private static final String ROUTE_NAME = "route";
	private static final String MVC_VIEW_REPOSITORY_FACTORY = "_mvc_viewRepositoryFactory";

	private static Logger log = LoggerFactory.getLogger(NSHandler.class);

	@Override
	public URL getSchemaLocation(final String namespace) {
		if (NS_URI.equals(namespace)) {
			final URL schemaLocation = getClass().getResource("/schemas/mvc.xsd");
			return schemaLocation;
		}
		return null;
	}

	@Override
	public Set<Class> getManagedClasses() {
		log.info("Retrieving the managed classes");
		Set<Class> managedClasses = new HashSet<>();
		managedClasses.add(ViewRepositoryFactory.class);
		return managedClasses;
	}

	@Override
	public Metadata parse(final Element element, final ParserContext pc) {
		log.info("Parsing elements");
		final ComponentDefinitionRegistry cdr = pc.getComponentDefinitionRegistry();
		final String elementName = element.getLocalName();
		if(CONTROLLER_NAME.equals(elementName)) {
			final String ref = element.getAttribute("ref");
			final String defaultAction = element.getAttribute("defaultAction");
			final String category = element.getAttribute("category");
			final String name = element.getAttribute("name");

			final MutableServiceMetadata service = pc.createMetadata(MutableServiceMetadata.class);
			service.setId(pc.generateId());
			service.addInterface(Controller.class.getName());
			if (StringUtils.isNotEmpty(name)) {
				service.addServiceProperty(createValue(pc, "mvc.controller.name"), createValue(pc, name));
			}
			if (StringUtils.isNotEmpty(defaultAction)) {
				service.addServiceProperty(createValue(pc, "mvc.controller.defaultAction"), createValue(pc, defaultAction));
			}
			if (StringUtils.isNotEmpty(defaultAction)) {
				service.addServiceProperty(createValue(pc, "mvc.controller.category"), createValue(pc, category));
			}

			final MutableRefMetadata serviceRef = pc.createMetadata(MutableRefMetadata.class);
			serviceRef.setComponentId(ref);
			service.setServiceComponent(serviceRef);
			return service;
		} else if(VIEW_NAME.equals(elementName)) {
			final String path = element.getAttribute("path");
			final String filePattern = element.getAttribute("filePattern");

			final MutableBeanMetadata viewBean = pc.createMetadata(MutableBeanMetadata.class);
			viewBean.setId(pc.generateId());
			viewBean.setFactoryMethod("resolveViews");

			final RefMetadata viewRepositoryFactory = getOrCreateViewRepositoryFactory(pc);
			viewBean.setFactoryComponent(viewRepositoryFactory);
			addArgument(pc, viewBean, "blueprintBundle", Bundle.class, 0);
			addArgument(pc, viewBean, path, 1);
			addArgument(pc, viewBean, StringUtils.defaultString(filePattern, ""), 2);
			pc.getComponentDefinitionRegistry().registerComponentDefinition(viewBean);

			final MutableServiceMetadata service = pc.createMetadata(MutableServiceMetadata.class);
			service.setId(pc.generateId());
			service.addInterface(List.class.getName());
			service.addServiceProperty(createValue(pc, "mvc.views"), createValue(pc, "true"));
			service.setServiceComponent(viewBean);
			return service;
		} else if(ROUTE_NAME.equals(elementName)) {
			final String name = element.getAttribute("name");
			final String url = element.getAttribute("url");

		}

		throw new UnsupportedOperationException("Invalid operation: " + element.toString());
	}

	private RefMetadata getOrCreateViewRepositoryFactory(final ParserContext pc) {
		if(!pc.getComponentDefinitionRegistry().containsComponentDefinition(MVC_VIEW_REPOSITORY_FACTORY)) {
			ServiceReferenceMetadata ref = createServiceRef(pc, ViewRepositoryFactory.class, MVC_VIEW_REPOSITORY_FACTORY);
			pc.getComponentDefinitionRegistry().registerComponentDefinition(ref);
		}

		return createRef(pc, MVC_VIEW_REPOSITORY_FACTORY);
	}

	@Override
	public ComponentMetadata decorate(final Node node, final ComponentMetadata cm, final ParserContext pc) {
		log.info("Decorating elements");
		return null;
	}

	private ValueMetadata createValue(ParserContext context, String value) {
		MutableValueMetadata v = context.createMetadata(MutableValueMetadata.class);
		v.setStringValue(value);
		return v;
	}

	private ServiceReferenceMetadata createServiceRef(ParserContext pc, Class<?> i, String id) {
		MutableReferenceMetadata r = pc.createMetadata(MutableReferenceMetadata.class);
		r.setId(id);
		r.setRuntimeInterface(i);
		//r.setInterface(i.getName());
		return r;
	}

	private ServiceReferenceMetadata createReference(ParserContext pc, String interfaceName, String id) {
		MutableReferenceMetadata r = pc.createMetadata(MutableReferenceMetadata.class);
		r.setId(id);
		r.setInterface(interfaceName);
		return r;
	}

	private void addArgument(final ParserContext pc, final MutableBeanMetadata bean, String name, Class<?> type, int idx) {
		MutableRefMetadata r = pc.createMetadata(MutableRefMetadata.class);
		r.setComponentId(name);

		bean.addArgument(r, null, idx);
	}

	private void addArgument(ParserContext pc, MutableBeanMetadata bean, String str, int idx) {
		final MutableValueMetadata value = pc.createMetadata(MutableValueMetadata.class);
		value.setStringValue(str);
		value.setType(String.class.getName());

		bean.addArgument(value, String.class.getName(), idx);
	}

	private RefMetadata createRef(ParserContext context, String value) {
		MutableRefMetadata r = context.createMetadata(MutableRefMetadata.class);
		r.setComponentId(value);
		return r;
	}
}
