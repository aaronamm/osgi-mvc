package com.funnic.mvc.templates.freemarker.directive;

import freemarker.template.TemplateDirectiveModel;

/**
 * @author Per
 */
public class HtmlDirective {
	private final TemplateDirectiveModel render;

	public HtmlDirective(TemplateDirectiveModel render) {
		this.render = render;
	}

	public TemplateDirectiveModel getRender() {
		return render;
	}
}
