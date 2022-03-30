package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import service.OrderService;
import util.JspHelper;

@WebServlet("/order-list")
public class OrderListServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("orders", orderService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("orderList"))
            .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var orderId = req.getParameter("deleteOrder");
        orderService.deleteOrder(Long.valueOf(orderId));
        doGet(req, resp);
    }
}
