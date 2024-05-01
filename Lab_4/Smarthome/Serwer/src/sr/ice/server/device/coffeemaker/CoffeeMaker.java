package sr.ice.server.device.coffeemaker;

import Smarthome.*;
import com.zeroc.Ice.Current;
import sr.ice.server.device.Device;

import java.util.Arrays;
import java.util.List;

public class CoffeeMaker extends Device implements ICoffeeMaker {

    protected int waterLevel;
    protected int coffeeLevel;
    protected Coffee currentCoffee;

    public CoffeeMaker(String name) {
        super(name);
        waterLevel = 0;
        coffeeLevel = 0;
        currentCoffee = new Coffee(CoffeeType.ESPRESSO, CoffeeStrength.LIGHT);
    }

    @Override
    public int getWaterLevel(Current current) {
        System.out.println("[CoffeeMaker] " + name + ", getWaterLevel: " + waterLevel + ",id: " + current.id.name + ", category: " + current.id.category);
        return waterLevel;
    }

    @Override
    public int getCoffeeLevel(Current current) {
        System.out.println("[CoffeeMaker] " + name + ", getCoffeeLevel: " + coffeeLevel + ",id: " + current.id.name + ", category: " + current.id.category);
        return coffeeLevel;
    }

    @Override
    public Coffee getCurrentCoffee(Current current) {
        System.out.println("[CoffeeMaker] " + name + ", getCurrentCoffee: " + currentCoffee + ",id: " + current.id.name + ", category: " + current.id.category);
        return currentCoffee;
    }

    @Override
    public boolean setCoffee(Coffee coffee, Current current) throws InvalidCoffeeException {
        System.out.println("[CoffeeMaker] " + name + ", setCoffee: " + coffee + ",id: " + current.id.name + ", category: " + current.id.category);

        if (coffee == null) {
            throw new InvalidCoffeeException("Coffee cannot be null!");
        }
        if (coffee.type == null) {
            throw new InvalidCoffeeException("Invalid coffee type, cannot be null!");
        }
        if (coffee.strength == null) {
            throw new InvalidCoffeeException("Invalid coffee strength, cannot be null!");
        }
        this.currentCoffee = coffee;

        return true;
    }

    @Override
    public Coffee makeCoffee(Current current) throws CoffeeUnderflowException, WaterUnderflowException {
        System.out.println("[CoffeeMaker] " + name + ", makeCoffee " + ",id: " + current.id.name + ", category: " + current.id.category);
        int waterNeeded = 200;
        if (waterLevel < waterNeeded) {
            throw new WaterUnderflowException("Not enough amount of water. Must be >= 200!");
        }
        int coffeeNeeded = (currentCoffee.type.value() + 1) * currentCoffee.strength.value();
        if (coffeeLevel < coffeeNeeded) {
            throw new CoffeeUnderflowException("Not enough coffee. Must be >=" + coffeeNeeded + " for this coffee!");
        }

        waterLevel -= waterNeeded;
        coffeeLevel -= coffeeNeeded;
        return currentCoffee;
    }

    @Override
    public List<CoffeeType> getCoffeeTypes(Current current) {
        List<CoffeeType> result = Arrays.stream(CoffeeType.values()).toList();
        System.out.println("[CoffeeMaker] " + name + ", getCoffeeTypes: " + result + ",id: " + current.id.name + ", category: " + current.id.category);
        return result;
    }

    @Override
    public List<CoffeeStrength> getCoffeeStrengths(Current current) {
        List<CoffeeStrength> result = Arrays.stream(CoffeeStrength.values()).toList();
        System.out.println("[CoffeeMaker] " + name + ", getCoffeeStrengths: " + result + ",id: " + current.id.name + ", category: " + current.id.category);
        return result;
    }

    @Override
    public boolean addWater(int water, Current current) throws InvalidAmountException{
        System.out.println("[CoffeeMaker] " + name + ", addWater: " + water + ",id: " + current.id.name + ", category: " + current.id.category);

        if (water < 0) {
            throw new InvalidAmountException("You can add only more than 0 ml of water!");
        }
        waterLevel += water;
        return true;
    }

    @Override
    public boolean addCoffee(int coffee, Current current) throws InvalidAmountException{
        System.out.println("[CoffeeMaker] " + name + ", addCoffee: " + coffee + ",id: " + current.id.name + ", category: " + current.id.category);
        if (coffee < 0) {
            throw new InvalidAmountException("You can add only more than 0 g of coffee!");
        }
        coffeeLevel += coffee;
        return true;
    }
}
