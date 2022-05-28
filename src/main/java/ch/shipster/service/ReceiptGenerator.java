package ch.shipster.service;

import ch.shipster.data.domain.OrderItem;
import ch.shipster.data.domain.User;
import ch.shipster.data.repository.OrderRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Service
public class ReceiptGenerator {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;


    public void createPDF(HttpServletResponse response, Long orderId) throws IOException {
        User user = orderService.getUser(orderId);
        List<OrderItem> basketItems = orderService.getOrderItems(orderId);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        //Types of text and fonts
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Font fontText = FontFactory.getFont(FontFactory.HELVETICA);
        fontText.setSize(12);

        /*
        //Write Table Header
        public void writeHeader(PdfPTable table){
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(Color.gray);
            cell.setPadding(5);

            font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontHeader.setColor(Color.white);

            cell.setPhrase(new Phrase("Article Name". fontHeader));
            table.addCell
        }
        */

        //Write Table Data

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
        customer.add(user.getFirstName() + " " + user.getLastName());
        customer.add("Address");
        customer.add("ZIP + City");
        customer.add("Country");
        customer.add(user.getEmail());

        //Basket table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(80f);
        //table.setWidthPercentage(new float[] {3.0f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.gray);
        cell.setPadding(5);

        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontHeader.setColor(Color.white);

        cell.setPhrase(new Phrase("Article Name", fontHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Quantity", fontHeader));
        table.addCell(cell);

        for (OrderItem i : basketItems){
            table.addCell(i.toString());
            table.addCell(String.valueOf(i.getQuantity()));
        }

        //Creating PDF
        document.add(title);
        document.add(shipster_logo);
        document.add(company);
        document.add(customer);
        document.add(table);
        document.close();
    }
}
