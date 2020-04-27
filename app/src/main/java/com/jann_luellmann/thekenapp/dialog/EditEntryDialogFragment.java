package com.jann_luellmann.thekenapp.dialog;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jann_luellmann.thekenapp.R;
import com.jann_luellmann.thekenapp.adapter.Entry;
import com.jann_luellmann.thekenapp.data.model.Customer;
import com.jann_luellmann.thekenapp.data.model.Drink;
import com.jann_luellmann.thekenapp.data.model.Event;
import com.jann_luellmann.thekenapp.data.util.Editable;
import com.jann_luellmann.thekenapp.data.view_model.CustomerViewModel;
import com.jann_luellmann.thekenapp.data.view_model.DrinkViewModel;
import com.jann_luellmann.thekenapp.data.view_model.EventViewModel;
import com.jann_luellmann.thekenapp.databinding.DialogEntryDateBinding;
import com.jann_luellmann.thekenapp.databinding.DialogEntryEdittextBinding;
import com.jann_luellmann.thekenapp.databinding.DialogEntryMoneyBinding;
import com.jann_luellmann.thekenapp.databinding.DialogFragmentEditEntryBinding;
import com.jann_luellmann.thekenapp.util.TextUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditEntryDialogFragment extends DialogFragment {

    private DialogFragmentEditEntryBinding binding;

    private Object item;
    private List<Entry> entries = new ArrayList<>();

    public EditEntryDialogFragment(Object item) {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DialogFragmentEditEntryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        getDialog().getWindow().setLayout((int) (width * 0.8f), (int) (height * 0.8f));

        binding.title.setText(getContext().getString(R.string.create_title));

        for (View v : generateInputs(item))
            binding.fieldsHolder.addView(v);

        binding.cancelButton.setOnClickListener(b -> dismiss());
        binding.saveButton.setOnClickListener(b -> updateEntry());
    }

    private List<View> generateInputs(Object item) {
        Field[] fields = item.getClass().getDeclaredFields();
        List<View> views = new ArrayList<>();

        for(Field field : fields) {
            if (field.isAnnotationPresent(Editable.class)) {
                if (field.getType() == String.class) {
                    DialogEntryEdittextBinding binding = DialogEntryEdittextBinding.inflate(getLayoutInflater());
                    attach(binding.textView, binding.editText, field);
                    views.add(binding.getRoot());
                }
                else if (field.getType() == long.class) {
                    DialogEntryMoneyBinding binding = DialogEntryMoneyBinding.inflate(getLayoutInflater());
                    attach(binding.textView, binding.editText, field);
                    views.add(binding.getRoot());
                }
                else if (field.getType() == Date.class) {
                    DialogEntryDateBinding binding = DialogEntryDateBinding.inflate(getLayoutInflater());
                    attach(binding.textView, binding.editText, field);
                    views.add(binding.getRoot());
                }
            }
        }

        return views;
    }

    private void attach(TextView textView, View valueView, Field field) {
        textView.setText(TextUtil.FirstLetterUpperCase(field.getName()));
        try {
            if(valueView instanceof EditText) {
                EditText editText = (EditText) valueView;
                field.setAccessible(true);
                String text = field.get(item).toString();

                // Handle Date EditText
                if(field.getType() == Date.class) {
                    text = TextUtil.dateToString((Date) field.get(item));
                    editText.setOnClickListener(view -> {
                        DatePickerFragment datePickerFragment = new DatePickerFragment(editText);
                        datePickerFragment.show(getFragmentManager(), "datePicker");
                    });
                }

                editText.setText(text);
            }
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
        entries.add(new Entry(field.getName(), valueView));
    }

    private void updateEntry() {
        if(item instanceof Drink) {
            Drink drink = (Drink) item;

            for (Entry entry : entries) {
                switch (entry.getName()) {
                    case "name":
                        String name = ((EditText) entry.getValue()).getText().toString();
                        drink.setName(TextUtil.FirstLetterUpperCase(name));
                        break;
                    case "price":
                        long price = Long.parseLong(((EditText) entry.getValue()).getText().toString());
                        drink.setPrice(price);
                        break;
                }
            }

            new DrinkViewModel().update(drink);
        }
        else if(item instanceof Customer) {
            Customer customer = (Customer) item;

            for (Entry entry : entries) {
                if ("name".equals(entry.getName())) {
                    String name = ((EditText) entry.getValue()).getText().toString();
                    customer.setName(TextUtil.FirstLetterUpperCase(name));
                }
            }

            new CustomerViewModel().update(customer);
        }
        else if(item instanceof Event) {
            Event event = (Event) item;

            for (Entry entry : entries) {
                switch (entry.getName()) {
                    case "name":
                        String name = ((EditText) entry.getValue()).getText().toString();
                        event.setName(TextUtil.FirstLetterUpperCase(name));
                        break;
                    case "date":
                        Date date = TextUtil.stringToDate(((EditText) entry.getValue()).getText().toString());
                        event.setDate(date);
                        break;
                }
            }

            new EventViewModel().update(event);
        }

        dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getShowsDialog()) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            getDialog().getWindow().setLayout((int) (width * 0.6f), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
