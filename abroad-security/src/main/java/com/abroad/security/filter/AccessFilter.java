package com.abroad.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abroad.common.comn.ResponseJson;
import com.abroad.common.comn.StatusCode;
import com.abroad.common.comn.web.ServletTools;
import com.abroad.security.cache.LocalCache;

/**
* @ClassName: AccessFilter
* @Description: TODO(访问过滤器)
* @author: mengxr
* @date 2017年3月29日 下午6:08:54
*/
public class AccessFilter implements Filter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	String url = ServletTools.getContextPath((HttpServletRequest)request);
    	boolean flag = LocalCache.ACCESS_URL.contains(url);
    	if(flag){ //如果还有的话继续走下一个过滤器
            chain.doFilter(request, response);
    	}else{
    		//没有找到相应的资源
    		ServletTools.sendResponse((HttpServletResponse) response, new ResponseJson(StatusCode.NOT_FOUND, false));
    	}
    }
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("访问过滤器AccessFilter注册成功----");
	}

	@Override
	public void destroy() {
		logger.info("访问过滤器AccessFilter摧毁成功-----");
	}
 
}