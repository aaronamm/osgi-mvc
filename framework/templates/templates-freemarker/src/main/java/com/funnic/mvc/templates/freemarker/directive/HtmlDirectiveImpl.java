package com.funnic.mvc.templates.freemarker.directive;

import freemarker.template.TemplateDirectiveModel;

/**
 * @author Per
 */
public class HtmlDirectiveImpl implements HtmlDirective {
	private final TemplateDirectiveModel render;
	private final TemplateDirectiveModel href;

	public HtmlDirectiveImpl(final TemplateDirectiveModel render, final TemplateDirectiveModel href) {
		this.render = render;
		this.href = href;
	}

	@Override
	public TemplateDirectiveModel getRender() {
		return render;
	}

	@Override
	public TemplateDirectiveModel getHref() {
		return href;
	}
}
