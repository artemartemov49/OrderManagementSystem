package service;

import dao.OrderDao;
import dto.CreateItemDto;
import dto.CreateOrderDto;
import dto.OrderDto;
import entity.Item;
import exception.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mapper.CreateItemMapper;
import mapper.CreateOrderMapper;
import util.LocalDateFormatter;
import validator.ByDateIntervalValidator;
import validator.CreateItemValidator;
import validator.CreateOrderValidator;
import validator.UpdateNameOrderValidator;

public class OrderService {

    private static final OrderService INSTANCE = new OrderService();
    private final OrderDao orderDao = OrderDao.getInstance();
    private final CreateOrderMapper createOrderMapper = CreateOrderMapper.getInstance();
    private final CreateOrderValidator createOrderValidator = CreateOrderValidator.getInstance();
    private final CreateItemValidator createItemValidator = CreateItemValidator.getInstance();
    private final ItemService itemService = ItemService.getInstance();
    private final ByDateIntervalValidator byDateIntervalValidator = ByDateIntervalValidator.getInstance();
    private final UpdateNameOrderValidator updateNameOrderValidator = UpdateNameOrderValidator.getInstance();
    private final CreateItemMapper createItemMapper = CreateItemMapper.getInstance();

    public List<OrderDto> findAll() {
        return orderDao.findAll();
    }

    public List<OrderDto> findByDateInterval(String from, String to) {
        var validationResult = byDateIntervalValidator.isValid(List.of(from, to));
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        return orderDao.findByDateInterval(LocalDateFormatter.format(from), LocalDateFormatter.format(to));
    }

    public Optional<OrderDto> findById(String orderId) {
        return orderDao.findById(Long.valueOf(orderId));
    }

    public void create(CreateOrderDto orderDto) {
        var orderValidationResult = createOrderValidator.isValid(orderDto);
        if (!orderValidationResult.isValid()) {
            throw new ValidationException(orderValidationResult.getErrors().stream().distinct().toList());
        }

        var order = createOrderMapper.mapFrom(orderDto);
        var createdOrder = orderDao.create(order);
        itemService.create(createdOrder);
    }

    public void deleteOrder(Long orderId) {
        orderDao.findById(orderId)
            .ifPresent(order -> orderDao.deleteOrder(order.getId()));
    }

    public void updateOrder(Long orderId, String clientFIO, List<CreateItemDto> itemDtos) {
        updateName(orderId, clientFIO);
        updateOrderItems(orderId, itemDtos);
    }

    public void updateOrderItems(Long orderId, List<CreateItemDto> itemDtos) {
        var orderValidationResult = createItemValidator.isValid(itemDtos);
        if (!orderValidationResult.isValid()) {
            throw new ValidationException(orderValidationResult.getErrors());
        }
        List<Item> items = new ArrayList<>();
        itemDtos.stream()
            .map(createItemMapper::mapFrom)
            .forEach(items::add);

        orderDao.nullifyTotalSum(orderId);
        itemService.updateItems(items, orderId);

    }

    public void updateName(Long orderId, String clientFIO) {
        var nameOrderValidatorValid = updateNameOrderValidator.isValid(clientFIO);
        if (!nameOrderValidatorValid.isValid()) {
            throw new ValidationException(nameOrderValidatorValid.getErrors());
        }
        var order = orderDao.findById(orderId);
        if (order.isPresent() && !Objects.equals(order.get().getClientFIO(), clientFIO)) {
            orderDao.updateOrderName(orderId, clientFIO);
        }

    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
