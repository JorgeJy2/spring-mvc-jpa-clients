package com.jorgejy.springboot.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
	private String url;
	private Page<T> page;
	
	private int totalPages;
	private int numberElementsPages;
	
	private int currentPage; 
	
	private List<PageItem> pages;
	
	
	public PageRender(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		
		this.pages = new ArrayList<PageItem>();
		
		numberElementsPages  = page.getSize();
		totalPages  = page.getTotalPages();
		currentPage = page.getNumber() + 1;
		
		int startPage;
		int endPage;
		
		if(totalPages <=  numberElementsPages) {
			startPage = 1;
			endPage = totalPages;
		} else  {
			if( currentPage <=  numberElementsPages / 2) {
				startPage = 1;
				endPage = numberElementsPages;
			} else if (currentPage >= totalPages  - numberElementsPages  / 2) {
				startPage= totalPages - numberElementsPages +1 ;
				endPage = numberElementsPages;
			} else {
				startPage = currentPage  - numberElementsPages / 2;
				endPage  = numberElementsPages;
			}
		}
		
		for(int i = 0; i < endPage; i++) {
			pages.add(new PageItem(startPage + i , currentPage == startPage + i ));
		}
	}


	public String getUrl() {
		return url;
	}


	public Page<T> getPage() {
		return page;
	}


	public int getTotalPages() {
		return totalPages;
	}


	public int getNumberElementsPages() {
		return numberElementsPages;
	}


	public List<PageItem> getPages() {
		return pages;
	}
	
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return page.hasPrevious();
	}


	public int getCurrentPage() {
		return currentPage;
	}
	
	
}
