package dev.gilbert.zacharia.managercraft.ui.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

@PWA(
        name = "Managercraft",
        shortName = "Managercraft",
        startPath = "/",
        offlineResources = {"images/logo.png"}
)
@PageTitle("Managercraft")
@Route("")
public class MainView extends VerticalLayout implements AppShellConfigurator {

    public MainView() {
        add(new Button("Click me",
                e -> Notification.show("Hello World!")));
    }
}
