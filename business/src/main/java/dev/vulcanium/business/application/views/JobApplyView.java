package dev.vulcanium.business.application.views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Job Apply")
@Route(value = "Jobs", layout = MainView.class)
@CssImport("./styles/views/helloworld/hello-world-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class JobApplyView extends VerticalLayout{

public JobApplyView(){
	VerticalLayout about = new VerticalLayout();
}
}
