package com.example.application.views.reservar;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.time.ZoneId;

import com.example.application.data.entity.ClientModel;
import com.example.application.data.entity.ClientResponse;
import com.example.application.data.entity.PackageModel;
import com.example.application.data.entity.PaqueteResponse;
import com.example.application.data.entity.ReservaModel;
import com.example.application.data.service.DatabaseServiceImplement;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Reservar")
@Route(value = "reservar", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)

public class ReservarView extends Div {

	private ComboBox<String> packageCombo = new ComboBox<>();
	Collection<PackageModel> collectionPaquetes;
	
	private ComboBox<String> clientCombo = new ComboBox<>();
	Collection<ClientModel> collectionClientes;
	
	private PackageModel packageSelected;
	private ClientModel clienteSelected;
	
	private DatePicker dateIn = new DatePicker("Fecha de inicio");
	private DatePicker dateOut = new DatePicker("Fecha de fin");
	private TextField price = new TextField("Precio");
	
    
    private Hr dividerHr = new Hr();

    private Button cancel = new Button("Cancelar");
    private Button save = new Button("Guardar");
    private Button calculate = new Button("Calcular");
    
    private DatabaseServiceImplement db;
    
    private List<String> itemsPaquetes = new ArrayList<>();
    private List<String> itemsClientes = new ArrayList<>();
    
    Date rangeInDate;
    Date rangeOutDate;
    

    public ReservarView() {
        addClassName("reservar-view");
        db = DatabaseServiceImplement.getInstance();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        
        add(createTitle());
        
        try {
        	PaqueteResponse paquetes = db.listarPaquetes();
        	collectionPaquetes = paquetes.getPaquetes();
        	
        	collectionPaquetes.forEach( (paquete) -> {
        		itemsPaquetes.add(paquete.getNamePackage());
        	});

		} catch (Exception e) {
			// TODO: handle exception
			Notification.show("No se pudieron cargar los paquetes.");
		}
        
        
        try {
        	ClientResponse paquetes = db.listarClientes();
        	collectionClientes = paquetes.getItems();
        	
        	collectionClientes.forEach( (paquete) -> {
        		itemsClientes.add(paquete.getName());
        	});

		} catch (Exception e) {
			// TODO: handle exception
			Notification.show("No se pudieron cargar los clientes.");
		}
        
        
        add(createFormLayout());
        add(createButtonLayout());

        cancel.addClickListener(e -> gotToPay());
        save.addClickListener(e -> {
        	
        	ReservaModel nuevaReserva = new ReservaModel();
        	
        	nuevaReserva.setPackageid(packageSelected.getPackageID());
        	nuevaReserva.setClienteid(clienteSelected.getClientID());
        	nuevaReserva.setFechainicio(rangeInDate.toString());
        	nuevaReserva.setFechafin(rangeOutDate.toString());
        	nuevaReserva.setPrice(Integer.parseInt(price.getValue()));
        	nuevaReserva.setPayed(0);
        	
        	try {
				db.crearReserva(nuevaReserva);
				Notification.show("Reserva guardada correctamente.");
				gotToPay();
			} catch (IOException e1) {
				Notification.show("Algo salió mal durante el guardado.");
				e1.printStackTrace();
			}
            
        });
        
        
        calculate.addClickListener(e -> {
        	String packageNameString = packageCombo.getValue();
        	
        	collectionPaquetes.forEach(paquete -> {
        		if( packageNameString == paquete.getNamePackage() ) {
        			packageSelected = paquete;
        		}
        	});
        	
        	
        	String clienteString = clientCombo.getValue();
        	
        	collectionClientes.forEach(cliente -> {
        		if( clienteString == cliente.getName() ) {
        			clienteSelected = cliente;
        		}
        	});
        	
        	LocalDate rangeIn = dateIn.getValue();
        	LocalDate rangeOut = dateOut.getValue();
        	rangeInDate = Date.from(rangeIn.atStartOfDay(defaultZoneId).toInstant());
        	rangeOutDate = Date.from(rangeOut.atStartOfDay(defaultZoneId).toInstant());
        	
        	
        	long range = rangeOutDate.getTime() - rangeInDate.getTime();
        	
        	TimeUnit time = TimeUnit.DAYS;
        	long difference = time.convert(range, TimeUnit.MILLISECONDS);
        	
        	Notification.show(difference + "");
        	int totalPrice = (int) (difference * packageSelected.getPrice());
        	
        	price.setValue(totalPrice + "");
        	
        });
    }

    private void gotToPay() {
        //binder.setBean(new SamplePerson());
    	UI.getCurrent().navigate(String.format("payment"));
    }

    private Component createTitle() {
        return new H3("Haz la reserva de paquete que más te guste");
    }

    private Component createFormLayout() {
    	
    	FormLayout formLayout = new FormLayout();
    	
    	packageCombo.setLabel("Paquetes");
    	packageCombo.setWidthFull();
    	packageCombo.setPlaceholder("Paquete");
    	packageCombo.setAllowedCharPattern("[\\+\\d]");
    	packageCombo.setItems(itemsPaquetes);
    	packageCombo.addCustomValueSetListener(e -> {
    		packageCombo.setValue(e.getDetail());
    	});
    	
    	clientCombo.setLabel("Clientes");
    	clientCombo.setWidthFull();
    	clientCombo.setPlaceholder("Clientes");
    	clientCombo.setAllowedCharPattern("[\\+\\d]");
    	clientCombo.setItems(itemsClientes);
    	clientCombo.addCustomValueSetListener(e -> {
    		clientCombo.setValue(e.getDetail());
    	});
        
    	

    	//email.setErrorMessage("Please enter a valid email address");
        formLayout.add(packageCombo, dividerHr, clientCombo, dividerHr, dateIn, dateOut, dividerHr, calculate, price); // Include the field you will need.
        formLayout.setResponsiveSteps(
                // Use one column by default
                new ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new ResponsiveStep("500px", 2));
        
        formLayout.setColspan(packageCombo, 2);
        formLayout.setColspan(clientCombo, 2);
        formLayout.setColspan(dividerHr, 2);
    	
    	return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }
    
}
