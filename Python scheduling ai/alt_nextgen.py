import random

def alt_next_gen(population, fitness_scores, population_size):
    
    next_generation = []

    while len(next_generation) < population_size:
        # Pick two random parents
        parent1 = random.choice(population)
        parent2 = random.choice(population)

        # Randomly split parents' DNA
        split = random.randint(1, len(parent1) - 1)

        # Make two children by swapping parts
        child1 = parent1[:split] + parent2[split:]
        child2 = parent2[:split] + parent1[split:]

        # Chance to mutate each child
        for child in [child1, child2]:
            if random.random() < 0.1:
                index = random.randint(0, len(child) - 1)
                child[index] = random.choice(['A', 'B', 'C', 'D', 'E'])

        next_generation.extend([child1, child2])

    return next_generation[:population_size]