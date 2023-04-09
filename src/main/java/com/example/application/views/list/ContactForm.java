package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.ComboBox.ItemFilter;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ContactForm extends FormLayout
{
	TextField tfFirstName;
	TextField tfLastName;
	EmailField efEmail;
	ComboBox<Company> cmbCompany;
	ComboBox<Status> cmbStatus;
	List<Status> allStatuses;
	List<Company> allCompanies;

	Button btnSave = new Button( "Save" );
	Button btnDelete = new Button( "Delete" );
	Button btnCancel = new Button( "Cancel" );

	Binder<Contact> binder = new BeanValidationBinder<>( Contact.class );

	public ContactForm( List<Company> companies, List<Status> statuses )
	{
		addClassName( "contact-form" );

		allCompanies = companies;
		allStatuses = statuses;

		add( createFirstName() );
		add( createLastName() );
		add( createEmail() );
		add( createCompany() );
		add( createStatus() );
		add( createBtnLayout() );
	}

	private TextField createFirstName()
	{
		tfFirstName = new TextField( "First Name" );
		tfFirstName.setTitle( "First Name" );
		tfFirstName.setRequired( true );

		binder.forField( tfFirstName )
				.asRequired( "Required" )
				.bind( Contact::getFirstName, Contact::setFirstName );

		return tfFirstName;
	}

	private TextField createLastName()
	{
		tfLastName = new TextField( "Last Name" );
		tfLastName.setTitle( "Last Name" );
		tfLastName.setRequired( true );

		binder.forField( tfLastName )
				.asRequired( "Required" )
				.bind( Contact::getLastName, Contact::setLastName );

		return tfLastName;
	}

	private EmailField createEmail()
	{
		efEmail = new EmailField( "Email" );
		efEmail.setTitle( "Email" );
		efEmail.setRequired( true );

		binder.forField( efEmail )
				.asRequired( "Required" )
				.bind( Contact::getEmail, Contact::setEmail );

		return efEmail;
	}

	private Component createCompany()
	{
		ItemFilter<Company> companyFilter = ( company, filterString ) -> company.getName().toLowerCase()
				.startsWith( filterString.toLowerCase() );

		cmbCompany = new ComboBox<>( "Company" );
		cmbCompany.setItems( companyFilter, allCompanies );
		cmbCompany.setItemLabelGenerator( Company::getName );

		binder.forField( cmbCompany )
				.bind( Contact::getCompany, Contact::setCompany );

		return cmbCompany;
	}

	private Component createStatus()
	{
		ItemFilter<Status> statusFilter = ( status, filterString ) -> status.getName().toLowerCase()
				.startsWith( filterString.toLowerCase() );
		
		cmbStatus = new ComboBox<>( "Status" );
		cmbStatus.setItems( statusFilter, allStatuses );
		cmbStatus.setItemLabelGenerator( Status::getName );

		binder.forField( cmbStatus )
				.bind( Contact::getStatus, Contact::setStatus );

		return cmbStatus;
	}

	private HorizontalLayout createBtnLayout()
	{
		btnSave.addThemeVariants( ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS );
		btnDelete.addThemeVariants( ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR );
		btnCancel.addThemeVariants( ButtonVariant.LUMO_PRIMARY );

		btnSave.addClickShortcut( Key.ENTER );
		btnCancel.addClickShortcut( Key.ESCAPE );

		btnSave.addClickListener( e -> validateAndSave() );
		btnDelete.addClickListener( e -> fireEvent( new DeleteEvent( this, binder.getBean() ) ) );
		btnCancel.addClickListener( e -> fireEvent( new CancelEvent( this, binder.getBean() ) ) );

		HorizontalLayout btnLayout = new HorizontalLayout();
		btnLayout.add( btnSave );
		btnLayout.add( btnDelete );
		btnLayout.add( btnCancel );

		return btnLayout;
	}

	private void validateAndSave()
	{
		if ( binder.isValid() )
			fireEvent( new SaveEvent( this, binder.getBean() ) );
	}

	public void setContact( Contact contact )
	{
		if ( contact == null )
			return;

		for ( Status status : allStatuses )
		{
			if ( contact.getStatus() != null && contact.getStatus().getName() == status.getName() )
				cmbStatus.setValue( contact.getStatus() );
		}

		for ( Company company : allCompanies )
		{
			if ( contact.getCompany() != null && contact.getCompany().getName() == company.getName() )
				cmbCompany.setValue( contact.getCompany() );
		}

		binder.setBean( contact );
	}

	public static abstract class ContactFormEvent extends ComponentEvent<ContactForm>
	{
		private Contact contact;

		protected ContactFormEvent( ContactForm source, Contact contact )
		{
			super( source, false );
			this.contact = contact;
		}

		public Contact getContact()
		{
			return contact;
		}
	}

	public static class SaveEvent extends ContactFormEvent
	{

		protected SaveEvent( ContactForm source, Contact contact )
		{
			super( source, contact );
		}
	}

	public static class DeleteEvent extends ContactFormEvent
	{

		protected DeleteEvent( ContactForm source, Contact contact )
		{
			super( source, contact );
		}
	}

	public static class CancelEvent extends ContactFormEvent
	{

		protected CancelEvent( ContactForm source, Contact contact )
		{
			super( source, null );
		}
	}

	public Registration addDeleteListener( ComponentEventListener<DeleteEvent> listener )
	{
		return addListener( DeleteEvent.class, listener );
	}

	public Registration addSaveListener( ComponentEventListener<SaveEvent> listener )
	{
		return addListener( SaveEvent.class, listener );
	}

	public Registration addCancelListener( ComponentEventListener<CancelEvent> listener )
	{
		return addListener( CancelEvent.class, listener );
	}
}