package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout
{
	private static final long serialVersionUID = -2358482846785243370L;
	private final SecurityService securityService;
	
	public MainLayout( SecurityService securityService )
	{
		this.securityService = securityService;
		createHeader();
		createDrawer();
	}

	private void createHeader()
	{
		H1 logo = new H1( "Vaadin CRM" );
		logo.addClassNames( LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM );

		String username = securityService.getAuthenticatedUser().getUsername();
		String formattedUsername = username.substring( 0, 1 ).toUpperCase() + username.substring( 1 );
		Button logout = new Button( "Log Out" + " " + formattedUsername, e -> securityService.logout() );
		
		HorizontalLayout header = new HorizontalLayout( logo, logout );
		
		header.setDefaultVerticalComponentAlignment( FlexComponent.Alignment.CENTER );
		header.expand( logo );
		header.setWidthFull();
		header.addClassNames( LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM );
		
		addToNavbar( header );
	}

	private void createDrawer()
	{
		RouterLink rlContacts = new RouterLink( "Contacts", ListView.class );

		VerticalLayout navLayout = new VerticalLayout();
		navLayout.add( rlContacts );

		addToDrawer( navLayout );
	}
}