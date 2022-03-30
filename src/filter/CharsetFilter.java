package filter;

import static java.nio.charset.StandardCharsets.UTF_8;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/")
public class CharsetFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        servletRequest.setCharacterEncoding(UTF_8.name());
        servletResponse.setCharacterEncoding(UTF_8.name());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
