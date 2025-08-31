import random


def next_gen(pop,pop_fit,pop_size):
    '''
    create the next generation based on the current population.
    Repeatedly choose parents, based on fitness, to create a child.
    '''
    
    next_generation = []

    # every set of parents creates 2 children, so do this popsize/2
    for i in range(pop_size//2):

        # choose parents based on fitness, which influences likelihood of selection
        parent1 = pop[monte_carlo_selection(pop_fit)]
        parent2 = pop[monte_carlo_selection(pop_fit)]

        # randomly choose a point to mix up their "dna"
        divide_at = random.randrange(1,len(parent1))
        next_generation.append(parent1[:divide_at]+parent2[divide_at:])
        next_generation.append(parent2[:divide_at]+parent1[divide_at:])

    return next_generation


#These two functions seem to exclusively work together
#Could be imported if it's a problem
def monte_carlo_selection(weights):
    '''
    Modified from claude.ai
    monte carlo selection randomly selects from a list based on how
    much each is "weighted". The more it is weighted, the more likely
    it is to be selected.
    '''

    # First, "normalize" all the weights. If you sum them all up, they total 1.0
    total = sum(weights)
    normalized = [int(w/total*100+.5)/100 for w in weights]
        
    # Generate random number betwen 0 and 1
    r = random.random()
    
    # randomly select based on probability for each item
    # returns the index of the item to be selected
    selection_probability = 0
    for i in range(len(normalized)):
        selection_probability += normalized[i]
        if r <= selection_probability:
            return i
    # in case the weights do not perfectly total 1.0 (due to rounding error)
    return i