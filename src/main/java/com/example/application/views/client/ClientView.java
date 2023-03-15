package com.example.application.views.client;

import com.example.application.data.entity.ClientModel;
import com.example.application.data.entity.ClientResponse;
import com.example.application.data.entity.PackageModel;
import com.example.application.data.entity.PaqueteResponse;
import com.example.application.data.service.DatabaseServiceImplement;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import java.util.Collection;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Client")
@Route(value = "client/:clientModelID?/:action?(edit)", layout = MainLayout.class)
public class ClientView extends Div implements BeforeEnterObserver {

    private final String CLIENTMODEL_ID = "clientModelID";
    private final String CLIENTMODEL_EDIT_ROUTE_TEMPLATE = "client/%s/edit";

    private final Grid<ClientModel> grid = new Grid<>(ClientModel.class, false);

    private TextField clientID;
    private TextField name;
    private TextField address;
    private TextField phone;
    private TextField email;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private ClientModel clientModel;
    
    private DatabaseServiceImplement db;


    public ClientView() {
        
        addClassNames("client-view");
        
        db = DatabaseServiceImplement.getInstance();

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("clientID").setAutoWidth(true).setHeader("ID");
        grid.addColumn("name").setAutoWidth(true).setHeader("Nombre del cliente") ;
        grid.addColumn("address").setAutoWidth(true).setHeader("Dirección") ;
        grid.addColumn("phone").setAutoWidth(true).setHeader("Teléfono");
        grid.addColumn("email").setAutoWidth(true).setHeader("Correo electrónico");
//        grid.setItems(query -> clientModelService.list(
//                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
//                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);


        grid.asSingleSelect().addValueChangeListener(event -> {
//            if (event.getValue() != null) {
//                UI.getCurrent().navigate(String.format(CLIENTMODEL_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
//            } else {
//                clearForm();
//                UI.getCurrent().navigate(ClientView.class);
//            }
        });
        
        try {
        	ClientResponse clientes = db.listarClientes();	
        	
        	Collection<ClientModel> collectionPaquetes = clientes.getItems();
        	grid.setItems(collectionPaquetes);
        	
		} catch (Exception e) {
			// TODO: handle exception
			Notification.show("No se puedieron cargar los paquetes.");
		}


        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.clientModel == null) {
                    this.clientModel = new ClientModel();
                }
                
                
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(ClientView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } 
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> clientModelId = event.getRouteParameters().get(CLIENTMODEL_ID).map(Long::parseLong);
        if (clientModelId.isPresent()) {
            
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        clientID = new TextField("Codigo de cliente");
        name = new TextField("Name");
        address = new TextField("Address");
        phone = new TextField("Phone");
        email = new TextField("Email");
        formLayout.add(clientID, name, address, phone, email);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(ClientModel value) {
        this.clientModel = value;

    }
}
