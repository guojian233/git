package com.zl.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iqn.bean.User;

/**
 * 未登录用户只可以访问login.jsp
 */
public class LoginStatusFilter implements Filter{
	private static List<String> loginOutlist = new ArrayList<String>();
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		//获取用户需要访问的servlet
		String url =request.getRequestURI();
		
		//判断用户是否登录
		User user = (User) request.getSession().getAttribute("user");
		if(user!=null||
				url.endsWith("css")||url.endsWith("js")||url.endsWith("png")||url.endsWith("gif")||url.endsWith("jpg")
				||url.endsWith(".do")
				){
			arg2.doFilter(request, response);
		}else{
			//未登录只能访问loginOutlist中指定的页面
			for (String item : loginOutlist) {
				//如果和未登录状态指定的可访问页面不一致。则返回到登录页面
				if(!url.equals(item)){
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					return ;
				}else{
					arg2.doFilter(request, response);
					return ;
				}
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		loginOutlist.add("/SMBMS/");
		loginOutlist.add("/SMBMS/login.jsp");
	}
	
}
