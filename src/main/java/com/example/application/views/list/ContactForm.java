package com.example.application.views.list;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class ContactForm extends FormLayout
{
	TextField firstName = new TextField( "First Name" );
	TextField lastName = new TextField( "Last Name" );
	EmailField email = new EmailField( "Email" );
	ComboBox<Status> cmbStatus = new ComboBox<>( "Status" );
	ComboBox<Company> cmbCompany = new ComboBox<>( "Company" );

	Button btnSave = new Button( "Save" );
	Button btnDelete = new Button( "Delete" );
	Button btnCancel = new Button( "Cancel" );

	public ContactForm( List<Company> companies, List<Status> statuses )
	{
		addClassName( "contact-form" );

		cmbCompany.setItems( companies );
		cmbCompany.setItemLabelGenerator( Company::getName );

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
		btnSave.addThemeVariants( ButtonVariant.LUMO_PRIMARY );
		btnDelete.addThemeVariants( ButtonVariant.LUMO_ERROR );
		btnCancel.addThemeVariants( ButtonVariant.LUMO_TERTIARY );
		
		btnSave.addClickShortcut( Key.ENTER );
		btnCancel.addClickShortcut( Key.ESCAPE );
		
		HorizontalLayout btnLayout = new HorizontalLayout();
		btnLayout.add(btnSave);
		btnLayout.add(btnDelete);
		btnLayout.add(btnCancel);
		
		return btnLayout;
	}
}