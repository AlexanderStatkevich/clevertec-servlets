package com.statkevich.receipttask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.statkevich.receipttask.domain.dto.DiscountCardDto;
import com.statkevich.receipttask.dto.PageDto;
import com.statkevich.receipttask.exceptions.DataAccessException;
import com.statkevich.receipttask.exceptions.DiscountCardNotExistException;
import com.statkevich.receipttask.exceptions.ProductNotExistException;
import com.statkevich.receipttask.service.DiscountCardService;
import com.statkevich.receipttask.service.singletonfactories.DiscountCardServiceSingleton;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "DiscountCardServlet", urlPatterns = "/cards")
public class DiscountCardServlet extends HttpServlet {

    private final DiscountCardService discountCardService = DiscountCardServiceSingleton.getInstance();
    private final XmlMapper xmlMapper = new XmlMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            if (null == req.getParameter("number")) {
                pageView(req, writer);
            } else {
                singleView(req, writer);
            }
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (DataAccessException | ProductNotExistException | DiscountCardNotExistException e) {
            resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, e.getMessage());
        }
    }

    private void singleView(HttpServletRequest req, PrintWriter writer) throws JsonProcessingException {
        String cardNumber = req.getParameter("number");
        DiscountCardDto discountCardDto = discountCardService.get(cardNumber);
        String cardInJson = xmlMapper.writeValueAsString(discountCardDto);
        writer.write(cardInJson);
    }

    private void pageView(HttpServletRequest req, PrintWriter writer) throws JsonProcessingException {
        Long page = Long.valueOf(req.getParameter("page"));
        Long size = Long.valueOf(req.getParameter("size"));
        PageDto<DiscountCardDto> pageDto = discountCardService.getPage(page, size);
        String pageInJson = xmlMapper.writeValueAsString(pageDto);
        writer.write(pageInJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        DiscountCardDto discountCardDto = xmlMapper.readValue(reader, DiscountCardDto.class);
        discountCardService.save(discountCardDto);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String cardNumber = req.getParameter("number");
        discountCardService.delete(cardNumber);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        DiscountCardDto discountCardDto = xmlMapper.readValue(reader, DiscountCardDto.class);
        discountCardService.update(discountCardDto);
    }
}
