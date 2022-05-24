package ch.shipster.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class ReceiptGenerator {
    public void createPDF(HttpServletResponse response) throws IOException {
        Document receipt = new Document(PageSize.A4);
        PdfWriter.getInstance(receipt, response.getOutputStream());

        receipt.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph title = new Paragraph("Shipster Receipt", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontText = FontFactory.getFont(FontFactory.HELVETICA);
        fontText.setSize(12);

        Paragraph text = new Paragraph("This is a test", fontText);
        text.setAlignment(Paragraph.ALIGN_LEFT);

        receipt.add(title);
        receipt.add(text);
        receipt.close();
    }
}
