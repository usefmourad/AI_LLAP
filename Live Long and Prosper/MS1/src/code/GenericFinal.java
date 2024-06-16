package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class GenericFinal {

	 public String problem;

	    public String solution;

	    public int initialProsperity;
	    public int initialFood;
	    public int initialMaterials;
	    public int initialEnergy;
	    public int unitPriceFood;
	    public int unitPriceMaterials;
	    public int unitPriceEnergy;
	    public int amountRequestFood;
	    public int delayRequestFood;
	    public int amountRequestMaterials;
	    public int delayRequestMaterials;
	    public int amountRequestEnergy;
	    public int delayRequestEnergy;
	    public int priceBUILD1;
	    public int foodUseBUILD1;
	    public int materialsUseBUILD1;
	    public int energyUseBUILD1;
	    public int prosperityBUILD1;
	    public int priceBUILD2;
	    public int foodUseBUILD2;
	    public int materialsUseBUILD2;
	    public int energyUseBUILD2;
	    public int prosperityBUILD2;

	    public int money_spent = 0;
	    public int exCount;

	    /// initializes all types of nodes
	    Node root;
	    Node childRF;
	    Node childRM;
	    Node childRE;
	    Node childB1;
	    Node childB2;
	    Node childW;

	    /// adds child nodes to be added to main queue.
	    ArrayList<Node> childNodes = new ArrayList<Node>();

	    /// adds all nodes to main queue.
	    ArrayList<Node> nodes = new ArrayList<Node>();

	    /// adds all visited nodes to avoid redundant states.
	    // ArrayList<Node> visited = new ArrayList<Node>();

	    HashSet<String> visited = new HashSet<String>();

	    Node currNode = null;


	   public GenericFinal() {
		   
	   }
	    public GenericFinal(String problem, String QingFun) {

	        solution = "NOSOLUTION";

	        String[] values = problem.split(";");

	        initialProsperity = Integer.parseInt(values[0].split(",")[0]);

	        initialFood = Integer.parseInt(values[1].split(",")[0]);
	        initialMaterials = Integer.parseInt(values[1].split(",")[1]);
	        initialEnergy = Integer.parseInt(values[1].split(",")[2]);

	        unitPriceFood = Integer.parseInt(values[2].split(",")[0]);
	        unitPriceMaterials = Integer.parseInt(values[2].split(",")[1]);
	        unitPriceEnergy = Integer.parseInt(values[2].split(",")[2]);

	        amountRequestFood = Integer.parseInt(values[3].split(",")[0]);
	        delayRequestFood = Integer.parseInt(values[3].split(",")[1]);

	        amountRequestMaterials = Integer.parseInt(values[4].split(",")[0]);
	        delayRequestMaterials = Integer.parseInt(values[4].split(",")[1]);

	        amountRequestEnergy = Integer.parseInt(values[5].split(",")[0]);
	        delayRequestEnergy = Integer.parseInt(values[5].split(",")[1]);

	        priceBUILD1 = Integer.parseInt(values[6].split(",")[0]);
	        foodUseBUILD1 = Integer.parseInt(values[6].split(",")[1]);
	        materialsUseBUILD1 = Integer.parseInt(values[6].split(",")[2]);
	        energyUseBUILD1 = Integer.parseInt(values[6].split(",")[3]);
	        prosperityBUILD1 = Integer.parseInt(values[6].split(",")[4]);

	        priceBUILD2 = Integer.parseInt(values[7].split(",")[0]);
	        foodUseBUILD2 = Integer.parseInt(values[7].split(",")[1]);
	        materialsUseBUILD2 = Integer.parseInt(values[7].split(",")[2]);
	        energyUseBUILD2 = Integer.parseInt(values[7].split(",")[3]);
	        prosperityBUILD2 = Integer.parseInt(values[7].split(",")[4]);

	        /// initialize a root.
	        root = new Node(initialProsperity, initialFood, initialMaterials, initialEnergy, null, "root", 0, 0, 0, 0, 0);

	        nodes.add(root);

	        GenericSearchM(QingFun);

	    }
	    
	    public void GenericSearchM(String QingFun) {

	        int idLevel = 0;

	        exCount = 0;

	        while (!nodes.isEmpty()) {

	            Node n = nodes.remove(0);
	            
	            if (isRedundant(n)) {
	                continue;
	            }

	            visited.add(tony(n));

	            exCount++;
	            if (n.prosperity >= 100) {
	                formulateSolution();
	                break;
	            }

	            currNode = n;

	            if (n.operator == "root") {
	            	
	            	currNode.requestType = 1;
	            	currNode.requestFood(amountRequestFood, delayRequestFood);
	            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childRF = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestFood", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childRF);
	            	}
	            	currNode = n;
	            	currNode.requestType = 2;
	            	currNode.requestMaterial(amountRequestMaterials, delayRequestMaterials);
	            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childRM = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestMaterial", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childRM);
	            	}
	            	currNode = n;
	            	currNode.requestType = 3;
	            	currNode.requestEnergy(amountRequestEnergy, delayRequestEnergy);
	            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childRE = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestEnergy", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childRE);
	            	}
	            	currNode = n;
	            	if(currNode.wait == -1 && currNode.requestType != 0) {
	            		if(currNode.requestType == 1) {
	            			currNode.food += amountRequestFood;
	            			if(currNode.food > 50)
	            				currNode.food = 50;
	            		}
	            		else if(currNode.requestType == 2) {
	            			currNode.materials += amountRequestMaterials;
	            			if(currNode.materials > 50)
	            				currNode.materials = 50;
	            		}
	            		 else {
	                            n.energy += amountRequestEnergy;
	                            if (n.energy > 50) {
	                                n.energy = 50;
	                            }
	                        }
	                        n.requestType = 0;
	                    }
	            	currNode.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);
	            	currNode.money_spent += priceBUILD1 + foodUseBUILD1 * unitPriceFood
	                        + materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childB1 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childB1);
	            	}
	            	currNode = n;
	            	if(currNode.wait == -1 && currNode.requestType != 0) {
	            		if(currNode.requestType == 1) {
	            			currNode.food += amountRequestFood;
	            			if(currNode.food > 50)
	            				currNode.food = 50;
	            		}
	            		else if(currNode.requestType == 2) {
	            			currNode.materials += amountRequestMaterials;
	            			if(currNode.materials > 50)
	            				currNode.materials = 50;
	            		}
	            		 else {
	                            n.energy += amountRequestEnergy;
	                            if (n.energy > 50) {
	                                n.energy = 50;
	                            }
	                        }
	                        n.requestType = 0;
	                    }
	            	currNode.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);
	            	currNode.money_spent += priceBUILD2 + foodUseBUILD2 * unitPriceFood
	                        + materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childB2 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childB2);
	            	}
	            	}
	            
	            if(n.operator == "requestFood") {
	            	currNode = n;
	            	if(currNode.wait == -1 && currNode.requestType != 0) {
	            		if(currNode.requestType == 1) {
	            			currNode.food += amountRequestFood;
	            			if(currNode.food > 50)
	            				currNode.food = 50;
	            		}
	            		else if(currNode.requestType == 2) {
	            			currNode.materials += amountRequestMaterials;
	            			if(currNode.materials > 50)
	            				currNode.materials = 50;
	            		}
	            		 else {
	                            n.energy += amountRequestEnergy;
	                            if (n.energy > 50) {
	                                n.energy = 50;
	                            }
	                        }
	                        n.requestType = 0;
	                    }
	            	currNode.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);
	            	currNode.money_spent += priceBUILD1 + foodUseBUILD1 * unitPriceFood
	                        + materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childB1 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childB1);
	            	}
	            	currNode = n;
	            	if(currNode.wait == -1 && currNode.requestType != 0) {
	            		if(currNode.requestType == 1) {
	            			currNode.food += amountRequestFood;
	            			if(currNode.food > 50)
	            				currNode.food = 50;
	            		}
	            		else if(currNode.requestType == 2) {
	            			currNode.materials += amountRequestMaterials;
	            			if(currNode.materials > 50)
	            				currNode.materials = 50;
	            		}
	            		 else {
	                            n.energy += amountRequestEnergy;
	                            if (n.energy > 50) {
	                                n.energy = 50;
	                            }
	                        }
	                        n.requestType = 0;
	                    }
	            	currNode.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);
	            	currNode.money_spent += priceBUILD2 + foodUseBUILD2 * unitPriceFood
	                        + materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childB2 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childB2);
	            	}
	            	
	            	currNode = n;
	            	
	            	currNode.Wait();
	            	currNode.wait--;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childW = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Wait", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childW);
	            	}
	            	
	            	
	            	
	            }
	            
	            if(n.operator == " requestMaterial") {
	            	currNode = n;
	            	if(currNode.wait == -1 && currNode.requestType != 0) {
	            		if(currNode.requestType == 1) {
	            			currNode.food += amountRequestFood;
	            			if(currNode.food > 50)
	            				currNode.food = 50;
	            		}
	            		else if(currNode.requestType == 2) {
	            			currNode.materials += amountRequestMaterials;
	            			if(currNode.materials > 50)
	            				currNode.materials = 50;
	            		}
	            		 else {
	                            n.energy += amountRequestEnergy;
	                            if (n.energy > 50) {
	                                n.energy = 50;
	                            }
	                        }
	                        n.requestType = 0;
	                    }
	            	currNode.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);
	            	currNode.money_spent += priceBUILD1 + foodUseBUILD1 * unitPriceFood
	                        + materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childB1 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childB1);
	            	}
	            	currNode = n;
	            	if(currNode.wait == -1 && currNode.requestType != 0) {
	            		if(currNode.requestType == 1) {
	            			currNode.food += amountRequestFood;
	            			if(currNode.food > 50)
	            				currNode.food = 50;
	            		}
	            		else if(currNode.requestType == 2) {
	            			currNode.materials += amountRequestMaterials;
	            			if(currNode.materials > 50)
	            				currNode.materials = 50;
	            		}
	            		 else {
	                            n.energy += amountRequestEnergy;
	                            if (n.energy > 50) {
	                                n.energy = 50;
	                            }
	                        }
	                        n.requestType = 0;
	                    }
	            	currNode.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);
	            	currNode.money_spent += priceBUILD2 + foodUseBUILD2 * unitPriceFood
	                        + materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childB2 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childB2);
	            	}
	            	
	            	currNode = n;
	            	
	            	currNode.Wait();
	            	currNode.wait --;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childW = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Wait", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childW);
	            	}
	            	
	            }
	            
	            if(n.operator == "requestEnergy") {
	            	currNode = n;
	            	if(currNode.wait == -1 && currNode.requestType != 0) {
	            		if(currNode.requestType == 1) {
	            			currNode.food += amountRequestFood;
	            			if(currNode.food > 50)
	            				currNode.food = 50;
	            		}
	            		else if(currNode.requestType == 2) {
	            			currNode.materials += amountRequestMaterials;
	            			if(currNode.materials > 50)
	            				currNode.materials = 50;
	            		}
	            		 else {
	                            n.energy += amountRequestEnergy;
	                            if (n.energy > 50) {
	                                n.energy = 50;
	                            }
	                        }
	                        n.requestType = 0;
	                    }
	            	currNode.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);
	            	currNode.money_spent += priceBUILD1 + foodUseBUILD1 * unitPriceFood
	                        + materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childB1 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childB1);
	            	}
	            	currNode = n;
	            	if(currNode.wait == -1 && currNode.requestType != 0) {
	            		if(currNode.requestType == 1) {
	            			currNode.food += amountRequestFood;
	            			if(currNode.food > 50)
	            				currNode.food = 50;
	            		}
	            		else if(currNode.requestType == 2) {
	            			currNode.materials += amountRequestMaterials;
	            			if(currNode.materials > 50)
	            				currNode.materials = 50;
	            		}
	            		 else {
	                            n.energy += amountRequestEnergy;
	                            if (n.energy > 50) {
	                                n.energy = 50;
	                            }
	                        }
	                        n.requestType = 0;
	                    }
	            	currNode.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);
	            	currNode.money_spent += priceBUILD2 + foodUseBUILD2 * unitPriceFood
	                        + materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childB2 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childB2);
	            	}
	            	
	            	currNode = n;
	            	
	            	currNode.Wait();
	            	currNode.wait --;
	            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
	            	{
	            		childW = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Wait", currNode.wait, currNode.requestType,
	            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
	            		childNodes.add(childW);
	            	}
	            	
	            	
	            
	            }
	            
	            if(n.operator == "build1") {
	            	currNode = n;
	            	if(currNode.wait == -1) {
	            		currNode.requestType = 1;
		            	currNode.requestFood(amountRequestFood, delayRequestFood);
		            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childRF = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestFood", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childRF);
		            	}
		            	currNode = n;
		            	currNode.requestType = 2;
		            	currNode.requestMaterial(amountRequestMaterials, delayRequestMaterials);
		            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childRM = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestMaterial", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childRM);
		            	}
		            	currNode = n;
		            	currNode.requestType = 3;
		            	currNode.requestEnergy(amountRequestEnergy, delayRequestEnergy);
		            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childRE = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestEnergy", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childRE);
		            	}
		            	currNode = n;
	            		currNode.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);
		            	currNode.money_spent += priceBUILD1 + foodUseBUILD1 * unitPriceFood
		                        + materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB1 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB1);
		            	}
		            	currNode = n;
		            	currNode.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);
		            	currNode.money_spent += priceBUILD2 + foodUseBUILD2 * unitPriceFood
		                        + materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB2 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB2);
		            	}
	            	}
	            	else {
	            		currNode = n;
		            	
		            	currNode.Wait();
		            	currNode.wait --;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childW = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Wait", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childW);
		            	}
		            	currNode = n;
	            		currNode.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);
		            	currNode.money_spent += priceBUILD1 + foodUseBUILD1 * unitPriceFood
		                        + materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB1 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB1);
		            	}
		            	currNode = n;
		            	currNode.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);
		            	currNode.money_spent += priceBUILD2 + foodUseBUILD2 * unitPriceFood
		                        + materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB2 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB2);
		            	}
	            	}
	            }
	            if(n.operator == "build2") {
	            	currNode = n;
	            	if(currNode.wait == -1) {
	            		currNode.requestType = 1;
		            	currNode.requestFood(amountRequestFood, delayRequestFood);
		            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childRF = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestFood", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childRF);
		            	}
		            	currNode = n;
		            	currNode.requestType = 2;
		            	currNode.requestMaterial(amountRequestMaterials, delayRequestMaterials);
		            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childRM = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestMaterial", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childRM);
		            	}
		            	currNode = n;
		            	currNode.requestType = 3;
		            	currNode.requestEnergy(amountRequestEnergy, delayRequestEnergy);
		            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childRE = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestEnergy", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childRE);
		            	}
		            	currNode = n;
	            		currNode.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);
		            	currNode.money_spent += priceBUILD1 + foodUseBUILD1 * unitPriceFood
		                        + materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB1 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB1);
		            	}
		            	currNode = n;
		            	currNode.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);
		            	currNode.money_spent += priceBUILD2 + foodUseBUILD2 * unitPriceFood
		                        + materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB2 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB2);
		            	}
	            	}
	            	else {
	            		currNode = n;
		            	
		            	currNode.Wait();
		            	currNode.wait --;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childW = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Wait", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childW);
		            	}
		            	currNode = n;
	            		currNode.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);
		            	currNode.money_spent += priceBUILD1 + foodUseBUILD1 * unitPriceFood
		                        + materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB1 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB1);
		            	}
		            	currNode = n;
		            	currNode.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);
		            	currNode.money_spent += priceBUILD2 + foodUseBUILD2 * unitPriceFood
		                        + materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB2 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB2);
		            	}
	            	}
	            }
	            
	            if(n.operator == "Wait") {
	            	currNode = n;
	            	if(currNode.wait == -1) {
	            		currNode.requestType = 1;
		            	currNode.requestFood(amountRequestFood, delayRequestFood);
		            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childRF = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestFood", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childRF);
		            	}
		            	currNode = n;
		            	currNode.requestType = 2;
		            	currNode.requestMaterial(amountRequestMaterials, delayRequestMaterials);
		            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childRM = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestMaterial", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childRM);
		            	}
		            	currNode = n;
		            	currNode.requestType = 3;
		            	currNode.requestEnergy(amountRequestEnergy, delayRequestEnergy);
		            	currNode.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childRE = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "requestEnergy", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childRE);
		            	}
		            	currNode = n;
	            		currNode.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);
		            	currNode.money_spent += priceBUILD1 + foodUseBUILD1 * unitPriceFood
		                        + materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB1 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB1);
		            	}
		            	currNode = n;
		            	currNode.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);
		            	currNode.money_spent += priceBUILD2 + foodUseBUILD2 * unitPriceFood
		                        + materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB2 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB2);
		            	}
	            	}
	            	else {
	            		currNode = n;
		            	
		            	currNode.Wait();
		            	currNode.wait --;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childW = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Wait", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childW);
		            	}
		            	currNode = n;
	            		currNode.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);
		            	currNode.money_spent += priceBUILD1 + foodUseBUILD1 * unitPriceFood
		                        + materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB1 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB1);
		            	}
		            	currNode = n;
		            	currNode.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);
		            	currNode.money_spent += priceBUILD2 + foodUseBUILD2 * unitPriceFood
		                        + materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
		            	if(currNode.food >= 0 && currNode.materials >= 0 && currNode.energy >= 0 && currNode.money_spent < 100000)
		            	{
		            		childB2 = new Node(currNode.prosperity, currNode.food, currNode.materials, currNode.energy, currNode, "Build1", currNode.wait, currNode.requestType,
		            				currNode.level + 1, currNode.money_spent, currNode.heuristic);
		            		childNodes.add(childB2);
		            	}
	            	}
	            }
	            if (QingFun == "EnqueueAtEnd") {
	                while (!childNodes.isEmpty()) {
	                    nodes.add(childNodes.remove(0));

	                }

	            }

	            /// DF
	            else if (QingFun == "EnqueueAtFront") {
	                while (!childNodes.isEmpty()) {
	                    nodes.add(0, childNodes.remove(0));

	                }

	            }

	            /// ID
	            else if (QingFun == "EnqueueID-Front") {

	                if (n.level == idLevel && nodes.isEmpty()) {
	                    nodes.add(root);

	                } else if (n.level <= idLevel) {
	                    while (!childNodes.isEmpty()) {

	                        nodes.add(0, childNodes.remove(0));

	                    }
	                }
	                idLevel++;
	            }

	            /// UC
	            else if (QingFun == "EnqueueCost") {

	                while (!childNodes.isEmpty()) {
	                    nodes.add(0, childNodes.remove(0));
	                }
	                Comparator<Node> nodeComp = Comparator.comparingInt(node -> node.money_spent);
	                Collections.sort(nodes, nodeComp);
	            }

	            /// GR1
	            else if (QingFun == "EnqueueProsperityGR1") {

	                while (!childNodes.isEmpty()) {
	                    nodes.add(childNodes.remove(0));
	                }

	                for (int i = 0; i < nodes.size(); i++) {
	                    Node no = nodes.get(i);
	                    no.heuristic = no.prosperity;
	                }

	                Comparator<Node> nodeComp = Comparator.comparingInt(node -> node.heuristic);
	                Collections.sort(nodes, nodeComp.reversed());
	            }

	            /// GR2
	            else if (QingFun == "EnqueueBuildsProsGR2") {
	                while (!childNodes.isEmpty()) {
	                    nodes.add(0, childNodes.remove(0));
	                }
	                int B1 = (100 - n.prosperity) / priceBUILD1;

	                int B2 = (100 - n.prosperity) / priceBUILD2;

	                n.heuristic = Math.min((B1 * priceBUILD1), (B2 * priceBUILD2));
	                Comparator<Node> nodeComp = Comparator.comparingInt(node -> node.heuristic);
	                Collections.sort(nodes, nodeComp);
	            }

	            /// AS1
	            else if (QingFun == "EnqueueProsperityAS1") {
	                while (!childNodes.isEmpty()) {
	                    nodes.add(childNodes.remove(0));
	                }
	                Comparator<Node> nodeComp = Comparator
	                        .comparingInt(node -> (node.money_spent + (100 - node.prosperity)));
	                Collections.sort(nodes, nodeComp);
	            }

	            /// AS2
	            else if (QingFun == "EnqueueBuildsProsAS2") {
	                while (!childNodes.isEmpty()) {
	                    nodes.add(0, childNodes.remove(0));
	                }

	                int B1 = (100 - n.prosperity) / priceBUILD1;

	                int B2 = (100 - n.prosperity) / priceBUILD2;

	                n.heuristic = Math.min((B1 * priceBUILD1), (B2 * priceBUILD2));
	                Comparator<Node> nodeComp = Comparator
	                        .comparingInt(node -> (node.money_spent + node.heuristic));
	                Collections.sort(nodes, nodeComp);
	            }

	        }

	     }
	    
	    public void formulateSolution() {
	        Node temp = currNode;
	        solution = "";
	        while (temp.parent != null) {
	            solution =    temp.operator + "," + solution  ;
	            temp = temp.parent;
	        }
	        solution = solution.substring(0, solution.length() - 1);
	        solution += ";" + currNode.money_spent + ";" + exCount;
	    }
	    
	    public boolean isRedundant(Node n) {
	        // for (int i = 0; i < visited.size(); i++) {

	        //     Node tmp = visited.get(i);

	        //     if (tmp.money_spent == n.money_spent && tmp.operator == n.operator && tmp.prosperity == n.prosperity
	        //             && tmp.food == n.food && tmp.energy == n.energy && tmp.materials == n.materials
	        //             && tmp.wait == n.wait && tmp.requestType == n.requestType) {
	        //         return true;
	        //     }
	        // }
	        if(visited.contains(tony(n))){
	            return true;
	        }
	        return false;
	    }

	    public static String tony(Node n) {
	        return n.money_spent + " " + n.prosperity
	                + " " + n.food + " " + n.energy + " " + n.materials
	                + " " + n.wait;
	    }

            

	    }