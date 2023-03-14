package com.example.application.views.payment;

import com.example.application.data.entity.SampleAddress;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Payment")
@Route(value = "payment", layout = MainLayout.class)
public class PaymentView extends Div {
	
	private ReservasCombo reservaSelectPackageField = new ReservasCombo("Reservas pendientes");
	
	private DatePicker paymentDate = new DatePicker("Fecha");
	
	Dialog dialog = new Dialog();

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<SampleAddress> binder = new Binder<>(SampleAddress.class);

    public PaymentView() {
    	
    	dialog.setHeaderTitle(String.format("Confirmación"));
        dialog.add("¿Está seguro de pagar esta reserva a nombre de este cliente?");
        dialog.add("Total a pagar: Lps: 5,000.00");
        
        Button cancelButton = new Button("Cancel", (e) -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getFooter().add(cancelButton);

        Button payButton = new Button("Pagar", (e) -> dialog.close());
        payButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        
        cancelButton.getStyle().set("margin-right", "auto");
        dialog.getFooter().add(payButton);

        
    	
    	
        addClassName("payment-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        //binder.bindInstanceFields(this);

        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
        	dialog.open();
            clearForm();
        });
    }

    private Component createTitle() {
        return new H3("Address");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        formLayout.add(reservaSelectPackageField, paymentDate);
        
        formLayout.setColspan(reservaSelectPackageField, 2);
        formLayout.setColspan(paymentDate, 2);
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

    private void clearForm() {
        this.binder.setBean(new SampleAddress());
    }
    
    
    
    private static class ReservasCombo extends CustomField<String> {
        private ComboBox<String> reservasField = new ComboBox<>();

        public ReservasCombo(String label) {
            setLabel(label);
            reservasField.setWidthFull();
            reservasField.setPlaceholder("Reservas");
            reservasField.setAllowedCharPattern("[\\+\\d]");
            reservasField.setItems("Reserva #1", "Reserva #2", "Reserva #3");
            reservasField.addCustomValueSetListener(e -> reservasField.setValue(e.getDetail()));
            HorizontalLayout layout = new HorizontalLayout(reservasField);
            add(layout);
        }

        @Override
        protected String generateModelValue() {
            if (reservasField.getValue() != null ) {
                String s = reservasField.getValue();
                return s;
            }
            return "";
        }

        @Override
        protected void setPresentationValue(String phoneNumber) {
            String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
            if (parts.length == 1) {
            	reservasField.clear();
            	
            } else if (parts.length == 2) {
            	reservasField.setValue(parts[0]);
            	
            } else {
            	reservasField.clear();
            	
            }
        }
    }
    
    
    

}
