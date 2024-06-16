package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

// import java.util.LinkedList;
// import java.util.Queue;

public class GenericSearch {

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
	public int timeStep = 0;
	// public int wait = 0;

	public int requestType; // 1= Food, 2= Material, 3= Energy
	// public int requestAmount;

	/// counts the number of expanded nodes.
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
	//ArrayList<Node> visited = new ArrayList<Node>();

	HashSet<String> visited = new HashSet<String>();
	
	Node currNode = null;

	boolean found = false;

	public GenericSearch() {
	}

	public GenericSearch(String problem, String QingFun) {

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

		solution = "NOSOLUTION";

		nodes.add(root);

		GenericSearchM(QingFun);

	}

	public void GenericSearchM(String QingFun) {

		/// used for ID strategy.
		int idLevel = 0;

		exCount = 0;

		while (!nodes.isEmpty()) {

			Node n = nodes.remove(0);
			currNode = n;
			// visited.add(n);

			if (n.operator == "Build1") {

			if (n.food < foodUseBUILD1 || n.materials < materialsUseBUILD1 || n.energy <
			energyUseBUILD1) {
			continue;
			}

			}

			if (n.operator == "Build2") {

			if (n.food < foodUseBUILD2 || n.materials < materialsUseBUILD2 || n.energy <
			energyUseBUILD2) {
			continue;
			}

			}

			Expand(n);
			/// visited queue to handle redundant states.

			if (!isRedundant(n)) {
				visited.add(tony(n));

				// {
				// nodes.add(n);
				// }
				// else{
				// // Expand(n);
				// }
				//
				// visited.add(tmp);
				// }

				if (n.money_spent < 100000 && n.food > 0 && n.materials > 0 && n.energy > 0) {
					exCount++;
					//System.out.println(exCount);

					if (n.prosperity >= 100) {
						formulateSolution();
						found = true;
						return;
					}

					/// BF
					if (QingFun == "EnqueueAtEnd") {
						while (!childNodes.isEmpty()) {
							nodes.add(childNodes.remove(0));

						}

					}

					/// DF
					if (QingFun == "EnqueueAtFront") {
						while (!childNodes.isEmpty()) {
							nodes.add(0, childNodes.remove(0));

						}

					}

					/// ID
					if (QingFun == "EnqueueID-Front") {

						if (n.level == idLevel && nodes.isEmpty()) {
							nodes.add(root);

						}
						if (n.level <= idLevel) {
							while (!childNodes.isEmpty()) {

								nodes.add(0, childNodes.remove(0));

							}
						}
						idLevel++;
					}

					/// UC
					if (QingFun == "EnqueueCost") {

						while (!childNodes.isEmpty()) {
							nodes.add(0, childNodes.remove(0));
						}
						Comparator<Node> nodeComp = Comparator.comparingInt(node -> node.money_spent);
						Collections.sort(nodes, nodeComp);
					}

					/// GR1
					if (QingFun == "EnqueueProsperityGR1") {

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
					if (QingFun == "EnqueueBuildsProsGR2") {
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
					if (QingFun == "EnqueueProsperityAS1") {
						while (!childNodes.isEmpty()) {
							nodes.add(childNodes.remove(0));
						}
						Comparator<Node> nodeComp = Comparator
								.comparingInt(node -> (node.money_spent + (100 - node.prosperity)));
						Collections.sort(nodes, nodeComp);
					}

					/// AS2
					if (QingFun == "EnqueueBuildsProsAS2") {
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

					/// reaching a goal.
				}

			}
			else{
				continue;
			}

			// if (!found)
			// solution = "NOSOLUTION";

			// if (solution.length() == 0) {
			// solution = "NOSOLUTION";
			// }

		}

		
	}

	public void formulateSolution() {
		Node temp = currNode;
		solution = "";
		while (temp.parent != null) {
			solution = temp.operator + "," + solution;
			temp = temp.parent;
		}
		solution = solution.substring(0, solution.length() - 1);
		solution += ";" + currNode.money_spent + ";" + exCount;
	}

	public boolean isRedundant(Node n) {
//		for (int i = 0; i < visited.size(); i++) {
//
//			Node tmp = visited.get(i);
//
//			if (tmp.money_spent == n.money_spent && tmp.operator == n.operator && tmp.prosperity == n.prosperity
//					&& tmp.food == n.food && tmp.energy == n.energy && tmp.materials == n.materials
//					&& tmp.wait == n.wait && tmp.requestType == n.requestType) {
//
//				// visited.add(tmp);
//				return true;
//			}
			// visited.add(tmp);
		if(visited.contains(tony(n))){
            return true;
        
		}
		return false;
	}

	/// expands each node to its corresponding children.
	public void Expand(Node n) {

		if (n.operator == "root") {
			childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build1", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build2", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Wait", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childRF = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestFood", n.wait, n.requestType,
					n.level + 1, n.money_spent, n.heuristic);
			childRE = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestEnergy", n.wait, n.requestType,
					n.level + 1, n.money_spent, n.heuristic);
			childRM = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestMaterial", n.wait, n.requestType,
					n.level + 1, n.money_spent, n.heuristic);
			childNodes.add(childRF);
			childNodes.add(childRM);
			childNodes.add(childRE);
			childNodes.add(childB1);
			childNodes.add(childB2);
		}
		if (n.operator == "requestFood" && !(n.food <= 0) && !(n.energy <= 0) && !(n.materials <= 0)) {
			n.requestFood(amountRequestFood, delayRequestFood);
			n.requestType = 1;
			n.money_spent = n.money_spent + unitPriceEnergy + unitPriceFood + unitPriceMaterials;

			childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build1", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build2", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Wait", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childNodes.add(childB1);
			childNodes.add(childB2);
			childNodes.add(childW);

		}

		if (n.operator == "requestMaterial" && !(n.food <= 0) && !(n.energy <= 0) && !(n.materials <= 0)) {

			n.requestMaterial(amountRequestMaterials, delayRequestMaterials);
			n.requestType = 2;
			n.money_spent = n.money_spent + unitPriceEnergy + unitPriceFood + unitPriceMaterials;
			childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build1", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build2", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Wait", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childNodes.add(childB1);
			childNodes.add(childB2);
			childNodes.add(childW);

		}

		if (n.operator == "requestEnergy" && !(n.food <= 0) && !(n.energy <= 0) && !(n.materials <= 0)) {
			n.requestEnergy(amountRequestEnergy, delayRequestEnergy);
			n.requestType = 3;
			n.money_spent = n.money_spent + unitPriceEnergy + unitPriceFood + unitPriceMaterials;

			childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build1", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build2", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Wait", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childNodes.add(childB1);
			childNodes.add(childB2);
			childNodes.add(childW);
		}

		if (n.operator == "Build1") {

			/// when wait time is over but STILL did NOT change food,material,energy.
			if (n.wait == 0) {
				if (n.requestType != 0) {
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

				/// requesting resources should be listned first then builds
				n.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);

				n.money_spent = n.money_spent + priceBUILD1 + foodUseBUILD1 * unitPriceFood
						+ materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
				/// adds the RF,RE,RM as children in addition to builds.
				childRF = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestFood", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childRE = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestEnergy", n.wait,
						n.requestType, n.level + 1, n.money_spent, n.heuristic);
				childRM = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestMaterial", n.wait,
						n.requestType, n.level + 1, n.money_spent, n.heuristic);
				childNodes.add(childRF);
				childNodes.add(childRM);
				childNodes.add(childRE);

				childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build1", n.wait, n.requestType,
						n.level + 1,
						n.money_spent, n.heuristic);
				childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build2", n.wait, n.requestType,
						n.level + 1,
						n.money_spent, n.heuristic);
				childNodes.add(childB1);
				childNodes.add(childB2);

				/// wait did not reach 0, adds wait in addition to builds
			} else if (n.wait > 0) {

				n.wait--;

				if (n.food < foodUseBUILD1 && n.materials < materialsUseBUILD1 && n.energy < energyUseBUILD1) {
					n.Build1(priceBUILD1, foodUseBUILD1, materialsUseBUILD1, energyUseBUILD1, prosperityBUILD1);

					n.money_spent = n.money_spent + priceBUILD1 + foodUseBUILD1 * unitPriceFood
							+ materialsUseBUILD1 * unitPriceMaterials + energyUseBUILD1 * unitPriceEnergy;
				}
				childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Wait", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childNodes.add(childW);
				childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build1", n.wait, n.requestType,
						n.level + 1,
						n.money_spent, n.heuristic);
				childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build2", n.wait, n.requestType,
						n.level + 1,
						n.money_spent, n.heuristic);
				childNodes.add(childB1);
				childNodes.add(childB2);
			}

			// n.wait--;

			// childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build1",
			// n.wait, n.requestType,
			// n.level + 1,
			// n.money_spent, n.heuristic);
			// childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build2",
			// n.wait, n.requestType,
			// n.level + 1,
			// n.money_spent, n.heuristic);
			// childNodes.add(childB1);
			// childNodes.add(childB2);

			/// builds are added in all
			// childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build1",
			/// n.wait, n.requestType,
			// n.level + 1,
			// n.money_spent, n.heuristic);
			// childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build2",
			/// n.wait, n.requestType,
			// n.level + 1,
			// n.money_spent, n.heuristic);
			// childNodes.add(childB1);
			// childNodes.add(childB2);

		}
		/// same as build1
		if (n.operator == "Build2")

		{
			if (n.wait == 0) {
				if (n.requestType != 0) {
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
				n.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);

				n.money_spent = n.money_spent + priceBUILD2 + foodUseBUILD2 * unitPriceFood
						+ materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
				childRF = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestFood", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childRE = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestEnergy", n.wait,
						n.requestType, n.level + 1, n.money_spent, n.heuristic);
				childRM = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestMaterial", n.wait,
						n.requestType, n.level + 1, n.money_spent, n.heuristic);
				childNodes.add(childRF);
				childNodes.add(childRM);
				childNodes.add(childRE);
				childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build1", n.wait, n.requestType,
						n.level + 1,
						n.money_spent, n.heuristic);
				childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build2", n.wait, n.requestType,
						n.level + 1,
						n.money_spent, n.heuristic);
				childNodes.add(childB1);
				childNodes.add(childB2);
			}

			else {

				n.wait--;
				if (n.food < foodUseBUILD2 && n.materials < materialsUseBUILD2 && n.energy < energyUseBUILD2) {
					n.Build2(priceBUILD2, foodUseBUILD2, materialsUseBUILD2, energyUseBUILD2, prosperityBUILD2);

					n.money_spent = n.money_spent + priceBUILD2 + foodUseBUILD2 * unitPriceFood
							+ materialsUseBUILD2 * unitPriceMaterials + energyUseBUILD2 * unitPriceEnergy;
				}
				childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Wait", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childNodes.add(childW);
				childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build1", n.wait, n.requestType,
						n.level + 1,
						n.money_spent, n.heuristic);
				childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build2", n.wait, n.requestType,
						n.level + 1,
						n.money_spent, n.heuristic);
				childNodes.add(childB1);
				childNodes.add(childB2);

			}

		}

		if (n.operator == "Wait") {
			n.Wait();
			n.money_spent = n.money_spent + unitPriceEnergy + unitPriceFood + unitPriceMaterials;
			n.wait--;

			if (n.wait == 0 && n.requestType != 0) {
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

				// Add children
				childRF = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestFood", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childRE = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestEnergy", n.wait,
						n.requestType, n.level + 1, n.money_spent, n.heuristic);
				childRM = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestMaterial", n.wait,
						n.requestType, n.level + 1, n.money_spent, n.heuristic);

				childNodes.add(childRF);
				childNodes.add(childRM);
				childNodes.add(childRE);

			}

			else if (n.wait > 0 && n.requestType != 0) {

				childW = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Wait", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);

				childNodes.add(childW);

			}

			else if (n.wait == 0 && n.requestType == 0) {

				childRF = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestFood", n.wait, n.requestType,
						n.level + 1, n.money_spent, n.heuristic);
				childRE = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestEnergy", n.wait,
						n.requestType, n.level + 1, n.money_spent, n.heuristic);
				childRM = new Node(n.prosperity, n.food, n.materials, n.energy, n, "requestMaterial", n.wait,
						n.requestType, n.level + 1, n.money_spent, n.heuristic);

				childNodes.add(childRF);
				childNodes.add(childRM);
				childNodes.add(childRE);

			}

			childB1 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build1", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childB2 = new Node(n.prosperity, n.food, n.materials, n.energy, n, "Build2", n.wait, n.requestType,
					n.level + 1,
					n.money_spent, n.heuristic);
			childNodes.add(childB1);
			childNodes.add(childB2);
			
		}
		
		
	
}
	public static String tony(Node n) {
        return n.money_spent + " " + n.operator + " " + n.prosperity
                + " " + n.food + " " + n.energy + " " + n.materials
                + " " + n.wait;
    }
}
