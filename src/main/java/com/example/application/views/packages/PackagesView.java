package com.example.application.views.packages;
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
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@SuppressWarnings("serial")
@PageTitle("Packages")
@Route(value = "packages/:packageModelID?/:action?(edit)", layout = MainLayout.class)
public class PackagesView extends Div implements BeforeEnterObserver {

    private final String PACKAGEMODEL_ID = "packageModelID";
    private final String PACKAGEMODEL_EDIT_ROUTE_TEMPLATE = "packages/%s/edit";

    private final Grid<PackageModel> grid = new Grid<>(PackageModel.class, false);

    private TextField namePackage;
    private TextField destiny;
    private TextField hotel;
    private TextField activities;

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");

    private PackageModel packageModel;
    
    private DatabaseServiceImplement db;
    private List<PackageModel> models;


    public PackagesView() {
    	
        addClassNames("packages-view");
        
        db = DatabaseServiceImplement.getInstance();

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("packageID").setAutoWidth(true).setHeader("ID");
        grid.addColumn("namePackage").setAutoWidth(true).setHeader("Nombre");
        grid.addColumn("destiny").setAutoWidth(true).setHeader("Destino");
        grid.addColumn("hotel").setAutoWidth(true).setHeader("Hotel");
        grid.addColumn("activities").setAutoWidth(true).setHeader("Actividades");

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
        	
            if (event.getValue() != null) {
              
              UI.getCurrent().navigate(String.format(PACKAGEMODEL_EDIT_ROUTE_TEMPLATE, event.getValue().getPackageID()));
            	
            	namePackage.setValue(event.getValue().getNamePackage());
            	packageID.setValue(event.getValue().getPackageID().toString());
            	
            } else {
                clearForm();
                UI.getCurrent().navigate(PackagesView.class);
            }
            
        });
        
        consultarProductos();
        

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.packageModel == null) {
                    this.packageModel = new PackageModel();
                    this.packageModel.setNamePackage(namePackage.getValue());
                    this.packageModel.setDestiny(destiny.getValue());
                    this.packageModel.setHotel(hotel.getValue());
                    this.packageModel.setActivities(activities.getValue());
                    
                    if(this.packageModel.getNamePackage()==null) {
                    	Notification.show("Para agregar un registro del paquete el campo nombre es requerido, favor digitar un valor valido");
        			}else if(this.packageModel.getDestiny()==null || this.packageModel.getDestiny().isEmpty()){
        				Notification.show("Para agregar un registro el campo telefono es requerido, favor digitar un valor valido");
        			}else {
        				try {
        					boolean creado = db.crearPaquetes(packageModel);
        							if(creado) {
                						Notification.show("Ficha del Cliente creado satisfactoriamente...");
                						clearForm();
                		                refreshGrid();
                		                consultarProductos();
                		                UI.getCurrent().navigate(PackagesView.class);
                					}else {
                						Notification.show("Ficha del cliente no pudo ser creada, favor ingresar datos correctos");
                					}
        					}	catch (IOException e1) {
	        					Notification.show("No se pudo crear el cliente favor revisa tu conexion a internet.");
	        					e1.printStackTrace();	
        					}

        			}
                }else {
        			//ACTUALIZACION
                	this.packageModel.setNamePackage(namePackage.getValue());
                    this.packageModel.setDestiny(destiny.getValue());
                    this.packageModel.setHotel(hotel.getValue());
                    this.packageModel.setActivities(activities.getValue());
                    
                    if(this.packageModel.getNamePackage()==null) {
                    	Notification.show("El nombre es requerido, favor digitar un valor valido");
        			}else if(this.packageModel.getDestiny()==null || this.packageModel.getDestiny().isEmpty()) {
        				Notification.show("El nombre es requerido, favor digitar un valor valido");
        			}else {
        			try {
    					boolean actualizado = db.actualizarPaquetes(packageModel);
	    					if(actualizado) {	    						
	    						clearForm();
	    		                refreshGrid();
	    		                Notification.show("Ficha del cliente fue actualizada satisfactoriamente...");
	    		                UI.getCurrent().navigate(PackagesView.class);
	    					}else {
	    						Notification.show("Ficha del cliente no pudo ser actualizada, favor ingresar datos correctos");
	    					}
	    					
	    				}   catch (IOException e1) {
	        					Notification.show("No se pudo actualizar la ficha del cliente favor revisa tu conexion a internet.");
	        					e1.printStackTrace();
    				}
        		}
    		}
                
                
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } 
        });
    }

	private void consultarProductos() {
		try {
        	PaqueteResponse paquetes = db.listarPaquetes();	
        	models = paquetes.getPaquetes();
        	Collection<PackageModel> collectionPaquetes = paquetes.getPaquetes();
        	grid.setItems(collectionPaquetes);//TODO: CAMBIO PENDIENTE
        	
		} catch (Exception e) {
			// TODO: handle exception
			Notification.show("No se puedieron cargar los paquetes.");
		}
	}

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> packageModelId = event.getRouteParameters().get(PACKAGEMODEL_ID).map(Long::parseLong);
        if (packageModelId.isPresent()) {
        	for(PackageModel packageModel : models) {
        		if(packageModel.getPackageID() == packageModelId.get()) {
        			populateForm(packageModel);
        			break;
        		}
        		
        	}

        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        namePackage = new TextField("Nombre del Paquete");
        destiny = new TextField("Destino");
        hotel = new TextField("Hotel");
        activities = new TextField("Actividades");
        formLayout.add(namePackage, destiny, hotel, activities);

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

    private void populateForm(PackageModel value) {
    	this.packageModel = value;
    	if(value == null) {
            namePackage.setValue("");
            destiny.setValue("");
            hotel.setValue("");
            activities.setValue("");
    	}else {	
    		namePackage.setValue(value.getNamePackage());
    		destiny.setValue(value.getDestiny());
    		hotel.setValue(value.getHotel());
    		activities.setValue(value.getActivities());

    	}

    }
}
