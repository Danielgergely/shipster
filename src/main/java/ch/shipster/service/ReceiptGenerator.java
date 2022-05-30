package ch.shipster.service;

import ch.shipster.data.domain.Address;
import ch.shipster.data.domain.Article;
import ch.shipster.data.domain.OrderItem;
import ch.shipster.data.domain.User;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    @Autowired
    ArticleService articleService;

    @Autowired
    AddressService addressService;

    @Autowired
    ShippingCostCalculator shippingCostCalculator;

    public void createPDF(HttpServletResponse response, Long orderId) throws IOException, InterruptedException {
        User user = orderService.getUser(orderId);
        Float productCost = 0.0f;
        List<OrderItem> basketItems = orderService.getOrderItems(orderId);
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        //Types of text and fonts
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Font fontText = FontFactory.getFont(FontFactory.HELVETICA);
        fontText.setSize(12);

        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontHeader.setSize(14);
        fontHeader.setColor(Color.white);

        Font infoHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        infoHeader.setSize(14);
        infoHeader.setColor(Color.black);

        //Title
        Paragraph title = new Paragraph("Shipster Receipt / Order Nr. " + orderId, fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(50);

        //Company logo
        Image shipster_logo = Image.getInstance("src/main/resources/static/images/shipster.jpeg");
        shipster_logo.setAlignment(Element.ALIGN_LEFT);
        shipster_logo.scaleAbsolute(130, 100);

        //Company Infos
        Paragraph seller = new Paragraph("Seller Information", infoHeader);
        Paragraph company = new Paragraph("Shipster Shipping Company", fontText);
        Paragraph street = new Paragraph("Bahnhofstrasse 6", fontText);
        Paragraph city = new Paragraph("5210 Windisch", fontText);
        Paragraph email = new Paragraph("info@shipster.ch", fontText);
        Paragraph country = new Paragraph("Switzerland", fontText);
        seller.setAlignment(Paragraph.ALIGN_LEFT);
        company.setAlignment(Paragraph.ALIGN_LEFT);
        street.setAlignment(Paragraph.ALIGN_LEFT);
        city.setAlignment(Paragraph.ALIGN_LEFT);
        email.setAlignment(Paragraph.ALIGN_LEFT);
        country.setAlignment(Paragraph.ALIGN_LEFT);

        //Customer information
        Address address = addressService.findAddressById(user.getAddressId());
        Paragraph buyer = new Paragraph("Customer Information", infoHeader);
        Paragraph customer = new Paragraph(user.getFirstName() + " " + user.getLastName(), fontText);
        Paragraph cEmail = new Paragraph(user.getEmail(), fontText);
        Paragraph cStreet = new Paragraph(address.getStreet() + " "  + address.getNumber(), fontText);
        Paragraph cCity = new Paragraph(address.getZip() +  " " + address.getCity(), fontText);
        Paragraph cCountry = new Paragraph(address.getCountry());
        buyer.setAlignment(Paragraph.ALIGN_RIGHT);
        customer.setAlignment(Paragraph.ALIGN_RIGHT);
        cEmail.setAlignment(Paragraph.ALIGN_RIGHT);
        cStreet.setAlignment(Paragraph.ALIGN_RIGHT);
        cCity.setAlignment(Paragraph.ALIGN_RIGHT);
        cCity.setAlignment(Paragraph.ALIGN_RIGHT);
        cCountry.setAlignment(Paragraph.ALIGN_RIGHT);

        // Info box
        PdfPTable info = new PdfPTable(2);
        info.getDefaultCell().setBorder(0);
        info.addCell(seller);
        info.addCell(buyer);
        info.addCell(company);
        info.addCell(customer);
        info.addCell(email);
        info.addCell(cEmail);
        info.addCell(street);
        info.addCell(cStreet);
        info.addCell(city);
        info.addCell(cCity);
        info.addCell(country);
        info.addCell(cCountry);

        //Basket table
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(80f);
        //table.setWidthPercentage(new float[] {3.0f, 3.0f, 3.0f});
        table.setSpacingBefore(30);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.gray);
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Article Name", fontHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Quantity", fontHeader));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Price", fontHeader));
        table.addCell(cell);

        for (OrderItem i : basketItems){
            Article article = articleService.findById(i.getArticleId());
            table.addCell(article.getName());
            table.addCell(String.valueOf(i.getQuantity()));
            table.addCell(String.valueOf(i.getQuantity() * orderItemService.getArticle(i).getPrice()) + ".- CHF");
            productCost = productCost + (i.getQuantity() * orderItemService.getArticle(i).getPrice());
        }

        //Total Product-Cost Cell
        table.addCell("");
        table.addCell("Total Product Cost");
        table.addCell(String.valueOf(productCost) + ".- CHF");

        //Total Pallet
        table.addCell("");
        table.addCell("Total Pallets");
        table.addCell(String.valueOf(shippingCostCalculator.palletCalculation(orderId)) + " Pallet(s)");

        //Total Shipping-Cost Cell
        table.addCell("");
        table.addCell("Total Shipping Cost");
        table.addCell(String.valueOf(shippingCostCalculator.costCalculation(orderId)) + ".- CHF");

        //Total Overall-Cost Cell
        table.addCell("");
        table.addCell("Grand Total Cost");
        table.addCell(String.valueOf((shippingCostCalculator.costCalculation(orderId)) + (shippingCostCalculator.palletCalculation(orderId))) + ".- CHF");

        //Creating PDF
        document.add(shipster_logo);
        document.add(title);
        document.add(info);
        document.add(table);
        document.close();
    }
}
