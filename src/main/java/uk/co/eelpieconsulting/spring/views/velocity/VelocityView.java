package uk.co.eelpieconsulting.spring.views.velocity;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

import java.util.Map;

public class VelocityView extends AbstractTemplateView {

    private VelocityEngine velocityEngine;

    public VelocityView() {
    }

    @Override
    protected void initServletContext(ServletContext servletContext) {
        this.velocityEngine = BeanFactoryUtils.beanOfTypeIncludingAncestors(
                obtainApplicationContext(), VelocityEngine.class, true, false);
    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        VelocityEngineUtils.mergeTemplate(velocityEngine, this.getUrl(), "UTF-8", map, httpServletResponse.getWriter());
    }

}
