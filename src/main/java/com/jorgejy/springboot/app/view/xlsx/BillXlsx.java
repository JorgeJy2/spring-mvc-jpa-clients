package com.jorgejy.springboot.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.jorgejy.springboot.app.model.entity.Bill;
import com.jorgejy.springboot.app.model.entity.ItemBill;

@Component("bill/show.xlsx")
public class BillXlsx extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MessageSourceAccessor messageSourceAccessor = getMessageSourceAccessor();

		Bill bill = (Bill) model.get("bill");

		Sheet sheet = workbook.createSheet("bill spring");

		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);

		CellStyle theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.AQUA.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);

		cell.setCellValue(messageSourceAccessor.getMessage("text.bill.show.client"));
		cell.setCellStyle(tbodyStyle);

		row = sheet.createRow(1);
		cell = row.createCell(0);

		cell.setCellValue(messageSourceAccessor.getMessage("text.client.name"));
		cell.setCellStyle(tbodyStyle);

		cell = row.createCell(1);
		cell.setCellValue(bill.getClient().getName() + " " + bill.getClient().getFirstName());
		cell.setCellStyle(tbodyStyle);

		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(messageSourceAccessor.getMessage("text.client.email"));
		cell.setCellStyle(tbodyStyle);

		cell = row.createCell(1);
		cell.setCellValue(bill.getClient().getEmail());
		cell.setCellStyle(tbodyStyle);

		row = sheet.createRow(4);
		cell = row.createCell(0);

		cell.setCellValue(messageSourceAccessor.getMessage("text.bill.show.bill"));
		cell.setCellStyle(tbodyStyle);

		row = sheet.createRow(5);
		cell = row.createCell(0);

		cell.setCellValue(messageSourceAccessor.getMessage("text.bill.folio") + ":");
		cell.setCellStyle(tbodyStyle);

		cell = row.createCell(1);
		cell.setCellValue(bill.getId());
		cell.setCellStyle(tbodyStyle);

		row = sheet.createRow(6);
		cell = row.createCell(0);

		cell.setCellValue(messageSourceAccessor.getMessage("text.bill.description") + ":");
		cell.setCellStyle(tbodyStyle);

		cell = row.createCell(1);
		cell.setCellValue(bill.getDescription());
		cell.setCellStyle(tbodyStyle);

		row = sheet.createRow(7);
		cell = row.createCell(0);

		cell.setCellValue(messageSourceAccessor.getMessage("text.bill.date") + ":");
		cell.setCellStyle(tbodyStyle);

		cell = row.createCell(1);
		cell.setCellValue(bill.getCreateAt());
		cell.setCellStyle(tbodyStyle);

		Row headerProducts = sheet.createRow(9);
		headerProducts.createCell(0).setCellValue(messageSourceAccessor.getMessage("text.bill.products.id"));
		headerProducts.createCell(1).setCellValue(messageSourceAccessor.getMessage("text.bill.products.price"));
		headerProducts.createCell(2).setCellValue(messageSourceAccessor.getMessage("text.bill.products.quantity"));
		headerProducts.createCell(3).setCellValue(messageSourceAccessor.getMessage("text.bill.products.total"));

		headerProducts.getCell(0).setCellStyle(theaderStyle);
		headerProducts.getCell(1).setCellStyle(theaderStyle);
		headerProducts.getCell(2).setCellStyle(theaderStyle);
		headerProducts.getCell(3).setCellStyle(theaderStyle);

		int rowItemNumber = 11;
		for (ItemBill itemBill : bill.getItems()) {
			Row itemRow = sheet.createRow(rowItemNumber++);

			cell = itemRow.createCell(0);

			cell.setCellValue(itemBill.getProduct().getName());
			cell.setCellStyle(tbodyStyle);

			cell = itemRow.createCell(1);
			cell.setCellValue(itemBill.getProduct().getPrice());
			cell.setCellStyle(tbodyStyle);

			cell = itemRow.createCell(2);
			cell.setCellValue(itemBill.getQuantity());
			cell.setCellStyle(tbodyStyle);

			cell = itemRow.createCell(3);
			cell.setCellValue(itemBill.calculateAmount());
			cell.setCellStyle(tbodyStyle);
		}

		Row totalRow = sheet.createRow(rowItemNumber);
		cell = totalRow.createCell(2);
		cell.setCellValue(messageSourceAccessor.getMessage("text.bill.products.total") + ":");
		cell.setCellStyle(tbodyStyle);

		cell = totalRow.createCell(3);
		cell.setCellValue(bill.getTotal());
		cell.setCellStyle(tbodyStyle);

		response.setHeader("Content-Disposition", "attachment; filename=\"bill_view.xlsx\"");
	}

}
