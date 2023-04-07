package com.example.application.views.list;

import java.util.Collections;

import com.example.application.data.entity.Contact;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "")
@PageTitle("Contacts | Vaadin CRM")
public class ListView extends VerticalLayout
{
	TextField tfFilter = new TextField();
	Grid<Contact> contactGrid;
	ContactForm contactForm;

	public ListView()
	{
		addClassName( "list-view" );
		setSizeFull();
		
		add( createMenu() );
		add( createContentLayout() );
	}

	private HorizontalLayout createContentLayout()
	{
		HorizontalLayout contentLayout = new HorizontalLayout();
		contentLayout.add( createGrid() );
		contentLayout.add( createForm() );
		contentLayout.setFlexGrow( 2, contactGrid );
		contentLayout.setFlexGrow( 1, contactForm );
		contentLayout.addClassNames( "content" );
		contentLayout.setSizeFull();
		
		return contentLayout;
	}

	private Component createGrid()
	{
		contactGrid = new Grid<>( Contact.class );
		contactGrid.setSizeFull();
		contactGrid.addClassNames( "contact-grid" );
		
		contactGrid.setColumns( "firstName", "lastName", "email" );
		contactGrid.addColumn( contact -> contact.getStatus().getName() ).setHeader( "Status" );
		contactGrid.addColumn( contact -> contact.getCompany().getName() ).setHeader( "Company" );
		contactGrid.getColumns().forEach( col -> col.setAutoWidth( true ) );

		return contactGrid;
	}

	private Component createForm()
	{
		contactForm = new ContactForm( Collections.emptyList(), Collections.emptyList() );
		contactForm.setWidth( "25em" );
		
		return contactForm;
	}

	private HorizontalLayout createMenu()
	{
		tfFilter.setPlaceholder( "Filter by name..." );
		tfFilter.setClearButtonVisible( true );
		tfFilter.setValueChangeMode( ValueChangeMode.LAZY );

		Button addContactButton = new Button( "Add contact" );

		HorizontalLayout menuLayout = new HorizontalLayout();
		menuLayout.add( tfFilter );
		menuLayout.add( addContactButton );
		menuLayout.addClassName( "toolbar" );

		return menuLayout;
	}	
}