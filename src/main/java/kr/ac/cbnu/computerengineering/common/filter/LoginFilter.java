package kr.ac.cbnu.computerengineering.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * LoginFilter implements Filter
 * 
 */

public class LoginFilter implements Filter{
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		if(uri.indexOf("user") < 0){
			if(uri.indexOf("css") > 0 || uri.indexOf("img") > 0){
				chain.doFilter(request, response);
			}
			else{
				HttpSession session = ((HttpServletRequest) request).getSession(); 
				if(session.getAttribute("user")==null)
				{
					((HttpServletResponse)response).sendRedirect("/ADRM/user/login");
					return ;
				}
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
