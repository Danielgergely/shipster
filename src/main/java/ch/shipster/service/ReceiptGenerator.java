package ch.shipster.service;

import ch.shipster.data.repository.OrderRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class ReceiptGenerator {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;


    public void createPDF(HttpServletResponse response, Long orderId) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(receipt, response.getOutputStream());

        document.open();

        //Types of text and fonts
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Font fontText = FontFactory.getFont(FontFactory.HELVETICA);
        fontText.setSize(12);

        //Title
        Paragraph title = new Paragraph("Shipster Receipt / Order Nr. " + orderId, fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);

        //Company logo
        document.add(new Paragraph("Shipster Logo"));
        Image shipster_logo = Image.getInstance("shipster.jpeg");
        //Alignment missing

        //Company Infos
        Paragraph company = new Paragraph("Shipster Shipping Company:", fontText);
        company.add("Shipping Street 123");
        company.add("Shipping City");
        company.add("info@shipster.ch");
        company.add("+41 62 888 77 66");
        company.setAlignment(Paragraph.ALIGN_LEFT);

        //Customer information
        Paragraph customer = new Paragraph("Customer Information:", fontText);
        customer.add("Customer Name");
        customer.add("Street");
        customer.add("ZIP + City");
        customer.add("Country");
        customer.add("email");

        //Creating PDF
        document.add(title);
        document.add(shipster_logo);
        document.add(company);
        document.add(customer);
        document.close();
    }
}
