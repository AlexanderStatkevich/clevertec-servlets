package com.statkevich.receipttask.printer;

public class PdfPrinterFactory implements PrinterFactory {

    @Override
    public Printer createPrinter() {
        return new PdfPrinter();
    }
}
