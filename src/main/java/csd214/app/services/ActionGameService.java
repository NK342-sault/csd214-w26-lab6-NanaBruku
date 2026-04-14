package csd214.app.services;

import csd214.app.entities.ProductEntity;
import csd214.app.pojos.ActionGame;
import csd214.app.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;


public class ActionGameService {
    private final ProductRepository<ProductEntity> repository;

    public ActionGameService(ProductRepository<ProductEntity> repository) {
        this.repository = repository;
    }

    public void launchMultiplayerExpansion(Long id, double priceSurcharge) {
        Optional<ProductEntity> entity = repository.findById(id);
        System.out.println("Error: Action Game with id " + id + " not found");
    }
    public void bulkRestockActionTitle(String name, String platform, double price,  int count){
        for (int i=0; i< count; i++){
            ActionGame newGame = new ActionGame(name, platform, price, i);
            repository.save(newGame);
        }
    }
    public List<ProductEntity> getAllActionGames() {
        return repository.findAll();
    }
}