package com.example.nhanct.config;

import javax.activation.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * PDFDataSource
 *
 * @author Smartee
 * @date 7/28/2023 10:54 AM
 */
public class PDFDataSource implements DataSource {
    private byte[] data;
    private String contentType;

    public PDFDataSource(byte[] data, String contentType) {
        this.data = data;
        this.contentType = contentType;
    }

    public PDFDataSource(byte[] data) {
        this.data = data;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(data);
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Read-only data source");
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getName() {
        return "PDFExport";
    }
}
