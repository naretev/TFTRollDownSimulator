# TFTRollDownSimulator
This Monte Carlo simulator calculates odds of finding champions in TFT.

For those unfamiliar with Teamfight Tactics and the processes it has, here is how it works.

Teamfight Tactics is an Auto Battler where players buy champions in a shop and then put these champions into play to create a team. Getting more copies of the same champion upgrades it, 3 copies turns into a 2-star champion and 9 total copies turns into a 3-star champion. Because these upgrades are very powerful, players often seek to upgrade their champions. To get as many copies of a single unit as possible, one must refresh their shop over and over until they have found and bought enough copies to upgrade their champion.

There is one issue though, sometimes it is hard to know if the best play is to roll for a champion upgrade or to level up and find champions of a stronger tier. This dilemma is what this tool aims to solve. If you know the probabilities you have for success, it will be easier to make the decision to level up or to roll for an upgrade.

How to calculate the odds of finding a champion. Each tier of champions has their own pool, all tier 3 champions that exist in each game for example, is the number of unique champions in that tier (13), times the number of total copies that exist of each champion (18). Together we get 234. Once a champion is bought however, it is then removed from that pool, slightly changing the odds. So the odds of hitting a certain champion is determined first by your level and the shop odds that level has. Then by the ratio of total champions that are currently in the pool, vs the number of copies of the champion you are looking for currently in the pool. 

So by calculating this over and over in a simulation, a very consistent probability chart is generated.

For more information, visit the website this project is hosted on.