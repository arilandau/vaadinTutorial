package com.example.application.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login | Vaadin CRM")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver
{
	private static final long serialVersionUID = -7331389615806598925L;

	private final LoginForm loginForm = new LoginForm();

	public LoginView()
	{
		addClassName( "login-view" );
		setSizeFull();
		setAlignItems( Alignment.CENTER );
		setJustifyContentMode( JustifyContentMode.CENTER );

		add( createLoginForm() );
	}

	private Component createLoginForm()
	{
		LoginI18n i18n = LoginI18n.createDefault();

		LoginI18n.Form i18nForm = i18n.getForm();
		i18nForm.setTitle( "Log In" );
		i18nForm.setUsername( "Username" );
		i18nForm.setPassword( "Password" );
		i18nForm.setSubmit( "Log In" );
		i18nForm.setForgotPassword( "Forgot Password?" );
		i18n.setForm( i18nForm );

		LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
		i18nErrorMessage.setTitle( "Error Logging In" );
		i18nErrorMessage.setMessage(
				"Please check your username and password." );
		i18n.setErrorMessage( i18nErrorMessage );

		loginForm.setI18n( i18n );
		loginForm.setAction( "login" );

		return loginForm;
	}

	@Override
	public void beforeEnter( BeforeEnterEvent beforeEnterEvent )
	{
		if ( beforeEnterEvent.getLocation()
				.getQueryParameters()
				.getParameters()
				.containsKey( "error" ) )
			loginForm.setError( true );
	}
}