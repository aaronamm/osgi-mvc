package com.funnic.mvc.core.impl.templating.freemarker;

import com.funnic.mvc.core.api.TemplateEngine;
import com.funnic.mvc.core.impl.templating.BundleTemplateEngine;
import com.funnic.mvc.core.impl.templating.BundleTemplateEngineFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Per
 */
public class FreemarkerTemplateEngine implements TemplateEngine, BundleListener {

	private static final Logger log = LoggerFactory.getLogger(FreemarkerTemplateEngine.class);
	private Map<Bundle, BundleTemplateEngine> bundles = new HashMap<Bundle, BundleTemplateEngine>();
	private final BundleContext bundleContext;
	private final BundleTemplateEngineFactory bundleTemplateEngineFactory;

	public FreemarkerTemplateEngine(final BundleContext bundleContext, final BundleTemplateEngineFactory bundleTemplateEngineFactory) {
		this.bundleContext = bundleContext;
		this.bundleTemplateEngineFactory = bundleTemplateEngineFactory;
	}

	public void initialize() {
		bundleContext.addBundleListener(this);
		Bundle[] bundles = bundleContext.getBundles();
		for (Bundle bundle : bundles) {
			addBundle(bundle);
		}
	}

	public void destroy() {
		bundleContext.removeBundleListener(this);
	}

	@Override
	public void process(final Bundle bundle, final String path, final Map<String, Object> models, final Writer writer) {
		BundleTemplateEngine bundleTemplateEngine = bundles.get(bundle);
		bundleTemplateEngine.process(path, models, writer);
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		if (event.getType() == BundleEvent.STARTED) {
			addBundle(event.getBundle());
		} else if (event.getType() == BundleEvent.STOPPING) {
			removeBundle(event.getBundle());
		}
	}

	private void removeBundle(final Bundle bundle) {
		bundles.remove(bundle);
	}

	private void addBundle(final Bundle bundle) {
		final BundleTemplateEngine bundleTemplateEngine = bundleTemplateEngineFactory.create(bundle);
		bundles.put(bundle, bundleTemplateEngine);
	}
}
