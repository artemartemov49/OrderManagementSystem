package servlet;

import dto.CreateProductDto;
import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import service.ProductService;
import util.JspHelper;

@WebServlet("/create-product")
public class CreateProductServlet extends HttpServlet {

    private final ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("createProduct"))
            .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateProductDto productDto = CreateProductDto.builder()
            .name(req.getParameter("name"))
            .price(req.getParameter("price"))
            .build();

        try {
            productService.create(productDto);
            resp.sendRedirect("/product-list");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
