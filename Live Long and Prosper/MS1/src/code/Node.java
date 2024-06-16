package code;

import java.util.Objects;

public class Node {
    public int prosperity;
    public int food;
    public int materials;
    public int energy;
    public Node parent;
    public String operator;
    public int pathCost;
    public int depth;
    public int wait;
    public int requestType;
    public int level;
    public int heuristic;
    public int money_spent;



    public Node(int prosperity, int food, int materials, int energy, Node parent, String operator, int wait,int requestType, int level, int money_spent,int heuristic) {
        this.prosperity = prosperity;
        this.food = food;
        this.materials = materials;
        this.energy = energy;
        this.parent = parent;
        this.operator = operator;
        this.wait = wait;
        this.requestType = requestType;
        this.level = level;
        this.money_spent = money_spent;
        this.heuristic = heuristic;
    }

    public void requestFood(int amount, int delay) {

        // requestType = 1;
        this.energy--;
        this.food--;
        this.materials--;
        // money_spent = money_spent + unitPriceEnergy + unitPriceFood +
        // unitPriceMaterials;
        // this.food += amount;
        this.wait = delay;
    }

    public void requestMaterial(int amount, int delay) {

        // requestType = 2;
        this.energy--;
        this.food--;
        this.materials--;
        // money_spent = money_spent + unitPriceEnergy + unitPriceFood +
        // unitPriceMaterials;
        // initialMaterials = initialMaterials + amount;
        this.wait = delay;
    }

    public void requestEnergy(int amount, int delay) {

        // requestType = 3;
        this.energy--;
        this.food--;
        this.materials--;
        // money_spent = money_spent + unitPriceEnergy + unitPriceFood +
        // unitPriceMaterials;
        // initialEnergy = initialEnergy + amount;
        this.wait = delay;
    }

    public void Build1(int price, int food, int material, int energy, int prosperity) {

        this.prosperity += prosperity;

        this.energy -= energy;
        this.food -= food;
        this.materials -= material;

    }

    public void Build2(int price, int food, int material, int energy, int prosperity) {

        this.prosperity += prosperity;

        this.energy -= energy;
        this.food -= food;
        this.materials -= material;

    }

    public void Wait() {
        this.energy--;
        this.food--;
        this.materials--;
        // this.wait--;

    }

    // public void Build2(int price, int food, int material, int energy, int
    // prosperity) {
    // if (wait > 0) {
    // wait--;
    // }
    // if (wait == 0 && requestType != 0) {
    // switch (requestType) {
    // case 1:
    // initialFood += amountRequestFood;
    // break;
    // case 2:
    // initialMaterials += amountRequestMaterials;
    // break;
    // case 3:
    // initialEnergy += amountRequestEnergy;
    // break;
    // }
    // requestType = 0;
    // }
    // initialProsperity += prosperity;
    // money_spent = money_spent + price + food * unitPriceFood + material *
    // unitPriceMaterials
    // + energy * unitPriceEnergy;
    // initialEnergy -= energy;
    // initialFood -= food;
    // initialMaterials -= material;
    // }
//	@Override
//	public int hashCode() {
//		return Objects.hash(materials, energy);
//	}
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) {
//			return true;
//		}
//		if (obj == null || getClass() != obj.getClass()) {
//			return false;
//		}
//		Node other = (Node) obj;
//		
//		// Add your comparison logic here
//		if (
//	     other.food == this.food ||
//	//        other.materials > this.materials ||
//	  //      other.energy > this.energy ||
//		other.prosperity == this.prosperity
//				|| (other.money_spent == this.money_spent )//&& other.prosperity < this.prosperity)
//				|| this.requestType == other.requestType || other.wait == this.wait 
//		// ||
//		// other.wait < this.wait
//		) {
//			return false; // Objects are not equal
//		} else {
//			return true; // Objects are equal
//		}
//	}


}
