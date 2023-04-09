package com.example.application.views.list;

import java.util.Collections;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
	CrmService service;

	public ListView( CrmService service )
	{
		this.service = service;
		addClassName( "list-view" );
		setSizeFull();

		add( createMenu() );
		add( createContentLayout() );

		closeEditor();
		updateList();
	}

	private HorizontalLayout createMenu()
	{
		tfFilter.setPlaceholder( "Search by name or email ..." );
		tfFilter.setClearButtonVisible( true );
		tfFilter.setValueChangeMode( ValueChangeMode.LAZY );
		tfFilter.addValueChangeListener( e -> updateList() );
		tfFilter.setWidth( "85%" );

		Button btnAddContact = new Button( "Add contact" );
		btnAddContact.addThemeVariants( ButtonVariant.LUMO_PRIMARY );
		btnAddContact.addClickListener( e -> addContact() );

		HorizontalLayout menuLayout = new HorizontalLayout();
		menuLayout.add( tfFilter );
		menuLayout.add( btnAddContact );
		menuLayout.addClassName( "toolbar" );
		menuLayout.setWidthFull();

		return menuLayout;
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

		contactGrid.asSingleSelect().addValueChangeListener( e -> editContact( e.getValue() ) );

		return contactGrid;
	}

	private Component createForm()
	{
		contactForm = new ContactForm( service.findAllCompanies(), service.findAllStatuses() );
		contactForm.setWidth( "25em" );

		return contactForm;
	}

	private void addContact()
	{
		contactGrid.asSingleSelect().clear();
		editContact( new Contact() );
	}

	public void editContact( Contact contact )
	{
		if ( contact == null )
			closeEditor();

		contactForm.setContact( contact );
		contactForm.setVisible( true );
		addClassName( "editing" );
	}

	private void closeEditor()
	{
		contactForm.setContact( null );
		contactForm.setVisible( false );
		removeClassName( "editing" );
	}

	private void updateList()
	{
		contactGrid.setItems( service.findAllContacts( tfFilter.getValue() ) );
	}

}