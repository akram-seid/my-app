package com.ilri.crop.views;

import com.ilri.crop.Model.Constants;
import com.ilri.crop.Model.Variety;
import com.ilri.crop.Service.VarietyService;
import com.mongodb.BasicDBObject;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Add Variety")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class AddVarietyView extends VerticalLayout {
    private final TextField cropName = new TextField("Crop Name");
    private final TextField varietyName = new TextField("Variety Name");
    private final TextField subType = new TextField("Sub TYpe");
    private final TextArea agronomic = new TextArea("Agronomic and morphological characteristics");
    private final TextField seedRate = new TextField("Seed Rate");
    private final TextField yearRelease = new TextField("Year of Release");
    private final TextField yearRegistration = new TextField("Year of Registration");
    private final TextField breeder = new TextField("Breeder / Maintainer");
    private final TextField key = new TextField("Attribute");
    private final TextArea value = new TextArea("Value");

    private final Button brace = new Button("{ }");
    private final Button fertilizer = new Button("Fertilizer");
    private final Button adaptation = new Button("Area");
    private final Button grain = new Button("Yield");
    private final Button space = new Button("Spacing");
    private final Button quality = new Button("Quality");

    private final Button add = new Button("Add");
    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    Binder<Variety> binder = new Binder<>(Variety.class);
    VarietyService service;

    public AddVarietyView(VarietyService service) {
        this.service = service;
        addClassName("person-form-view");
        H4 title = new H4("Add new varieties here.");
        agronomic.setMinHeight("100%");
        agronomic.setMinWidth("60%");
        value.setMinWidth("60%");

        add(title);
        add(cropLayout());
        add(otherAgroLayout());
        add(specials());
        add(simplifierLayout());
        add(agronomic);
        add(yearLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this);
        add.addClickListener(click -> {
            agronomic.setValue(agronomic.getValue() + key.getValue().replace(" ", "") + ":" + value.getValue() + "," + "\n");

        });
        brace.addClickListener(buttonClickEvent -> {
            value.setValue(Constants.BRACE);
        });
        fertilizer.addClickListener(buttonClickEvent -> {
            key.setValue("fertilizer Rate");
            value.setValue(Constants.FERTILIZER);
        });
        space.addClickListener(buttonClickEvent -> {
            key.setValue("spacing");
            value.setValue(Constants.SPACING);
        });
        grain.addClickListener(click -> {
            key.setValue("grain Yield");
            value.setValue(Constants.YIELD);
        });
        adaptation.addClickListener(click -> {
            key.setValue("adaptation Area");
            value.setValue(Constants.ADAPTATION);
        });

        quality.addClickListener(click -> {
            key.setValue("quality Data");
            value.setValue(Constants.QUALITY);
        });

        save.addClickListener(e -> {

            saveData();
        });

        cancel.addClickListener(c -> clearForm());
        clearForm();
    }

    private Component specials() {
        brace.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        fertilizer.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        adaptation.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        grain.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        space.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(brace, fertilizer, adaptation, grain, space);
        layout.setAlignItems(Alignment.BASELINE);
        return layout;
    }

    private Component simplifierLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        H4 colon = new H4(":");
        key.setClearButtonVisible(true);
        value.setClearButtonVisible(true);
        layout.add(key, colon, value, add);
        add.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layout.setAlignItems(Alignment.BASELINE);
        return layout;
    }

    public void saveData() {
        Variety variety = binder.getBean();

        if (checkJson()) {
            BasicDBObject agro = BasicDBObject.parse("{" + agronomic.getValue() + "}");
            variety.setAgronomicChar(agro);
            service.save(variety);
            Notification.show("Successfully saved!").setPosition(Notification.Position.MIDDLE);
            clearForm();
        } else {
            Notification.show("Invalid data format detected! \n correct your data format!", 3000, Notification.Position.MIDDLE);
        }
    }

    public boolean checkJson() {

        try {
            BasicDBObject.parse("{" + agronomic.getValue() + "}");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private Component yearLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(yearRegistration, yearRelease, breeder);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        return formLayout;
    }

    private Component otherAgroLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(seedRate);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        return formLayout;
    }

    private Component cropLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(cropName, subType, varietyName);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
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

    private final void clearForm() {
        binder.setBean(new Variety());
    }
}
