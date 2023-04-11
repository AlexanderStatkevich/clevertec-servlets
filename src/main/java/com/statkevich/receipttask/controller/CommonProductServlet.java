package com.statkevich.receipttask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.statkevich.receipttask.domain.dto.ProductCreateDto;
import com.statkevich.receipttask.domain.dto.ProductDto;
import com.statkevich.receipttask.dto.PageDto;
import com.statkevich.receipttask.exceptions.DataAccessException;
import com.statkevich.receipttask.exceptions.DiscountCardNotExistException;
import com.statkevich.receipttask.exceptions.ProductNotExistException;
import com.statkevich.receipttask.service.ProductService;
import com.statkevich.receipttask.service.singletonfactories.ProductServiceSingleton;
import com.statkevich.receipttask.util.ValidationUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "CommonProductServlet", urlPatterns = "/products")
public class CommonProductServlet extends HttpServlet {
    private final ProductService productService = ProductServiceSingleton.getInstance();
    private final XmlMapper xmlMapper = new XmlMapper();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = resp.getWriter();
            if (null == req.getParameter("id")) {
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
        Long id = Long.valueOf(req.getParameter("id"));
        ProductDto productDto = productService.get(id);
        String productInJson = xmlMapper.writeValueAsString(productDto);
        writer.write(productInJson);
    }

    private void pageView(HttpServletRequest req, PrintWriter writer) throws JsonProcessingException {
        Long page = Long.valueOf(req.getParameter("page"));
        Long size = Long.valueOf(req.getParameter("size"));
        PageDto<ProductDto> pageDto = productService.getPage(page, size);
        String pageInJson = xmlMapper.writeValueAsString(pageDto);
        writer.write(pageInJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        ProductCreateDto productCreateDto = xmlMapper.readValue(reader, ProductCreateDto.class);
        boolean validated = ValidationUtil.validate(productCreateDto);
        if (validated) {
            productService.save(productCreateDto);
        } else {
            resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long id = Long.valueOf(req.getParameter("id"));
        productService.delete(id);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        ProductDto productDto = xmlMapper.readValue(reader, ProductDto.class);
        productService.update(productDto);
    }
}
