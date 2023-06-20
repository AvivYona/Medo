package logic;

public enum Rating {
	
	OverGrowth,//A move that is worse,gives the enemy an advantage of at least 6 stones.
	teamSuicide,//a move that will cause to team suicide
	Suicide,//A move that results in the player's own soldier being captured.
	regular,//A regular move that doesn't fall under any of the above categories.
	nerbyTeam,//A move that put a stone near team stone.
	nerbyenemy,//A move that put a stone near enemy stone.
	eightFromEat,//A move that with 8 more stones will cause to eat
	sevenFromEat,//A move that with 7 more stones will cause to eat
	sixFromEat,//A move that with 6 more stones will cause to eat
	fiveFromEat,//A move that with 5 more stones will cause to eat
	fourFromEat,//A move that with 4 more stones will cause to eat
	threeFromEat,// threeFromEat:A move that with 3 more stones will cause to eat
	twoFromEat,// twoFromEat:A move that with 2 more stones will cause to eat
	oneFromDefence,// oneFromDefence: A move that with 1 more stones will cause to defence
	oneFromEat,// oneFromEat:A move that with 1 more stones will cause to eat
	defence,// defence:A move that will cause to defence
	defenceTheMost,// defenceTheMost: A move that will cause to defence the most stones
	eat,// eat: A move that captures an opposing soldiers, but not the most that he can eat.
	eatTheMost//A Move that is best, captures the most opposing soldiers.
}
