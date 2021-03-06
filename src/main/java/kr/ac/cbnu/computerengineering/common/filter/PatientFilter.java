package kr.ac.cbnu.computerengineering.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.ac.cbnu.computerengineering.common.datatype.UserDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleDataType;
import kr.ac.cbnu.computerengineering.common.datatype.UserRoleType;

@WebFilter("/PatientFilter")
public class PatientFilter implements Filter  {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String uri = req.getRequestURI();
		if(uri.indexOf("login") > 0 || uri.indexOf("css") > 0 || uri.indexOf("img") > 0)
			chain.doFilter(request, response);
		else{
			UserDataType user = (UserDataType) session.getAttribute("user");
			if(user==null){
				((HttpServletResponse)response).sendRedirect("/ADRM/patient/login");
				return;
			}
				
			boolean patient = false;
			for(UserRoleDataType userRole : user.getRoles()) {
				if(userRole.getUserRole() == UserRoleType.PATIENT)
					patient=true;
			}
			if(patient)
				chain.doFilter(request, response);
			else
				((HttpServletResponse)response).sendRedirect("/ADRM/patient/login?resultType=DONTHAVEROLE");
		}

	}

	@Override
	public void destroy() {
		
	}

}
