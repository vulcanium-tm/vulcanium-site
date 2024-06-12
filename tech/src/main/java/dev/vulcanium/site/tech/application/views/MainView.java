package dev.vulcanium.site.tech.application.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import dev.vulcanium.business.application.views.ShopView;

@Route(value = "shop", layout = dev.vulcanium.business.application.views.MainView.class)
@PageTitle("shop")
@CssImport("./styles/views/helloworld/hello-world-view.css")
@RouteAlias(value = "", layout = dev.vulcanium.business.application.views.MainView.class)
public class MainView extends ShopView{

public MainView(){
	setId("about-view");
}
}
