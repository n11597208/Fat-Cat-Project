package com.example.cab302project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import com.example.cab302project.Observer;

public class AccountListViewObserver implements Observer {
    private ListView<String> accountListView;
    private ObservableList<String> accounts;

    // Constructor to initialize the ListView and account list
    public AccountListViewObserver(ListView<String> accountListView, ObservableList<String> accounts) {
        this.accountListView = accountListView;
        this.accounts = accounts;
    }

    // This method will be called when the search term changes in the Subject
    @Override
    public void update(String searchTerm) {
        if (searchTerm.isEmpty()) {
            // If the search term is empty, clear the ListView
            accountListView.setItems(FXCollections.observableArrayList());
        } else {
            // Filter accounts based on the search term and update the ListView
            accountListView.setItems(accounts.filtered(account -> account.toLowerCase().contains(searchTerm.toLowerCase())));
        }
    }
}
