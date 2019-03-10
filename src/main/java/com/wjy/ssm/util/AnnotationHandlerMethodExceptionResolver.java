package com.wjy.ssm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author WangJinYi 2019/3/10
 */
public class AnnotationHandlerMethodExceptionResolver
		extends ExceptionHandlerExceptionResolver {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AnnotationHandlerMethodExceptionResolver.class);

	private String defaultErrorView;
	private Properties exceptionMappings;

	public String getDefaultErrorView() {
		return this.defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

	@Override
	protected ModelAndView doResolveHandlerMethodException(
			HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		ModelAndView returnValue = super.doResolveHandlerMethodException(
				request, response, handlerMethod, exception);
		Method method = handlerMethod.getMethod();
		ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method,
				ResponseBody.class);
		if (isAjaxRequest(request, responseBodyAnn)) {
			response.setStatus(200);
			return buildAjaxJsonResponse(exception);
		}
		if (this.exceptionMappings != null) {
			Set<Entry<Object, Object>> eSet = this.exceptionMappings.entrySet();
			for (Entry entry : eSet) {
				if (exception.getClass().getName().equals(entry.getKey())) {
					Map param = new HashMap();
					String errorMsg = exception.getMessage();
					if (StringUtils.isEmpty(errorMsg)) {
						errorMsg = "系统异常！";
					}
					try {
						param.put("errorMsg",
								URLEncoder.encode(errorMsg, "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					LOGGER.error("系统捕获" + entry.getKey() + ":" + errorMsg);
					returnValue = new ModelAndView(entry.getValue().toString(),
							param);
					break;
				}
			}
		}

		if (returnValue == null) {
			Map param = new HashMap();
			try {
				param.put("errorMsg", URLEncoder.encode("系统异常！", "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.reset();
			returnValue = new ModelAndView(this.defaultErrorView, param);
		}

		return returnValue;
	}

	private ModelAndView buildAjaxJsonResponse(Exception exception) {
		ModelAndView jsonView = new ModelAndView(
                new MappingJackson2JsonView());
		if ((exception instanceof BusinessException)) {
            BusinessException busException = (BusinessException) exception;
            jsonView.addObject("code", busException.getErrorCode());
            jsonView.addObject("message", busException.getMessage());
            jsonView.addObject("data", new HashMap<>());
        } else {
            LOGGER.error("异常处理捕获非BusinessException:", exception);
            jsonView.addObject("code", "99999");
            jsonView.addObject("message", "系统异常");
            jsonView.addObject("data", new HashMap<>());
        } return jsonView;
	}

	private boolean isAjaxRequest(HttpServletRequest request, ResponseBody responseBodyAnn) {
		return ("XMLHttpRequest".equals(request.getHeader("X-Requested-With")))
				|| (responseBodyAnn != null);
	}

	public Properties getExceptionMappings() {
		return this.exceptionMappings;
	}

	public void setExceptionMappings(Properties exceptionMappings) {
		this.exceptionMappings = exceptionMappings;
	}

}
