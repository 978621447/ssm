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
 *
 *
 * @author 郑翔
 */
public class AnnotationHandlerMethodExceptionResolver
		extends ExceptionHandlerExceptionResolver {

	private static Logger logger = LoggerFactory
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
		if (handlerMethod == null) {
			return null;
		}
		Method method = handlerMethod.getMethod();
		ModelAndView returnValue = super.doResolveHandlerMethodException(
				request, response, handlerMethod, exception);
		ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method,
				ResponseBody.class);
		if (("XMLHttpRequest".equals(request.getHeader("X-Requested-With")))
				|| (responseBodyAnn != null)) {
			try {
				ModelAndView jsonView = new ModelAndView(
						new MappingJackson2JsonView());
				if ((exception instanceof BusinessException)) {
					BusinessException bexception = (BusinessException) exception;
					jsonView.addObject("code", bexception.getErrorCode());
					jsonView.addObject("message", bexception.getMessage());
					jsonView.addObject("data", new HashMap<Object, Object>());
				} else {
					logger.error("异常处理捕获非BusinessException:", exception);
					jsonView.addObject("code", "99999");
					jsonView.addObject("message", "系统异常");
					jsonView.addObject("data", new HashMap<>());
				}
				response.setStatus(200);
				return jsonView;
			} catch (Exception e) {
				response.setStatus(200);
				ModelAndView jsonView = new ModelAndView(
						new MappingJackson2JsonView());
				logger.error("异常处理捕获失败:" + Arrays.toString(e.getStackTrace()));
				jsonView.addObject("code", "99998");
				jsonView.addObject("message", "系统异常");
				jsonView.addObject("data", new HashMap<Object, Object>());
				return jsonView;
			}
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
					logger.error("系统捕获" + entry.getKey() + ":" + errorMsg);
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

	public Properties getExceptionMappings() {
		return this.exceptionMappings;
	}

	public void setExceptionMappings(Properties exceptionMappings) {
		this.exceptionMappings = exceptionMappings;
	}

}
