package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Stack;
// import java.util.LinkedList;
// import java.util.Queue;
//import java.util.Objects;

public class GenericSearch1 {

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
	HashSet<String> visited = new HashSet<String>();

	Node currNode = null;

	public GenericSearch1() {
	}

	public GenericSearch1(String problem, String QingFun) {

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

			currNode = n;

//			System.out.println("Node poped from queue: " + n.operator + " " + n.level);
//			System.out.println("----------------------------------------");
//			System.out.println("WWWWWWW ::" + n.wait);
//			System.out.println("AKLI::: " + n.food);
//			System.out.println("MATRRRR::" + n.materials);
//			System.out.println("ENERGYY:::" + n.energy);
//			System.out.println("REQUEST TYPE :::  " + n.requestType);

			switch (n.operator) {

			case "root":
				childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD1", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD2", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "WAIT", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childRF = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestFood", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childRE = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestEnergy", n.wait,
						n.requestType, n.level + 1, n.money_spent, n.heuristic);
				childRM = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestMaterials", n.wait,
						n.requestType, n.level + 1, n.money_spent, n.heuristic);
				childNodes.add(childRF);
				childNodes.add(childRM);
				childNodes.add(childRE);
				childNodes.add(childB1);
				childNodes.add(childB2);
				break;

			case "RequestFood":
				if (n.wait == 0) {
					if (n.requestType != 0) {
						if (n.requestType == 1) {
							n.food += amountRequestFood;
							if (n.food > 50) {
								n.food = 50;
							}
						}

						else if (n.requestType == 2) {
							n.materials += amountRequestMaterials;
							if (n.materials > 50) {
								n.materials = 50;
							}
						}

						else {
							n.energy += amountRequestEnergy;
							if (n.energy > 50) {
								n.energy = 50;
							}
						}
						n.requestType = 0;
					}
				}
				n.requestFood(amountRequestFood, delayRequestFood);
				n.requestType = 1;
				n.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
				if (n.food < 0 || n.materials < 0 || n.energy < 0 || n.money_spent > 100000) {
					continue;
				}
				break;

			case "RequestMaterials":

				if (n.wait == 0) {
					if (n.requestType != 0) {
						if (n.requestType == 1) {
							n.food += amountRequestFood;
							if (n.food > 50) {
								n.food = 50;
							}
						}

						else if (n.requestType == 2) {
							n.materials += amountRequestMaterials;
							if (n.materials > 50) {
								n.materials = 50;
							}
						}

						else {
							n.energy += amountRequestEnergy;
							if (n.energy > 50) {
								n.energy = 50;
							}
						}
						n.requestType = 0;
					}
				}
				n.requestMaterial(amountRequestMaterials, delayRequestMaterials);
				n.requestType = 2;
				n.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;
				if (n.food < 0 || n.materials < 0 || n.energy < 0 || n.money_spent > 100000) {
					continue;
				}

				break;

			case "RequestEnergy":
				if (n.wait == 0) {
					if (n.requestType != 0) {
						if (n.requestType == 1) {
							n.food += amountRequestFood;
							if (n.food > 50) {
								n.food = 50;
							}
						}

						else if (n.requestType == 2) {
							n.materials += amountRequestMaterials;
							if (n.materials > 50) {
								n.materials = 50;
							}
						}

						else {
							n.energy += amountRequestEnergy;
							if (n.energy > 50) {
								n.energy = 50;
							}
						}
						n.requestType = 0;
					}
				}
				n.requestEnergy(amountRequestEnergy, delayRequestEnergy);
				n.requestType = 3;
				n.money_spent += unitPriceEnergy + unitPriceFood + unitPriceMaterials;

				if (n.food < 0 || n.materials < 0 || n.energy < 0 || n.money_spent > 100000) {
					continue;
				}

				break;

			case "BUILD1":

				if (n.wait > 0)
					n.wait--;
				else if (n.wait == 0) {
					if (n.requestType != 0) {
						if (n.requestType == 1) {
							n.food += amountRequestFood;
							if (n.food > 50) {
								n.food = 50;
							}
						}

						else if (n.requestType == 2) {
							n.materials += amountRequestMaterials;
							if (n.materials > 50) {
								n.materials = 50;
							}
						}

						else {
							n.energy += amountRequestEnergy;
							if (n.energy > 50) {
								n.energy = 50;
							}
						}
						n.requestType = 0;
					}
				}

				n.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);
				n.money_spent = n.money_spent + priceBUILD1 + foodUseBUILD1 * unitPriceFood
						+ materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
				if (n.food < 0 || n.materials < 0 || n.energy < 0 || n.money_spent > 100000) {
					continue;
				}

				break;

			case "BUILD2":

				if (n.wait > 0)
					n.wait--;

				else if (n.wait == 0) {
					if (n.requestType != 0) {
						if (n.requestType == 1) {
							n.food += amountRequestFood;
							if (n.food > 50) {
								n.food = 50;
							}
						}

						else if (n.requestType == 2) {
							n.materials += amountRequestMaterials;
							if (n.materials > 50) {
								n.materials = 50;
							}
						}

						else {
							n.energy += amountRequestEnergy;
							if (n.energy > 50) {
								n.energy = 50;
							}
						}
						n.requestType = 0;
					}
				}

				n.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);
				n.money_spent = n.money_spent + priceBUILD2 + foodUseBUILD2 * unitPriceFood
						+ materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;

				if (n.food < 0 || n.materials < 0 || n.energy < 0 || n.money_spent > 100000) {
					continue;
				}

				break;

			case "WAIT":
				if (n.wait > 0)
					// System.out.println("--------------------alelniiii----------------:::" +
					// n.wait);
					n.wait--;
				n.Wait();
				// System.out.println("MERCIII----------------:::" + n.wait);
				// System.out.println(" AKLI:" + n.food + " MATERRR:" + n.materials + " TAQtyy:"
				// + n.energy + " PROPROPRO:" + n.prosperity);
				if (n.wait == 0 && n.requestType != 0) {
					// System.out.println("ANA REQUEST TYPE ::::" + n.requestType);
					switch (n.requestType) {
					case 1:
						n.food += amountRequestFood;
						if (n.food > 50) {
							n.food = 50;
						}
						break;
					case 2:
						n.materials += amountRequestMaterials;
						if (n.materials > 50) {
							n.materials = 50;
						}
						break;
					case 3:
						n.energy += amountRequestEnergy;
						if (n.energy > 50) {
							n.energy = 50;
						}
						break;
					}
					n.requestType = 0;
				}

				n.money_spent = n.money_spent + unitPriceEnergy + unitPriceFood + unitPriceMaterials;
				if (n.food < 0 || n.materials < 0 || n.energy < 0 || n.money_spent > 100000) {
					continue;
				}

				break;
			}

			if (n.prosperity >= 100) {
				formulateSolution();
				break;
			}

			if (isRedundant(n)) {
//				System.out.println("PRINTTTT RDDDDD");
//				System.out.println("food :" + n.food);
//				System.out.println("materials :" + n.materials);
//				System.out.println("energy : " + n.energy) ;
//				System.out.println("prosperity : " + n.prosperity) ;
//				System.out.println("request Type  : " + n.requestType) ;

				continue;
			}
			visited.add(tony(n));

			exCount++;

//			if (n.food < 0 || n.materials < 0 || n.energy < 0 || n.money_spent > 100000) {
//				continue;
//			}

			/// CREATINGGGG CHILDRENNNN
			switch (n.operator) {

			case "RequestFood":

				childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD1", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD2", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "WAIT", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childNodes.add(childW);
				childNodes.add(childB1);
				childNodes.add(childB2);

				break;

			case "RequestMaterials":
				childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD1", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD2", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "WAIT", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childNodes.add(childW);
				childNodes.add(childB1);
				childNodes.add(childB2);

				break;

			case "RequestEnergy":

				childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD1", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD2", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "WAIT", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childNodes.add(childW);
				childNodes.add(childB1);
				childNodes.add(childB2);

				break;

			case "BUILD1":
				if (n.wait <= 0) {
					childRF = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestFood", n.wait,
							n.requestType, n.level + 1, n.money_spent, n.heuristic);
					childRE = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestEnergy", n.wait,
							n.requestType, n.level + 1, n.money_spent, n.heuristic);
					childRM = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestMaterials", n.wait,
							n.requestType, n.level + 1, n.money_spent, n.heuristic);
					childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD1", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD2", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);

					childNodes.add(childRF);
					childNodes.add(childRM);
					childNodes.add(childRE);
					childNodes.add(childB1);
					childNodes.add(childB2);
				} else {
					childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "WAIT", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD1", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD2", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childNodes.add(childW);
					childNodes.add(childB1);
					childNodes.add(childB2);
				}

				break;

			case "BUILD2":
				if (n.wait <= 0) {
					childRF = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestFood", n.wait,
							n.requestType, n.level + 1, n.money_spent, n.heuristic);
					childRE = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestEnergy", n.wait,
							n.requestType, n.level + 1, n.money_spent, n.heuristic);
					childRM = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestMaterials", n.wait,
							n.requestType, n.level + 1, n.money_spent, n.heuristic);
					childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD1", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD2", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);

					childNodes.add(childRF);
					childNodes.add(childRM);
					childNodes.add(childRE);
					childNodes.add(childB1);
					childNodes.add(childB2);
				} else {
					childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "WAIT", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD1", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD2", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childNodes.add(childW);
					childNodes.add(childB1);
					childNodes.add(childB2);
				}

				break;

			case "WAIT":
				if (n.wait <= 0) {
					childRF = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestFood", n.wait,
							n.requestType, n.level + 1, n.money_spent, n.heuristic);
					childRE = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestEnergy", n.wait,
							n.requestType, n.level + 1, n.money_spent, n.heuristic);
					childRM = new Node(n.prosperity, n.food, n.materials, n.energy, n, "RequestMaterials", n.wait,
							n.requestType, n.level + 1, n.money_spent, n.heuristic);

					childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD1", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD2", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childNodes.add(childRF);
					childNodes.add(childRM);
					childNodes.add(childRE);
					childNodes.add(childB1);
					childNodes.add(childB2);
				}

				else {
					childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "WAIT", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD1", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);
					childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "BUILD2", n.wait, n.requestType,
							n.level + 1, n.money_spent, n.heuristic);

					childNodes.add(childW);
					childNodes.add(childB1);
					childNodes.add(childB2);
				}

				break;
			}

			switch (QingFun) {
			
			case "EnqueueAtEnd": //BF
				while (!childNodes.isEmpty()) {
					nodes.add(childNodes.remove(childNodes.size() - 1));

				}
				break;
				
			case  "EnqueueAtFront" : //DF
				while (!childNodes.isEmpty()) {
					nodes.add(0, childNodes.remove(0));

				}
				break;
				
			case "EnqueueID-Front": //ID
				
				if (n.level == idLevel && nodes.isEmpty()) {
					nodes.add(root);

				} else if (n.level <= idLevel) {
					while (!childNodes.isEmpty()) {

						nodes.add(0, childNodes.remove(0));

					}
				}
				idLevel++;
				visited.clear();

				break;
				
			case "EnqueueCost": //UC
				
				while (!childNodes.isEmpty()) {
//					nodes.add(0, childNodes.remove(0));
					
					binarySearchAndInsertMoney(nodes, childNodes.remove(0));
					
					
				}
//				Comparator<Node> nodeComp = Comparator.comparingInt(node -> node.money_spent);
//				Collections.sort(nodes, nodeComp);
				
				

				break;


			case "EnqueueProsperityGR1": //GR1
				while (!childNodes.isEmpty()) {
//					nodes.add(0, childNodes.remove(0));
					
				Node no = childNodes.remove(0);	
				int noLeftB1 = (100 - no.prosperity) / prosperityBUILD1;
				int materNeededB1 = noLeftB1 * materialsUseBUILD1;

				int reqMatLeftB1 = (materNeededB1 - n.materials) / amountRequestMaterials;
				if (reqMatLeftB1 > 0)
					reqMatLeftB1 = 0;

				int pricereqMaterB1 = (unitPriceEnergy + unitPriceFood + unitPriceMaterials);
				int moneyNeededB1 = reqMatLeftB1 * pricereqMaterB1;

				int noLeftB2 = (100 - no.prosperity) / prosperityBUILD2;
				int materNeededB2 = noLeftB2 * materialsUseBUILD2;
				int reqMatLeftB2 = (materNeededB2 - n.materials) / amountRequestMaterials;
				if (reqMatLeftB2 > 0)
					reqMatLeftB2 = 0;

				int pricereqMaterB2 = (unitPriceEnergy + unitPriceFood + unitPriceMaterials);
				int moneyNeededB2 = reqMatLeftB2 * pricereqMaterB2;

				no.heuristic = Math.min((moneyNeededB1), (moneyNeededB2));
				binarySearchAndInsertG(nodes, no);
				}
				
//				Comparator<Node> nodeCompGR = Comparator.comparingInt(node -> node.heuristic);
//				Collections.sort(nodes, nodeCompGR.reversed());
				
				break;
				
			case "EnqueueBuildsProsGR2": //GR2
				while (!childNodes.isEmpty()) {
//					nodes.add(0, childNodes.remove(0));
					
				Node no = childNodes.remove(0);	
				int B1 = (100 - no.prosperity) / prosperityBUILD1;

				int B2 = (100 - no.prosperity) / prosperityBUILD2;


				int priceB1 = priceBUILD1 + foodUseBUILD1 * unitPriceFood + materialsUseBUILD1 * unitPriceMaterials
						+ energyUseBUILD1 * unitPriceEnergy;
				int priceB2 = priceBUILD2 + foodUseBUILD2 * unitPriceFood + materialsUseBUILD2 * unitPriceMaterials
						+ energyUseBUILD2 * unitPriceEnergy;

				no.heuristic = Math.min((B1 * priceB1), (B2 * priceB2));
//				Comparator<Node> nodeCompGR2 = Comparator.comparingInt(node -> node.heuristic);
//				Collections.sort(nodes, nodeCompGR2);
				binarySearchAndInsertG(nodes, no);
				}
		
				break;
				
			case "EnqueueProsperityAS1": //AS1
				
				while (!childNodes.isEmpty()) {
//					nodes.add(0, childNodes.remove(0));
					
				Node no = childNodes.remove(0);

				int noLeftB1A = (100 - no.prosperity) / prosperityBUILD1;
				int materNeededB1A = noLeftB1A * materialsUseBUILD1;

				int reqMatLeftB1A = (materNeededB1A - no.materials) / amountRequestMaterials;
				if (reqMatLeftB1A > 0)
					reqMatLeftB1A = 0;

				int pricereqMaterB1A = (unitPriceEnergy + unitPriceFood + unitPriceMaterials);
				int moneyNeededB1A = reqMatLeftB1A * pricereqMaterB1A;

				int noLeftB2A = (100 - no.prosperity) / prosperityBUILD2;
				int materNeededB2A = noLeftB2A * materialsUseBUILD2;
				int reqMatLeftB2A = (materNeededB2A - no.materials) / amountRequestMaterials;
				if (reqMatLeftB2A > 0)
					reqMatLeftB2A = 0;

				int pricereqMaterB2A = (unitPriceEnergy + unitPriceFood + unitPriceMaterials);
				int moneyNeededB2A = reqMatLeftB2A * pricereqMaterB2A;

				no.heuristic = Math.min((moneyNeededB1A), (moneyNeededB2A));
//				Comparator<Node> nodeCompA = Comparator.comparingInt(node -> (node.money_spent + node.heuristic));
//				Collections.sort(nodes, nodeCompA);
				binarySearchAndInsertA(nodes, no);
				}
				
				break;
				
			case "EnqueueBuildsProsAS2": //
				while (!childNodes.isEmpty()) {
				
				Node no = childNodes.remove(0);

				int B1A = (100 - no.prosperity) / prosperityBUILD1;

				int B2A = (100 - no.prosperity) / prosperityBUILD2;

				int priceB1A = priceBUILD1 + foodUseBUILD1 * unitPriceFood + materialsUseBUILD1 * unitPriceMaterials
						+ energyUseBUILD1 * unitPriceEnergy;
				int priceB2A = priceBUILD2 + foodUseBUILD2 * unitPriceFood + materialsUseBUILD2 * unitPriceMaterials
						+ energyUseBUILD2 * unitPriceEnergy;

				no.heuristic = Math.min((B1A * priceB1A), (B2A * priceB2A));
//				Comparator<Node> nodeCompA2 = Comparator.comparingInt(node -> (node.money_spent + node.heuristic));
//				Collections.sort(nodes, nodeCompA2);
				
				binarySearchAndInsertA(nodes, no);
				}

				break;
				
			}







		}

	}
	
	public static void binarySearchAndInsertA(ArrayList<Node> nodes, Node newNode) {
        Comparator<Node> nodeComp = Comparator.comparingInt(node -> (node.heuristic + node.money_spent));
        int index = Collections.binarySearch(nodes, newNode, nodeComp);

        if (index < 0) {
            // If not found, calculate the insertion point
            index = -index - 1;
        }

        // Insert the new node at the calculated index
        nodes.add(index, newNode);
    }
	
	public static void binarySearchAndInsertG(ArrayList<Node> nodes, Node newNode) {
        Comparator<Node> nodeComp = Comparator.comparingInt(node -> node.heuristic);
        int index = Collections.binarySearch(nodes, newNode, nodeComp);

        if (index < 0) {
            // If not found, calculate the insertion point
            index = -index - 1;
        }

        // Insert the new node at the calculated index
        nodes.add(index, newNode);
    }
	
	public static void binarySearchAndInsertMoney(ArrayList<Node> nodes, Node newNode) {
        Comparator<Node> nodeComp = Comparator.comparingInt(node -> node.money_spent);
        int index = Collections.binarySearch(nodes, newNode, nodeComp);

        if (index < 0) {
            // If not found, calculate the insertion point
            index = -index - 1;
        }

        // Insert the new node at the calculated index
        nodes.add(index, newNode);
    }
	

	public void formulateSolution() {
		Node temp = currNode;
		solution = "";
//		while (temp.parent != null) {
//			solution = temp.operator + "," + solution;
//			temp = temp.parent;
//		}

		///// OPTIMIZEDDD FORMULATE SOLTION
		///// !!______________________________________________
		StringBuilder solutionBuilder = new StringBuilder();
		while (temp.parent != null) {
			solutionBuilder.insert(0, temp.operator + ",");
			temp = temp.parent;
		}
		solution = solutionBuilder.toString();
		solution = solution.substring(0, solution.length() - 1);
		solution += ";" + currNode.money_spent + ";" + exCount;
	}

	public boolean isRedundant(Node n) {
		// for (int i = 0; i < visited.size(); i++) {

		// Node tmp = visited.get(i);

		// if (tmp.money_spent == n.money_spent && tmp.operator == n.operator &&
		// tmp.prosperity == n.prosperity
		// && tmp.food == n.food && tmp.energy == n.energy && tmp.materials ==
		// n.materials
		// && tmp.wait == n.wait && tmp.requestType == n.requestType) {
		// return true;
		// }
		// }
		if (visited.contains(tony(n))) {
			return true;
		}
		return false;
	}

	public static String tony(Node n) {
		return n.money_spent + " " + n.prosperity + " " + n.food + " " + n.energy + " " + n.materials + " " + n.wait
				+ " " + n.requestType;
	}
}