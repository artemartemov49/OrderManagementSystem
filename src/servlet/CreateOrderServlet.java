package servlet;

import dao.ProductDao;
import dto.CreateOrderDto;
import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import service.ItemService;
import service.OrderService;
import util.JspHelper;

@WebServlet("/create-order")
public class CreateOrderServlet extends HttpServlet {

    private final ProductDao productDao = ProductDao.getInstance();
    private final OrderService orderService = OrderService.getInstance();
    private final ItemService itemService = ItemService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productDao.findAll());
        req.getRequestDispatcher(JspHelper.getPath("createOrder"))
            .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var productIds = req.getParameterValues("productId");
        var itemDtos = itemService.createItemDtoList(productIds, req);
        var orderDto = CreateOrderDto.builder()
            .userFIO(req.getParameter("clientFIO"))
            .itemListDto(itemDtos)
            .build();

        try {
            orderService.create(orderDto);
            resp.sendRedirect("/order-list");
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }

    }

}
