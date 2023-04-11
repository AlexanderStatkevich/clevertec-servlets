package com.statkevich.receipttask.controller;

import com.statkevich.receipttask.dto.OrderDto;
import com.statkevich.receipttask.dto.ReceiptDto;
import com.statkevich.receipttask.exceptions.DataAccessException;
import com.statkevich.receipttask.exceptions.DiscountCardNotExistException;
import com.statkevich.receipttask.exceptions.ProductNotExistException;
import com.statkevich.receipttask.parser.WebInputParser;
import com.statkevich.receipttask.service.OrderService;
import com.statkevich.receipttask.service.singletonfactories.OrderServiceSingleton;
import com.statkevich.receipttask.service.singletonfactories.ProductServiceSingleton;
import com.statkevich.receipttask.util.PdfGenerateUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@WebServlet(name = "ReceiptServlet", urlPatterns = "/order")
public class CreateReceiptServlet extends HttpServlet {

    private final WebInputParser webInputParser = new WebInputParser(ProductServiceSingleton.getInstance());
    private final OrderService orderService = OrderServiceSingleton.getInstance();

    private final PdfGenerateUtil pdfGenerateUtil = new PdfGenerateUtil();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("application/pdf");
            resp.setHeader("Content-disposition", "inline; filename='receipt.pdf'");

            ServletOutputStream outputStream = resp.getOutputStream();

            Map<String, String[]> parameterMap = req.getParameterMap();
            OrderDto orderDto = webInputParser.parse(parameterMap);
            ReceiptDto receiptDto = orderService.processingOrder(orderDto);
            byte[] pdf = pdfGenerateUtil.getPdf(receiptDto);

            outputStream.write(pdf);

        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (DataAccessException | ProductNotExistException | DiscountCardNotExistException e) {
            resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, e.getMessage());
        }
    }
}
