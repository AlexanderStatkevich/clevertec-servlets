package com.statkevich.receipttask.printer;

public class FilePrinterFactory implements PrinterFactory {

    @Override
    public Printer createPrinter() {
        return new FilePrinter();
    }
}
