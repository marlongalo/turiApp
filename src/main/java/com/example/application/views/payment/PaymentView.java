package com.example.application.views.payment;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Payment")
@Route(value = "payment", layout = MainLayout.class)
public class PaymentView extends Div {
	
	private ReservasCombo reservaSelectPackageField = new ReservasCombo("Reservas pendientes");
	
	private DatePicker paymentDate = new DatePicker("Fecha");
	

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    
    private Span status;

    public PaymentView() {
    	
    	status = new Span();
        status.setVisible(false);
        
        ConfirmDialog dialog = new ConfirmDialog();
    	
    	dialog.setHeader(String.format("Confirmación de pago"));
        dialog.setText(new Html("<p>¿Está seguro que desea realizar el pago por la cantidad de "
        		+ "<b>Lps. 5,000.00</b> por la compra del paquete?"));
        
        dialog.add("Este cobro se realizará al base a la tarifa de bancos internacionales.");
        
        dialog.setCancelable(true);
        
        dialog.addCancelListener(event -> dialogCancel("Canceled"));
        
        dialog.setConfirmText("Realizar el pago");
        dialog.setConfirmButtonTheme("success");
        dialog.addConfirmListener(event -> dialogPagar("Pagado"));
        
    	
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
    
    private void dialogCancel(String value) {
        // Cancelando
        status.setVisible(true);
    }
    
    private void dialogPagar(String value) {
    	// TODO: Guardar en base de datos
        status.setVisible(true);
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
