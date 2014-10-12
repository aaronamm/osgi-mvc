package com.funnic.mvc.templates.freemarker;

import com.funnic.mvc.core.api.exceptions.TemplateInfoNotFoundException;
import com.funnic.mvc.core.api.repository.TemplateInfo;
import com.funnic.mvc.core.api.repository.TemplateRepository;
import com.funnic.mvc.core.api.templating.TemplateEngine;
import com.funnic.mvc.templates.freemarker.directive.HtmlDirective;
import freemarker.cache.TemplateLoader;
import freemarker.template.*;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
import java.util.Stack;

/**
 * @author Per
 */
public class FreemarkerTemplateEngine implements TemplateEngine, TemplateLoader {

	private static final Logger log = LoggerFactory.getLogger(FreemarkerTemplateEngine.class);

	private final TemplateRepository templateRepository;
	private final TemplateDirectiveModel renderDirective;
	private final Configuration cfg;
	private static ThreadLocal<Stack<Bundle>> currentBundle = new ThreadLocal<Stack<Bundle>>();

	public FreemarkerTemplateEngine(final TemplateRepository templateRepository, final TemplateDirectiveModel renderDirective) {
		this.templateRepository = templateRepository;
		this.renderDirective = renderDirective;

		cfg = new Configuration();
		cfg.setIncompatibleImprovements(new freemarker.template.Version(2, 3, 20));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateUpdateDelay(1);
		cfg.setLocalizedLookup(false);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setTemplateLoader(this);
	}

	@Override
	public boolean accept(final String path) {
		return path.endsWith(".ftl");
	}

	@Override
	public void process(final Bundle bundle, final String path, final Map<String, Object> models, final Writer writer) {
		pushBundle(bundle);
		try {
			process(path, models, writer);
		} finally {
			popBundle();
		}
	}

	@Override
	public void process(String path, Map<String, Object> models, Writer writer) {
		try {
			final Template template = cfg.getTemplate("/" + path);
			models.put("html", new HtmlDirective(renderDirective));
			template.process(models, writer);
		} catch (final IOException e) {
			throw new IllegalStateException("Could not process template: " + path, e);
		} catch (TemplateException e) {
			throw new IllegalStateException("Could not process template: " + path, e);
		}
	}

	private static void pushBundle(final Bundle bundle) {
		Stack<Bundle> stack = currentBundle.get();
		if(stack == null) {
			stack = new Stack<Bundle>();
			currentBundle.set(stack);
		}

		stack.push(bundle);
	}

	private static void popBundle() {
		Stack<Bundle> stack = currentBundle.get();
		stack.pop();
	}

	public static Bundle getBundle() {
		Stack<Bundle> stack = currentBundle.get();
		if(stack == null) {
			return null;
		}

		return stack.peek();
	}

	@Override
	public Object findTemplateSource(final String path) {
		final Bundle currentBundle = getBundle();
		try {
			final TemplateInfo info = templateRepository.getTemplateInfo(currentBundle, path);
			pushBundle(info.getBundle());
			return new TemplateInfoSource(info);
		} catch (TemplateInfoNotFoundException e) {
			log.error("Could not find template info for name: " + path , e);
		} catch (IOException e) {
			log.error("Could not find template info for name: " + path, e);
		}

		return null;
	}

	@Override
	public long getLastModified(final Object templateSource) {
		return ((TemplateInfoSource)templateSource).getLastModified();
	}

	@Override
	public Reader getReader(Object templateSource, String encoding) throws IOException {
		return new InputStreamReader(((TemplateInfoSource)templateSource).getInputStream(), encoding);
	}

	@Override
	public void closeTemplateSource(Object templateSource) throws IOException {
		((TemplateInfoSource)templateSource).close();
		popBundle();
	}
}
