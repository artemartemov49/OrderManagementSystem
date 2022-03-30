package servlet;

import dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import util.JspHelper;

@WebServlet("/product-list")
public class ProductListServlet extends HttpServlet {

    private final ProductDao productDao = ProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productDao.findAll());
        req.getRequestDispatcher(JspHelper.getPath("productList"))
            .forward(req, resp);
    }
}
