package com.jorgejy.springboot.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.jorgejy.springboot.app.model.entity.Bill;
import com.jorgejy.springboot.app.model.entity.ItemBill;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Component("bill/show")
public class BillPdfView extends AbstractPdfView {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Bill bill =(Bill) model.get("bill");
		
		
		MessageSourceAccessor messageSourceAccessor = getMessageSourceAccessor();
		
		
		PdfPTable tableClient = new PdfPTable(1);
		tableClient.setSpacingAfter(20);
		Font font = new Font();
		font.setColor(Color.DARK_GRAY);

		PdfPCell cellTitle = null;
		Locale locale = localeResolver.resolveLocale(request);
		
		cellTitle = new PdfPCell(new Phrase(messageSource.getMessage("text.bill.show.client",null, locale), font));
		cellTitle.setBackgroundColor(new Color(184,218,255));
		cellTitle.setPadding(8f);
		 
		tableClient.addCell(cellTitle);
		tableClient.addCell(bill.getClient().getName()+ " "+bill.getClient().getFirstName());
		tableClient.addCell(bill.getClient().getEmail());
		
		PdfPTable tableBill = new PdfPTable(1);
		tableBill.setSpacingAfter(20);
		
		cellTitle = new PdfPCell(new Phrase(messageSource.getMessage("text.bill.show.bill",null, locale), font));
		cellTitle.setBackgroundColor(new Color(195,230,203));
		cellTitle.setPadding(8f);

		
		tableBill.addCell(cellTitle );
		tableBill.addCell(messageSourceAccessor.getMessage("text.bill.folio") +": " + bill.getId());
		tableBill.addCell(messageSourceAccessor.getMessage("text.bill.description") +": " + bill.getDescription());
		tableBill.addCell(messageSourceAccessor.getMessage("text.bill.date") +": " + bill.getCreateAt());
		
		
		PdfPTable tableProducts = new PdfPTable(4);
		tableProducts.setWidths(new float[] {3.5f,1,1,1});
		tableProducts.setSpacingAfter(20);
		tableProducts.addCell(messageSourceAccessor.getMessage("text.bill.products.id").concat(":"));
		tableProducts.addCell(messageSourceAccessor.getMessage("text.bill.products.price").concat(":"));
		tableProducts.addCell(messageSourceAccessor.getMessage("text.bill.products.quantity").concat(":"));
		tableProducts.addCell(messageSourceAccessor.getMessage("text.bill.products.total").concat(":"));
		
		
		for(ItemBill item: bill.getItems()) {
			tableProducts.addCell(item.getProduct().getName());
			tableProducts.addCell(item.getProduct().getPrice().toString());
			PdfPCell quantityCell = new PdfPCell(new Phrase(item.getQuantity().toString()));
			quantityCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tableProducts.addCell(quantityCell);
			tableProducts.addCell(item.calculateAmount().toString());
			
		}
		
		PdfPCell totalCell = new PdfPCell(new Phrase(messageSourceAccessor.getMessage("text.bill.products.total")+": ")); 
		totalCell.setColspan(3);
		totalCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		
		tableProducts.addCell(totalCell);
		tableProducts.addCell(bill.getTotal().toString());
		
		
		document.add(tableClient);
		document.add(tableBill);
		document.add(tableProducts);
		
		
	}

}
