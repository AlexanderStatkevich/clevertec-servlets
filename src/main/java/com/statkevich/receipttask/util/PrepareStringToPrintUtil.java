package com.statkevich.receipttask.util;

import com.statkevich.receipttask.dto.ReceiptDto;
import com.statkevich.receipttask.dto.ReceiptRow;

import java.math.BigDecimal;
import java.util.Formatter;
import java.util.List;

public class PrepareStringToPrintUtil {

    public static String prepareReceipt(ReceiptDto receiptDto) {

        Formatter fmt = new Formatter();
        fmt.format("%15s %15s %15s %15s %15s %15s\n\n", "Quantity", "Description", "Price", "Total", "Sale", "Sale Amount");

        List<ReceiptRow> receiptRowList = receiptDto.receiptRow();
        for (ReceiptRow receiptRow : receiptRowList) {
            receiptRowPrepareView(fmt, receiptRow);
        }
        BigDecimal total = receiptDto.total();
        fmt.format("\n%47s %15s\n", "Total:", total);
        return fmt.toString();
    }

    private static void receiptRowPrepareView(Formatter fmt, ReceiptRow receiptRow) {
        int quantity = receiptRow.quantity();
        String productName = receiptRow.productName();
        BigDecimal price = receiptRow.price();
        BigDecimal totalRowPrice = receiptRow.totalRow();
        BigDecimal salePercentage = (receiptRow.salePercentage() == null)
                ? BigDecimal.ZERO
                : receiptRow.salePercentage();
        BigDecimal saleAmount = (receiptRow.saleAmount() == null)
                ? BigDecimal.ZERO
                : receiptRow.saleAmount();
        fmt.format("%15s %15s %15s %15s %15s %15s\n", quantity, productName, price, totalRowPrice, "-" + salePercentage + "%", "-" + saleAmount);
    }
}
