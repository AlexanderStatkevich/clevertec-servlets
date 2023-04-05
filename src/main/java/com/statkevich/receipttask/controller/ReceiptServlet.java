package com.statkevich.receipttask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.statkevich.receipttask.dto.OrderDto;
import com.statkevich.receipttask.dto.ReceiptDto;
import com.statkevich.receipttask.exceptions.DataAccessException;
import com.statkevich.receipttask.exceptions.DiscountCardNotExistException;
import com.statkevich.receipttask.exceptions.ProductNotExistException;
import com.statkevich.receipttask.parser.WebInputParser;
import com.statkevich.receipttask.service.OrderService;
import com.statkevich.receipttask.service.singletonfactories.OrderServiceSingleton;
import com.statkevich.receipttask.service.singletonfactories.ProductServiceSingleton;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@WebServlet(name = "ReceiptServlet", urlPatterns = "/order")
public class ReceiptServlet extends HttpServlet {

    private final WebInputParser webInputParser = new WebInputParser(ProductServiceSingleton.getInstance());
    private final OrderService orderService = OrderServiceSingleton.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            Map<String, String[]> parameterMap = req.getParameterMap();
            OrderDto orderDto = webInputParser.parse(parameterMap);
            ReceiptDto receiptDto = orderService.processingOrder(orderDto);
            String receiptJson = objectWriter.writeValueAsString(receiptDto);
            writer.write(receiptJson);

        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (DataAccessException | ProductNotExistException | DiscountCardNotExistException e) {
            resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, e.getMessage());
        }
    }
}
