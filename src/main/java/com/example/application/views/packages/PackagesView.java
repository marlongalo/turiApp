package com.example.application.views.packages;

import com.example.application.data.entity.PackageModel;
import com.example.application.data.service.PackageModelService;
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
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

@PageTitle("Packages")
@Route(value = "packages/:packageModelID?/:action?(edit)", layout = MainLayout.class)
public class PackagesView extends Div implements BeforeEnterObserver {

    private final String PACKAGEMODEL_ID = "packageModelID";
    private final String PACKAGEMODEL_EDIT_ROUTE_TEMPLATE = "packages/%s/edit";

    private final Grid<PackageModel> grid = new Grid<>(PackageModel.class, false);

    private TextField packageID;
    private TextField image;
    private TextField namePackage;
    private TextField destiny;
    private TextField duration;
    private TextField hotel;
    private TextField activities;
    private TextField price;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<PackageModel> binder;

    private PackageModel packageModel;

    private final PackageModelService packageModelService;

    public PackagesView(PackageModelService packageModelService) {
        this.packageModelService = packageModelService;
        addClassNames("packages-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("packageID").setAutoWidth(true);
        grid.addColumn("image").setAutoWidth(true);
        grid.addColumn("namePackage").setAutoWidth(true);
        grid.addColumn("destiny").setAutoWidth(true);
        grid.addColumn("duration").setAutoWidth(true);
        grid.addColumn("hotel").setAutoWidth(true);
        grid.addColumn("activities").setAutoWidth(true);
        grid.addColumn("price").setAutoWidth(true);
        grid.setItems(query -> packageModelService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PACKAGEMODEL_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(PackagesView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(PackageModel.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(packageID).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("packageID");
        binder.forField(duration).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
                .bind("duration");
        binder.forField(price).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("price");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.packageModel == null) {
                    this.packageModel = new PackageModel();
                }
                binder.writeBean(this.packageModel);
                packageModelService.update(this.packageModel);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(PackagesView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> packageModelId = event.getRouteParameters().get(PACKAGEMODEL_ID).map(Long::parseLong);
        if (packageModelId.isPresent()) {
            Optional<PackageModel> packageModelFromBackend = packageModelService.get(packageModelId.get());
            if (packageModelFromBackend.isPresent()) {
                populateForm(packageModelFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested packageModel was not found, ID = %s", packageModelId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(PackagesView.class);
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
        packageID = new TextField("Package ID");
        image = new TextField("Image");
        namePackage = new TextField("Name Package");
        destiny = new TextField("Destiny");
        duration = new TextField("Duration");
        hotel = new TextField("Hotel");
        activities = new TextField("Activities");
        price = new TextField("Price");
        formLayout.add(packageID, image, namePackage, destiny, duration, hotel, activities, price);

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
        binder.readBean(this.packageModel);

    }
}
