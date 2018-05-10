package com.jdr.interview.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Created by SongpoLiu on 2017/5/28.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 静态页面配置
     *
     * @param registry
     */
	@Bean
	public SecurityInterceptor getSecurityInterceptor() {
		return new SecurityInterceptor();
	}
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

		
		// 拦截配置
		addInterceptor.addPathPatterns("/adminer**");
		addInterceptor.addPathPatterns("/to**");
	}
	public final static String SESSION_KEY = "user";
	private class SecurityInterceptor extends HandlerInterceptorAdapter {

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			HttpSession session = request.getSession();
			if (session.getAttribute(SESSION_KEY) != null)
				return true;

			// 跳转登录
			String url = "/interview/login";
			response.sendRedirect(url);
			return false;
		}
	}
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	
        // 登录
        registry.addViewController("/login").setViewName("login");
        //主页                                           
        registry.addViewController("/tomain").setViewName("main");
        //一级标题管理
        registry.addViewController("/totitle").setViewName("unorder");
        //二级标题管理
        registry.addViewController("/totitle2").setViewName("unorder3");
        //问题管理
        registry.addViewController("/toquestion").setViewName("question");
        //添加问题
        registry.addViewController("/toaddques").setViewName("addQuestion");
        //轮播图管理
        registry.addViewController("/tolunbo").setViewName("lunbo");
        //消息管理
        registry.addViewController("/tomessage").setViewName("message");
        super.addViewControllers(registry);
    }
}
