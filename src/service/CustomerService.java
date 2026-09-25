package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages customers using the Java Collections Framework.
 * ArrayList keeps customers in insertion order while HashMap provides fast ID lookup.
 */
public class CustomerService {
    private final List<Customer> customers = new ArrayList<>();
    private final Map<Integer, Customer> customerById = new HashMap<>();

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        if (customerById.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Customer ID already exists: " + customer.getId());
        }

        customers.add(customer);
        customerById.put(customer.getId(), customer);
    }

    public Customer findById(int id) {
        return customerById.get(id);
    }

    public boolean removeById(int id) {
        Customer customer = customerById.remove(id);
        if (customer == null) {
            return false;
        }
        return customers.remove(customer);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public List<Customer> searchByName(String name) {
        List<Customer> matches = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return matches;
        }

        String searchText = name.trim().toLowerCase();
        for (Customer customer : customers) {
            if (customer.getName().toLowerCase().contains(searchText)) {
                matches.add(customer);
            }
        }
        return matches;
    }

    public List<Customer> getCustomersSortedByName() {
        List<Customer> sortedCustomers = new ArrayList<>(customers);
        sortedCustomers.sort(Comparator.comparing(Customer::getName, String.CASE_INSENSITIVE_ORDER));
        return sortedCustomers;
    }

    public int size() {
        return customers.size();
    }
}
