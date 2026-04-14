package csd214.app;

import com.github.javafaker.Faker;
import csd214.app.entities.*;
import csd214.app.pojos.*;
import csd214.app.repositories.ProductRepository;
import csd214.app.services.BookstoreService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component // Tells Spring to manage this class and execute its run() method
public class BookstoreCLI implements CommandLineRunner {

    private final ProductRepository repository;
    private final BookstoreService service;
    private final CashTill cashTill = new CashTill();
    private final Scanner input = new Scanner(System.in);

    // Constructor Injection! Spring hands us the Repository and the Service
    public BookstoreCLI(ProductRepository repository, BookstoreService service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    public void run(String... args) {
        System.out.println("Running on Spring Boot Data JPA!");

        if (repository.count() == 0) {
            populate();
        }

        int choice = 0;
        while (choice != 99) {
            System.out.println("\n***********************");
            System.out.println("   SPRING BOOT STORE   ");
            System.out.println("***********************");
            System.out.println(" 1. Add Items");
            System.out.println(" 2. Edit Items");
            System.out.println(" 3. Delete Items");
            System.out.println(" 4. Sell item(s)");
            System.out.println(" 5. List items");
            System.out.println(" 6. System Reset (Wipe DB)");
            System.out.println("99. Quit");
            System.out.println("***********************");
            System.out.print("Enter choice: ");

            try {
                String line = input.nextLine();
                if (line.trim().isEmpty()) continue;
                choice = Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                choice = 0;
            }

            switch (choice) {
                case 1: addItem(); break;
                case 2: editItem(); break;
                case 3: deleteItem(); break;
                case 4: sellItem(); break;
                case 5: listAny(); break;
                case 6: systemReset(); break;
                case 99: System.out.println("Goodbye."); break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    public void addItem() {
        System.out.println("\n--- Add an item ---");
        System.out.println("1. Book");
        System.out.println("2. Ticket");
        System.out.print("Choice: ");
        int choice = getIntInput();

        try {
            if (choice == 1) {
                Book bPojo = new Book();
                bPojo.initialize(input);
                BookEntity bEnt = new BookEntity();
                bEnt.setTitle(bPojo.getTitle());
                bEnt.setPrice(bPojo.getPrice());
                bEnt.setCopies(bPojo.getCopies());
                bEnt.setAuthor(bPojo.getAuthor());
                repository.save(bEnt);
            } else if (choice == 2) {
                Ticket tPojo = new Ticket();
                tPojo.initialize(input);
                TicketEntity tEnt = new TicketEntity();
                tEnt.setDescription(tPojo.getDescription());
                tEnt.setPrice(tPojo.getPrice());
                repository.save(tEnt);
            }
            System.out.println("Item saved to Database!");
        } catch (Exception e) {
            System.out.println("Error saving item: " + e.getMessage());
        }
    }

    public void listAny() {
        List<ProductEntity> results = repository.findAll();
        System.out.println("\n--- Inventory List (" + results.size() + ") ---");
        for (int i = 0; i < results.size(); i++) {
            System.out.println("[" + i + "] " + results.get(i));
        }
    }

    public void editItem() {
        List<ProductEntity> results = repository.findAll();
        if (results.isEmpty()) return;

        listAny();
        System.out.print("Select index to edit: ");
        int idx = getIntInput();
        if (idx < 0 || idx >= results.size()) return;

        ProductEntity entity = results.get(idx);

        if (entity instanceof BookEntity be) {
            Book pojo = new Book(be.getAuthor(), be.getTitle(), be.getPrice(), be.getCopies());
            pojo.edit(input);
            be.setAuthor(pojo.getAuthor());
            be.setTitle(pojo.getTitle());
            be.setPrice(pojo.getPrice());
            be.setCopies(pojo.getCopies());
            repository.save(be);
            System.out.println("Update successful.");
        }
    }

    public void deleteItem() {
        List<ProductEntity> results = repository.findAll();
        if(results.isEmpty()) return;
        listAny();
        System.out.print("Select index to delete: ");
        int idx = getIntInput();
        if (idx < 0 || idx >= results.size()) return;

        // Spring Data JPA uses deleteById()
        repository.deleteById(results.get(idx).getId());
        System.out.println("Deleted.");
    }

    public void sellItem() {
        List<ProductEntity> results = repository.findAll();
        if (results.isEmpty()) return;

        listAny();
        System.out.print("Select index to sell: ");
        int idx = getIntInput();
        if (idx < 0 || idx >= results.size()) return;

        ProductEntity item = results.get(idx);

        // DELEGATION: Pass ID to the Spring Service!
        service.performSale(item.getId());

        cashTill.sellItem(new SaleableItem() {
            @Override public void sellItem() {}
            @Override public double getPrice() { return item.getPrice(); }
        });
    }

    public void systemReset() {
        long count = repository.count();
        repository.deleteAll();
        System.out.println("Success! " + count + " items were permanently destroyed.");
    }

    public void populate() {
        System.out.println("Populating Database with Faker...");
        Faker faker = new Faker();
        for (int i = 0; i < 3; i++) {
            BookEntity b = new BookEntity();
            b.setAuthor(faker.book().author());
            b.setTitle(faker.book().title());
            b.setPrice(faker.number().randomDouble(2, 10, 50));
            b.setCopies(faker.number().numberBetween(1, 20));
            repository.save(b);
        }
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}