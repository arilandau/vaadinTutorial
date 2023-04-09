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
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ContactForm extends FormLayout
{
	TextField firstName = new TextField( "First Name" );
	TextField lastName = new TextField( "Last Name" );
	EmailField email = new EmailField( "Email" );
	ComboBox<Status> cmbStatus = new ComboBox<>( "Status" );
	ComboBox<Company> cmbCompany = new ComboBox<>( "Company" );
	List<Status> allStatuses;
	List<Company> allCompanies;

	Button btnSave = new Button( "Save" );
	Button btnDelete = new Button( "Delete" );
	Button btnCancel = new Button( "Cancel" );

	Binder<Contact> binder = new BeanValidationBinder<>( Contact.class );

	public ContactForm( List<Company> companies, List<Status> statuses )
	{
		addClassName( "contact-form" );
		binder.bindInstanceFields( this );

		allCompanies = companies;
		cmbCompany.setItems( companies );
		cmbCompany.setItemLabelGenerator( Company::getName );

		allStatuses = statuses;
		cmbStatus.setItems( statuses );
		cmbStatus.setItemLabelGenerator( Status::getName );

		add( firstName );
		add( lastName );
		add( email );
		add( cmbCompany );
		add( cmbStatus );
		add( createBtnLayout() );
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
			if ( contact.getStatus().getName() == status.getName() )
				cmbStatus.setValue( contact.getStatus() );
		}
		
		for ( Company company: allCompanies )
		{
			if ( contact.getCompany().getName() == company.getName())
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