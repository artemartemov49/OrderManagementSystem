package servlet;

import exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import service.OrderService;
import util.JspHelper;

@WebServlet("/filter-orders")
public class OrdersFilterServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("filterOrders"))
            .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = " There were no orders in this period!";
        var fromDate = req.getParameter("fromDate");
        var toDate = req.getParameter("toDate");

        try {
            var orderDtos = orderService.findByDateInterval(fromDate, toDate);
            req.setAttribute("ordersByDate", orderDtos);
            if (orderDtos.isEmpty()) {
                req.setAttribute("noOrders", message);
            }
            doGet(req, resp);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
