package servlet;

import static java.util.stream.Collectors.toMap;

import dao.ProductDao;
import dto.ItemDto;
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

@WebServlet("/edit-order")
public class EditOrderServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();
    private final ItemService itemService = ItemService.getInstance();
    private final ProductDao productDao = ProductDao.getInstance();

    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var orderDto = orderService.findById(req.getParameter("orderId"));

        if (orderDto.isPresent()) {
            var order = orderDto.get();
            req.setAttribute("products", productDao.findAll());
            req.setAttribute("order", order);
            req.setAttribute("productsId", order.getItemDtos().stream().map(ItemDto::getProductId).toList());
            req.setAttribute("countMap",
                order.getItemDtos().stream().collect(toMap(ItemDto::getProductId, ItemDto::getCount)));
            req.getRequestDispatcher(JspHelper.getPath("editOrder"))
                .forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var toParseOrderId = req.getParameter("orderId");
        var productIds = req.getParameterValues("productId");
        var clientFIO = req.getParameter("clientFIO");

        var itemDtos = itemService.createItemDtoList(productIds, req);

        try {
            var orderId = Long.valueOf(toParseOrderId);
            orderService.updateOrder(orderId, clientFIO, itemDtos);
            doGet(req, resp);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }

    }
}
