package com.example.application.views;

import com.example.application.views.list.ListView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
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

	public MainLayout()
	{
		createHeader();
		createDrawer();
	}

	private void createHeader()
	{
		H1 logo = new H1( "Vaadin CRM" );
		
		logo.addClassNames( LumoUtility.FontSize.LARGE, LumoUtility.Margin.MEDIUM );

		HorizontalLayout header = new HorizontalLayout( new DrawerToggle(), logo );
		
		header.setDefaultVerticalComponentAlignment( FlexComponent.Alignment.CENTER );
		header.setWidthFull();
		header.addClassNames( LumoUtility.Padding.Vertical.NONE, LumoUtility.Padding.Horizontal.MEDIUM );
		
		addToNavbar( header );
	}

	private void createDrawer()
	{
		H4 navHeader = new H4( "Other Pages" );
		RouterLink rlList = new RouterLink( "List", ListView.class );
		VerticalLayout navLayout = new VerticalLayout();

		navLayout.add( navHeader );
		navLayout.add( rlList );

		addToDrawer( navLayout );
	}
}