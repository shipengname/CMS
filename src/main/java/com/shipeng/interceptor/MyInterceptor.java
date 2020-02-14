package com.shipeng.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.shipeng.bean.User;

public class MyInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null) {
			return true;
		}else {
			request.setAttribute("error", "请先登录");
			String requestURI = request.getRequestURI();
			System.out.println("requestURI===="+requestURI);
			if(requestURI.equals("/admin")) {
				request.getRequestDispatcher("WEB-INF/view/admin/login.jsp").forward(request, response);
			}else if(requestURI.equals("/my")){
				request.getRequestDispatcher("WEB-INF/view/index/login.jsp").forward(request, response);
			}
			return false;
		}
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
