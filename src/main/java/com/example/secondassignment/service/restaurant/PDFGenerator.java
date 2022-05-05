package com.example.secondassignment.service.restaurant;

import com.example.secondassignment.model.Food;
import com.example.secondassignment.model.Restaurant;
import lombok.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.*;

/**
 * PDFWriter is a class responsible for writing into a .pdf file the data of the bill created when an order is placed by a client.
 *
 * @author Denisa Filip
 * @version $Id: $Id
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class PDFGenerator {

    @NonNull
    private Restaurant restaurant;
    private PDPage currentPage = new PDPage();

    /**
     *<p> writeMenuToPDF is responsible for exporting the menu of a restaurant to a PDF. </p>
     * @return The path of the created PDF document.
     */
    public String writeMenuToPDF() {
        try (PDDocument doc = new PDDocument()) {

            doc.addPage(currentPage);
            PDRectangle mediaBox = currentPage.getMediaBox();
            try (PDPageContentStream contentStream = new PDPageContentStream(doc, currentPage)) {
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                centerTitle(contentStream, mediaBox);

                writeRestaurantData(contentStream);
                writeMenuData(doc, contentStream);
            }

            String path = "src/main/resources/pdfs/menu" + this.restaurant.getName() + ".pdf";
            doc.save(path);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Writes the details of a restaurant to the PDF.
     * @param contentStream with which the data is written to the PDF document
     * @throws IOException If an error occurs during writing.
     */
    private void writeRestaurantData(PDPageContentStream contentStream) throws IOException {
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
        contentStream.newLine();
        contentStream.newLine();
        contentStream.newLine();
        contentStream.showText("Details of the restaurant: ");
        contentStream.newLine();
        contentStream.showText("    Name: " + restaurant.getName());
        contentStream.newLine();
        contentStream.showText("    Address: " + restaurant.getAddress().getStreet() + ", " + restaurant.getAddress().getNumber());
        contentStream.newLine();
        contentStream.newLine();
    }

    /**
     * Writes the menu of the restaurant to the PDF document.
     * @param doc to which the data is written
     * @param contentStream with which the data is written to the PDF document
     * @throws IOException If an error occurs during writing.
     */
    private void writeMenuData(PDDocument doc, PDPageContentStream contentStream) throws IOException {
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 15);
        contentStream.showText("The menu of the restaurant: ");
        contentStream.newLine();
        contentStream.newLine();
        int indexOfCurrentMenuItem = 1;
        List<Food> foods = new ArrayList<>(this.restaurant.getFoods());
        foods.sort(Comparator.comparing(f -> f.getCategory().getName()));
        for (Food food : foods) {
            if (indexOfCurrentMenuItem % 8 == 0) {
                contentStream.endText();
                this.currentPage = new PDPage();
                doc.addPage(currentPage);
                contentStream.close();
                contentStream = new PDPageContentStream(doc, currentPage);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 700);
            }
            contentStream.setFont(PDType1Font.TIMES_BOLD, 15);
            contentStream.showText(food.getCategory().getName() + " - ");
            contentStream.newLine();
            contentStream.showText(food.getName() + ": ");
            contentStream.newLine();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
            contentStream.showText(food.getDescription());
            contentStream.newLine();
            contentStream.setFont(PDType1Font.TIMES_BOLD, 14);
            contentStream.showText("     price: " + food.getPrice());
            contentStream.newLine();
            contentStream.showText("--------------------------------------------------------------------------------------------");
            contentStream.newLine();
            indexOfCurrentMenuItem++;
        }
        contentStream.newLine();
        contentStream.newLine();
        contentStream.endText();
        contentStream.close();

    }

    /**
     * Centers the title of the PDF document.
     * @param contentStream with which the data is written to the PDF document
     * @param mediaBox: the shape of the PDF document.
     * @throws IOException If an error occurs during writing.
     */
    private void centerTitle(PDPageContentStream contentStream, PDRectangle mediaBox) throws IOException {
        String title = "RESTAURANT " + this.restaurant.getName();
        PDFont font = PDType1Font.TIMES_BOLD;
        int marginTop = 30;
        int fontSize = 22;

        float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

        float startX = (mediaBox.getWidth() - titleWidth) / 2;
        float startY = mediaBox.getHeight() - marginTop - titleHeight;

        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(startX, startY);

        contentStream.showText(title);
        contentStream.newLine();
        contentStream.newLineAtOffset(-startX+25, -15);
    }

}
